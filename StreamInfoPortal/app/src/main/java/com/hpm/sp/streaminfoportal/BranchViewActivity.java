package com.hpm.sp.streaminfoportal;

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

import org.json.JSONObject;

import java.util.ArrayList;

public class BranchViewActivity extends AppCompatActivity {

    ArrayList<BranchDataObject> articleList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.branch_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        articleList.add(new BranchDataObject("Please refresh the list", " ", " "));
        articleList.add(new BranchDataObject("Please refresh the list", " ", " "));
        articleList.add(new BranchDataObject("Please refresh the list", " ", " "));
        articleList.add(new BranchDataObject("Please refresh the list", " ", " "));
        articleList.add(new BranchDataObject("Please refresh the list", " ", " "));
        mAdapter = new BranchRecyclerViewAdapter(articleList);
        mRecyclerView.setAdapter(mAdapter);
    }


    protected void onResume() {
        super.onResume();
        ((BranchRecyclerViewAdapter) mAdapter).setOnItemClickListener(new BranchRecyclerViewAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                System.out.println("Oh yeah!");
            }
        });
    }

}
