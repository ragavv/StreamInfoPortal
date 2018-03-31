package com.hpm.sp.streaminfoportal.Main;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

//import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.hpm.sp.streaminfoportal.AboutUsActivity.AboutUsActivity;
import com.hpm.sp.streaminfoportal.AboutUsActivity.VyasarajaruActivity;
import com.hpm.sp.streaminfoportal.Announcements.AnnouncementActivity;
import com.hpm.sp.streaminfoportal.AradhaneActivity.AradhaneActivity;
import com.hpm.sp.streaminfoportal.AradhaneActivity.AradhaneRecyclerViewAdapter;
import com.hpm.sp.streaminfoportal.Constants;
import com.hpm.sp.streaminfoportal.Downloads.DownloadActivity;
import com.hpm.sp.streaminfoportal.EkadashiActivity.EkadashiActivity;
import com.hpm.sp.streaminfoportal.EventsActivity.EventActivity;
import com.hpm.sp.streaminfoportal.EventsActivity.EventRecyclerViewAdapter;
import com.hpm.sp.streaminfoportal.FreshBytViewPager;
import com.hpm.sp.streaminfoportal.GuruParampare.GuruParampareActivity;
import com.hpm.sp.streaminfoportal.Interfaces.RecyclerViewClickListener;
import com.hpm.sp.streaminfoportal.Interfaces.ResponseInterface;
import com.hpm.sp.streaminfoportal.Models.EventDataObject;
import com.hpm.sp.streaminfoportal.Models.PanchangaObject;
import com.hpm.sp.streaminfoportal.Network.DatabaseHandler;
import com.hpm.sp.streaminfoportal.Network.NetworkHelper;
import com.hpm.sp.streaminfoportal.R;
import com.hpm.sp.streaminfoportal.SplEvents.SplEventActivity;
import com.hpm.sp.streaminfoportal.Utils;
import com.hpm.sp.streaminfoportal.app.Config;
import com.hpm.sp.streaminfoportal.service.MessageReceiver;
import com.hpm.sp.streaminfoportal.utils.NotificationUtils;
import com.rd.PageIndicatorView;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener, RecyclerViewClickListener, View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Utils utils = new Utils();
    private EventRecyclerViewAdapter adapter;
    public static final int MY_PERMISSIONS_REQUEST_CALL = 99;
    private ArrayList<EventDataObject> events;
    private final Date minDate = Utils.getMinDate();
    private final Date maxDate = Utils.getMaxDate();

    @BindView(R.id.viewPager)
    FreshBytViewPager pager;

    @BindView(R.id.pageIndicatorView)
    PageIndicatorView pageIndicatorView;


    // @BindView(R.id.main_recycler_view)
    //RecyclerView recyclerView;

    @BindView(R.id.dateDisplay)
    TextView dateDisplay;


    @BindView(R.id.Samvatsara)
    TextView mPanchangaYear;

    @BindView(R.id.Ayana)
    TextView mPanchangaAyana;

    @BindView(R.id.Ritu)
    TextView mPachangaRitu;

    @BindView(R.id.Maasa)
    TextView mPachangaMonth;

    @BindView(R.id.Paksha)
    TextView mPachangaPaksham;

    @BindView(R.id.thithi)
    TextView mPachangaTithi;

    @BindView(R.id.Vaar)
    TextView mPachangaVaara;

    @BindView(R.id.nakshatram)
    TextView mPachangaNakshatram;


    @BindView(R.id.Yoga)
    TextView mPachangaYoga;


    @BindView(R.id.Karana)
    TextView mPachangaKarana;

    @BindView(R.id.TEndTime)
    TextView mPachangaTEndTime;


    @BindView(R.id.NEndTime)
    TextView mPachangaNEndTime;


    @BindView(R.id.ShThithi)
    TextView mPachangaShThithi;


    @BindView(R.id.maasaNiyamaka)
    TextView maasaNiyamaka;


    /*@BindView(R.id.Raahu)
    TextView mPachangaRaahu;


    @BindView(R.id.Yamakanda)
    TextView mPachangaYama;*/


    @BindView(R.id.Events)
    TextView mPachangaEvents;

    @BindView(R.id.YEndTime)
    TextView YEndTime;

    @BindView(R.id.KEndTime)
    TextView KEndTime;

    @BindView(R.id.ShTEndTime)
    TextView ShTEndTime;

   /* @BindView(R.id.Guli)
    TextView gulika;*/

    @BindView(R.id.SRTime)
    TextView SunRiseTime;

  /*  @BindView(R.id.SSTime)
    TextView SunSetTime;

    @BindView(R.id.MRTime)
    TextView MoonRiseTime;

    @BindView(R.id.MSTime)
    TextView MoonSetTime;*/

    //@BindView(R.id.see_all_events_btn)
    //TextView mSeeAllEventsBtn;

    //@BindView(R.id.swipe_container)
    //LinearLayout refreshLayout;

    private Boolean eventsLoaded = false;
    private Boolean panchaganaLoaded = false;
    private DatabaseHandler db = null;

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private TextView txtRegId, txtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtRegId = (TextView) findViewById(R.id.txt_reg_id);
        txtMessage = (TextView) findViewById(R.id.txt_push_message);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                    txtMessage.setText(message);
                }
            }
        };

        displayFirebaseRegId();

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
        //imageList.add("https://ssm.kumardivyarajat.com/assets/home_image_1.jpeg");
        imageList.add(Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.home_image_1).toString());
        imageList.add(Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.home_image_2).toString());
        imageList.add(Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.home_image_3).toString());
        pager.setAdapter(new HomePagerAdapter(getSupportFragmentManager(), imageList));
        pager.addOnPageChangeListener(this);
        setupAutoPager(pager, imageList.size());

        //recyclerView.setHasFixedSize(true);
        events = new ArrayList<>();
        adapter = new EventRecyclerViewAdapter((ArrayList<EventDataObject>) events, Constants.HORIZONTAL);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        //recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        //recyclerView.setClipToPadding(true);
        //mSeeAllEventsBtn.setOnClickListener(this);
        //refreshLayout.setOnRefreshListener(this);
        try {
            db = new DatabaseHandler(this);
        } catch (SQLException sqlEx) {

        }
        refreshPanchanga(db);
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


    private int currentPage = 0;

    private void setupAutoPager(final FreshBytViewPager viewPager, final Integer size) {
        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            public void run() {

                viewPager.setCurrentItem(currentPage, true);
                if (currentPage == size) {
                    currentPage = 0;
                } else {
                    ++currentPage;
                }
            }
        };


        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 500, 2500);
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
        } /*else if (id == R.id.nav_about) {
            openNewActivity(AboutUsActivity.class);
        }*/ else if (id == R.id.nav_aboutUs) {
            openNewActivity(AboutUsActivity.class);
        } else if (id == R.id.nav_about) {
            openNewActivity(VyasarajaruActivity.class);
        } else if (id == R.id.nav_parampara) {
            openNewActivity(GuruParampareActivity.class);

        } else if (id == R.id.nav_downloads) {
            openNewActivity(DownloadActivity.class);

        } else if (id == R.id.nav_aradhane) {
            openNewActivity(AradhaneActivity.class);

        } else if (id == R.id.nav_splEvents) {
            Intent viewEventsIntent = new Intent(this, SplEventActivity.class);
            startActivity(viewEventsIntent);

        } else if (id == R.id.nav_announcements) {
            Intent viewEventsIntent = new Intent(this, AnnouncementActivity.class);
            startActivity(viewEventsIntent);

        } else if (id == R.id.nav_pravachanas) {
            //openNewActivity(PravachanaActivity.class);
            String url = "https://www.youtube.com/channel/UCImc0ySbFzBIKwuztyEUG-w/videos";
            //https://www.googleapis.com/youtube/v3/search?key=AIzaSyCxFBLqmxn4EFV4yu_PHArOTabQsg7QCWA&channelId=UCImc0ySbFzBIKwuztyEUG&part=snippet,id&order=date&maxResults=20"
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
        } /*else if(id == R.id.nav_phone){
            String number = "+919840174694";
            Intent phoneIntent = new Intent(Intent.ACTION_CALL);
            phoneIntent.setData(Uri.parse("tel:"+ number));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                startActivity(phoneIntent);
            }else
            {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_CALL);
                startActivity(phoneIntent);
            }

        }*/ else if (id == R.id.nav_paata) {
            String url = "https://www.youtube.com/channel/UCmUHkSDsogUwCnuiZcjgnIA/videos";
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
        } else if (id == R.id.nav_panchanga) {
            openNewActivity(EventActivity.class);
        } /*else if (id == R.id.nav_mail) {
            openNewActivity(ContactInfoActivity.class);
        } else if (id == R.id.nav_branch) {
            openNewActivity(BranchViewActivity.class);
        } else if (id == R.id.nav_tatva) {
            openNewActivity(TatvaArticlesActivity.class);
        } */ else if (id == R.id.nav_ekadashi) {
            openNewActivity(EkadashiActivity.class);
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
        } else if (id == R.id.nav_twitter) {
            String url = "https://twitter.com/SrishaTirtha/";
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
        } else if (id == R.id.nav_web) {
            String url = "http://srivyasarajamathasosale.org";
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


    protected void refreshPanchanga(DatabaseHandler db) {
        try {
            panchaganaLoaded = false;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date todayDate = calendar.getTime();
            if (todayDate.before(minDate))
                todayDate = minDate;
            else if (todayDate.after(maxDate))
                todayDate = maxDate;
            String date = utils.getDateWithMonthAndYear(todayDate);
            if (utils.isConnectedToNetwork(this)) {
                PanchangaObject pObject = NetworkHelper.getPanchangaForToday(db, date);
               /* if (eventsLoaded) {
                    refreshLayout.setRefreshing(false);
                }*/
                //mPachangaDate.setText(utils.getDateWithMonth(todayDate));
                dateDisplay.setText(utils.getDateWithMonth(todayDate));
                mPanchangaYear.setText(pObject.getSamvatsara());
                mPanchangaAyana.setText(pObject.getAyana());
                mPachangaRitu.setText(pObject.getRuthu());
                mPachangaMonth.setText(pObject.getMaasa());
                mPachangaPaksham.setText(pObject.getPaksha());
                mPachangaTithi.setText(pObject.getThithi());
                mPachangaVaara.setText(pObject.getVaasara());
                mPachangaNakshatram.setText(pObject.getNakshathra());
                mPachangaYoga.setText(pObject.getYoga());
                mPachangaKarana.setText(pObject.getKarana());
                mPachangaTEndTime.setText(pObject.gettEndTime());
                mPachangaNEndTime.setText(pObject.getnEndTime());
                mPachangaShThithi.setText(pObject.getShThithi());
                //mPachangaRaahu.setText(pObject.getRaahu());
                //mPachangaYama.setText(pObject.getYama());
                mPachangaEvents.setText(pObject.getEvents());
                YEndTime.setText(pObject.getyEndTime());
                KEndTime.setText(pObject.getkEndTime());
                ShTEndTime.setText(pObject.getShTEndTime());
                //gulika.setText(pObject.getGulika());
                SunRiseTime.setText(pObject.getSunriseTime());
                maasaNiyamaka.setText(pObject.getMaasaNiyamaka());
                //SunSetTime.setText(pObject.getSunsetTime());
                //MoonRiseTime.setText(pObject.getMoonriseTime());
                //MoonSetTime.setText(pObject.getMoonsetTime());

                panchaganaLoaded = true;
            }
        } catch (ParseException ex) {

        }

    }

    protected void refreshEvents() {
        //refreshLayout.setRefreshing(true);
        eventsLoaded = false;
        if (utils.isConnectedToNetwork(this)) {
            Calendar calendar = Calendar.getInstance();
            Date todayDate = calendar.getTime();
            calendar.add(Calendar.DATE, 3);
            NetworkHelper.getAllEvents("-dateTime", utils.getGMTDateString(calendar.getTime()), utils.getGMTDateString(todayDate), new ResponseInterface() {
                @Override
                public void onResponseFromServer(List<?> objects, Exception e) {
                    /*if (panchaganaLoaded) {
                        refreshLayout.setRefreshing(false);
                    }*/
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
        /*switch (view.getId()) {

            case R.id.see_all_events_btn:
                startActivity(new Intent(this, AnnouncementActivity.class));
                break;
        }*/
    }

   /* @Override
    public void onRefresh() {
        refreshEvents();
        refreshPanchanga(db);
    }*/

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId))
            txtRegId.setText("Firebase Reg Id: " + regId);
        else
            txtRegId.setText("Firebase Reg Id is not received yet!");
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

}



