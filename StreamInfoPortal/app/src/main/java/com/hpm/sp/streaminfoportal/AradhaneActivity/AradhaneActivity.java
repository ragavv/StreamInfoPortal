package com.hpm.sp.streaminfoportal.AradhaneActivity;

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
import com.hpm.sp.streaminfoportal.Models.EkadashiDataObject;
import com.hpm.sp.streaminfoportal.Network.DatabaseHandler;
import com.hpm.sp.streaminfoportal.R;
import com.hpm.sp.streaminfoportal.Utils;

import java.sql.SQLException;
import java.util.List;

public class AradhaneActivity extends AppCompatActivity {

    private static final String TAG = AradhaneActivity.class.getSimpleName();
    private Utils utils = new Utils();
    private DatabaseHandler db = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aradhane);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DatabaseHandler db = null;
        Drawable drawable = null;
        String colorString = "#FFFFFF";
        try {
            db = new DatabaseHandler(this);
            TableLayout t2 = (TableLayout) findViewById(R.id.table22);
            List<AradhaneDataObject> abouts = db.getAradhaneDetails();
            addHeaderRowInTable(t2);
            for (int i = 0; i < abouts.size(); i++) {
                if(i%2 == 0)
                    drawable = ContextCompat.getDrawable(this,R.drawable.table_shape_white);
                else
                    drawable = ContextCompat.getDrawable(this,R.drawable.table_shape);
                AradhaneDataObject cn = abouts.get(i);
                // Create a TableRow and give it an ID
                TableRow tr = new TableRow(this);
                tr.setGravity(Gravity.CENTER);
                tr.setId(100 + i);
                tr.setLayoutParams(new ActionBar.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));

                TextView labelTV = new TextView(this);
                labelTV.setId(200 + i);
                labelTV.setText(cn.getNameText());
                labelTV.setTextSize(14);
                labelTV.setWidth(0);
                labelTV.setPadding(10,0,0,0);
                //labelTV.setSingleLine(false);
                // labelTV.setLayoutParams(new TableRow.LayoutParams(
                //       TableRow.LayoutParams.MATCH_PARENT,
                //     TableRow.LayoutParams.MATCH_PARENT));
                labelTV.setGravity(Gravity.LEFT);
                labelTV.setTextColor(Color.DKGRAY);
                labelTV.setBackground(drawable);
                tr.addView(labelTV);

                TextView valueTV = new TextView(this);
                /*valueTV = new TextView(this);
                valueTV.setId(400 + i);
                valueTV.setText(cn.getBrindavanaText());
                valueTV.setTextSize(14);
                valueTV.setWidth(0);
                //valueTV.setSingleLine(false);
                /*valueTV.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                valueTV.setGravity(Gravity.CENTER);
                valueTV.setTextColor(Color.DKGRAY);
                valueTV.setBackground(drawable);
                tr.addView(valueTV);*/

                valueTV = new TextView(this);
                valueTV.setId(600 + i);
                valueTV.setWidth(0);
                valueTV.setText(cn.getDateText());
                valueTV.setTextSize(14);
                //valueTV.setSingleLine(false);
                /*valueTV.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT))*/;
                valueTV.setGravity(Gravity.CENTER);
                valueTV.setTextColor(Color.DKGRAY);
                valueTV.setBackground(drawable);
                tr.addView(valueTV);

                t2.setColumnStretchable(0, true);
                t2.setColumnStretchable(1, true);
                t2.addView(tr, new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));
            }

            db.close();

        } catch (SQLException ex) {

        }
    }

    private void addHeaderRowInTable(TableLayout t2){
        Drawable drawable = ContextCompat.getDrawable(this,R.drawable.table_shape);
        TableRow tr = new TableRow(this);
        tr.setGravity(Gravity.CENTER);
        tr.setLayoutParams(new ActionBar.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));

        TextView labelTV = new TextView(this);
        labelTV.setText("Gurugalu");
        labelTV.setTextSize(14);
        labelTV.setWidth(0);
        //labelTV.setSingleLine(false);
        // labelTV.setLayoutParams(new TableRow.LayoutParams(
        //       TableRow.LayoutParams.MATCH_PARENT,
        //     TableRow.LayoutParams.MATCH_PARENT));
        labelTV.setGravity(Gravity.CENTER);
        labelTV.setTextColor(Color.DKGRAY);
        labelTV.setBackground(drawable);
        tr.addView(labelTV);

        TextView valueTV = new TextView(this);
        /*valueTV = new TextView(this);
        valueTV.setText("Brindavana");
        valueTV.setTextSize(14);
        valueTV.setWidth(0);
        //valueTV.setSingleLine(false);
                *//*valueTV.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));*//*
        valueTV.setGravity(Gravity.CENTER);
        valueTV.setTextColor(Color.DKGRAY);
        valueTV.setBackground(drawable);
        tr.addView(valueTV);*/

        valueTV = new TextView(this);
        valueTV.setWidth(0);
        valueTV.setText("Date");
        valueTV.setTextSize(14);
        //valueTV.setSingleLine(false);
                /*valueTV.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT))*/;
        valueTV.setGravity(Gravity.CENTER);
        valueTV.setTextColor(Color.DKGRAY);
        valueTV.setBackground(drawable);
        tr.addView(valueTV);

        t2.setColumnStretchable(0, true);
        t2.setColumnStretchable(1, true);
        t2.addView(tr, new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
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