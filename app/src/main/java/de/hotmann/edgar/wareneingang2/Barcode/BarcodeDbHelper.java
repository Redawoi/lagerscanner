package de.hotmann.edgar.wareneingang2.Barcode;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;


public class BarcodeDbHelper extends SQLiteOpenHelper{

    private static  final String LOG_TAG = BarcodeDbHelper.class.getSimpleName();

    private static final String DB_NAME = Environment.getExternalStorageDirectory() + "/Wareneingang/codelist.db";
    private static final int DB_VERSION = 1;

    static final String WARENEINGANG_TABLENAME = "codelist";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SEASON = "season";
    public static final String COLUMN_STYLE = "style";
    public static final String COLUMN_QUALITY = "quality";
    public static final String COLUMN_LGD = "lgd";
    public static final String COLUMN_COLOUR = "colour";
    public static final String COLUMN_SIZE = "size";
    public static final String COLUMN_EANNO = "eanno";
    public static final String COLUMN_ITEMNAME = "itemname";
    public static final String COLUMN_PRODGROUP = "productgroup";

    private static final String SQL_CREATE =
            "CREATE TABLE IF NOT EXISTS " + WARENEINGANG_TABLENAME +
                    "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_SEASON + " TEXT NOT NULL, " +
                    COLUMN_STYLE + " TEXT NOT NULL, " +
                    COLUMN_QUALITY + " TEXT NOT NULL, " +
                    COLUMN_LGD + " TEXT NOT NULL, " +
                    COLUMN_COLOUR + " TEXT NOT NULL, " +
                    COLUMN_SIZE + " TEXT NOT NULL, " +
                    COLUMN_EANNO + " TEXT NOT NULL, " +
                    COLUMN_ITEMNAME + " TEXT NOT NULL, " +
                    COLUMN_PRODGROUP + "TEXT";

    BarcodeDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d(LOG_TAG, "DbHelper hat die Datenbank: " +getDatabaseName() + " erzeugt.");
    }











    // onCreate
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.d(LOG_TAG, "Die Tabelle wird mit SQL-Befehl: " + SQL_CREATE + " angelegt.");
            db.execSQL(SQL_CREATE);
        }
        catch (Exception ex) {
            Log.e(LOG_TAG, "Fehler beim Anlegen der Tabelle: " + ex.getMessage());
        }
    }









    // on Upgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
