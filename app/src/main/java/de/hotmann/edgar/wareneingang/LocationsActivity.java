package de.hotmann.edgar.wareneingang;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hotmann.edgar.wareneingang.Barcode.BarcodeDataSource;
import de.hotmann.edgar.wareneingang.Eingang.WareneingangPaletten;
import de.hotmann.edgar.wareneingang.R;

import static android.app.PendingIntent.getActivity;

public class LocationsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private Spinner spinnerhalle,spinnerebene,spinnerreihe;
    public static final String LOG_TAG = WareneingangPaletten.class.getSimpleName();
    private BarcodeDataSource dataSourcebarcode;


    public void scanthen(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Fahre über einen Barcode\n" +
                "Lautstärke (+)(–) für Taschenlampe an/aus");
        integrator.setOrientationLocked(true);
        integrator.initiateScan();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        addItemsOrteSpinner();
        dataSourcebarcode = new BarcodeDataSource(this);


        /*

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }
        });

        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainstart, menu);
        return true;
    }




    public void addItemsOrteSpinner() {
        spinnerhalle = (Spinner) findViewById(R.id.spinner_halle);

        List<String> hallenlist = Arrays.asList("Halle 2", "Halle 3", "Halle 4", "Halle 5", "Halle 7");
        ArrayAdapter<String > halleadapter = new ArrayAdapter<String> (this, R.layout.spinner_style,hallenlist);
        halleadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerhalle.setAdapter(halleadapter);

        spinnerebene = (Spinner) findViewById(R.id.spinner_ebene);

        List<String> ebenenlist = Arrays.asList("1.Ebene", "2.Ebene", "3.Ebene", "4.Ebene");
        ArrayAdapter<String > ebenenadapter = new ArrayAdapter<String> (this, R.layout.spinner_style,ebenenlist);
        ebenenadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerebene.setAdapter(ebenenadapter);

        spinnerreihe = (Spinner) findViewById(R.id.spinner_reihe);

        List<String> reihenlist = new ArrayList<String>();
        for(int l=1; l<=60; l++){
            String al = Integer.toString(l);
            reihenlist.add(al);
        }
        ArrayAdapter<String > reihenadapter = new ArrayAdapter<String> (this, R.layout.spinner_style,reihenlist);
        reihenadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerreihe.setAdapter(reihenadapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if ( id == R.id.action_menu_exit) {
            this.finishAffinity();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_palette) {
            Intent palettenintent = new Intent(this, WareneingangPaletten.class);
            this.startActivity(palettenintent);
        } else if (id == R.id.nav_reihen) {
            Intent reihenintent = new Intent(this, LocationsActivity.class);
            this.startActivity(reihenintent);

        } else if (id == R.id.nav_eingang) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }





    View.OnClickListener scannen = new View.OnClickListener() {
        public void onClick(View v) {
             }
    };



    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // Lese Ausgabe
        Log.d(LOG_TAG, "Ein Eintrag enthielt keinen Text. Daher Abbruch der Änderung.");
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult.getContents() != null) {
            // Ergebnis erhalten
            String scanContent = scanningResult.getContents();
            dataSourcebarcode.open();
            if(dataSourcebarcode.CheckIsBarcodeInDBorNot(scanContent)){
                // Ergebnis ist in Datenbank
                // Ausgabe des Ergebnisses auf TextViews
                String[] Params = dataSourcebarcode.getOneBarcode(scanContent);
                EditText editTextOrtSeason = (EditText) findViewById(R.id.editTextOrtSeason);
                EditText editTextOrtStyle = (EditText) findViewById(R.id.editTextOrtStyle);
                EditText editTextOrtQuality = (EditText) findViewById(R.id.editTextOrtQualität);
                EditText editTextOrtColour = (EditText) findViewById(R.id.editTextOrtColour);
                EditText editTextOrtSize = (EditText) findViewById(R.id.editTextOrtSize);


                editTextOrtSeason.setText(Params[0]);
                editTextOrtStyle.setText(Params[1]);
                editTextOrtQuality.setText(Params[2]);
                editTextOrtColour.setText(Params[3]);
                editTextOrtSize.setText(Params[4]);
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















}
