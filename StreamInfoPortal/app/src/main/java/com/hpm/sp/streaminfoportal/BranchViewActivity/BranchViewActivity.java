package com.hpm.sp.streaminfoportal.BranchViewActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.hpm.sp.streaminfoportal.BranchDataObject;
import com.hpm.sp.streaminfoportal.Interfaces.RecyclerViewClickListener;
import com.hpm.sp.streaminfoportal.Interfaces.ResponseInterface;
import com.hpm.sp.streaminfoportal.Models.Branch;
import com.hpm.sp.streaminfoportal.Network.NetworkHelper;
import com.hpm.sp.streaminfoportal.R;
import com.hpm.sp.streaminfoportal.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BranchViewActivity extends AppCompatActivity implements RecyclerViewClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = BranchViewActivity.class.getSimpleName();
    private List<Branch> branchList;
    private BranchRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Utils utils;

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.branch_recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Our Branches");

        ButterKnife.bind(this);

        utils = new Utils();
        mRecyclerView.setHasFixedSize(true);

//        articleList.add(new BranchDataObject("Sri Sudarshan", "# 1, Benne Govindappa Road,Gandhibazaar, Bangalore, 560004", "080 2661 3348"));
//        articleList.add(new BranchDataObject("Sri J N Gururaja Rao", "12/A, 1st cross, Ganesha Block,Mahalakshmi Layout, Bangalore, 560089", "+91-9945845014"));
//        articleList.add(new BranchDataObject("Sri H N Nagarajachar", "Gopalakrishna Temple Street,Chickpet, Bangalore, 560002", "+91-93418 01596"));
//        articleList.add(new BranchDataObject("Sri H G Narasimha Murthy", "Byrapura Post, Tirumakudalu,T Narasipura Dist.", "+91-98454 32984"));
//        articleList.add(new BranchDataObject("Sri B R Anantharam", "226, 2nd cross,3rd main, Krishnamurthypuram,Mysore, 500004", "0821 2332676 / +91-96324 74208"));
//        articleList.add(new BranchDataObject("Smt M N Padmavati", "12/A, 1st cross,Ganesha Block,Kshanayya Street, Srirangapatna, 571 438", "+91-9980349228"));
//        articleList.add(new BranchDataObject("Sri A N Ramachandra", "Yelandur Branch, Chamrajnagar Dist", "0822 6240192 / +91-9980349228"));
//        articleList.add(new BranchDataObject("Sri N Subrahmanya", "1944,Kotwal Ramaiah Street,Devaraj Mohalla Street, Mysore, 560 001", "+91-95909 76780"));
//        articleList.add(new BranchDataObject("Sri GV Nagaraj/KR Prakash", "KR Road, Vidyanagar, Mandya", "08234 27974 / +91-87628 53196"));
//        articleList.add(new BranchDataObject("Sri T R Prahlad Rao", "Chickpet, Achar Street, Tumkur, 572 101", "0816 2278590"));
//        articleList.add(new BranchDataObject("Sri K V Gururaja Rao", "Fort Kanakapura, Ramanagara Dist.", "+91-98867 00358"));

        branchList = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(BranchViewActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new BranchRecyclerViewAdapter(branchList);
        mRecyclerView.setAdapter(mAdapter);
//        mRecyclerView.addItemDecoration(new HeaderItemDecoration(mAdapter));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter.setOnItemClickListener(BranchViewActivity.this);
        refreshLayout.setOnRefreshListener(this);
        fetchBranches();
    }

    private void fetchBranches() {
        if (utils.isConnectedToNetwork(this)) {
            refreshLayout.setRefreshing(true);
            NetworkHelper.getAllBranches(new ResponseInterface() {
                @Override
                public void onResponseFromServer(List<?> objects, Exception e) {
                    refreshLayout.setRefreshing(false);
                    if (e == null) {
                        branchList = (List<Branch>) objects;
                        mAdapter.swap(branchList);
                    } else {
                        Log.d(TAG, "onResponseFromServer: Error", e);
                    }
                }
            });
        }
    }


    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onItemClick(int position, Object data) {
        Branch branch = (Branch) data;
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", branch.getContactURI(), "/")));
    }

    @Override
    public void onRefresh() {
        fetchBranches();
    }
}
