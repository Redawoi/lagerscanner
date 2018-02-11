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
            BarcodeDbHelper.COLUMN_ITEMNAME,
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
        return new Barcode(season, style, quality, lgd, colour, size, eanno,itemname,id, productgroup);
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

    public String[] getOneLine(int id) {
        Log.d(LOG_TAG, "GetOneLine: Start");
        String table = "codelist";
        String[] result;
        Cursor cursor = database.query(table, allcolumns, BarcodeDbHelper.COLUMN_ID + "=" + id, null, null, null, null, null);
        cursor.moveToFirst();
        Barcode barcode;
        barcode = cursorToBarcode(cursor);
        String ergebnis1 = barcode.getSeason();
        String ergebnis2 = barcode.getStyle();
        String ergebnis3 = barcode.getQuality();
        String ergebnis4 = barcode.getLgd();
        String ergebnis5 = barcode.getColour();
        String ergebnis6 = barcode.getSize();
        String ergebnis7 = barcode.getEanno();
        String ergebnis8 = barcode.getItemname();
        String ergebnis9 = barcode.getProductgroup();
        result= new String[]{ergebnis1, ergebnis2, ergebnis3, ergebnis4, ergebnis5, ergebnis6,ergebnis7, ergebnis8, ergebnis9};
        cursor.close();
        return  result;
    }

    public Barcode transferiereEineLinie(String season, String style, String quality, String lgd, String colour, String size, String eanno, String itemname, String productgroup) {
        open();
        ContentValues values = new ContentValues();
        values.put(BarcodeDbHelper.COLUMN_SEASON, season);
        Log.d(LOG_TAG, "[put]" + season);
        values.put(BarcodeDbHelper.COLUMN_STYLE, style);
        Log.d(LOG_TAG, "[put]" + style);
        values.put(BarcodeDbHelper.COLUMN_QUALITY, quality);
        Log.d(LOG_TAG, "[put]" + quality);
        values.put(BarcodeDbHelper.COLUMN_LGD, lgd);
        Log.d(LOG_TAG, "[put]" + lgd);
        values.put(BarcodeDbHelper.COLUMN_COLOUR, colour);
        Log.d(LOG_TAG, "[put]" + colour);
        values.put(BarcodeDbHelper.COLUMN_SIZE, size);
        Log.d(LOG_TAG, "[put]" + size);
        values.put(BarcodeDbHelper.COLUMN_EANNO, eanno);
        Log.d(LOG_TAG, "[put]" + eanno);
        values.put(BarcodeDbHelper.COLUMN_ITEMNAME, itemname);
        Log.d(LOG_TAG, "[put]" + itemname);
        values.put(BarcodeDbHelper.COLUMN_PRODGROUP, productgroup);
        Log.d(LOG_TAG, "[put]" + productgroup);

        String table = "codelist" + eanno.substring(eanno.length() - 1);
        Log.d(LOG_TAG, "[put to] " + table);
        long insertId = database.insert(table,null,values);
        Log.d(LOG_TAG, "[put to insertid]" + insertId);
        Cursor cursor = database.query(table,allcolumns, EingangDbHelper.COLUMN_ID + "=" + insertId, null,null,null,null);
        cursor.moveToFirst();
        Barcode barcode = cursorToBarcode(cursor);
        cursor.close();
        return barcode;
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
        Log.d(LOG_TAG, "int i = " + p);
        String[] params = getOneLine(p);
        Log.d(LOG_TAG, "[0]" + params[0]);
        Log.d(LOG_TAG, "[1]" + params[1]);
        Log.d(LOG_TAG, "[2]" + params[2]);
        Log.d(LOG_TAG, "[3]" + params[3]);
        Log.d(LOG_TAG, "[4]" + params[4]);
        Log.d(LOG_TAG, "[5]" + params[5]);
        Log.d(LOG_TAG, "[6]" + params[6]);
        Log.d(LOG_TAG, "[7]" + params[7]);
        Log.d(LOG_TAG, "[8]" + params[8]);
        transferiereEineLinie(params[0],params[1],params[2],params[3],params[4],params[5],params[6],params[7],params[8]);
    }

    public void CreateSubtables() {
        database = dbHelper.getWritableDatabase();
        Log.d(LOG_TAG, "Eine Referenz auf die Datenbank wird jetzt angefragt.");
        int i=0;
        Log.d(LOG_TAG, "Subtables  Start");
        Log.d(LOG_TAG, "I= " +i);
        while (i<=9) {
            Log.d(LOG_TAG, "Schleife " + i + " Start");
            String Query1 = "DROP TABLE IF EXISTS codelist" + i;
            database.execSQL(Query1);
            Log.d(LOG_TAG, "Schleife " + i + " Drop");
            String Query2 = "CREATE TABLE codelist" + i + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, season TEXT NOT NULL, style TEXT NOT NULL, quality TEXT NOT NULL, lgd TEXT NOT NULL, colour TEXT NOT NULL, size TEXT NOT NULL, eanno TEXT NOT NULL, itemname TEXT NOT NULL, productgroup TEXT )";
            database.execSQL(Query2);
            Log.d(LOG_TAG, "Schleife " + i + " Create");
            Log.d(LOG_TAG, "Schleife " + i + " Ende");
            i++;
        }
        Log.d(LOG_TAG, "Subtables  Ende");
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
