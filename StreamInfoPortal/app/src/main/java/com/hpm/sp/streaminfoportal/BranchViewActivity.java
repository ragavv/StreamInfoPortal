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

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Our Branches");

        mRecyclerView = (RecyclerView) findViewById(R.id.branch_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        articleList.add(new BranchDataObject("Sri Sudarshan", "# 1, Benne Govindappa Road,Gandhibazaar, Bangalore, 560004", "080 2661 3348"));
        articleList.add(new BranchDataObject("Sri J N Gururaja Rao", "12/A, 1st cross, Ganesha Block,Mahalakshmi Layout, Bangalore, 560089", "+91-9945845014"));
        articleList.add(new BranchDataObject("Sri H N Nagarajachar", "Gopalakrishna Temple Street,Chickpet, Bangalore, 560002", "+91-93418 01596"));
        articleList.add(new BranchDataObject("Sri H G Narasimha Murthy", "Byrapura Post, Tirumakudalu,T Narasipura Dist.", "+91-98454 32984"));
        articleList.add(new BranchDataObject("Sri B R Anantharam", "226, 2nd cross,3rd main, Krishnamurthypuram,Mysore, 500004", "0821 2332676 / +91-96324 74208"));
        articleList.add(new BranchDataObject("Smt M N Padmavati", "12/A, 1st cross,Ganesha Block,Kshanayya Street, Srirangapatna, 571 438", "+91-9980349228"));
        articleList.add(new BranchDataObject("Sri A N Ramachandra", "Yelandur Branch, Chamrajnagar Dist", "0822 6240192 / +91-9980349228"));
        articleList.add(new BranchDataObject("Sri N Subrahmanya", "1944,Kotwal Ramaiah Street,Devaraj Mohalla Street, Mysore, 560 001", "+91-95909 76780"));
        articleList.add(new BranchDataObject("Sri GV Nagaraj/KR Prakash", "KR Road, Vidyanagar, Mandya", "08234 27974 / +91-87628 53196"));
        articleList.add(new BranchDataObject("Sri T R Prahlad Rao", "Chickpet, Achar Street, Tumkur, 572 101", "0816 2278590"));
        articleList.add(new BranchDataObject("Sri K V Gururaja Rao", "Fort Kanakapura, Ramanagara Dist.", "+91-98867 00358"));
        mAdapter = new BranchRecyclerViewAdapter(articleList);
        mRecyclerView.setAdapter(mAdapter);
    }


    protected void onResume() {
        super.onResume();
        ((BranchRecyclerViewAdapter) mAdapter).setOnItemClickListener(new BranchRecyclerViewAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                System.out.println("Yes...");
            }
        });
    }

}
