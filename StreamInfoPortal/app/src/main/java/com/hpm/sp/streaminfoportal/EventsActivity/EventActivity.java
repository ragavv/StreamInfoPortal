package com.hpm.sp.streaminfoportal.EventsActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.hpm.sp.streaminfoportal.Interfaces.ResponseInterface;
import com.hpm.sp.streaminfoportal.Network.NetworkHelper;
import com.hpm.sp.streaminfoportal.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EventActivity extends AppCompatActivity {

    ArrayList<EventDataObject> eventList = new ArrayList<>();
    public static JSONObject jsonObject = new JSONObject();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static final String TAG = EventActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Events");

//        EventFetchHandler fetchBranches = new EventFetchHandler("http://192.168.0.14:8888");
//        fetchBranches.execute();
//        System.out.println(jsonObject);



        mRecyclerView = (RecyclerView) findViewById(R.id.event_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventList.clear();
//                mAdapter = new EventRecyclerViewAdapter(refreshList());
//                mRecyclerView.setAdapter(mAdapter);
                refreshList();
            }
        });
        refreshList();

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected void refreshList() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            NetworkHelper.getAllEvents(new ResponseInterface() {
                @Override
                public void onResponseFromServer(List<?> objects, Exception e) {
                    if (e == null) {
                        Log.d(TAG, "onResponseFromServer: " + objects.get(0));
                        mAdapter = new EventRecyclerViewAdapter((ArrayList<EventDataObject>) objects);
                        mRecyclerView.setAdapter(mAdapter);
                        ((EventRecyclerViewAdapter) mAdapter).setOnItemClickListener(new EventRecyclerViewAdapter.MyClickListener() {
                            @Override
                            public void onItemClick(int position, View v) {
                                System.out.println(position);
                            }
                        });
                    } else {
                        Log.d(TAG, "onResponseFromServer: " + e);
                        e.printStackTrace();
                    }
                }
            });
        } else
            Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();

    }

    protected void onResume() {
        super.onResume();

    }

}
