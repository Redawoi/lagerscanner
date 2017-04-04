package de.hotmann.edgar.wareneingang.Barcode;

/**
 * Created by edgar on 25.05.2016.
 */

import android.content.Context;
import android.content.ContentValues;

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



    public Barcode createBarcode(String season, String style, String quality, String lgd, String colour, String size, String eanno, String itemname, String table) {
        ContentValues values = new ContentValues();
        values.put(BarcodeDbHelper.COLUMN_SEASON, season);
        values.put(BarcodeDbHelper.COLUMN_STYLE, style);
        values.put(BarcodeDbHelper.COLUMN_QUALITY, quality);
        values.put(BarcodeDbHelper.COLUMN_LGD, lgd);
        values.put(BarcodeDbHelper.COLUMN_COLOUR, colour);
        values.put(BarcodeDbHelper.COLUMN_SIZE, size);
        values.put(BarcodeDbHelper.COLUMN_EANNO, eanno);
        values.put(BarcodeDbHelper.COLUMN_ITEMNAME, itemname);


        long insertId = database.insert(table, null, values);

        Cursor cursor = database.query(table,
                columns, BarcodeDbHelper.COLUMN_ID + "=" + insertId,
                null, null, null, null);
        cursor.moveToFirst();
        Barcode barcode = cursorToBarcode(cursor);
        cursor.close();

        return barcode;
    }

    public Barcode updateBarcode(long id, String newSeason, String newStyle, String newQuality, String newLgd, String newColour, String newSize, String newEanno, String newItemname, String table) {
        ContentValues values = new ContentValues();
        values.put(BarcodeDbHelper.COLUMN_SEASON, newSeason);
        values.put(BarcodeDbHelper.COLUMN_STYLE, newStyle);
        values.put(BarcodeDbHelper.COLUMN_QUALITY, newQuality);
        values.put(BarcodeDbHelper.COLUMN_LGD, newLgd);
        values.put(BarcodeDbHelper.COLUMN_COLOUR, newColour);
        values.put(BarcodeDbHelper.COLUMN_SIZE, newSize);
        values.put(BarcodeDbHelper.COLUMN_EANNO, newEanno);
        values.put(BarcodeDbHelper.COLUMN_ITEMNAME, newItemname);

        database.update(table,
                values,
                BarcodeDbHelper.COLUMN_ID + "=" + id,
                null);

        Cursor cursor = database.query(table,
                columns, BarcodeDbHelper.COLUMN_ID +"=" +id,
                null, null, null, null);

        cursor.moveToFirst();
        Barcode barcode = cursorToBarcode(cursor);
        cursor.close();

        return barcode;
    }

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

        Barcode barcode = new Barcode(season, style, quality, lgd, colour, size, eanno,itemname,id );

        return barcode;
    }

    public String[] getOneBarcode(String eannoparam, String table) {
        Log.d(LOG_TAG, "Kommt raus? Main Activity3");


            String[] result = {null,null,null,null,null,null};
            Cursor cursor = database.query(table, columns, BarcodeDbHelper.COLUMN_EANNO + "=" + eannoparam, null, null, null, null, null);
                    cursor.moveToFirst();
                    Barcode barcode;
                    barcode = cursorToBarcode(cursor);
                    String ergebnis1 = barcode.getSeason();
                    String ergebnis2 = barcode.getStyle();
                    String ergebnis3 = barcode.getQuality();
                    String ergebnis4 = barcode.getColour();
                    String ergebnis5 = barcode.getSize();
                    String ergebnis6 = barcode.getItemname();
                    String[] title = {ergebnis1, ergebnis2, ergebnis3, ergebnis4, ergebnis5, ergebnis6};
                    result=title;
                    cursor.close();
        //Cursor cursor = database.query(BarcodeDbHelper.WARENEINGANG_TABLENAME, columns, null, null, null, null, null, null);
        return  result;
    }

    public boolean CheckIsBarcodeInDBorNot(String fieldValue, String table) {

        String Query = "Select * from " + table + " where " + table + " = " + fieldValue;
        Cursor cursor = database.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
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

    public void deleteBarcode(Barcode barcode, String table) {
        long id = barcode.getId();

        database.delete(table,
                BarcodeDbHelper.COLUMN_ID + "=" +id,
                null);
        Log.d(LOG_TAG, "Eintrag gelöscht! ID: " + id + " Inhalt: " + barcode.toString());
    }


}
