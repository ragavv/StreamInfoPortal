package com.hpm.sp.streaminfoportal.EventsActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.hpm.sp.streaminfoportal.Constants;
import com.hpm.sp.streaminfoportal.EventDetailsActivity;
import com.hpm.sp.streaminfoportal.Interfaces.RecyclerViewClickListener;
import com.hpm.sp.streaminfoportal.Interfaces.ResponseInterface;
import com.hpm.sp.streaminfoportal.Models.EventDataObject;
import com.hpm.sp.streaminfoportal.Network.NetworkHelper;
import com.hpm.sp.streaminfoportal.R;
import com.hpm.sp.streaminfoportal.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

    @BindView(R.id.currentDate)
    TextView mCurrentDate;

    @BindView(R.id.noEvents)
    RelativeLayout mNoEvents;

    @BindView(R.id.main_layout)
    ConstraintLayout mMainLayout;

    @BindView(R.id.event_recycler_view)
    RecyclerView mRecyclerView;

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
        previousDate = new Date();
        mTitleText.setText(new SimpleDateFormat("MMMM").format(previousDate));
        appBarLayout.addOnOffsetChangedListener(this);
        appBarLayout.setExpanded(isExpanded);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading Events ... ");
        mCurrentDate.setText(new SimpleDateFormat("EEEE, dd-MMM-YYYY").format(previousDate));
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
        progressDialog.show();
        if (utils.isConnectedToNetwork(this)) {
            NetworkHelper.getAllEvents("dateTime", null, null, new ResponseInterface() {
                @Override
                public void onResponseFromServer(List<?> objects, Exception e) {
                    progressDialog.hide();
                    if (e == null) {
                        Log.d(TAG, "onResponseFromServer: " + objects.get(0));
                        eventList = (ArrayList<EventDataObject>) objects;
                        mAdapter = new EventRecyclerViewAdapter(Constants.VERTICAL);
                        mRecyclerView.setAdapter(mAdapter);
                        mRecyclerView.addItemDecoration(new DividerItemDecoration(EventActivity.this, DividerItemDecoration.VERTICAL));
                        mapEvents();
                        mAdapter.setOnItemClickListener(new RecyclerViewClickListener() {
                            @Override
                            public void onItemClick(int position, Object dataObject) {
//                                Intent intent = new Intent(EventActivity.this, EventDetailsActivity.class);
//                                intent.putExtra(Constants.EVENT_ITEM, (EventDataObject) dataObject);
//                                startActivity(intent);
                            }
                        });
                        updateList(widget, previousDate);
                    } else {
                        Log.d(TAG, "onResponseFromServer: " + e);
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void mapEvents() {
        for (EventDataObject event : eventList) {
            Event ev = new Event(Color.RED, event.getDateText().getTime(), event);
            widget.addEvent(ev, true);
        }
    }


    private void updateList(@NonNull CompactCalendarView widget, @NonNull Date date) {
        mCurrentDate.setText(new SimpleDateFormat("EEEE, dd-MMM-YYYY").format(date));
        ArrayList<EventDataObject> eventDataObjects = new ArrayList<>();
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
        updateList(widget, dateClicked);
    }

    @Override
    public void onMonthScroll(Date firstDayOfNewMonth) {
        mTitleText.setText(new SimpleDateFormat("MMMM").format(firstDayOfNewMonth));
    }

    @Override
    protected void onPause() {
        super.onPause();
        progressDialog.dismiss();
    }
}
