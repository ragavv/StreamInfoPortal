package com.hpm.sp.streaminfoportal.GuruParampare;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.hpm.sp.streaminfoportal.Interfaces.ResponseInterface;
import com.hpm.sp.streaminfoportal.Models.Guru;
import com.hpm.sp.streaminfoportal.Network.NetworkHelper;
import com.hpm.sp.streaminfoportal.R;
import com.hpm.sp.streaminfoportal.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuruParampareActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final String TAG = GuruParampareActivity.class.getSimpleName();
    private List<Guru> guruList = new ArrayList<>();
    private ProgressDialog mProgressDialog;
    private GuruParampareAdapter mAdapter;
    private Utils utils = new Utils();

    @BindView(R.id.gurus)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guru_parampare);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setTitle("Loading Guru List ...");
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(GuruParampareActivity.this, DividerItemDecoration.VERTICAL));

        fetchGurus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_guru_parampare, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
            searchView.setOnQueryTextListener(GuruParampareActivity.this);

            MenuItemCompat.setOnActionExpandListener(item,
                    new MenuItemCompat.OnActionExpandListener() {
                        @Override
                        public boolean onMenuItemActionCollapse(MenuItem item) {
                            mAdapter.setFilter(guruList);
                            return true; // Return true to collapse action view
                        }

                        @Override
                        public boolean onMenuItemActionExpand(MenuItem item) {
                            return true;
                        }
                    });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void fetchGurus() {
        if (utils.isConnectedToNetwork(this)) {
            mProgressDialog.show();
            NetworkHelper.getAllGurus(new ResponseInterface() {
                @Override
                public void onResponseFromServer(List<?> objects, Exception e) {
                    mProgressDialog.hide();
                    if (e == null) {
                        guruList = (List<Guru>) objects;
                        handleResponse();
                    } else {
                        Log.e(TAG, "onResponseFromServer: Error", e);
                    }
                }
            });
        }
    }

    private void handleResponse() {
        mAdapter = new GuruParampareAdapter(this, guruList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        final List<Guru> filteredModelList = filter(guruList, s);
        mAdapter.setFilter(filteredModelList);
        return true;
    }

    private List<Guru> filter(List<Guru> models, String query) {
        query = query.toLowerCase();
        final List<Guru> filteredModelList = new ArrayList<>();
        for (Guru model : models) {
            final String text = model.getName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mProgressDialog.dismiss();
    }
}
