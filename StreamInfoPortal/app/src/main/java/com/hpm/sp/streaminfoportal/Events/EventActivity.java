package com.hpm.sp.streaminfoportal.Events;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hpm.sp.streaminfoportal.Constants;
import com.hpm.sp.streaminfoportal.EventDetailsActivity;
import com.hpm.sp.streaminfoportal.Interfaces.ResponseInterface;
import com.hpm.sp.streaminfoportal.Models.EventDataObject;
import com.hpm.sp.streaminfoportal.Network.NetworkHelper;
import com.hpm.sp.streaminfoportal.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventActivity extends AppCompatActivity implements
        OnDateSelectedListener,
        AppBarLayout.OnOffsetChangedListener,
        OnMonthChangedListener {

    ArrayList<EventDataObject> eventList = new ArrayList<>();
    public static JSONObject jsonObject = new JSONObject();
    private RecyclerView mRecyclerView;
    private EventRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static final String TAG = EventActivity.class.getSimpleName();
    private HashMap<String, List<EventDataObject>> mEventsMap = new HashMap<>();
    private CalendarDay previousDate;
    private ProgressDialog progressDialog;

    @BindView(R.id.calendarView)
    MaterialCalendarView widget;

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

    @BindView(R.id.currentDate)
    TextView mCurrentDate;

    @BindView(R.id.noEvents)
    RelativeLayout mNoEvents;

    boolean isShow = true;
    int scrollRange = -1;
    boolean isExpanded = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.event_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        widget.setOnDateChangedListener(this);
        widget.setOnMonthChangedListener(this);
        previousDate = new CalendarDay(new Date());
        widget.setSelectedDate(previousDate.getDate());
        widget.addDecorator(new SelectedDateDecorator(Color.WHITE, previousDate));
        mTitleText.setText(new SimpleDateFormat("MMMM").format(widget.getSelectedDate().getCalendar().getTime()));
        appBarLayout.addOnOffsetChangedListener(this);
        appBarLayout.setExpanded(isExpanded);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading Events ... ");
        mCurrentDate.setText(new SimpleDateFormat("EEEE, dd-MMM-YYYY").format(widget.getSelectedDate().getCalendar().getTime()));
        mMonthParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: " + isExpanded);
                isExpanded = !isExpanded;
                appBarLayout.setExpanded(isExpanded);
            }
        });

        refreshList();

    }

    protected void refreshList() {
        mRecyclerView.setVisibility(View.GONE);
        progressDialog.show();
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            NetworkHelper.getAllEvents(new ResponseInterface() {
                @Override
                public void onResponseFromServer(List<?> objects, Exception e) {
                    progressDialog.hide();
                    mRecyclerView.setVisibility(View.VISIBLE);
                    if (e == null) {
                        Log.d(TAG, "onResponseFromServer: " + objects.get(0));
                        eventList = (ArrayList<EventDataObject>) objects;
                        mAdapter = new EventRecyclerViewAdapter();
                        mRecyclerView.setAdapter(mAdapter);
                        mapEvents();
                        mAdapter.setOnItemClickListener(new EventRecyclerViewAdapter.MyClickListener() {
                            @Override
                            public void onItemClick(EventDataObject dataObject, TextView v) {
                                Intent intent = new Intent(EventActivity.this, EventDetailsActivity.class);
                                intent.putExtra(Constants.EVENT_ITEM, dataObject);
//                                intent.putExtra(Constants.EVENT_TRANSITION_ELEMENT, Constants.EVENT_TRANSITION_NAME);
//
//                                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                                        EventActivity.this,
//                                        v,
//                                        Constants.EVENT_TRANSITION_NAME);

//                                startActivity(intent, options.toBundle());
                                startActivity(intent);
                            }
                        });
                        addDecorators();
                        updateList(widget, previousDate);
                    } else {
                        Log.d(TAG, "onResponseFromServer: " + e);
                        e.printStackTrace();
                    }
                }
            });
        } else
            Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();
    }

    private void mapEvents() {
        for (EventDataObject event : eventList) {
            String key = new SimpleDateFormat("dd-MMM-YYYY").format(event.getDateText());
            if (mEventsMap.containsKey(key)) {
                List<EventDataObject> events = mEventsMap.get(key);
                events.add(event);
                mEventsMap.put(key, events);
            } else {
                List<EventDataObject> events = new ArrayList<>();
                events.add(event);
                mEventsMap.put(key, events);
            }
        }
    }

    private void addDecorators() {
        List<CalendarDay> calendarDays = new ArrayList<>();
        for (EventDataObject event : eventList) {
            calendarDays.add(CalendarDay.from(event.getDateText()));
        }
        widget.addDecorator(new EventDecorator(Color.BLUE, calendarDays));
    }


    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        addDecorators();
        if (previousDate != null) {
            widget.addDecorator(new SelectedDateDecorator(Color.BLACK, previousDate));
        }
        previousDate = date;
        updateList(widget, date);
    }

    private void updateList(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date) {
        widget.addDecorator(new SelectedDateDecorator(Color.WHITE, date));
        mCurrentDate.setText(new SimpleDateFormat("EEEE, dd-MMM-YYYY").format(widget.getSelectedDate().getCalendar().getTime()));
        ArrayList<EventDataObject> eventDataObjects = (ArrayList<EventDataObject>) mEventsMap.get(new SimpleDateFormat("dd-MMM-YYYY").format(widget.getSelectedDate().getCalendar().getTime()));
        mAdapter.swap(eventDataObjects);
        if (eventDataObjects == null || eventDataObjects.size() == 0) {
            mRecyclerView.setVisibility(View.GONE);
            mNoEvents.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            mNoEvents.setVisibility(View.GONE);
        }
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

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        Log.d(TAG, "onMonthChanged: " + date);
        mTitleText.setText(new SimpleDateFormat("MMMM").format(date.getCalendar().getTime()));
    }

    protected void onResume() {
        super.onResume();
    }
}
