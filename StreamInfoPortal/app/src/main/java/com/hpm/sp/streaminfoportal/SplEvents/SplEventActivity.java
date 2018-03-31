package com.hpm.sp.streaminfoportal.SplEvents;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.hpm.sp.streaminfoportal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SplEventActivity extends AppCompatActivity {

    ArrayList<SplEventDataObject> eventList = new ArrayList<>();
    public static JSONObject jsonObject = new JSONObject();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private WebView webview ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splevent);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Special Events");

        //webview use to call own site
        webview =(WebView)findViewById(R.id.webView);

        webview.setWebViewClient(new WebViewClient());
        webview .getSettings().setJavaScriptEnabled(true);
        webview .getSettings().setDomStorageEnabled(true);
        webview.loadUrl("https://sosalevyasarajamatha.wordpress.com/events");

        /*SplEventFetchHandler fetchBranches = new SplEventFetchHandler("https://public-api.wordpress.com/rest/v1.1/sites/144047956/posts/18");
        // SplEventFetchHandler fetchBranches = new SplEventFetchHandler("http://vyasa.heliohost.org/eventsList.php");
        fetchBranches.execute();
        System.out.println(jsonObject);

        mRecyclerView = (RecyclerView) findViewById(R.id.splevent_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        eventList.clear();
        mAdapter = new SplEventRecyclerViewAdapter(refreshList());
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventList.clear();
                mAdapter = new SplEventRecyclerViewAdapter(refreshList());
                mRecyclerView.setAdapter(mAdapter);
            }
        });


        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            eventList.clear();
            mAdapter = new SplEventRecyclerViewAdapter(refreshList());
            mRecyclerView.setAdapter(mAdapter);
        }
        else
            Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();*/

       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected ArrayList<SplEventDataObject> refreshList(){
        try {
            /*JSONArray jsonArray = jsonObject.getJSONArray("keys");
            for(int i=0; i<jsonArray.length(); i++) {*/
                DateFormat yyFormat = new SimpleDateFormat("yyyy-MM-dd");
                DateFormat ddFormat = new SimpleDateFormat("dd MMM yyyy");
                //String inputDate = (String) jsonArray.getJSONObject(i).get("date");
                String inputDate = "2018-03-23";
                Date eventDate = null;
                try {
                    eventDate = yyFormat.parse(inputDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //SplEventDataObject dataObject = new SplEventDataObject((String) jsonArray.getJSONObject(i).get("name"), ddFormat.format(eventDate), (String) jsonArray.getJSONObject(i).get("time"), (String) jsonArray.getJSONObject(i).get("location"), (String) jsonArray.getJSONObject(i).get("details"));
                SplEventDataObject dataObject = new SplEventDataObject(android.text.Html.fromHtml((String) jsonObject.get("content")).toString());
                eventList.add(dataObject);
            //}
            /*if(jsonArray.length() == 0)
            {
                Toast.makeText(getApplicationContext(), "Network error, Please try again later", Toast.LENGTH_SHORT).show();
            }*/
            return eventList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void onResume() {
        super.onResume();
        /*((SplEventRecyclerViewAdapter) mAdapter).setOnItemClickListener(new SplEventRecyclerViewAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                System.out.println(position);
            }
        });*/
    }

}
