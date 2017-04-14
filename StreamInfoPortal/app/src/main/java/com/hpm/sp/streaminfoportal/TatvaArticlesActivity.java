package com.hpm.sp.streaminfoportal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class TatvaArticlesActivity extends AppCompatActivity {

    ArrayList<String> articleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tatva_articles);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshList();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    protected void refreshList(){

        final ListView authorListView = (ListView) findViewById(R.id.authorList);

        articleList.clear();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, articleList);
        authorListView.setAdapter(adapter);

        articleList.add("Test 1");
        articleList.add("Test 2");
        articleList.add("Test 3");
        articleList.add("Test 4");
        articleList.add("Test 5");

        authorListView.setAdapter(adapter);

        authorListView.setClickable(true);
        authorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

    }

}
