package de.hotmann.edgar.wareneingang2.Barcode;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import java.util.ArrayList;

public class BarcodeDataSource {

    private static final String LOG_TAG = BarcodeDataSource.class.getSimpleName();

    private SQLiteDatabase database;
    private BarcodeDbHelper dbHelper;

    private String[] allcolumns = {
            BarcodeDbHelper.COLUMN_ID,
            BarcodeDbHelper.COLUMN_SEASON,
            BarcodeDbHelper.COLUMN_STYLE,
            BarcodeDbHelper.COLUMN_QUALITY,
            BarcodeDbHelper.COLUMN_LGD,
            BarcodeDbHelper.COLUMN_COLOUR,
            BarcodeDbHelper.COLUMN_SIZE,
            BarcodeDbHelper.COLUMN_EANNO,
            BarcodeDbHelper.COLUMN_ITEMNAME,
            BarcodeDbHelper.COLUMN_PRODGROUP,
    };

    private Barcode cursorToBarcode(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(BarcodeDbHelper.COLUMN_ID);
        int idSeason = cursor.getColumnIndex(BarcodeDbHelper.COLUMN_SEASON);
        int idStyle = cursor.getColumnIndex(BarcodeDbHelper.COLUMN_STYLE);
        int idQuality = cursor.getColumnIndex(BarcodeDbHelper.COLUMN_QUALITY);
        int idLgd = cursor.getColumnIndex(BarcodeDbHelper.COLUMN_LGD);
        int idColour = cursor.getColumnIndex(BarcodeDbHelper.COLUMN_COLOUR);
        int idSize = cursor.getColumnIndex(BarcodeDbHelper.COLUMN_SIZE);
        int idEanno = cursor.getColumnIndex(BarcodeDbHelper.COLUMN_EANNO);
        int idItemname = cursor.getColumnIndex(BarcodeDbHelper.COLUMN_ITEMNAME);
        int idProductgroup = cursor.getColumnIndex(BarcodeDbHelper.COLUMN_PRODGROUP);
        String season = cursor.getString(idSeason);
        String style = cursor.getString(idStyle);
        String quality = cursor.getString(idQuality);
        String lgd = cursor.getString(idLgd);
        String colour = cursor.getString(idColour);
        String size = cursor.getString(idSize);
        String eanno = cursor.getString(idEanno);
        String itemname = cursor.getString(idItemname);
        String productgroup = cursor.getString(idProductgroup);
        long id = cursor.getLong(idIndex);
        return new Barcode(season, style, quality, lgd, colour, size, eanno,itemname,id, productgroup);
    }

    public String[] getOneBarcode(String eannoparam) {
        String table = "codelist", ergebnis1 ="", ergebnis2="", ergebnis3="",ergebnis4="",ergebnis5="",ergebnis6="",ergebnis7="";
        if(CheckforFastDB()) {
            table = "codelist" + eannoparam.substring(eannoparam.length() - 1);
        }
        open();
        database.beginTransaction();
        if(CheckIsBarcodeInDBorNot(eannoparam)) {
            Cursor cursor = database.query(table, allcolumns, BarcodeDbHelper.COLUMN_EANNO + "='" + eannoparam + "'", null, null, null, null, null);
            cursor.moveToNext();
            Barcode barcode;
            barcode = cursorToBarcode(cursor);
            ergebnis1 = barcode.getSeason();
            ergebnis2 = barcode.getStyle();
            ergebnis3 = barcode.getQuality();
            ergebnis4 = barcode.getColour();
            ergebnis5 = barcode.getSize();
            ergebnis6 = barcode.getLgd();
            ergebnis7 = barcode.getItemname();
            cursor.close();
        }
        database.setTransactionSuccessful();
        database.endTransaction();
        close();
        return new String[]{ergebnis1, ergebnis2, ergebnis3, ergebnis4, ergebnis5, ergebnis6,ergebnis7};
    }

    public void optimiereDB() {
        int subtable = 0;
        open();
        database.beginTransaction();
        try {
            while (subtable<10) {
                ArrayList<Barcode> barcodeArray = new ArrayList<>();
                Barcode barcode;
                Cursor cursor = database.query(BarcodeDbHelper.WARENEINGANG_TABLENAME, allcolumns, "substr(eanno, -1) = '" + subtable +"'", null, null ,null, null);
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    barcode = cursorToBarcode(cursor);
                    barcodeArray.add(barcode);
                    cursor.moveToNext();
                }
                SQLiteStatement insert =
                        database.compileStatement("insert into "
                                + BarcodeDbHelper.WARENEINGANG_TABLENAME + subtable
                                + "(" + BarcodeDbHelper.COLUMN_ID
                                + "," + BarcodeDbHelper.COLUMN_SEASON
                                + "," + BarcodeDbHelper.COLUMN_STYLE
                                + "," + BarcodeDbHelper.COLUMN_QUALITY
                                + "," + BarcodeDbHelper.COLUMN_LGD
                                + "," + BarcodeDbHelper.COLUMN_COLOUR
                                + "," + BarcodeDbHelper.COLUMN_SIZE
                                + "," + BarcodeDbHelper.COLUMN_EANNO
                                + "," + BarcodeDbHelper.COLUMN_ITEMNAME
                                + "," + BarcodeDbHelper.COLUMN_PRODGROUP
                                + ")"
                                +" values " + "(?,?,?,?,?,?,?,?,?,?)");
                for (Barcode value : barcodeArray){
                    //bind the 1-indexed ?'s to the values specified

                    System.out.println(value.getId() + " " + value.getEanno());

                    insert.bindNull(1);
                    insert.bindString(2, value.getSeason());
                    insert.bindString(3, value.getStyle());
                    insert.bindString(4, value.getQuality());
                    insert.bindString(5, value.getLgd());
                    insert.bindString(6, value.getColour());
                    insert.bindString(7, value.getSize());
                    insert.bindString(8, value.getEanno());
                    insert.bindString(9, value.getItemname());
                    insert.bindString(10, value.getProductgroup());
                    insert.execute();
                }
                subtable++;
            }
            database.execSQL("DROP TABLE IF EXISTS codelist");
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();

        }
        Log.d(LOG_TAG, "[Insgesamt]: " + subtable + ". Reihen");
    }



    public boolean CheckforFastDB() {
        String Query = "SELECT name FROM sqlite_master WHERE type='table' AND name='codelist0' OR name= 'codelist1' OR name= 'codelist2' OR name= 'codelist3' OR name= 'codelist4' OR name= 'codelist5' OR name= 'codelist6' OR name= 'codelist7' OR name= 'codelist8' OR name= 'codelist9';";
        Cursor cursor = database.rawQuery(Query, null);
        if(cursor.getCount() == 10) {
            cursor.close();
            return true;
        }
        return false;
    }

    public boolean CheckIsBarcodeInDBorNot(String fieldValue) {
        String table = "codelist";
        if(CheckforFastDB()) table = "codelist" + fieldValue.substring(fieldValue.length() - 1);
        String Query = "Select * from "+ table + " where eanno = " + fieldValue;
        Cursor cursor = database.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public void CreateSubtables() {
        Log.d(LOG_TAG, "Subtables  Start");
        database = dbHelper.getWritableDatabase();
        int i=0;
        while (i<=9) {
            String Query1 = "DROP TABLE IF EXISTS codelist" + i;
            Log.d(LOG_TAG, "Subtables  Ende" + "DROP TABLE IF EXISTS codelist" + i);
            database.execSQL(Query1);
            Log.d(LOG_TAG, "Subtables  Ende" + "CREATE TABLE codelist" + i);
            String Query2 = "CREATE TABLE codelist" + i + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, season TEXT NOT NULL, style TEXT NOT NULL, quality TEXT NOT NULL, lgd TEXT NOT NULL, colour TEXT NOT NULL, size TEXT NOT NULL, eanno TEXT NOT NULL, itemname TEXT NOT NULL, productgroup TEXT )";
            database.execSQL(Query2);
            i++;
        }
        Log.d(LOG_TAG, "Subtables  Ende");
    }

    public BarcodeDataSource(Context context) {
        dbHelper = new BarcodeDbHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }
}
