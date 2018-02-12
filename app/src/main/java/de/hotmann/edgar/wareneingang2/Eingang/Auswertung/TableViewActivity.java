package de.hotmann.edgar.wareneingang2.Eingang.Auswertung;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


import au.com.bytecode.opencsv.CSVWriter;
import de.hotmann.edgar.wareneingang2.Eingang.EingangDataSource;
import de.hotmann.edgar.wareneingang2.Eingang.WareneingangPaletten;
import de.hotmann.edgar.wareneingang2.R;


public class TableViewActivity extends AppCompatActivity {
    public static final String LOG_TAG = WareneingangPaletten.class.getSimpleName();
    private Spinner spinnerseason, spinnerstyle, spinnerquality, spinnercolour;
    private Button aktualisieren, senden;
    private TextView size32, size34, size36, size38, size40, size42, size44, size46, size48, size50, size52, size54, size56, total;
    private TextView secsize32, secsize34, secsize36, secsize38, secsize40, secsize42, secsize44, secsize46, secsize48, secsize50, secsize52, secsize54, secsize56, sectotal;
    private TextView totalofsize32, totalofsize34, totalofsize36, totalofsize38, totalofsize40, totalofsize42, totalofsize44, totalofsize46, totalofsize48, totalofsize50, totalofsize52, totalofsize54, totalofsize56, totaloftotal;
    private EingangDataSource dataSource;
    private EditText ordernumber;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        setContentView(R.layout.activity_table_view);
        Log.d(LOG_TAG, "Das Datenquellen-Objekt wird angelegt.");
        dataSource = new EingangDataSource(this);


        /*gridView = (GridView) findViewById(R.id.listview_zählmengen);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, numbers);
        gridView.setAdapter(adapter); */


        addButtonListener();
        SendButtonListener();
        initializeWidgets();
        // addListenerOnSpinnerSelection();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    protected void onResume() {
        super.onResume();

        Log.d(LOG_TAG, "Die EingangDatenquelle wird geöffnet.");
        dataSource.open();
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(LOG_TAG, "Die EingangDatenquelle wird geschlossen.");
        dataSource.close();
        setZaehlmengeToTextView();
    }

    public boolean istallesleer() {
        return Integer.parseInt(totaloftotal.getText().toString())==0?false:true;
    }

    public void initializeWidgets() {
        addItemsToSeasonSpinner();
        addItemsToStyleSpinner();
        addItemsToQualitySpinner();
        addItemsToColourSpinner();
        size32 = (TextView) findViewById(R.id.size32);
        size34 = (TextView) findViewById(R.id.size34);
        size36 = (TextView) findViewById(R.id.size36);
        size38 = (TextView) findViewById(R.id.size38);
        size40 = (TextView) findViewById(R.id.size40);
        size42 = (TextView) findViewById(R.id.size42);
        size44 = (TextView) findViewById(R.id.size44);
        size46 = (TextView) findViewById(R.id.size46);
        size48 = (TextView) findViewById(R.id.size48);
        size50 = (TextView) findViewById(R.id.size50);
        size52 = (TextView) findViewById(R.id.size52);
        size54 = (TextView) findViewById(R.id.size54);
        size56 = (TextView) findViewById(R.id.size56);
        total = (TextView) findViewById(R.id.total);
        secsize32 = (TextView) findViewById(R.id.secsize32);
        secsize34 = (TextView) findViewById(R.id.secsize34);
        secsize36 = (TextView) findViewById(R.id.secsize36);
        secsize38 = (TextView) findViewById(R.id.secsize38);
        secsize40 = (TextView) findViewById(R.id.secsize40);
        secsize42 = (TextView) findViewById(R.id.secsize42);
        secsize44 = (TextView) findViewById(R.id.secsize44);
        secsize46 = (TextView) findViewById(R.id.secsize46);
        secsize48 = (TextView) findViewById(R.id.secsize48);
        secsize50 = (TextView) findViewById(R.id.secsize50);
        secsize52 = (TextView) findViewById(R.id.secsize52);
        secsize54 = (TextView) findViewById(R.id.secsize54);
        secsize56 = (TextView) findViewById(R.id.secsize56);
        sectotal = (TextView) findViewById(R.id.sectotal);
        totalofsize32 = (TextView) findViewById(R.id.totalofsize32);
        totalofsize34 = (TextView) findViewById(R.id.totalofsize34);
        totalofsize36 = (TextView) findViewById(R.id.totalofsize36);
        totalofsize38 = (TextView) findViewById(R.id.totalofsize38);
        totalofsize40 = (TextView) findViewById(R.id.totalofsize40);
        totalofsize42 = (TextView) findViewById(R.id.totalofsize42);
        totalofsize44 = (TextView) findViewById(R.id.totalofsize44);
        totalofsize46 = (TextView) findViewById(R.id.totalofsize46);
        totalofsize48 = (TextView) findViewById(R.id.totalofsize48);
        totalofsize50 = (TextView) findViewById(R.id.totalofsize50);
        totalofsize52 = (TextView) findViewById(R.id.totalofsize52);
        totalofsize54 = (TextView) findViewById(R.id.totalofsize54);
        totalofsize56 = (TextView) findViewById(R.id.totalofsize56);
        totaloftotal = (TextView) findViewById(R.id.totaloftotal);
        spinnerseason = (Spinner) findViewById(R.id.spinnerseason);
        spinnerstyle = (Spinner) findViewById(R.id.spinnerstyle);
        spinnerquality = (Spinner) findViewById(R.id.spinnerquality);
        spinnercolour = (Spinner) findViewById(R.id.spinnercolour);
    }
    public void addItemsToSeasonSpinner() {
        spinnerseason = (Spinner) findViewById(R.id.spinnerseason);
        List<String> seasonList = dataSource.getAllSeasons();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, seasonList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerseason.setAdapter(dataAdapter);
    }
    public void addItemsToStyleSpinner() {
        spinnerstyle = (Spinner) findViewById(R.id.spinnerstyle);
        List<String> styleList = dataSource.getAllStyles();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, styleList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerstyle.setAdapter(dataAdapter);
    }
    public void addItemsToQualitySpinner() {
        spinnerquality = (Spinner) findViewById(R.id.spinnerquality);
        List<String> qualList = dataSource.getAllQualities();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, qualList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerquality.setAdapter(dataAdapter);
    }
    public void addItemsToColourSpinner() {
        spinnercolour = (Spinner) findViewById(R.id.spinnercolour);
        List<String> colList = dataSource.getAllColours();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, colList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnercolour.setAdapter(dataAdapter);

    }
    // GETTERS //
    public String GetSeason() {
        String season = String.valueOf(spinnerseason.getSelectedItem());
        return season;
    }
    public String GetStyle() {
        String style = String.valueOf(spinnerstyle.getSelectedItem());
        return style;
    }
    public String GetQuality() {
        String quality = String.valueOf(spinnerquality.getSelectedItem());
        return quality;
    }
    public String GetColour() {
        String colour = String.valueOf(spinnercolour.getSelectedItem());
        return colour;
    }
    public String GetOrderno() {
        ordernumber = (EditText) findViewById(R.id.ordernotext);
        String orderno = ordernumber.getText().toString();
        return orderno;
    }

    // LISTENER //
    public void SendButtonListener() {
        senden = (Button) findViewById(R.id.sendbutton);
        senden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveExcelFile();
                toCSVFile();

                String filename = "/Wareneingang/result.xls";

                File filelocation = new File(Environment.getExternalStorageDirectory(), filename);
                String Hallo = filelocation.toString();
                Log.d(LOG_TAG, Hallo);
                Uri path = Uri.fromFile(filelocation);
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
// set the type to 'email
                emailIntent.setType("vnd.android.cursor.dir/email");
                String to[] = {"edgarhotmann@gmail.com"};
                //String to[] = {"dpe@godske.de"};
                //String bcc[] = {"edgarhotmann@gmail.com", "pick-pack@godske.de"};
                emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
                //emailIntent.putExtra(Intent.EXTRA_BCC, bcc);
// the attachment
                emailIntent.putExtra(Intent.EXTRA_STREAM, path);
// the mail subject
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Wareneingang der Ordernummer " + GetOrderno());
                startActivity(Intent.createChooser(emailIntent, "Sende CSV..."));
            }

        });
    }
    public void addButtonListener() {
        aktualisieren = (Button) findViewById(R.id.button_search_product);
        aktualisieren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setZaehlmengeToTextView();
            }
        });

    }

    public void toCSVFile() {
        CSVWriter writer = null;
        try {
            String orderno = ordernumber.getText().toString();
            String season = String.valueOf(spinnerseason.getSelectedItem());
            String style = String.valueOf(spinnerstyle.getSelectedItem());
            String quality = String.valueOf(spinnerquality.getSelectedItem());
            String colour = String.valueOf(spinnercolour.getSelectedItem());
            Log.d(LOG_TAG, "CSV_WRITER_STABLE");
            writer = new CSVWriter(new FileWriter(Environment.getExternalStorageDirectory() + "/Wareneingang/resultcsvwriter.csv"), '\t');
            // feed in your array (or convert your data to an array)
            String entry = "Orderno.#" + GetOrderno() + "#Season#" + GetSeason() + "#Style#" + GetStyle() + "#Qual.#" + GetQuality() + "#Farbe#" + GetColour();
            String[] entries = entry.split("#");
            writer.writeNext(entries);
            entry = "Gr.#32#34#36#38#40#42#44#46#48#50#52#54#56#Total";
            entries = entry.split("#");
            writer.writeNext(entries);
            entry = "1.Wahl#"+ Einererstewahl(32) + "#"+
                    Einererstewahl(34) + "#"+
                    Einererstewahl(36) + "#"+
                    Einererstewahl(38) + "#"+
                    Einererstewahl(40) + "#"+
                    Einererstewahl(42) + "#"+
                    Einererstewahl(44) + "#"+
                    Einererstewahl(46) + "#"+
                    Einererstewahl(48) + "#"+
                    Einererstewahl(50) + "#"+
                    Einererstewahl(52) + "#"+
                    Einererstewahl(54) + "#"+
                    Einererstewahl(56) + "#"+
                    TotalofOneEingang(false);
            entries = entry.split("#");
            writer.writeNext(entries);
            entry = "2.Wahl#"+ Einerzweitewahl(32) + "#"+
                    Einerzweitewahl(34) + "#"+
                    Einerzweitewahl(36) + "#"+
                    Einerzweitewahl(38) + "#"+
                    Einerzweitewahl(40) + "#"+
                    Einerzweitewahl(42) + "#"+
                    Einerzweitewahl(44) + "#"+
                    Einerzweitewahl(46) + "#"+
                    Einerzweitewahl(48) + "#"+
                    Einerzweitewahl(50) + "#"+
                    Einerzweitewahl(52) + "#"+
                    Einerzweitewahl(54) + "#"+
                    Einerzweitewahl(56) + "#"+
                    TotalofOneEingang(true);
            entries = entry.split("#");
            writer.writeNext(entries);
            entry = "Total#"+ Einertotal(32) + "#"+
                    Einertotal(34) + "#"+
                    Einertotal(36) + "#"+
                    Einertotal(38) + "#"+
                    Einertotal(40) + "#"+
                    Einertotal(42) + "#"+
                    Einertotal(44) + "#"+
                    Einertotal(46) + "#"+
                    Einertotal(48) + "#"+
                    Einertotal(50) + "#"+
                    Einertotal(52) + "#"+
                    Einertotal(54) + "#"+
                    Einertotal(56) + "#"+
                    dataSource.getTotalofOneEingang(season, style, quality, colour);
            entries = entry.split("#");
            writer.writeNext(entries);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String Einererstewahl(int groesse) {
        dataSource.open();
        String season = GetSeason();
        String style = GetStyle();
        String quality = GetQuality();
        String colour = GetColour();
        String size = Integer.toString(groesse);
        return dataSource.getEinEingang(season, style, quality, colour, size, false);
    }
    public String Einerzweitewahl(int groesse) {
        String season = GetSeason();
        String style = GetStyle();
        String quality = GetQuality();
        String colour = GetColour();
        String size = Integer.toString(groesse);
        return dataSource.getEinEingang(season, style, quality, colour, size, true);
    }
    public String TotalofOneEingang(boolean firstorsec) {
        String season = GetSeason();
        String style = GetStyle();
        String quality = GetQuality();
        String colour = GetColour();
        return dataSource.getTotalofOneEingangFirstOrSecChoice(season, style, quality, colour, firstorsec);
    }
    public String Einertotal(int groesse) {
        String season = GetSeason();
        String style = GetStyle();
        String quality = GetQuality();
        String colour = GetColour();
        String size = Integer.toString(groesse);
        return dataSource.getEinEingangTotal(season, style, quality, colour, size);
    }
    public String TotalTotalofOneEingang() {
        String season = GetSeason();
        String style = GetStyle();
        String quality = GetQuality();
        String colour = GetColour();
        return dataSource.getTotalofOneEingang(season, style, quality, colour);
    }


    public void createHeadline(Sheet sheet, CellStyle Style1) {

        Row row = sheet.createRow(0);
        Cell zelle=null;
        zelle=row.createCell(0);
        zelle.setCellValue(getResources().getString(R.string.orderno));
        zelle.setCellStyle(Style1);

        zelle=row.createCell(1);
        zelle.setCellValue(GetOrderno());
        zelle.setCellStyle(Style1);

        zelle=row.createCell(2);
        zelle.setCellValue(getResources().getString(R.string.season));
        zelle.setCellStyle(Style1);

        zelle=row.createCell(3);
        zelle.setCellValue(GetSeason());
        zelle.setCellStyle(Style1);

        zelle=row.createCell(4);
        zelle.setCellValue(getResources().getString(R.string.style));
        zelle.setCellStyle(Style1);

        zelle=row.createCell(5);
        zelle.setCellValue(GetStyle());
        zelle.setCellStyle(Style1);

        zelle=row.createCell(6);
        zelle.setCellValue(getResources().getString(R.string.quality));
        zelle.setCellStyle(Style1);

        zelle=row.createCell(7);
        zelle.setCellValue(GetQuality());
        zelle.setCellStyle(Style1);

        zelle=row.createCell(8);
        zelle.setCellValue(getResources().getString(R.string.colour));
        zelle.setCellStyle(Style1);

        zelle=row.createCell(9);
        zelle.setCellValue(GetColour());
        zelle.setCellStyle(Style1);
    }
    public boolean saveExcelFile() {

        boolean success = false;
        // Neues ExcelFile
        Workbook wb = new HSSFWorkbook();

        // Neues Blatt
        Sheet sheet1 = null;
        sheet1 = wb.createSheet("Zusammenfassung");
        Cell c = null;
        // Zellen style ändern für Kopfzeile
        CellStyle center = wb.createCellStyle();
        center.setAlignment(CellStyle.ALIGN_CENTER);

        CellStyle cs = wb.createCellStyle();
        CellStyle bolddick = wb.createCellStyle();

        Font dick = wb.createFont();
        dick.setBoldweight(Font.BOLDWEIGHT_BOLD);
        bolddick.setFont(dick);


        CellStyle right = wb.createCellStyle();
        right.setAlignment(CellStyle.ALIGN_RIGHT);
        for (int i=0; i<15;i++) {sheet1.setColumnWidth(i, (15 * 200));}

        //SpaltenÜberschriften
        createHeadline(sheet1,cs);


        Row row2 = sheet1.createRow(2);
        c=row2.createCell(0);
        c.setCellValue(getResources().getString(R.string.size));
        for (int j=32, ar=1; j<58; j=j+2, ar++)
        {
            c=row2.createCell(ar);
            c.setCellValue(j);
            c.setCellStyle(bolddick);

        }
        c=row2.createCell(14);
        c.setCellValue("Σ");
        c.setCellStyle(right);


        Row row3 = sheet1.createRow(3);
        c=row3.createCell(0);
        c.setCellValue(getResources().getString(R.string.firstchoice));
        for (int j=32, ar=1; j<58; j=j+2, ar++)
        {
            c=row3.createCell(ar);
            c.setCellValue(Einererstewahl(j));
            c.setCellStyle(center);
        }
        c=row3.createCell(14);
        c.setCellValue(TotalofOneEingang(false));
        c.setCellStyle(right);


        Row row4 = sheet1.createRow(4);
        c=row4.createCell(0);
        c.setCellValue(getResources().getString(R.string.secchoice));
        for (int j=32, ar=1; j<58; j=j+2, ar++)
        {
            c=row4.createCell(ar);
            c.setCellValue(Einerzweitewahl(j));
            c.setCellStyle(center);
        }
        c=row4.createCell(14);
        c.setCellValue(TotalofOneEingang(true));
        c.setCellStyle(right);


        Row row6 = sheet1.createRow(6);
        c=row6.createCell(0);
        c.setCellValue(getResources().getString(R.string.total));
        for (int j=32, ar=1; j<58; j=j+2, ar++)
        {
            c=row6.createCell(ar);
            c.setCellValue(Einertotal(j));
            c.setCellStyle(center);
        }
        c=row6.createCell(14);
        c.setCellValue(TotalTotalofOneEingang());
        c.setCellStyle(right);


        // Speicherort für File
        File file = new File(Environment.getExternalStorageDirectory() + "/Wareneingang/result.xls");
        FileOutputStream os = null;

        try
        {
             os = new FileOutputStream(file);
            wb.write(os);
            Log.d(LOG_TAG, "Schreibe auf Datei " + file);
            success = true;
        } catch (IOException e)
        {
            Log.d(LOG_TAG,"Fehler beim Erstellen der Datei " + file);
        } catch (Exception e)
        {
            Log.d(LOG_TAG,"Fehler beim Speichern der Datei " + file);
        } finally
        {
            try
            {
               if (null!=os)
                   os.close();
            } catch (Exception ex){ }
        }
        return success;
    }

    public void setZaehlmengeToTextView() {
        size32.setText(Einererstewahl(32));
        size34.setText(Einererstewahl(34));
        size36.setText(Einererstewahl(36));
        size38.setText(Einererstewahl(38));
        size40.setText(Einererstewahl(40));
        size42.setText(Einererstewahl(42));
        size44.setText(Einererstewahl(44));
        size46.setText(Einererstewahl(46));
        size48.setText(Einererstewahl(48));
        size50.setText(Einererstewahl(50));
        size52.setText(Einererstewahl(52));
        size54.setText(Einererstewahl(54));
        size56.setText(Einererstewahl(56));
        total.setText(TotalofOneEingang(false));
        secsize32.setText(Einerzweitewahl(32));
        secsize34.setText(Einerzweitewahl(34));
        secsize36.setText(Einerzweitewahl(36));
        secsize38.setText(Einerzweitewahl(38));
        secsize40.setText(Einerzweitewahl(40));
        secsize42.setText(Einerzweitewahl(42));
        secsize44.setText(Einerzweitewahl(44));
        secsize46.setText(Einerzweitewahl(46));
        secsize48.setText(Einerzweitewahl(48));
        secsize50.setText(Einerzweitewahl(50));
        secsize52.setText(Einerzweitewahl(52));
        secsize54.setText(Einerzweitewahl(54));
        secsize56.setText(Einerzweitewahl(56));
        sectotal.setText(TotalofOneEingang(true));
        totalofsize32.setText(Einertotal(32));
        totalofsize34.setText(Einertotal(34));
        totalofsize36.setText(Einertotal(36));
        totalofsize38.setText(Einertotal(38));
        totalofsize40.setText(Einertotal(40));
        totalofsize42.setText(Einertotal(42));
        totalofsize44.setText(Einertotal(44));
        totalofsize46.setText(Einertotal(46));
        totalofsize48.setText(Einertotal(48));
        totalofsize50.setText(Einertotal(50));
        totalofsize52.setText(Einertotal(52));
        totalofsize54.setText(Einertotal(54));
        totalofsize56.setText(Einertotal(56));
        totaloftotal.setText(TotalTotalofOneEingang());
    }
    @Override
    public void onStart() {
        super.onStart();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "TableView Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://de.hotmann.edgar.wareneingang/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "TableView Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://de.hotmann.edgar.wareneingang/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
