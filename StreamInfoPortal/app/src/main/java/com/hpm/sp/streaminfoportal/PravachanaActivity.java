package com.hpm.sp.streaminfoportal;

import android.annotation.SuppressLint;
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
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hpm.sp.streaminfoportal.Interfaces.RecyclerViewClickListener;
import com.hpm.sp.streaminfoportal.Interfaces.ResponseInterface;
import com.hpm.sp.streaminfoportal.Models.Video;
import com.hpm.sp.streaminfoportal.Network.NetworkHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PravachanaActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, RecyclerViewClickListener {

    private RecyclerView mRecyclerView;
    private PravachanaRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeLayout;
    public static final String TAG = PravachanaActivity.class.getSimpleName();

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pravachana);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setRefreshing(true);
        mRecyclerView = (RecyclerView) findViewById(R.id.pravachana_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        fetchVideos();
    }

    private void fetchVideos() {
        NetworkHelper.getAllVideos(new ResponseInterface() {
            @Override
            public void onResponseFromServer(List<?> objects, Exception e) {
                swipeLayout.setRefreshing(false);
                if (e == null) {
                    ArrayList<Video> videos = new ArrayList<>();
                    for (Video video : (ArrayList<Video>) objects) {
                        if (video.getId().getVideoId() != null) {
                            videos.add(video);
                        }
                    }
                    mAdapter = new PravachanaRecyclerViewAdapter(PravachanaActivity.this, videos);
                    mRecyclerView.setAdapter(mAdapter);
                    if (mAdapter != null) {
                        initListener();
                    }
                } else {
                    Log.d(TAG, "onResponseFromServer: " + e);
                    e.printStackTrace();
                }
            }
        });
    }

    protected void onResume() {
        super.onResume();
        if (mAdapter != null) {
            initListener();
        }
    }

    private void initListener() {
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onRefresh() {
        fetchVideos();
    }

    @Override
    public void onItemClick(int position, Object data) {
        Uri articleUri = Uri.parse("http://www.youtube.com/watch?v=" + ((Video)data).getId().getVideoId());
        System.out.println(articleUri.toString());
        Intent intent = new Intent(Intent.ACTION_VIEW, articleUri);
        intent.setPackage("com.google.android.youtube");
        startActivity(intent);
    }
}
