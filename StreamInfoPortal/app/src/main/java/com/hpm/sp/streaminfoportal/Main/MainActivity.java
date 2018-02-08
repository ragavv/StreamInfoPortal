package com.hpm.sp.streaminfoportal.Main;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.hpm.sp.streaminfoportal.AboutUsActitviy.AboutUsActivity;
import com.hpm.sp.streaminfoportal.BranchViewActivity.BranchViewActivity;
import com.hpm.sp.streaminfoportal.Constants;
import com.hpm.sp.streaminfoportal.ContactInfoActivity;
import com.hpm.sp.streaminfoportal.EventsActivity.EventActivity;
import com.hpm.sp.streaminfoportal.EventsActivity.EventRecyclerViewAdapter;
import com.hpm.sp.streaminfoportal.FreshBytViewPager;
import com.hpm.sp.streaminfoportal.GuruParampare.GuruParampareActivity;
import com.hpm.sp.streaminfoportal.Interfaces.RecyclerViewClickListener;
import com.hpm.sp.streaminfoportal.Interfaces.ResponseInterface;
import com.hpm.sp.streaminfoportal.Models.EventDataObject;
import com.hpm.sp.streaminfoportal.Models.Panchanga;
import com.hpm.sp.streaminfoportal.Network.NetworkHelper;
import com.hpm.sp.streaminfoportal.PravachanaActivity;
import com.hpm.sp.streaminfoportal.R;
import com.hpm.sp.streaminfoportal.Articles.TatvaArticlesActivity;
import com.hpm.sp.streaminfoportal.Utils;
import com.rd.PageIndicatorView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener, RecyclerViewClickListener, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Utils utils = new Utils();
    private EventRecyclerViewAdapter adapter;
    private ArrayList<EventDataObject> events;

    @BindView(R.id.viewPager)
    FreshBytViewPager pager;

    @BindView(R.id.pageIndicatorView)
    PageIndicatorView pageIndicatorView;


    @BindView(R.id.main_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.date)
    TextView mPachangaDate;

    @BindView(R.id.month)
    TextView mPachangaMonth;

    @BindView(R.id.tithi)
    TextView mPachangaTithi;

    @BindView(R.id.paksham)
    TextView mPachangaPaksham;

    @BindView(R.id.nakshatram)
    TextView mPachangaNakshatram;

    @BindView(R.id.rahukalam)
    TextView mPachangaRahukalam;

    @BindView(R.id.see_all_events_btn)
    TextView mSeeAllEventsBtn;

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout refreshLayout;

    private Boolean eventsLoaded = false;
    private Boolean panchaganaLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        pager.setClipToPadding(false);
        pager.setPageMargin(0);
        List<String> imageList = new ArrayList<>();
        imageList.add("https://ssm.kumardivyarajat.com/assets/home_image_1.jpeg");
        imageList.add("https://ssm.kumardivyarajat.com/assets/home_image_2.jpeg");
        imageList.add("https://ssm.kumardivyarajat.com/assets/home_image_3.jpeg");
        pager.setAdapter(new HomePagerAdapter(getSupportFragmentManager(), imageList));
        pager.addOnPageChangeListener(this);

        recyclerView.setHasFixedSize(true);
        events = new ArrayList<>();
        adapter = new EventRecyclerViewAdapter((ArrayList<EventDataObject>) events, Constants.HORIZONTAL);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        recyclerView.setClipToPadding(true);
        mSeeAllEventsBtn.setOnClickListener(this);
        refreshLayout.setOnRefreshListener(this);

        refreshPanchanga();
        refreshEvents();
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
        panchaganaLoaded = false;
        if (utils.isConnectedToNetwork(this)) {
            NetworkHelper.getPanchangaForToday(new ResponseInterface() {
                @Override
                public void onResponseFromServer(List<?> objects, Exception e) {
                    if (eventsLoaded) {
                        refreshLayout.setRefreshing(false);
                    }
                    if (e == null) {
                        Log.d(TAG, "onResponseFromServer: Panchanga : " + (List<Panchanga>) objects);
                        if (objects.get(0) != null) {
                            Panchanga panchanga = (Panchanga) objects.get(0);
                            mPachangaDate.setText("Date : " + utils.getDateFromString(panchanga.getDate()));
                            mPachangaMonth.setText("Month : " + panchanga.getMonth());
                            mPachangaTithi.setText("Tithi : " + panchanga.getTithi());
                            mPachangaPaksham.setText("Paksham : " + panchanga.getPaksham());
                            mPachangaNakshatram.setText("Nakshatram : " + panchanga.getNakshatram());
                            mPachangaRahukalam.setText("Rahukalam : " + panchanga.getRahukalam());
                        }
                        panchaganaLoaded = true;
                    } else {
                        Log.e(TAG, "onResponseFromServer: Panchanga", e);
                    }
                }
            });
        }

    }

    protected void refreshEvents() {
        refreshLayout.setRefreshing(true);
        eventsLoaded = false;
        if (utils.isConnectedToNetwork(this)) {
            Calendar calendar = Calendar.getInstance();
            Date todayDate = calendar.getTime();
            calendar.add(Calendar.DATE, 3);
            NetworkHelper.getAllEvents("-dateTime", utils.getGMTDateString(calendar.getTime()), utils.getGMTDateString(todayDate), new ResponseInterface() {
                @Override
                public void onResponseFromServer(List<?> objects, Exception e) {
                    if (panchaganaLoaded) {
                        refreshLayout.setRefreshing(false);
                    }
                    if (e == null) {
                        events = (ArrayList<EventDataObject>) objects;
                        adapter.swap(events);
                        eventsLoaded = true;
                        Log.d(TAG, "onResponseFromServer: Events :" + (List<EventDataObject>) objects);
                    } else {
                        Log.e(TAG, "onResponseFromServer: Events", e);
                    }
                }
            });
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        pageIndicatorView.setSelection(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onItemClick(int position, Object data) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.see_all_events_btn:
                startActivity(new Intent(this, EventActivity.class));
                break;
        }
    }

    @Override
    public void onRefresh() {
        refreshEvents();
        refreshPanchanga();
    }
}
