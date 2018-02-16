package de.hotmann.edgar.wareneingang2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;

import de.hotmann.edgar.wareneingang2.Barcode.BarcodeDataSource;
import de.hotmann.edgar.wareneingang2.Eingang.WareneingangPaletten;
import de.hotmann.edgar.wareneingang2.Lagerorte.LocationsActivity;

public class MainstartActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private BarcodeDataSource dataSource;
    private static final String LOG_TAG = MainstartActivity.class.getSimpleName();
    @SuppressLint("SetTextI18n")
    public void doUpdate () {

        Button optimieren = (Button) findViewById(R.id.dboptimierung);
        assert optimieren != null;
        optimieren.setVisibility(View.INVISIBLE);
        long starttime = 0;
        Log.d(LOG_TAG, "Entrance");
        dataSource = new BarcodeDataSource(this);
        dataSource.open();
        dataSource.CreateSubtables();
        if (BuildConfig.DEBUG) {
            starttime = System.currentTimeMillis();
        }
        dataSource.optimiereDB();
        if(BuildConfig.DEBUG) {
            long dauer = System.currentTimeMillis()-starttime;
            Context context = getApplicationContext();
            CharSequence text = "Abfrage wurde beendet nach " + dauer + " ms\n";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            final TextView textview3 = (TextView) findViewById(R.id.textView3);
            assert textview3 != null;
            textview3.setText("Dauer: " + dauer/1000 + "," + dauer%1000 + " Sekunden");
        }


        /*
        new Thread(new Runnable() {
            @SuppressLint("SetTextI18n")
            public void run() {
                while (progressStatus <= z) {
                    // Update the progress bar and display the
                    //current value in the text view
                    dataSource.optimiereDB(progressStatus);
                    handler.post(new Runnable() {
                        public void run() {
                            int zahl = z+1;
                            progressBar.setProgress(progressStatus);
                            textviewprozent.setText(progressStatus+" / "+zahl);
                        }
                    }

                    );
                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }progressStatus++;
                }
            }
        }).start();
        */

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                Intent palettenintent = new Intent(this, WareneingangPaletten.class);
                this.startActivity(palettenintent);
                break;
            case KeyEvent.KEYCODE_VOLUME_UP:
                Intent locationintent = new Intent(this, LocationsActivity.class);
                this.startActivity(locationintent);
                break;
        }
        return true;
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainstart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Button optimieren = (Button) findViewById(R.id.dboptimierung);
        final TextView textview3 = (TextView) findViewById(R.id.textView3);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.determinateBar);
        assert textview3 != null;
        textview3.setText("Die Datenbank ist langsam.");
        assert optimieren != null;
        optimieren.setVisibility(View.VISIBLE);
        assert progressBar != null;
        progressBar.setVisibility(View.VISIBLE);
        if(CheckforFastDB()) {
            optimieren.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            textview3.setText("Die Datenbank ist optimiert.");

        }





        optimieren.setOnClickListener(new View.OnClickListener(){
            @SuppressLint("SetTextI18n")
            public void onClick(View v) {

                doUpdate();
                //textview3.setText("Fertig");
            }
        });




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
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
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }
    public boolean CheckforFastDB () {
        dataSource = new BarcodeDataSource(this);
        dataSource.open();
        if(dataSource.CheckforFastDB()) {
            dataSource.close();
            return true;
        }else{
            dataSource.close();
            return false;
        }
    }
}
