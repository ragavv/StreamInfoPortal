package com.hpm.sp.streaminfoportal;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EventActivity extends AppCompatActivity {

    ArrayList<EventDataObject> eventList = new ArrayList<>();
    public static JSONObject jsonObject = new JSONObject();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Events");

        EventFetchHandler fetchBranches = new EventFetchHandler("Put URL of server here");
        fetchBranches.execute();
        System.out.println(jsonObject);

        mRecyclerView = (RecyclerView) findViewById(R.id.event_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventList.clear();
                mAdapter = new EventRecyclerViewAdapter(refreshList());
                mRecyclerView.setAdapter(mAdapter);
            }
        });


        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            mAdapter = new EventRecyclerViewAdapter(refreshList());
            mRecyclerView.setAdapter(mAdapter);
        }
        else
            Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected ArrayList<EventDataObject> refreshList(){
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            for(int i=0; i<jsonArray.length(); i++) {
                DateFormat yyFormat = new SimpleDateFormat("yyyy-MM-dd");
                DateFormat ddFormat = new SimpleDateFormat("dd MMM yyyy");
                String inputDate = (String) jsonArray.getJSONObject(i).get("date");
                Date eventDate = null;
                try {
                    eventDate = yyFormat.parse(inputDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                EventDataObject dataObject = new EventDataObject((String) jsonArray.getJSONObject(i).get("name"), ddFormat.format(eventDate), (String) jsonArray.getJSONObject(i).get("time"), (String) jsonArray.getJSONObject(i).get("location"), (String) jsonArray.getJSONObject(i).get("details"));
                eventList.add(dataObject);
            }
            if(jsonArray.length() == 0)
            {
                Toast.makeText(getApplicationContext(), "Network error, Please try again later", Toast.LENGTH_SHORT).show();
            }
            return eventList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void onResume() {
        super.onResume();
        ((EventRecyclerViewAdapter) mAdapter).setOnItemClickListener(new EventRecyclerViewAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                System.out.println(position);
            }
        });
    }

}
