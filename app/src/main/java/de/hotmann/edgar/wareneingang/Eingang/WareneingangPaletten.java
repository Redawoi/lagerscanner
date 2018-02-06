package de.hotmann.edgar.wareneingang.Eingang;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Calendar;
import java.util.List;
import android.widget.EditText;
import android.widget.Button;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.widget.AbsListView;
import android.widget.ToggleButton;

import de.hotmann.edgar.wareneingang.Barcode.BarcodeDataSource;
import de.hotmann.edgar.wareneingang.MainstartActivity;
import de.hotmann.edgar.wareneingang.R;
import de.hotmann.edgar.wareneingang.Eingang.Auswertung.TableViewActivity;


public class WareneingangPaletten extends AppCompatActivity {

    public static final String LOG_TAG = WareneingangPaletten.class.getSimpleName();
    private EingangDataSource dataSource;
    private BarcodeDataSource dataSourcebarcode;
    private GoogleApiClient client;
    public EditText palettentextfeld, editTextSeason, editTextStyle, editTextQuality, editTextColour, editTextSize, editTextQuantity;
    public ListView eingangListView;
    Button nextpalette, prevpalette;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);

        setContentView(R.layout.activity_main);
        dataSource = new EingangDataSource(this);
        dataSourcebarcode = new BarcodeDataSource(this);

        initializeWidgets();
        activateAddButton();
        initializeContextualActionBar();
        palettennummeraktualisieren();
        kartonanzahlaktualisieren();


        final EditText palettennummer = (EditText) findViewById(R.id.editText_palette);
        palettennummer.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String nummer = palettennummer.getText().toString();
                    showAllListEntries1(nummer);
                    kartonanzahlaktualisieren();
                    return true;
                }
                return false;
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
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
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://de.hotmann.edgar.wareneingang/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
        dataSource.close();
    }
    @Override
    protected void onResume() {
        super.onResume();

        Log.d(LOG_TAG, "Die EingangDatenquelle wird geöffnet.");
        dataSource.open();

        Log.d(LOG_TAG, "Folgende Einträge sind in der EingangDatenbank vorhanden:");
        showAllListEntries();
    }
    @Override
    protected void onPause() {
        super.onPause();


        Log.d(LOG_TAG, "Die EingangDatenquelle wird geschlossen.");
        dataSource.close();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (item.getItemId()) {
            case (R.id.action_datenblatt):
                Intent intent = new Intent(this, TableViewActivity.class);
                this.startActivity(intent);
                break;
            case (R.id.action_settings):
                Intent intent2 = new Intent(this, MainstartActivity.class);
                this.startActivity(intent2);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;


    }
    private void initializeContextualActionBar() {

        final ListView eingangListView = (ListView) findViewById(R.id.listview_eingaenge);
        eingangListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        eingangListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            int selCouunt= 0;

            // In dieser Callback-Methode zählen wir die ausgewählten Listeneinträge mit
            // und fordern ein Aktualisieren der Contextual Action Bar mit invalidate() an
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                if (checked) {
                    selCouunt++;
                } else {
                    selCouunt--;
                }
                String cabTitle = selCouunt + " " +getString(R.string.cab_checked_string);
                mode.setTitle(cabTitle);
                mode.invalidate();
            }

            // In dieser Callback-Methode legen wir die CAB-Menüeinträge an
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                getMenuInflater().inflate(R.menu.menu_contextual_action_bar, menu);
                return true;
            }

            // In dieser Callback-Methode reagieren wir auf den invalidate() Aufruf
            // Wir lassen das Edit-Symbol verschwinden, wenn mehr als 1 Eintrag ausgewählt ist
            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                MenuItem item = menu.findItem(R.id.cab_change);
                if (selCouunt == 1) {
                    item.setVisible(true);
                } else {
                    item.setVisible(false);
                }

                return true;
            }


            // In dieser Callback-Methode reagieren wir auf Action Item-Klicks
            // Je nachdem ob das LÖschen- oder Ändern-Symbol angeklickt wurde
            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                boolean returnValue = true;
                SparseBooleanArray touchedEingangPositionen = eingangListView.getCheckedItemPositions();

                switch (item.getItemId()) {
                    case R.id.cab_delete:
                        for (int i = 0; i < touchedEingangPositionen.size(); i++) {
                            boolean isChecked = touchedEingangPositionen.valueAt(i);
                            if (isChecked) {
                                int positionInListView = touchedEingangPositionen.keyAt(i);
                                Eingangwosum eingang = (Eingangwosum) eingangListView.getItemAtPosition(positionInListView);
                                Log.d(LOG_TAG, "Position im ListView: " + positionInListView + " Inhalt: " + eingang.toString());
                                dataSource.deleteEingang(eingang);
                            }
                        }
                        showAllListEntries();
                        mode.finish();
                        break;
                    case R.id.cab_change:
                        Log.d(LOG_TAG, "Eintrag ändern");
                        for (int i= 0; i < touchedEingangPositionen.size(); i++) {
                            boolean isChecked = touchedEingangPositionen.valueAt(i);
                            if (isChecked) {
                                int positionInListView = touchedEingangPositionen.keyAt(i);
                                Eingangwosum eingang = (Eingangwosum) eingangListView.getItemAtPosition(positionInListView);
                                Log.d(LOG_TAG, "Position im ListView: " + positionInListView + " Inhalt: " + eingang.toString());

                                AlertDialog editEingänge = createEditEingang(eingang);
                                editEingänge.show();
                            }
                        }

                        mode.finish();
                        break;
                    default:
                        returnValue = false;
                        break;
                }
                return returnValue;
            }

            // In dieser Callback-Methode reagieren wir auf das Schließen der CAB
            // Wir setzen den Zähler auf 0 zurück
            @Override
            public void onDestroyActionMode(ActionMode mode) {
                selCouunt = 0;

            }
        });
    }

    public void initializeWidgets() {
        nextpalette = (Button) findViewById(R.id.nextpallet);
        prevpalette = (Button) findViewById(R.id.prevpallet);
        nextpalette.setOnClickListener(palettemehr);
        prevpalette.setOnClickListener(paletteweniger);
        palettentextfeld = (EditText) findViewById(R.id.editText_palette);
        eingangListView = (ListView) findViewById(R.id.listview_eingaenge);
        editTextSeason = (EditText) findViewById(R.id.editText_season);
        editTextStyle = (EditText) findViewById(R.id.editText_style);
        editTextQuality = (EditText) findViewById(R.id.editText_quality);
        editTextColour = (EditText) findViewById(R.id.editText_colour);
        editTextSize = (EditText) findViewById(R.id.editText_size);
        editTextQuantity = (EditText) findViewById(R.id.editText_quantity);

    }
    public void aktualisierenBeiPalettenWechsel() {
        showAllListEntries();
        kartonanzahlaktualisieren();
    }
    public void kartonanzahlaktualisieren() {
        EditText palettennummer = (EditText) findViewById(R.id.editText_palette);
        TextView palettefuelle = (TextView) findViewById(R.id.palettefuelle);
        String nummer = palettennummer.getText().toString();
        dataSource.open();
        palettefuelle.setText(dataSource.getKartonAnzahlOnPalette(nummer));
    }
    public void palettennummeraktualisieren() {
        palettentextfeld.setText(dataSource.getlastpalette());
        showAllListEntries();
    }

    public void scanNow(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Fahre über einen Barcode\n" +
                "Lautstärke (+)(–) für Taschenlampe an/aus");
        integrator.setOrientationLocked(true);
        integrator.initiateScan();
    }
    public void clearNow(View view) {
        editTextSeason.setText("");
        editTextStyle.setText("");
        editTextQuality.setText("");
        editTextColour.setText("");
        editTextSize.setText("");
        editTextQuantity.setText("");
    }
    public String getAktuellePalette() {
        String palettentextcount = palettentextfeld.getText().toString();
        return  palettentextcount;
    }
    public List<Eingangwosum> EinträgeEinerPalette(String palette){
        return dataSource.getAllEingaengewoSum(palette);
    }
    private void showAllListEntries() {
        String palette = getAktuellePalette();
        List<Eingangwosum> eingangList = EinträgeEinerPalette(palette);
        ArrayAdapter<Eingangwosum> eingangArrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_multiple_choice,
                eingangList);
        eingangListView.setAdapter(eingangArrayAdapter);
    }
    private void showAllListEntries1(String param) {
        List<Eingangwosum> eingangList = dataSource.getAllEingaengewoSum(param);
        ArrayAdapter<Eingangwosum> eingangArrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_multiple_choice,
                eingangList);
        eingangListView.setAdapter(eingangArrayAdapter);
    }

    private void activateAddButton() {
        Button addEingang = (Button) findViewById(R.id.button_add_product);
        final EditText editTextPalette = (EditText) findViewById(R.id.editText_palette);
        final EditText editTextSeason = (EditText) findViewById(R.id.editText_season);
        final EditText editTextStyle = (EditText) findViewById(R.id.editText_style);
        final EditText editTextQuality = (EditText) findViewById(R.id.editText_quality);
        final EditText editTextColour = (EditText) findViewById(R.id.editText_colour);
        final EditText editTextSize = (EditText) findViewById(R.id.editText_size);
        final EditText editTextQuantity = (EditText) findViewById(R.id.editText_quantity);
        final ToggleButton secondarychoicechkbox = (ToggleButton) findViewById(R.id.secondarychoicecheckbox);
        final ToggleButton countornot = (ToggleButton) findViewById(R.id.countcheckbox);

        addEingang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String paletteString = editTextPalette.getText().toString();
                String season = editTextSeason.getText().toString();
                String style = editTextStyle.getText().toString();
                String quality = editTextQuality.getText().toString();
                String colour = editTextColour.getText().toString();
                String size = editTextSize.getText().toString();
                String quantityString = editTextQuantity.getText().toString();
                String secondarchoicestring = String.valueOf(secondarychoicechkbox.isChecked());
                String countwithString = String.valueOf(countornot.isChecked());

                if (TextUtils.isEmpty(paletteString)) {
                    editTextPalette.setError(getString(R.string.editText_errorMessage));
                    return;
                }
                if (TextUtils.isEmpty(season)) {
                    editTextSeason.setError(getString(R.string.editText_errorMessage));
                    return;
                }
                if (TextUtils.isEmpty(style)) {
                    editTextStyle.setError(getString(R.string.editText_errorMessage));
                    return;
                }
                if (TextUtils.isEmpty(quality)) {
                    editTextQuality.setError(getString(R.string.editText_errorMessage));
                    return;
                }
                if (TextUtils.isEmpty(colour)) {
                    editTextColour.setError(getString(R.string.editText_errorMessage));
                    return;
                }
                if (TextUtils.isEmpty(size)) {
                    editTextSize.setError(getString(R.string.editText_errorMessage));
                    return;
                }
                if (TextUtils.isEmpty(quantityString)) {
                    editTextQuantity.setError(getString(R.string.editText_errorMessage));
                    return;
                }
                int palette = Integer.parseInt(paletteString);
                int quantity = Integer.parseInt(quantityString);
                boolean secondarchoice = Boolean.parseBoolean(secondarchoicestring);
                boolean countwith = Boolean.parseBoolean(countwithString);
                secondarychoicechkbox.setChecked(false);
                secondarychoicechkbox.setSelected(false);

                Calendar jetzt = Calendar.getInstance();
                int day = jetzt.get(Calendar.DAY_OF_MONTH);
                int month  = jetzt.get(Calendar.MONTH)+1;
                int year = jetzt.get(Calendar.YEAR);
                int week= jetzt.get(Calendar.WEEK_OF_YEAR);
                dataSource.createEingang(palette, season, style, quality, colour, size, secondarchoice, countwith, quantity,day,month,year,week);

                InputMethodManager inputMethodManager;
                inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (getCurrentFocus() != null) {
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
                palettennummeraktualisieren();
                kartonanzahlaktualisieren();
            }

        });
    }

    private AlertDialog createEditEingang(final Eingangwosum eingang) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogsView = inflater.inflate(R.layout.dialog_edit_eintraege, null);

        final EditText editTextNewPalette = (EditText) dialogsView.findViewById(R.id.editText_new_palette);
        editTextNewPalette.setText(String.valueOf(eingang.getPalette()));

        final EditText editTextNewSeason = (EditText) dialogsView.findViewById(R.id.editText_new_season);
        editTextNewSeason.setText(String.valueOf(eingang.getSeason()));

        final EditText editTextNewStyle = (EditText) dialogsView.findViewById(R.id.editText_new_style);
        editTextNewStyle.setText(String.valueOf(eingang.getStyle()));

        final EditText editTextNewQuality = (EditText) dialogsView.findViewById(R.id.editText_new_quality);
        editTextNewQuality.setText(eingang.getQuality());

        final EditText editTextNewColour = (EditText) dialogsView.findViewById(R.id.editText_new_colour);
        editTextNewColour.setText(String.valueOf(eingang.getColour()));

        final EditText editTextNewSize = (EditText) dialogsView.findViewById(R.id.editText_new_size);
        editTextNewSize.setText(String.valueOf(eingang.getSize()));

        final EditText editTextNewQuantity = (EditText) dialogsView.findViewById(R.id.editText_new_quantity);
        editTextNewQuantity.setText(String.valueOf(eingang.getQuantity()));

        final ToggleButton checkBoxNewSecondary = (ToggleButton) dialogsView.findViewById(R.id.checkbox_newSecondarychoice);
        checkBoxNewSecondary.setChecked(Boolean.parseBoolean(String.valueOf(eingang.getSecondarychoice())));

        final ToggleButton checkBoxCountOrNot = (ToggleButton) dialogsView.findViewById(R.id.checkbox_newCountornot);
        checkBoxCountOrNot.setChecked(Boolean.parseBoolean(String.valueOf(eingang.getCounttoPallet())));



        builder.setView(dialogsView)
                .setTitle(R.string.dialog_title)
                .setPositiveButton(R.string.dialog_button_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String paletteString = editTextNewPalette.getText().toString();
                        String season = editTextNewSeason.getText().toString();
                        String style = editTextNewStyle.getText().toString();
                        String quality = editTextNewQuality.getText().toString();
                        String colour = editTextNewColour.getText().toString();
                        String size = editTextNewSize.getText().toString();
                        String quantityString = editTextNewQuantity.getText().toString();
                        int newSecondary = checkBoxNewSecondary.isChecked()?1:0;
                        int newCountornot = checkBoxCountOrNot.isChecked()?1:0;


                        if ((TextUtils.isEmpty(quantityString))
                                || (TextUtils.isEmpty(quality))
                                || (TextUtils.isEmpty(size))
                                || (TextUtils.isEmpty(colour))
                                || (TextUtils.isEmpty(style))
                                || (TextUtils.isEmpty(season))
                                || (TextUtils.isEmpty(paletteString))) {
                            Log.d(LOG_TAG, "Ein Eintrag enthielt keinen Text. Daher Abbruch der Änderung.");
                            return;
                        }

                        int quantity = Integer.parseInt(quantityString);
                        int palette = Integer.parseInt(paletteString);
                        boolean secondarychoice = (newSecondary==1)?true:false;
                        boolean countwith = (newCountornot==1)?true:false;
                        // An dieser Stelle schreiben wir die geänderten Daten in die SQLite Datenbank
                        Eingangwosum updatedEingang = dataSource.updateEingang(eingang.getId(), palette, season, style, quality, colour, size, secondarychoice, countwith, quantity);

                        Log.d(LOG_TAG, "Alter Eintrag - ID: " + eingang.getId() + " Inhalt: " + eingang.toString());
                        Log.d(LOG_TAG, "Neuer Eintrag - ID: " + updatedEingang.getId() + " Inhalt: " + updatedEingang.toString());

                        showAllListEntries();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.dialog_button_negative, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // Lese Ausgabe
        Log.d(LOG_TAG, "Ein Eintrag enthielt keinen Text. Daher Abbruch der Änderung.");
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult.getContents() != null) {
            // Ergebnis erhalten
            String scanContent = scanningResult.getContents();
            dataSourcebarcode.open();
            if(dataSourcebarcode.CheckIsBarcodeInDBorNot(scanContent, "codelist")){
                // Ergebnis ist in Datenbank
                // Ausgabe des Ergebnisses auf TextViews
                String proofdigit = "codelist";
                String[] Params = dataSourcebarcode.getOneBarcode(scanContent, proofdigit);
                editTextSeason.setText(Params[0]);
                editTextStyle.setText(Params[1]);
                editTextQuality.setText(Params[2]);
                editTextColour.setText(Params[3]);
                editTextSize.setText(Params[4]);
                editTextQuantity.requestFocus();
                if(editTextQuantity.requestFocus()) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }else{
                // Ergebnis nicht in unserer Datenbank
                // Fehlermeldung
                String message1 = "-!-!-!-Barcode nicht auffindbar-!-!-!-\n\n" +
                        "1. Versuchen Sie keine betriebsfremden Barcodes zu scannen. (Virengefahr).\n\n" +
                        "2. Zittern Sie mal nicht so blöd rum\n\n" +
                        "3. Wenn Sie unschuldig sind, dann tut das uns leid.\n\n" +
                        "4. Trotzdem sollten Sie nicht denken, dass wir Ihnen Leid zufügen wollen.\nSchauen Sie sich doch einfach im Spiegel an.\n\n" +
                        "5. Lesen Sie die AGBs vor Nutzung dieser App sorgfältig durch\n\n" +
                        "6. Je öfter Sie mich anklicken, desto länger bleibe ich hier!";
                String message2= "CODE nicht in Datenbank "+ scanContent;
                    final Toast toast = Toast.makeText(getApplicationContext(), message2 , Toast.LENGTH_LONG);
                    toast.show();
            }
            dataSourcebarcode.close();
        } else {
            // Kein Ergebnis erhalten
            Toast toast = Toast.makeText(getApplicationContext(), "Konnte keine Daten lesen", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    View.OnClickListener palettemehr = new View.OnClickListener() {
        public void onClick(View v) {
            palettentextfeld.setText(Integer.toString((Integer.parseInt(getAktuellePalette())+1)));
            aktualisierenBeiPalettenWechsel(); }
    };

     View.OnClickListener paletteweniger = new View.OnClickListener() {
        public void onClick(View v) {
            if (Integer.parseInt(getAktuellePalette())==1) {
                palettentextfeld.setText("1");
            } else {
                palettentextfeld.setText(Integer.toString((Integer.parseInt(getAktuellePalette())-1)));
            }
            aktualisierenBeiPalettenWechsel(); }};

}
