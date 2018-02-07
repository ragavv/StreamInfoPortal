package com.hpm.sp.streaminfoportal.Main;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ViewFlipper;

import com.hpm.sp.streaminfoportal.AboutUs.AboutUsActivity;
import com.hpm.sp.streaminfoportal.BranchViewActivity;
import com.hpm.sp.streaminfoportal.ContactInfoActivity;
import com.hpm.sp.streaminfoportal.Events.EventActivity;
import com.hpm.sp.streaminfoportal.GuruParampare.GuruParampareActivity;
import com.hpm.sp.streaminfoportal.Interfaces.ResponseInterface;
import com.hpm.sp.streaminfoportal.Models.EventDataObject;
import com.hpm.sp.streaminfoportal.Models.Panchanga;
import com.hpm.sp.streaminfoportal.Network.NetworkHelper;
import com.hpm.sp.streaminfoportal.PravachanaActivity;
import com.hpm.sp.streaminfoportal.R;
import com.hpm.sp.streaminfoportal.Articles.TatvaArticlesActivity;
import com.hpm.sp.streaminfoportal.Utils;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Utils utils = new Utils();

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
        viewFlipper.setFlipInterval(8888);
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
        } else if (id == R.id.nav_about) {
            openNewActivity(AboutUsActivity.class);
        } else if (id == R.id.nav_parampara) {
            openNewActivity(GuruParampareActivity.class);
        } else if (id == R.id.nav_events) {
            openNewActivity(EventActivity.class);
        } else if (id == R.id.nav_pravachanas) {
            openNewActivity(PravachanaActivity.class);
        } else if (id == R.id.nav_mail) {
            openNewActivity(ContactInfoActivity.class);
        } else if (id == R.id.nav_branch) {
            openNewActivity(BranchViewActivity.class);
        } else if (id == R.id.nav_tatva) {
            openNewActivity(TatvaArticlesActivity.class);
        } else if (id == R.id.nav_facebook) {
            String url = "https://www.facebook.com/srividyashreeshatirtharu/";
            try {
                Intent i = new Intent("android.intent.action.MAIN");
                i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
                i.addCategory("android.intent.category.LAUNCHER");
                i.setData(Uri.parse(url));
                startActivity(i);
            } catch (ActivityNotFoundException e) {
                // Chrome is not installed
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(i);
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openNewActivity(Class targetClass) {
        Intent intent = new Intent(this, targetClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    protected void refreshPanchanga() {
        if (utils.isConnectedToNetwork(this)) {
            NetworkHelper.getPanchangaForToday(new ResponseInterface() {
                @Override
                public void onResponseFromServer(List<?> objects, Exception e) {
                    if (e == null) {
                        Log.d(TAG, "onResponseFromServer: Panchanga : " + (List<Panchanga>) objects);
                    } else {
                        Log.e(TAG, "onResponseFromServer: Panchanga", e);
                    }
                }
            });
        }

    }

    protected void refreshEvents() {
        if (utils.isConnectedToNetwork(this)) {
            NetworkHelper.getAllEvents(new ResponseInterface() {
                @Override
                public void onResponseFromServer(List<?> objects, Exception e) {
                    if (e == null) {
                        Log.d(TAG, "onResponseFromServer: Events :" + (List<EventDataObject>) objects);
                    } else {
                        Log.e(TAG, "onResponseFromServer: Events", e);
                    }
                }
            });
        }
    }
}
