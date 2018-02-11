package de.hotmann.edgar.wareneingang.Barcode;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import de.hotmann.edgar.wareneingang.Eingang.EingangDbHelper;
import de.hotmann.edgar.wareneingang.MainstartActivity;
import de.hotmann.edgar.wareneingang.R;

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
    };

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

    private String[] maxid = {
            BarcodeDbHelper.COLUMN_MAXID,

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
        Barcode barcode = new Barcode(season, style, quality, lgd, colour, size, eanno,itemname,id, productgroup);
        return barcode;
    }

    public String[] getOneBarcode(String eannoparam) {
        String table = "codelist";
        open();
        if(CheckforFastDB()) table = "codelist" + eannoparam.substring(eannoparam.length() - 1);
            String[] result;
            Cursor cursor = database.query(table, allcolumns, BarcodeDbHelper.COLUMN_EANNO + "=" + eannoparam, null, null, null, null, null);
            cursor.moveToNext();;
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

    public void getOneLine(int id) {
        //Log.d(LOG_TAG, "GetOneLine: Start");
        /*String tablealt = "codelist";
        Cursor cursor2 = database.query(tablealt, allcolumns, BarcodeDbHelper.COLUMN_ID + "=" + id, null, null, null, null, null);
        cursor2.moveToNext();
        Barcode barcodealt;
        barcodealt = cursorToBarcode(cursor2);
        transferiereEineLinie(barcodealt.getSeason(),
                barcodealt.getStyle(),
                barcodealt.getQuality(),
                barcodealt.getLgd(),
                barcodealt.getColour(),
                barcodealt.getSize(),
                barcodealt.getEanno(),
                barcodealt.getItemname(),
                barcodealt.getProductgroup());*/


    }

    public void transferiereEineLinie(int id) {
        open();
        String tablealt = "codelist";
        Cursor cursor2 = database.query(tablealt, allcolumns, BarcodeDbHelper.COLUMN_ID + "=" + id, null, null, null, null, null);
        cursor2.moveToNext();
        Barcode barcodealt;
        barcodealt = cursorToBarcode(cursor2);
        ContentValues values = new ContentValues();
        values.put(BarcodeDbHelper.COLUMN_SEASON, barcodealt.getSeason());
        values.put(BarcodeDbHelper.COLUMN_STYLE, barcodealt.getStyle());
        values.put(BarcodeDbHelper.COLUMN_QUALITY, barcodealt.getQuality());
        values.put(BarcodeDbHelper.COLUMN_LGD, barcodealt.getLgd());
        values.put(BarcodeDbHelper.COLUMN_COLOUR, barcodealt.getColour());
        values.put(BarcodeDbHelper.COLUMN_SIZE, barcodealt.getSize());
        values.put(BarcodeDbHelper.COLUMN_EANNO, barcodealt.getEanno());
        values.put(BarcodeDbHelper.COLUMN_ITEMNAME, "'"+barcodealt.getItemname()+"'");
        values.put(BarcodeDbHelper.COLUMN_PRODGROUP, barcodealt.getProductgroup());
        String tableneu = "codelist" + barcodealt.getEanno().substring(barcodealt.getEanno().length() - 1);
        long insertId = database.insert(tableneu,null,values);
        //Log.d(LOG_TAG, "[put to insertid]" + insertId);
        Cursor cursor = database.query(tableneu, allcolumns, BarcodeDbHelper.COLUMN_ID + "=" + insertId, null,null,null,null);
        cursor.moveToNext();
        Barcode barcode = cursorToBarcode(cursor);
        cursor.close();
        close();
    }


    private BarcodeMaxId cursorToBarcodeMaxId(Cursor cursor){
        int idIndex = cursor.getColumnIndex(BarcodeDbHelper.COLUMN_MAXID);
        int id= cursor.getInt(idIndex);
        BarcodeMaxId maxcode = new BarcodeMaxId(id);
        return maxcode;
    }

    public int getmaxid() {
        Cursor cursor = database.query("codelist", maxid,null,  null, null, null, null, null);
        cursor.moveToFirst();
        BarcodeMaxId eingang = cursorToBarcodeMaxId(cursor);
        int ergebnis = eingang.getMaxId();
        cursor.close();
        return ergebnis;
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

    public  void DBOptimieren(int p) {
        getOneLine(p);
    }

    public void CreateSubtables() {
        database = dbHelper.getWritableDatabase();
        int i=0;
        while (i<=9) {
            String Query1 = "DROP TABLE IF EXISTS codelist" + i;
            database.execSQL(Query1);
            String Query2 = "CREATE TABLE codelist" + i + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, season TEXT NOT NULL, style TEXT NOT NULL, quality TEXT NOT NULL, lgd TEXT NOT NULL, colour TEXT NOT NULL, size TEXT NOT NULL, eanno TEXT NOT NULL, itemname TEXT NOT NULL, productgroup TEXT )";
            database.execSQL(Query2);
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
