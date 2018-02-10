package de.hotmann.edgar.wareneingang.Eingang;

/**
 * Created by edgar on 25.05.2016.
 */

import android.content.Context;
import android.content.ContentValues;
import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import de.hotmann.edgar.wareneingang.Barcode.BarcodeDbHelper;

public class EingangDataSource {

    private static final String LOG_TAG = EingangDataSource.class.getSimpleName();

    private SQLiteDatabase database;
    private EingangDbHelper dbHelper;
    private String[] columns = {
            EingangDbHelper.COLUMN_PALETTE,
            EingangDbHelper.COLUMN_ID,
            EingangDbHelper.COLUMN_SEASON,
            EingangDbHelper.COLUMN_STYLE,
            EingangDbHelper.COLUMN_QUALITY,
            EingangDbHelper.COLUMN_COLOUR,
            EingangDbHelper.COLUMN_SIZE,
            EingangDbHelper.COLUMN_LGD,
            EingangDbHelper.COLUMN_QUANTITY,
            EingangDbHelper.COLUMN_QUANTSUM,
            EingangDbHelper.COLUMN_DAY,
            EingangDbHelper.COLUMN_MONTH,
            EingangDbHelper.COLUMN_YEAR,
            EingangDbHelper.COLUMN_WEEK,
            EingangDbHelper.COLUMN_SECONDARYCHOICE,
            EingangDbHelper.COLUMN_COUNTTOPALLET,
    };
    private String[] columnsSumKartons = {
            EingangDbHelper.COLUMN_PALETTE,
            EingangDbHelper.COLUMN_ID,
            EingangDbHelper.COLUMN_SEASON,
            EingangDbHelper.COLUMN_STYLE,
            EingangDbHelper.COLUMN_QUALITY,
            EingangDbHelper.COLUMN_COLOUR,
            EingangDbHelper.COLUMN_SIZE,
            EingangDbHelper.COLUMN_LGD,
            EingangDbHelper.COLUMN_QUANTITY,
            EingangDbHelper.COLUMN_QUANTSUM,
            EingangDbHelper.COLUMN_DAY,
            EingangDbHelper.COLUMN_MONTH,
            EingangDbHelper.COLUMN_YEAR,
            EingangDbHelper.COLUMN_WEEK,
            EingangDbHelper.COLUMN_SECONDARYCHOICE,
            EingangDbHelper.COLUMN_COUNTTOPALLET,
            EingangDbHelper.COLUMN_KARTSUM,
    };
    private String[] columnswosum = {
            EingangDbHelper.COLUMN_PALETTE,
            EingangDbHelper.COLUMN_ID,
            EingangDbHelper.COLUMN_SEASON,
            EingangDbHelper.COLUMN_STYLE,
            EingangDbHelper.COLUMN_QUALITY,
            EingangDbHelper.COLUMN_COLOUR,
            EingangDbHelper.COLUMN_SIZE,
            EingangDbHelper.COLUMN_LGD,
            EingangDbHelper.COLUMN_QUANTITY,
            EingangDbHelper.COLUMN_DAY,
            EingangDbHelper.COLUMN_MONTH,
            EingangDbHelper.COLUMN_YEAR,
            EingangDbHelper.COLUMN_WEEK,
            EingangDbHelper.COLUMN_SECONDARYCHOICE,
            EingangDbHelper.COLUMN_COUNTTOPALLET,
    };
    private String[] columns2 = {
            BarcodeDbHelper.COLUMN_ID,
            BarcodeDbHelper.COLUMN_SEASON,
            BarcodeDbHelper.COLUMN_STYLE,
            BarcodeDbHelper.COLUMN_QUALITY,
            BarcodeDbHelper.COLUMN_LGD,
            BarcodeDbHelper.COLUMN_COLOUR,
            BarcodeDbHelper.COLUMN_SIZE,
            BarcodeDbHelper.COLUMN_EANNO,
            BarcodeDbHelper.COLUMN_ITEMNAME,
            EingangDbHelper.COLUMN_DAY,
            EingangDbHelper.COLUMN_MONTH,
            EingangDbHelper.COLUMN_YEAR,
            EingangDbHelper.COLUMN_WEEK,
    };

    public  Eingang createEingang(int palette, String season, String style, String quality, String colour,String size,String lgd, boolean secondarychoice, boolean countwith, int quantity, int day, int month, int year, int week) {
        open();
        ContentValues values = new ContentValues();
        values.put(EingangDbHelper.COLUMN_PALETTE, palette);
        values.put(EingangDbHelper.COLUMN_SEASON, season);
        values.put(EingangDbHelper.COLUMN_STYLE, style);
        values.put(EingangDbHelper.COLUMN_QUALITY, quality);
        values.put(EingangDbHelper.COLUMN_COLOUR, colour);
        values.put(EingangDbHelper.COLUMN_SIZE, size);
        values.put(EingangDbHelper.COLUMN_LGD, lgd);
        values.put(EingangDbHelper.COLUMN_SECONDARYCHOICE, secondarychoice);
        values.put(EingangDbHelper.COLUMN_COUNTTOPALLET, countwith);
        values.put(EingangDbHelper.COLUMN_QUANTITY, quantity);
        values.put(EingangDbHelper.COLUMN_DAY, day);
        values.put(EingangDbHelper.COLUMN_MONTH, month);
        values.put(EingangDbHelper.COLUMN_YEAR, year);
        values.put(EingangDbHelper.COLUMN_WEEK, week);



        long insertId = database.insert(EingangDbHelper.WARENEINGANG_TABLENAME, null, values);

        Cursor cursor = database.query(EingangDbHelper.WARENEINGANG_TABLENAME,
                columns, EingangDbHelper.COLUMN_ID + "=" + insertId,
                null, null, null, null);
        cursor.moveToFirst();
        Eingang eingang = cursorToEingang(cursor);
        cursor.close();

        return eingang;
    }

    public Eingangwosum updateEingang(long id, int newPalette, String newSeason, String newStyle, String newQuality, String newColour, String newSize, String newLgd, boolean newSecondarychoice, boolean newCountwith, int newQuantity) {
        ContentValues values = new ContentValues();
        values.put(EingangDbHelper.COLUMN_PALETTE, newPalette);
        values.put(EingangDbHelper.COLUMN_SEASON, newSeason);
        values.put(EingangDbHelper.COLUMN_STYLE, newStyle);
        values.put(EingangDbHelper.COLUMN_QUALITY, newQuality);
        values.put(EingangDbHelper.COLUMN_COLOUR, newColour);
        values.put(EingangDbHelper.COLUMN_SIZE, newSize);
        values.put(EingangDbHelper.COLUMN_LGD, newLgd);
        values.put(EingangDbHelper.COLUMN_SECONDARYCHOICE, newSecondarychoice);
        values.put(EingangDbHelper.COLUMN_COUNTTOPALLET, newCountwith);
        values.put(EingangDbHelper.COLUMN_QUANTITY, newQuantity);

        database.update(EingangDbHelper.WARENEINGANG_TABLENAME,
                values,
                EingangDbHelper.COLUMN_ID + "=" + id,
                null);

        Cursor cursor = database.query(EingangDbHelper.WARENEINGANG_TABLENAME,
                columns, EingangDbHelper.COLUMN_ID +"=" +id,
                null, null, null, null);

        cursor.moveToFirst();
        Eingangwosum eingang = cursorToEingangwosum(cursor);
        cursor.close();

        return eingang;
    }

    private EingangSumKartons cursorToEingangSumKartons(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(EingangDbHelper.COLUMN_ID);
        int idPalette = cursor.getColumnIndex(EingangDbHelper.COLUMN_PALETTE);
        int idCountwith = cursor.getColumnIndex(EingangDbHelper.COLUMN_COUNTTOPALLET);
        int idKartonanzahl = cursor.getColumnIndex(EingangDbHelper.COLUMN_KARTSUM);

        int palette = cursor.getInt(idPalette);
        boolean countwith = Boolean.parseBoolean(cursor.getString(idCountwith));
        int kartonanzahl = cursor.getInt(idKartonanzahl);
        long id = cursor.getLong(idIndex);

        EingangSumKartons eingang = new EingangSumKartons(palette, countwith, kartonanzahl, id);

        return eingang;
    }

    private Eingang cursorToEingang(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(EingangDbHelper.COLUMN_ID);
        int idPalette = cursor.getColumnIndex(EingangDbHelper.COLUMN_PALETTE);
        int idSeason = cursor.getColumnIndex(EingangDbHelper.COLUMN_SEASON);
        int idStyle = cursor.getColumnIndex(EingangDbHelper.COLUMN_STYLE);
        int idQuality = cursor.getColumnIndex(EingangDbHelper.COLUMN_QUALITY);
        int idColour = cursor.getColumnIndex(EingangDbHelper.COLUMN_COLOUR);
        int idSize = cursor.getColumnIndex(EingangDbHelper.COLUMN_SIZE);
        int idLgd = cursor.getColumnIndex(EingangDbHelper.COLUMN_LGD);
        int idQuantity = cursor.getColumnIndex(EingangDbHelper.COLUMN_QUANTITY);
        int idDay = cursor.getColumnIndex(EingangDbHelper.COLUMN_DAY);
        int idMonth = cursor.getColumnIndex(EingangDbHelper.COLUMN_MONTH);
        int idYear = cursor.getColumnIndex(EingangDbHelper.COLUMN_YEAR);
        int idWeek = cursor.getColumnIndex(EingangDbHelper.COLUMN_WEEK);
        int idSecondarychoice = cursor.getColumnIndex(EingangDbHelper.COLUMN_SECONDARYCHOICE);
        int idCountwith = cursor.getColumnIndex(EingangDbHelper.COLUMN_COUNTTOPALLET);
        int idQuantSum = cursor.getColumnIndex(EingangDbHelper.COLUMN_QUANTSUM);

        int palette = cursor.getInt(idPalette);
        String season = cursor.getString(idSeason);
        String style = cursor.getString(idStyle);
        String quality = cursor.getString(idQuality);
        String colour = cursor.getString(idColour);
        String size = cursor.getString(idSize);
        String lgd = cursor.getString(idLgd);
        boolean secondarychoice = Boolean.parseBoolean(cursor.getString(idSecondarychoice));
        boolean countwith = Boolean.parseBoolean(cursor.getString(idCountwith));
        int day = cursor.getInt(idDay);
        int month = cursor.getInt(idMonth);
        int year = cursor.getInt(idYear);
        int week = cursor.getInt(idWeek);
        int quantity = cursor.getInt(idQuantity);
        int quantsum = cursor.getInt(idQuantSum);
        long id = cursor.getLong(idIndex);

        Eingang eingang = new Eingang(palette, season, style, quality, colour, size,lgd, quantity, quantsum, secondarychoice,countwith, day, month, year, week, id);

        return eingang;
    }


    private Eingangwosum cursorToEingangwosum(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(EingangDbHelper.COLUMN_ID);
        int idPalette = cursor.getColumnIndex(EingangDbHelper.COLUMN_PALETTE);
        int idSeason = cursor.getColumnIndex(EingangDbHelper.COLUMN_SEASON);
        int idStyle = cursor.getColumnIndex(EingangDbHelper.COLUMN_STYLE);
        int idQuality = cursor.getColumnIndex(EingangDbHelper.COLUMN_QUALITY);
        int idColour = cursor.getColumnIndex(EingangDbHelper.COLUMN_COLOUR);
        int idSize = cursor.getColumnIndex(EingangDbHelper.COLUMN_SIZE);
        int idLgd =cursor.getColumnIndex(EingangDbHelper.COLUMN_LGD);
        int idDay = cursor.getColumnIndex(EingangDbHelper.COLUMN_DAY);
        int idMonth = cursor.getColumnIndex(EingangDbHelper.COLUMN_MONTH);
        int idYear = cursor.getColumnIndex(EingangDbHelper.COLUMN_YEAR);
        int idWeek = cursor.getColumnIndex(EingangDbHelper.COLUMN_WEEK);
        int idSecondary = cursor.getColumnIndex(EingangDbHelper.COLUMN_SECONDARYCHOICE);
        int idCountwith = cursor.getColumnIndex(EingangDbHelper.COLUMN_COUNTTOPALLET);
        int idQuantity = cursor.getColumnIndex(EingangDbHelper.COLUMN_QUANTITY);

        int palette = cursor.getInt(idPalette);
        String season = cursor.getString(idSeason);
        String style = cursor.getString(idStyle);
        String quality = cursor.getString(idQuality);
        String colour = cursor.getString(idColour);
        String size = cursor.getString(idSize);
        String lgd = cursor.getString(idLgd);
        int quantity = cursor.getInt(idQuantity);
        boolean secondary = cursor.getInt(idSecondary) == 1;
        boolean countwith = cursor.getInt(idCountwith) == 1;
        int day = cursor.getInt(idDay);
        int month = cursor.getInt(idMonth);
        int year = cursor.getInt(idYear);
        int week = cursor.getInt(idWeek);
        long id = cursor.getLong(idIndex);

        return new Eingangwosum(palette, season, style, quality, colour, size,lgd, quantity, secondary,countwith,day,month,year, id);
    }

    public List<Eingangwosum> getAllEingaengewoSum(String palette) {
        List<Eingangwosum> eingangList = new ArrayList<>();

        Cursor cursor = database.query(EingangDbHelper.WARENEINGANG_TABLENAME, columnswosum, null, null, null ,null, null);

        cursor.moveToFirst();
        Eingangwosum eingang;

        while (!cursor.isAfterLast()) {
            eingang = cursorToEingangwosum(cursor);
            int PalleteInt = Integer.parseInt(palette);
            if (eingang.getPalette()==PalleteInt) {
                eingangList.add(eingang);
                Log.d(LOG_TAG, "ID: " + eingang.getId() + ", Inhalt: " +eingang.toString());
            }
            cursor.moveToNext();
        }

        cursor.close();

        return eingangList;
    }

    public List<String> getAllSeasons() {
        List<String> eingangList = new ArrayList<>();
        open();
        Cursor cursor = database.query(EingangDbHelper.WARENEINGANG_TABLENAME, columnswosum, null, null, "season",null, null);

        cursor.moveToFirst();
        Eingangwosum eingang;

        while (!cursor.isAfterLast()) {
            eingang = cursorToEingangwosum(cursor);
            String style = eingang.getSeason();
            eingangList.add(style);
            Log.d(LOG_TAG, "ID: " + eingang.getId() + ", Inhalt: " +eingang.toString());
            cursor.moveToNext();
        }


        cursor.close();

        return eingangList;
    }

    public List<String> getAllStyles() {
        List<String> eingangList = new ArrayList<>();
        open();
        Cursor cursor = database.query(EingangDbHelper.WARENEINGANG_TABLENAME, columnswosum, null, null, "style",null, null);

        cursor.moveToFirst();
        Eingangwosum eingang;

        while (!cursor.isAfterLast()) {
            eingang = cursorToEingangwosum(cursor);
            String style = eingang.getStyle();
            eingangList.add(style);
            Log.d(LOG_TAG, "ID: " + eingang.getId() + ", Inhalt: " +eingang.toString());
            cursor.moveToNext();
        }

        cursor.close();

        return eingangList;
    }

    public List<String> getAllQualities() {
        List<String> eingangList = new ArrayList<>();
        open();
        Cursor cursor = database.query(EingangDbHelper.WARENEINGANG_TABLENAME, columnswosum, null, null, "quality",null, null);

        cursor.moveToFirst();
        Eingangwosum eingang;

        while (!cursor.isAfterLast()) {
            eingang = cursorToEingangwosum(cursor);
            String style = eingang.getQuality();
            eingangList.add(style);
            Log.d(LOG_TAG, "ID: " + eingang.getId() + ", Inhalt: " +eingang.toString());
            cursor.moveToNext();
        }

        cursor.close();


        return eingangList;
    }

    public List<String> getAllColours() {
        List<String> eingangList = new ArrayList<>();
        open();
        Cursor cursor = database.query(EingangDbHelper.WARENEINGANG_TABLENAME, columnswosum, null, null, "colour",null, null);

        cursor.moveToFirst();
        Eingangwosum eingang;

        while (!cursor.isAfterLast()) {
            eingang = cursorToEingangwosum(cursor);
            String style = eingang.getColour();
            eingangList.add(style);
            Log.d(LOG_TAG, "ID: " + eingang.getId() + ", Inhalt: " +eingang.toString());
            cursor.moveToNext();
        }

        cursor.close();

        return eingangList;
    }

    public String getKartonAnzahlOnPalette(String palette) {
        Cursor cursor = database.query(EingangDbHelper.WARENEINGANG_TABLENAME,columnsSumKartons,EingangDbHelper.COLUMN_PALETTE + "=" + palette,  null, null, null, null, null);
        cursor.moveToFirst();
        EingangSumKartons eingang;
        eingang = cursorToEingangSumKartons(cursor);
        String ergebnis = eingang.toString();

        cursor.close();

        return ergebnis;
    }


    public String getEinEingang(String season, String style, String quality,  String colour, String size, boolean zweitwahl) {
            Log.d(LOG_TAG, "Kommt raus? Main Activity3");

            String zweitwahlString = (zweitwahl)?"1":"0";
            Cursor cursor = database.query(EingangDbHelper.WARENEINGANG_TABLENAME, columns,
                    EingangDbHelper.COLUMN_SEASON + "=" + season + " AND " +
                            EingangDbHelper.COLUMN_STYLE + "=" + style + " AND " +
                            EingangDbHelper.COLUMN_QUALITY + "=" + quality + " AND " +
                            EingangDbHelper.COLUMN_COLOUR + "=" + colour + " AND " +
                            EingangDbHelper.COLUMN_SIZE + "=" + size + " AND " +
                            EingangDbHelper.COLUMN_SECONDARYCHOICE + "=" + zweitwahlString, null, null, null, null, null);
            Log.d(LOG_TAG, "Kommt raus? Main Activity4");

            //Cursor cursor = database.query(BarcodeDbHelper.WARENEINGANG_TABLENAME, columns, null, null, null, null, null, null);
            cursor.moveToFirst();
            Eingang eingang;
            eingang = cursorToEingang(cursor);
            String ergebnis1= Integer.toString(eingang.getQuantsum());
            cursor.close();
            return ergebnis1;

        }

    public String getEinEingangTotal(String season, String style, String quality,  String colour, String size) {
        Log.d(LOG_TAG, "Kommt raus? Main Activity3");

        Cursor cursor = database.query(EingangDbHelper.WARENEINGANG_TABLENAME, columns,
                EingangDbHelper.COLUMN_SEASON + "=" + season + " AND " +
                        EingangDbHelper.COLUMN_STYLE + "=" + style + " AND " +
                        EingangDbHelper.COLUMN_QUALITY + "=" + quality + " AND " +
                        EingangDbHelper.COLUMN_COLOUR + "=" + colour + " AND " +
                        EingangDbHelper.COLUMN_SIZE + "=" + size, null, null, null, null, null);
        Log.d(LOG_TAG, "Kommt raus? Main Activity4");

        //Cursor cursor = database.query(BarcodeDbHelper.WARENEINGANG_TABLENAME, columns, null, null, null, null, null, null);
        cursor.moveToFirst();
        Eingang eingang;
        eingang = cursorToEingang(cursor);
        String ergebnis1= Integer.toString(eingang.getQuantsum());
        cursor.close();
        return ergebnis1;

    }

    public String getTotalofOneEingangFirstOrSecChoice(String season, String style, String quality,  String colour, boolean zweitwahl) {
        Log.d(LOG_TAG, "Kommt raus? Main Activity3");

        String zweitwahlString = (zweitwahl)?"1":"0";
        Cursor cursor = database.query(EingangDbHelper.WARENEINGANG_TABLENAME, columns,
                EingangDbHelper.COLUMN_SEASON + "=" + season + " AND " +
                        EingangDbHelper.COLUMN_STYLE + "=" + style + " AND " +
                        EingangDbHelper.COLUMN_QUALITY + "=" + quality + " AND " +
                        EingangDbHelper.COLUMN_COLOUR + "=" + colour + " AND " +
                        EingangDbHelper.COLUMN_SECONDARYCHOICE + "=" + zweitwahlString, null, null, null, null, null);
        Log.d(LOG_TAG, "Kommt raus? Main Activity4");

        //Cursor cursor = database.query(BarcodeDbHelper.WARENEINGANG_TABLENAME, columns, null, null, null, null, null, null);
        cursor.moveToFirst();
        Eingang eingang;
        eingang = cursorToEingang(cursor);
        String ergebnis1= Integer.toString(eingang.getQuantsum());
        cursor.close();
        return ergebnis1;

    }

    public String getTotalofOneEingang(String season, String style, String quality,  String colour) {
        Log.d(LOG_TAG, "Kommt raus? Main Activity3");


        Cursor cursor = database.query(EingangDbHelper.WARENEINGANG_TABLENAME, columns,
                EingangDbHelper.COLUMN_SEASON + "=" + season + " AND " +
                        EingangDbHelper.COLUMN_STYLE + "=" + style + " AND " +
                        EingangDbHelper.COLUMN_QUALITY + "=" + quality + " AND " +
                        EingangDbHelper.COLUMN_COLOUR + "=" + colour, null, null, null, null, null);
        Log.d(LOG_TAG, "Kommt raus? Main Activity4");

        //Cursor cursor = database.query(BarcodeDbHelper.WARENEINGANG_TABLENAME, columns, null, null, null, null, null, null);
        cursor.moveToFirst();
        Eingang eingang;
        eingang = cursorToEingang(cursor);
        String ergebnis1= Integer.toString(eingang.getQuantsum());
        cursor.close();
        return ergebnis1;

    }

    public EingangDataSource(Context context) {
        Log.d(LOG_TAG, "Unsere DataSource erzeugt jetzt den dbHelper.");
        dbHelper = new EingangDbHelper(context);
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

    public void deleteEingang(Eingangwosum eingang) {
        long id = eingang.getId();

        database.delete(EingangDbHelper.WARENEINGANG_TABLENAME,
                        EingangDbHelper.COLUMN_ID + "=" +id,
                        null);
        Log.d(LOG_TAG, "Eintrag gelöscht! ID: " + id + " Inhalt: " + eingang.toString());
    }


    public String getlastpalette() {
        Log.d(LOG_TAG, "Kommt raus? Main Activity3");

        open();
        Cursor cursor = database.query(EingangDbHelper.WARENEINGANG_TABLENAME, columns,null, null, null, null, null);
        Log.d(LOG_TAG, "Kommt raus? Main Activity4");

        //Cursor cursor = database.query(BarcodeDbHelper.WARENEINGANG_TABLENAME, columns, null, null, null, null, null, null);
        cursor.moveToLast();
        Eingang eingang;
        eingang = cursorToEingang(cursor);
        int ergebnis2= eingang.getPalette();
        String ergebnis3 = Integer.toString(ergebnis2);
        cursor.close();
        return ergebnis3;
    }


}


