package com.hpm.sp.streaminfoportal;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
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

import java.util.ArrayList;
import java.util.List;

public class PravachanaActivity extends AppCompatActivity {

    ArrayList<PravachanaDataObject> articleList = new ArrayList<>();
    public static JSONObject jsonObject = new JSONObject();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pravachana);

        getSupportActionBar().setTitle("Pravachanas");

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        PravachanaFetchHandler fetchArticles = new PravachanaFetchHandler("Put URL of server here");
        fetchArticles.execute();

        mRecyclerView = (RecyclerView) findViewById(R.id.pravachana_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        articleList.add(new PravachanaDataObject("Please refresh the list", " ", " "));
        mAdapter = new PravachanaRecyclerViewAdapter(articleList);
        mRecyclerView.setAdapter(mAdapter);

        System.out.println(jsonObject);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.refreshPravachana);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                articleList.clear();
                mAdapter = new PravachanaRecyclerViewAdapter(articleList);
                mRecyclerView.setAdapter(mAdapter);
                boolean connected = false;
                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    connected = true;
                }
                else
                    connected = false;
                if(connected){
                    mAdapter = new PravachanaRecyclerViewAdapter(refreshList());
                    System.out.println(mAdapter);
                    mRecyclerView.setAdapter(mAdapter);
                }
                else
                    Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();
            }
        });
    }

    protected ArrayList<PravachanaDataObject> refreshList(){
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            for(int i=0; i<jsonArray.length(); i++) {
                PravachanaDataObject dataObject = new PravachanaDataObject((String) jsonArray.getJSONObject(i).get("title"), (String) jsonArray.getJSONObject(i).get("description"), (String) jsonArray.getJSONObject(i).get("link"));
                articleList.add(dataObject);
            }
            if(jsonArray.length() == 0)
            {
                Toast.makeText(getApplicationContext(), "Network Error, Please try again later", Toast.LENGTH_SHORT).show();
            }
            return articleList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void onResume() {
        super.onResume();
        ((PravachanaRecyclerViewAdapter) mAdapter).setOnItemClickListener(new PravachanaRecyclerViewAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Uri articleUri = Uri.parse(((TextView) v.findViewById(R.id.videoLink)).getText().toString());
                System.out.println(articleUri.toString());
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, articleUri);
                intent.setPackage("com.google.android.youtube");
                startActivity(intent);
            }
        });
    }


}
