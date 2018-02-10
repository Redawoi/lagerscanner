package de.hotmann.edgar.wareneingang.Barcode;

import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class BarcodeDataSource {

    private static final String LOG_TAG = BarcodeDataSource.class.getSimpleName();

    private SQLiteDatabase database;
    private BarcodeDbHelper dbHelper;

    private String[] columns = {
            BarcodeDbHelper.COLUMN_ID,
            BarcodeDbHelper.COLUMN_SEASON,
            BarcodeDbHelper.COLUMN_STYLE,
            BarcodeDbHelper.COLUMN_QUALITY,
            BarcodeDbHelper.COLUMN_LGD,
            BarcodeDbHelper.COLUMN_COLOUR,
            BarcodeDbHelper.COLUMN_SIZE,
            BarcodeDbHelper.COLUMN_EANNO,
            BarcodeDbHelper.COLUMN_ITEMNAME,
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
        String season = cursor.getString(idSeason);
        String style = cursor.getString(idStyle);
        String quality = cursor.getString(idQuality);
        String lgd = cursor.getString(idLgd);
        String colour = cursor.getString(idColour);
        String size = cursor.getString(idSize);
        String eanno = cursor.getString(idEanno);
        String itemname = cursor.getString(idItemname);
        long id = cursor.getLong(idIndex);
        return new Barcode(season, style, quality, lgd, colour, size, eanno,itemname,id );
    }

    public String[] getOneBarcode(String eannoparam) {
        Log.d(LOG_TAG, "Kommt raus? Main Activity3");
        String table = "codelist";
        if(CheckforFastDB()) table = "codelist" + eannoparam.substring(eannoparam.length() - 1);
            String[] result;
            Cursor cursor = database.query(table, columns, BarcodeDbHelper.COLUMN_EANNO + "=" + eannoparam, null, null, null, null, null);
                    cursor.moveToFirst();
                    Barcode barcode;
                    barcode = cursorToBarcode(cursor);
                    String ergebnis1 = barcode.getSeason();
                    String ergebnis2 = barcode.getStyle();
                    String ergebnis3 = barcode.getQuality();
                    String ergebnis4 = barcode.getColour();
                    String ergebnis5 = barcode.getSize();
                    String ergebnis6 = barcode.getLgd();
                    String ergebnis7 = barcode.getItemname();
        result= new String[]{ergebnis1, ergebnis2, ergebnis3, ergebnis4, ergebnis5, ergebnis6,ergebnis7};
                    cursor.close();
        return  result;
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

    public void DBOptimieren() {
        String Query = "CREATE TABLE codelist0 ( _id INTEGER PRIMARY KEY AUTOINCREMENT, season TEXT NOT NULL, style TEXT NOT NULL, quality TEXT NOT NULL, lgd TEXT NOT NULL, colour TEXT NOT NULL, size TEXT NOT NULL, eanno TEXT NOT NULL, itemname TEXT NOT NULL, productgroup TEXT )";
        Cursor cursor = database.rawQuery(Query,null);
                cursor.close();
    }

    public BarcodeDataSource(Context context) {
        Log.d(LOG_TAG, "Unsere DataSource erzeugt jetzt den dbHelper.");
        dbHelper = new BarcodeDbHelper(context);
    }

    public void open() {
        Log.d(LOG_TAG, "Eine Referenz auf die Datenbank wird jetzt angefragt.");
        database = dbHelper.getWritableDatabase();
        Log.d(LOG_TAG, "Datenbank-Referenz erhalten. Pfad zur Datenbank: " + database.getPath());
    }

    public void close() {
        dbHelper.close();
        Log.d(LOG_TAG, "Datenbank mit Hilfe des DbHelpers geschlossen.");
    }
}
