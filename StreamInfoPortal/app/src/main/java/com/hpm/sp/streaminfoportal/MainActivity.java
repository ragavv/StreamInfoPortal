package com.hpm.sp.streaminfoportal;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        refreshPanchanga();
        refreshEvents();

        Thread imageThread = new Thread();
        imageThread.run();

        ViewFlipper viewFlipper = (ViewFlipper) findViewById(R.id.image_flipper);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.startFlipping();

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
//        getMenuInflater().inflate(R.menu.main, menu);
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
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the home
        }
        else if (id == R.id.nav_about) {

            Intent callAboutPage = new Intent(this, AboutUsActivity.class);
            startActivity(callAboutPage);

        }
        else if (id == R.id.nav_parampara) {
            Intent guruParampareIntent = new Intent(this, GuruParampareActivity.class);
            startActivity(guruParampareIntent);
        }
        else if (id == R.id.nav_events) {
            Intent viewEventsIntent = new Intent(this, EventActivity.class);
            startActivity(viewEventsIntent);
        }
        else if(id == R.id.nav_pravachanas)
        {
            Intent pravachanaViewIntent = new Intent(this, PravachanaActivity.class);
            startActivity(pravachanaViewIntent);
        }
        else if (id == R.id.nav_mail) {

            Intent contactInfoIntent = new Intent(this, ContactInfoActivity.class);
            startActivity(contactInfoIntent);

        }
        else if (id == R.id.nav_branch) {
            Intent callBranchPage = new Intent(this, BranchViewActivity.class);
            startActivity(callBranchPage);
        }
        else if(id == R.id.nav_tatva){
            Intent callTatvaPage = new Intent(this, TatvaArticlesActivity.class);
            startActivity(callTatvaPage);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    protected void refreshPanchanga(){

        TextView panchangaView = (TextView) findViewById(R.id.panchanga_text);
        if(!(panchangaView.getText() == ""))
        {
            return;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            PanchangaFetchHandler fetchToday = new PanchangaFetchHandler("Put URL of server here", panchangaView);
            fetchToday.execute();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();
        }
    }
    protected void refreshEvents(){
        TextView eventsView = (TextView) findViewById(R.id.event_text);
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            EventFetchHandler fetchToday = new EventFetchHandler("Put URL of server here", eventsView);
            fetchToday.execute();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();
        }
    }
}
