package com.hpm.sp.streaminfoportal.EventsActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.hpm.sp.streaminfoportal.Constants;
import com.hpm.sp.streaminfoportal.EventDetailsActivity;
import com.hpm.sp.streaminfoportal.FreshBytViewPager;
import com.hpm.sp.streaminfoportal.Interfaces.RecyclerViewClickListener;
import com.hpm.sp.streaminfoportal.Interfaces.ResponseInterface;
import com.hpm.sp.streaminfoportal.Models.EventDataObject;
import com.hpm.sp.streaminfoportal.Models.PanchangaObject;
import com.hpm.sp.streaminfoportal.Network.DatabaseHandler;
import com.hpm.sp.streaminfoportal.Network.NetworkHelper;
import com.hpm.sp.streaminfoportal.R;
import com.hpm.sp.streaminfoportal.Utils;
import com.rd.PageIndicatorView;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventActivity extends AppCompatActivity implements
        AppBarLayout.OnOffsetChangedListener, CompactCalendarView.CompactCalendarViewListener {

    ArrayList<EventDataObject> eventList = new ArrayList<>();
    private EventRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static final String TAG = EventActivity.class.getSimpleName();
    private HashMap<String, List<EventDataObject>> mEventsMap = new HashMap<>();
    private Date previousDate;
    private ProgressDialog progressDialog;
    private DatabaseHandler db = null;
    private final Date minDate = Utils.getMinDate();
    private final Date maxDate = Utils.getMaxDate();


    @BindView(R.id.calendarView)
    CompactCalendarView widget;

    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;

    @BindView(R.id.collapsibleToolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

   @BindView(R.id.title)
   TextView mTitleText;

   @BindView(R.id.toolbar_month_parent)
   LinearLayout mMonthParent;

   @BindView(R.id.icon)
   ImageView mArrowIcon;

    /*@BindView(R.id.currentDate)
    TextView mCurrentDate;*/

    @BindView(R.id.panchanga_view_detail)
    RelativeLayout mNoEvents;

    @BindView(R.id.main_layout)
    ConstraintLayout mMainLayout;


    @BindView(R.id.event_recycler_view)
    RecyclerView mRecyclerView;


    @BindView(R.id.date)
    TextView mPachangaDate;

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


   /* @BindView(R.id.Raahu)
    TextView mPachangaRaahu;


    @BindView(R.id.Yamakanda)
    TextView mPachangaYama;*/

    @BindView(R.id.maasaNiyamaka)
    TextView maasaNiyamaka;


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

   /* @BindView(R.id.SSTime)
    TextView SunSetTime;

    @BindView(R.id.MRTime)
    TextView MoonRiseTime;

    @BindView(R.id.MSTime)
    TextView MoonSetTime;*/

    @BindView(R.id.prev_button)
    Button showPreviousMonthBut;

    @BindView(R.id.next_button)
    Button showNextMonthBut;

    //@BindView(R.id.see_all_events_btn)
    //TextView mSeeAllEventsBtn;

    //@BindView(R.id.swipe_container)
    //SwipeRefreshLayout refreshLayout;

    boolean isShow = true;
    int scrollRange = -1;
    boolean isExpanded = true;
    private Utils utils = new Utils();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        widget.setListener(this);
        widget.shouldScrollMonth(false);
        showPreviousMonthBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                widget.showPreviousMonth();
            }
        });

        showNextMonthBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                widget.showNextMonth();
            }
        });
        previousDate = new Date();
        mTitleText.setText(new SimpleDateFormat("MMM yyyy").format(previousDate));
        dateDisplay.setText(new SimpleDateFormat("MMM-dd-yyyy").format(previousDate));
        showPreviousMonthBut.setText("<<");
        showNextMonthBut.setText(">>");
        appBarLayout.addOnOffsetChangedListener(this);
        appBarLayout.setExpanded(isExpanded);
        mMonthParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: " + isExpanded);
                isExpanded = !isExpanded;
                appBarLayout.setExpanded(isExpanded);
            }
        });
        try {
            db = new DatabaseHandler(this);
        } catch (SQLException sqlEx) {

        }
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        String date = utils.getDateWithMonthAndYear(calendar.getTime());
        if(calendar.getTime().before(minDate)) {
            date=utils.getDateWithMonthAndYear(minDate);
            widget.setCurrentDate(minDate);
        }
        refreshPanchanga(db, date);

        refreshList();
    }

    protected void refreshList() {
        mAdapter = new EventRecyclerViewAdapter(Constants.VERTICAL);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(EventActivity.this, DividerItemDecoration.VERTICAL));
        mapEvents();
        if(previousDate.before(minDate)) {
            previousDate=minDate;
            widget.setCurrentDate(previousDate);
        }
        refreshPanchanga(db, utils.getDateWithMonthAndYear(previousDate));
        updateList(widget, previousDate);

    }

    protected void refreshPanchanga(DatabaseHandler db, String date) {
        try
        {
            if(utils.isConnectedToNetwork(this))
            {
                PanchangaObject pObject = NetworkHelper.getPanchangaForToday(db, date);
                mPachangaDate.setText("Date : " + date);
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
               // mPachangaRaahu.setText(pObject.getRaahu());
               // mPachangaYama.setText(pObject.getYama());
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
            }
     }catch(ParseException ex)
        {
        }

    }

    private void mapEvents() {
        for (EventDataObject event : eventList) {
            Event ev = new Event(Color.RED, event.getDateText().getTime(), event);
            widget.addEvent(ev, true);
        }
    }


    private void updateList(@NonNull CompactCalendarView widget, @NonNull Date date) {
        //mCurrentDate.setText(new SimpleDateFormat("EEEE, dd-MMM-YYYY").format(date));
        ArrayList<EventDataObject> eventDataObjects = new ArrayList<>();
        refreshPanchanga(db,utils.getDateWithMonthAndYear(date));
        for (Event event : widget.getEvents(date)) {
            eventDataObjects.add((EventDataObject) event.getData());
        }
        mAdapter.swap(eventDataObjects);
        if (eventDataObjects.size() == 0) {
            mRecyclerView.setVisibility(View.GONE);
            mNoEvents.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            mNoEvents.setVisibility(View.GONE);
        }
        mTitleText.setText(new SimpleDateFormat("MMM yyyy").format(date));
        dateDisplay.setText(new SimpleDateFormat("MMM-dd-yyyy").format(date));
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (scrollRange == -1) {
            scrollRange = appBarLayout.getTotalScrollRange();
        }
        if (scrollRange + verticalOffset == 0) {
            collapsingToolbarLayout.setTitle(" ");
            isShow = true;
            isExpanded = false;
            mArrowIcon.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
        } else if (isShow) {
            collapsingToolbarLayout.setTitle(" ");
            isShow = false;
            isExpanded = true;
            mArrowIcon.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
        }
    }

    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onDayClick(Date dateClicked) {
        previousDate = dateClicked;
        if(dateClicked.before(Utils.getMinDate())) {
            dateClicked = minDate;
            widget.setCurrentDate(dateClicked);
        }
        else if (dateClicked.after(maxDate)) {
            dateClicked = maxDate;
            widget.setCurrentDate(dateClicked);
        }

        updateList(widget, dateClicked);
    }

    @Override
    public void onMonthScroll(Date firstDayOfNewMonth) {
        mTitleText.setText(new SimpleDateFormat("MMM yyyy").format(firstDayOfNewMonth));
        //showPreviousMonthBut.setText(Utils.getPreviousMonth(firstDayOfNewMonth));
        //showNextMonthBut.setText(Utils.getNextMonth(firstDayOfNewMonth));
        showPreviousMonthBut.setText("<<");
        showNextMonthBut.setText(">>");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //progressDialog.dismiss();
    }


}
