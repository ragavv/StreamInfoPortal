package com.hpm.sp.streaminfoportal.GuruParampare;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.hpm.sp.streaminfoportal.Models.AradhaneDataObject;
import com.hpm.sp.streaminfoportal.Network.DatabaseHandler;
import com.hpm.sp.streaminfoportal.R;
import com.hpm.sp.streaminfoportal.Utils;

import java.sql.SQLException;
import java.util.List;

public class GuruParampareActivity extends AppCompatActivity {

    private static final String TAG = GuruParampareActivity.class.getSimpleName();
    private Utils utils = new Utils();
    private DatabaseHandler db = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guru_parampare);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }



    private String pad(String str, int size, char padChar) {
        StringBuilder sbr = new StringBuilder(str);
        while (sbr.length() < size) {
            sbr.append(padChar);
        }
        return sbr.toString();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}