package com.hpm.sp.streaminfoportal.EkadashiActivity;

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
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.hpm.sp.streaminfoportal.Models.AradhaneDataObject;
import com.hpm.sp.streaminfoportal.Models.EkadashiDataObject;
import com.hpm.sp.streaminfoportal.Network.DatabaseHandler;
import com.hpm.sp.streaminfoportal.R;
import com.hpm.sp.streaminfoportal.Utils;

import java.sql.SQLException;
import java.util.List;

public class EkadashiActivity extends AppCompatActivity {

    private static final String TAG = EkadashiActivity.class.getSimpleName();
    private Utils utils = new Utils();
    private DatabaseHandler db = null;
    private String radioValue = "English";
    private Drawable drawable = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ekadashi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DatabaseHandler db = null;
        setTableRows(radioValue, drawable);

        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup1);
        int checkedRadioButtonID = rg.getCheckedRadioButtonId();
        if(checkedRadioButtonID == -1){
            ((RadioButton)findViewById(R.id.Radio1)).setChecked(true);
        }
       /* rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.Radio1:
                        radioValue = "English";
                        setTableRows(radioValue, drawable);
                        break;
                    case R.id.Radio2:
                        radioValue = "Tamil";
                        setTableRows(radioValue, drawable);
                        break;
                    case R.id.Radio3:
                        radioValue = "Kannada";
                        setTableRows(radioValue, drawable);
                        break;
                    case R.id.Radio4:
                        radioValue = "Telugu";
                        setTableRows(radioValue, drawable);
                        break;
                    case R.id.Radio5:
                        radioValue = "Sanskrit";
                        setTableRows(radioValue, drawable);
                        break;
                    default:
                        radioValue = "English";
                        setTableRows(radioValue, drawable);
                        break;

                }
            }
        });*/

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        ((RadioButton)findViewById(R.id.Radio1)).setChecked(false);
        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup1);
        rg.check(view.getId());
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.Radio1:
                radioValue = "English";
                setTableRows(radioValue, drawable);
                break;
            case R.id.Radio2:
                radioValue = "Tamil";
                setTableRows(radioValue, drawable);
                break;
            case R.id.Radio3:
                radioValue = "Kannada";
                setTableRows(radioValue, drawable);
                break;
            case R.id.Radio4:
                radioValue = "Telugu";
                setTableRows(radioValue, drawable);
                break;
            case R.id.Radio5:
                radioValue = "Sanskrit";
                setTableRows(radioValue, drawable);
                break;
            default:
                radioValue = "English";
                setTableRows(radioValue, drawable);
                break;
        }
    }

    private void setTableRows(String radioValue, Drawable drawable) {
        String colorString = "#FFFFFF";
        try {
            db = new DatabaseHandler(this);
            TableLayout t2 = (TableLayout) findViewById(R.id.table22);
            t2.removeAllViews();
            List<EkadashiDataObject> abouts = db.getEkadashiDetails(radioValue);
            switch (radioValue) {
                case "English":
                    addHeaderRowInTableEnglish(t2);
                    break;
                case "Tamil":
                    addHeaderRowInTableTamil(t2);
                    break;
                case "Kannada":
                    addHeaderRowInTableKannada(t2);
                    break;
                case "Telugu":
                    addHeaderRowInTableTelugu(t2);
                    break;
                case "Sanskrit":
                    addHeaderRowInTableSanskrit(t2);
                    break;
                default:
                    addHeaderRowInTableEnglish(t2);
                    break;
            }
            for (int i = 0; i < abouts.size(); i++) {
                if (i % 2 == 0)
                    drawable = ContextCompat.getDrawable(this, R.drawable.table_shape_white);
                else
                    drawable = ContextCompat.getDrawable(this, R.drawable.table_shape);
                EkadashiDataObject cn = abouts.get(i);
                // Create a TableRow and give it an ID
                TableRow tr = new TableRow(this);
                tr.setGravity(Gravity.CENTER);
                tr.setId(100 + i);
                tr.setLayoutParams(new ActionBar.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));

                TextView labelTV = new TextView(this);
                labelTV.setId(200 + i);
                labelTV.setText(cn.getMaasaText());
                labelTV.setTextSize(14);
                // labelTV.setWidth(0);
                labelTV.setPadding(10, 0, 10, 0);
                labelTV.setSingleLine(false);
                // labelTV.setLayoutParams(new TableRow.LayoutParams(
                //       TableRow.LayoutParams.MATCH_PARENT,
                //     TableRow.LayoutParams.MATCH_PARENT));
                labelTV.setGravity(Gravity.LEFT);
                labelTV.setTextColor(Color.DKGRAY);
                labelTV.setBackground(drawable);
                tr.addView(labelTV);

                TextView valueTV = new TextView(this);
                valueTV = new TextView(this);
                valueTV.setId(400 + i);
                valueTV.setText(cn.getPakshaText());
                valueTV.setPadding(10, 0, 10, 0);
                valueTV.setTextSize(14);
                //  valueTV.setWidth(0);
                valueTV.setSingleLine(false);
                /*valueTV.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));*/
                valueTV.setGravity(Gravity.CENTER);
                valueTV.setTextColor(Color.DKGRAY);
                valueTV.setBackground(drawable);
                tr.addView(valueTV);

                valueTV = new TextView(this);
                valueTV.setId(600 + i);
                //    valueTV.setWidth(0);
                valueTV.setText(cn.getDashamiText());
                valueTV.setPadding(10, 0, 10, 0);
                valueTV.setTextSize(14);
                valueTV.setSingleLine(false);
                /*valueTV.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT))*/
                ;
                valueTV.setGravity(Gravity.CENTER);
                valueTV.setTextColor(Color.DKGRAY);
                valueTV.setBackground(drawable);
                tr.addView(valueTV);

                valueTV = new TextView(this);
                valueTV.setId(800 + i);
                //      valueTV.setWidth(0);
                valueTV.setText(cn.getEkadashiText());
                valueTV.setPadding(10, 0, 10, 0);
                valueTV.setTextSize(14);
                valueTV.setSingleLine(false);
                /*valueTV.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT))*/
                ;
                valueTV.setGravity(Gravity.CENTER);
                valueTV.setTextColor(Color.DKGRAY);
                valueTV.setBackground(drawable);
                tr.addView(valueTV);

                valueTV = new TextView(this);
                valueTV.setId(650 + i);
                //      valueTV.setWidth(0);
                valueTV.setText(cn.getDwadashiText());
                valueTV.setPadding(10, 0, 10, 0);
                valueTV.setTextSize(14);
                valueTV.setSingleLine(false);
                /*valueTV.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT))*/
                ;
                valueTV.setGravity(Gravity.CENTER);
                valueTV.setTextColor(Color.DKGRAY);
                valueTV.setBackground(drawable);
                tr.addView(valueTV);

                valueTV = new TextView(this);
                valueTV.setId(850 + i);
                //       valueTV.setWidth(0);
                valueTV.setText(cn.getDarshaText());
                valueTV.setPadding(10, 0, 10, 0);
                valueTV.setTextSize(14);
                valueTV.setSingleLine(false);
                /*valueTV.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT))*/
                ;
                valueTV.setGravity(Gravity.CENTER);
                valueTV.setTextColor(Color.DKGRAY);
                valueTV.setBackground(drawable);
                tr.addView(valueTV);

                valueTV = new TextView(this);
                valueTV.setId(900 + i);
                //    valueTV.setWidth(0);
                valueTV.setText(cn.getVishnuPanchakaText());
                valueTV.setPadding(10, 0, 10, 0);
                valueTV.setTextSize(14);
                valueTV.setSingleLine(false);
                /*valueTV.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT))*/
                ;
                valueTV.setGravity(Gravity.CENTER);
                valueTV.setTextColor(Color.DKGRAY);
                valueTV.setBackground(drawable);
                tr.addView(valueTV);

                /*t2.setColumnStretchable(0, true);
                t2.setColumnStretchable(1, true);
                t2.setColumnStretchable(2, true);
                t2.setColumnStretchable(3, true);
                t2.setColumnStretchable(4, true);
                t2.setColumnStretchable(5, true);
                t2.setColumnStretchable(6, true);*/
                t2.addView(tr, new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));
            }

            db.close();

        } catch (SQLException ex) {

        }
    }

    private void addHeaderRowInTableEnglish(TableLayout t2){
        Drawable drawable = ContextCompat.getDrawable(this,R.drawable.table_shape);
        TableRow tr = new TableRow(this);
        tr.setGravity(Gravity.CENTER);
        tr.setLayoutParams(new ActionBar.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));

        TextView labelTV = new TextView(this);
        labelTV.setText("māsa");
        labelTV.setTextSize(14);
        labelTV.setPadding(10,0,10,0);
     //   labelTV.setWidth(0);
        labelTV.setSingleLine(false);
        // labelTV.setLayoutParams(new TableRow.LayoutParams(
        //       TableRow.LayoutParams.MATCH_PARENT,
        //     TableRow.LayoutParams.MATCH_PARENT));
        labelTV.setGravity(Gravity.CENTER);
        labelTV.setTextColor(Color.DKGRAY);
        labelTV.setBackground(drawable);
        tr.addView(labelTV);

        TextView valueTV = new TextView(this);
        valueTV = new TextView(this);
        valueTV.setText("pakṣa");
        valueTV.setPadding(10,0,10,0);
        valueTV.setTextSize(14);
   //     valueTV.setWidth(0);
        valueTV.setSingleLine(false);
                /*valueTV.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));*/
        valueTV.setGravity(Gravity.CENTER);
        valueTV.setTextColor(Color.DKGRAY);
        valueTV.setBackground(drawable);
        tr.addView(valueTV);

        valueTV = new TextView(this);
   //     valueTV.setWidth(0);
        valueTV.setText("daśamī");
        valueTV.setPadding(10,0,10,0);
        valueTV.setTextSize(14);
        valueTV.setSingleLine(false);
                /*valueTV.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT))*/;
        valueTV.setGravity(Gravity.CENTER);
        valueTV.setTextColor(Color.DKGRAY);
        valueTV.setBackground(drawable);
        tr.addView(valueTV);

        valueTV = new TextView(this);
   //     valueTV.setWidth(0);
        valueTV.setText("ekādaśī");
        valueTV.setPadding(10,0,10,0);
        valueTV.setTextSize(14);
        valueTV.setSingleLine(false);
                /*valueTV.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT))*/;
        valueTV.setGravity(Gravity.CENTER);
        valueTV.setTextColor(Color.DKGRAY);
        valueTV.setBackground(drawable);
        tr.addView(valueTV);

        valueTV = new TextView(this);
    //    valueTV.setWidth(0);
        valueTV.setText("dvādaśī");
        valueTV.setPadding(10,0,10,0);
        valueTV.setTextSize(14);
        valueTV.setSingleLine(false);
                /*valueTV.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT))*/;
        valueTV.setGravity(Gravity.CENTER);
        valueTV.setTextColor(Color.DKGRAY);
        valueTV.setBackground(drawable);
        tr.addView(valueTV);

        valueTV = new TextView(this);
     //   valueTV.setWidth(0);
        valueTV.setText("darśa / pūrṇimā");
        valueTV.setPadding(10,0,10,0);
        valueTV.setTextSize(14);
        valueTV.setSingleLine(false);
                /*valueTV.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT))*/;
        valueTV.setGravity(Gravity.CENTER);
        valueTV.setTextColor(Color.DKGRAY);
        valueTV.setBackground(drawable);
        tr.addView(valueTV);

        valueTV = new TextView(this);
     //   valueTV.setWidth(0);
        valueTV.setText("viśṇu pañcaka (śravaṇā)");
        valueTV.setPadding(10,0,0,0);
        valueTV.setTextSize(14);
        valueTV.setSingleLine(false);
                /*valueTV.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT))*/;
        valueTV.setGravity(Gravity.CENTER);
        valueTV.setTextColor(Color.DKGRAY);
        valueTV.setBackground(drawable);
        tr.addView(valueTV);


        t2.setColumnStretchable(0, true);
        t2.setColumnStretchable(1, true);
        t2.setColumnStretchable(2, true);
        t2.addView(tr, new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
    }

    private void addHeaderRowInTableSanskrit(TableLayout t2){
        Drawable drawable = ContextCompat.getDrawable(this,R.drawable.table_shape);
        TableRow tr = new TableRow(this);
        tr.setGravity(Gravity.CENTER);
        tr.setLayoutParams(new ActionBar.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));

        TextView labelTV = new TextView(this);
        labelTV.setText("मास");
        labelTV.setTextSize(14);
        labelTV.setPadding(10,0,10,0);
        labelTV.setWidth(0);
        labelTV.setSingleLine(false);
        // labelTV.setLayoutParams(new TableRow.LayoutParams(
        //       TableRow.LayoutParams.MATCH_PARENT,
        //     TableRow.LayoutParams.MATCH_PARENT));
        labelTV.setGravity(Gravity.CENTER);
        labelTV.setTextColor(Color.DKGRAY);
        labelTV.setBackground(drawable);
        tr.addView(labelTV);

        TextView valueTV = new TextView(this);
        valueTV = new TextView(this);
        valueTV.setText("पक्ष");
        valueTV.setPadding(10,0,10,0);
        valueTV.setTextSize(14);
        valueTV.setWidth(0);
        valueTV.setSingleLine(false);
                /*valueTV.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));*/
        valueTV.setGravity(Gravity.CENTER);
        valueTV.setTextColor(Color.DKGRAY);
        valueTV.setBackground(drawable);
        tr.addView(valueTV);

        valueTV = new TextView(this);
        valueTV.setWidth(0);
        valueTV.setPadding(10,0,10,0);
        valueTV.setText("दशमी");
        valueTV.setTextSize(14);
        valueTV.setSingleLine(false);
                /*valueTV.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT))*/;
        valueTV.setGravity(Gravity.CENTER);
        valueTV.setTextColor(Color.DKGRAY);
        valueTV.setBackground(drawable);
        tr.addView(valueTV);

        valueTV = new TextView(this);
        valueTV.setWidth(0);
        valueTV.setText("एकादशी");
        valueTV.setPadding(10,0,10,0);
        valueTV.setTextSize(14);
        valueTV.setSingleLine(false);
                /*valueTV.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT))*/;
        valueTV.setGravity(Gravity.CENTER);
        valueTV.setTextColor(Color.DKGRAY);
        valueTV.setBackground(drawable);
        tr.addView(valueTV);

        valueTV = new TextView(this);
        valueTV.setWidth(0);
        valueTV.setText("द्वादशी");
        valueTV.setPadding(10,0,10,0);
        valueTV.setTextSize(14);
        valueTV.setSingleLine(false);
                /*valueTV.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT))*/;
        valueTV.setGravity(Gravity.CENTER);
        valueTV.setTextColor(Color.DKGRAY);
        valueTV.setBackground(drawable);
        tr.addView(valueTV);

        valueTV = new TextView(this);
        valueTV.setWidth(0);
        valueTV.setText("दर्श / पूर्णिमा");
        valueTV.setPadding(10,0,10,0);
        valueTV.setTextSize(14);
        valueTV.setSingleLine(false);
                /*valueTV.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT))*/;
        valueTV.setGravity(Gravity.CENTER);
        valueTV.setTextColor(Color.DKGRAY);
        valueTV.setBackground(drawable);
        tr.addView(valueTV);

        valueTV = new TextView(this);
        valueTV.setWidth(0);
        valueTV.setText("विश्णु पञ्चक (श्रवणा)");
        valueTV.setPadding(10,0,10,0);
        valueTV.setTextSize(14);
        valueTV.setSingleLine(false);
                /*valueTV.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT))*/;
        valueTV.setGravity(Gravity.CENTER);
        valueTV.setTextColor(Color.DKGRAY);
        valueTV.setBackground(drawable);
        tr.addView(valueTV);

        t2.setColumnStretchable(0, true);
        t2.setColumnStretchable(1, true);
        t2.setColumnStretchable(2, true);
        t2.addView(tr, new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
    }

    private void addHeaderRowInTableTamil(TableLayout t2){
        Drawable drawable = ContextCompat.getDrawable(this,R.drawable.table_shape);
        TableRow tr = new TableRow(this);
        tr.setGravity(Gravity.CENTER);
        tr.setLayoutParams(new ActionBar.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));

        TextView labelTV = new TextView(this);
        labelTV.setText("மாஸ");
        labelTV.setTextSize(14);
        labelTV.setPadding(10,0,10,0);
        labelTV.setWidth(0);
        labelTV.setSingleLine(false);
        // labelTV.setLayoutParams(new TableRow.LayoutParams(
        //       TableRow.LayoutParams.MATCH_PARENT,
        //     TableRow.LayoutParams.MATCH_PARENT));
        labelTV.setGravity(Gravity.CENTER);
        labelTV.setTextColor(Color.DKGRAY);
        labelTV.setBackground(drawable);
        tr.addView(labelTV);

        TextView valueTV = new TextView(this);
        valueTV = new TextView(this);
        valueTV.setText("பகஂஷ");
        valueTV.setPadding(10,0,10,0);
        valueTV.setTextSize(14);
        valueTV.setWidth(0);
        valueTV.setSingleLine(false);
                /*valueTV.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));*/
        valueTV.setGravity(Gravity.CENTER);
        valueTV.setTextColor(Color.DKGRAY);
        valueTV.setBackground(drawable);
        tr.addView(valueTV);

        valueTV = new TextView(this);
        valueTV.setWidth(0);
        valueTV.setText("தஸமீ");
        valueTV.setPadding(10,0,10,0);
        valueTV.setTextSize(14);
        valueTV.setSingleLine(false);
                /*valueTV.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT))*/;
        valueTV.setGravity(Gravity.CENTER);
        valueTV.setTextColor(Color.DKGRAY);
        valueTV.setBackground(drawable);
        tr.addView(valueTV);

        valueTV = new TextView(this);
        valueTV.setWidth(0);
        valueTV.setText("ஏகாதஸீ");
        valueTV.setPadding(10,0,10,0);
        valueTV.setTextSize(14);
        valueTV.setSingleLine(false);
                /*valueTV.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT))*/;
        valueTV.setGravity(Gravity.CENTER);
        valueTV.setTextColor(Color.DKGRAY);
        valueTV.setBackground(drawable);
        tr.addView(valueTV);

        valueTV = new TextView(this);
        valueTV.setWidth(0);
        valueTV.setText("தஂவாதஸீ");
        valueTV.setPadding(10,0,10,0);
        valueTV.setTextSize(14);
        valueTV.setSingleLine(false);
                /*valueTV.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT))*/;
        valueTV.setGravity(Gravity.CENTER);
        valueTV.setTextColor(Color.DKGRAY);
        valueTV.setBackground(drawable);
        tr.addView(valueTV);

        valueTV = new TextView(this);
        valueTV.setWidth(0);
        valueTV.setText("தரஂஸ / பூரஂணிமா");
        valueTV.setPadding(10,0,10,0);
        valueTV.setTextSize(14);
        valueTV.setSingleLine(false);
                /*valueTV.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT))*/;
        valueTV.setGravity(Gravity.CENTER);
        valueTV.setTextColor(Color.DKGRAY);
        valueTV.setBackground(drawable);
        tr.addView(valueTV);

        valueTV = new TextView(this);
        valueTV.setWidth(0);
        valueTV.setText("விஸஂணு பஞஂசக (ஸஂரவணா)");
        valueTV.setPadding(10,0,10,0);
        valueTV.setTextSize(14);
        valueTV.setSingleLine(false);
                /*valueTV.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT))*/;
        valueTV.setGravity(Gravity.CENTER);
        valueTV.setTextColor(Color.DKGRAY);
        valueTV.setBackground(drawable);
        tr.addView(valueTV);

        t2.setColumnStretchable(0, true);
        t2.setColumnStretchable(1, true);
        t2.setColumnStretchable(2, true);
        t2.addView(tr, new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
    }

    private void addHeaderRowInTableKannada(TableLayout t2){
        Drawable drawable = ContextCompat.getDrawable(this,R.drawable.table_shape);
        TableRow tr = new TableRow(this);
        tr.setGravity(Gravity.CENTER);
        tr.setLayoutParams(new ActionBar.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));

        TextView labelTV = new TextView(this);
        labelTV.setText("ಮಾಸ");
        labelTV.setTextSize(14);
        labelTV.setPadding(10,0,10,0);
        labelTV.setWidth(0);
        labelTV.setSingleLine(false);
        // labelTV.setLayoutParams(new TableRow.LayoutParams(
        //       TableRow.LayoutParams.MATCH_PARENT,
        //     TableRow.LayoutParams.MATCH_PARENT));
        labelTV.setGravity(Gravity.CENTER);
        labelTV.setTextColor(Color.DKGRAY);
        labelTV.setBackground(drawable);
        tr.addView(labelTV);

        TextView valueTV = new TextView(this);
        valueTV = new TextView(this);
        valueTV.setText("ಪಕ್ಷ");
        valueTV.setPadding(10,0,10,0);
        valueTV.setTextSize(14);
        valueTV.setWidth(0);
        valueTV.setSingleLine(false);
                /*valueTV.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));*/
        valueTV.setGravity(Gravity.CENTER);
        valueTV.setTextColor(Color.DKGRAY);
        valueTV.setBackground(drawable);
        tr.addView(valueTV);

        valueTV = new TextView(this);
        valueTV.setWidth(0);
        valueTV.setText("ದಶಮೀ");
        valueTV.setPadding(10,0,10,0);
        valueTV.setTextSize(14);
        valueTV.setSingleLine(false);
                /*valueTV.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT))*/;
        valueTV.setGravity(Gravity.CENTER);
        valueTV.setTextColor(Color.DKGRAY);
        valueTV.setBackground(drawable);
        tr.addView(valueTV);

        valueTV = new TextView(this);
        valueTV.setWidth(0);
        valueTV.setText("ಏಕಾದಶೀ");
        valueTV.setPadding(10,0,10,0);
        valueTV.setTextSize(14);
        valueTV.setSingleLine(false);
                /*valueTV.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT))*/;
        valueTV.setGravity(Gravity.CENTER);
        valueTV.setTextColor(Color.DKGRAY);
        valueTV.setBackground(drawable);
        tr.addView(valueTV);

        valueTV = new TextView(this);
        valueTV.setWidth(0);
        valueTV.setText("ದ್ವಾದಶೀ");
        valueTV.setPadding(10,0,10,0);
        valueTV.setTextSize(14);
        valueTV.setSingleLine(false);
                /*valueTV.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT))*/;
        valueTV.setGravity(Gravity.CENTER);
        valueTV.setTextColor(Color.DKGRAY);
        valueTV.setBackground(drawable);
        tr.addView(valueTV);

        valueTV = new TextView(this);
        valueTV.setWidth(0);
        valueTV.setText("ದರ್ಶ / ಪೂರ್ಣಿಮಾ");
        valueTV.setPadding(10,0,10,0);
        valueTV.setTextSize(14);
        valueTV.setSingleLine(false);
                /*valueTV.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT))*/;
        valueTV.setGravity(Gravity.CENTER);
        valueTV.setTextColor(Color.DKGRAY);
        valueTV.setBackground(drawable);
        tr.addView(valueTV);

        valueTV = new TextView(this);
        valueTV.setWidth(0);
        valueTV.setText("ವಿಶ್ಣು ಪಞ್ಚಕ (ಶ್ರವಣಾ)");
        valueTV.setPadding(10,0,10,0);
        valueTV.setTextSize(14);
        valueTV.setSingleLine(false);
                /*valueTV.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT))*/;
        valueTV.setGravity(Gravity.CENTER);
        valueTV.setTextColor(Color.DKGRAY);
        valueTV.setBackground(drawable);
        tr.addView(valueTV);

        t2.setColumnStretchable(0, true);
        t2.setColumnStretchable(1, true);
        t2.setColumnStretchable(2, true);
        t2.addView(tr, new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
    }

    private void addHeaderRowInTableTelugu(TableLayout t2){
        Drawable drawable = ContextCompat.getDrawable(this,R.drawable.table_shape);
        TableRow tr = new TableRow(this);
        tr.setGravity(Gravity.CENTER);
        tr.setLayoutParams(new ActionBar.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));

        TextView labelTV = new TextView(this);
        labelTV.setText("మాస");
        labelTV.setPadding(10,0,10,0);
        labelTV.setTextSize(14);
        labelTV.setWidth(0);
        labelTV.setSingleLine(false);
        // labelTV.setLayoutParams(new TableRow.LayoutParams(
        //       TableRow.LayoutParams.MATCH_PARENT,
        //     TableRow.LayoutParams.MATCH_PARENT));
        labelTV.setGravity(Gravity.CENTER);
        labelTV.setTextColor(Color.DKGRAY);
        labelTV.setBackground(drawable);
        tr.addView(labelTV);

        TextView valueTV = new TextView(this);
        valueTV = new TextView(this);
        valueTV.setText("పక్ష");
        valueTV.setPadding(10,0,10,0);
        valueTV.setTextSize(14);
        valueTV.setWidth(0);
        valueTV.setSingleLine(false);
                /*valueTV.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));*/
        valueTV.setGravity(Gravity.CENTER);
        valueTV.setTextColor(Color.DKGRAY);
        valueTV.setBackground(drawable);
        tr.addView(valueTV);

        valueTV = new TextView(this);
        valueTV.setWidth(0);
        valueTV.setText("దశమీ");
        valueTV.setPadding(10,0,10,0);
        valueTV.setTextSize(14);
        valueTV.setSingleLine(false);
                /*valueTV.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT))*/;
        valueTV.setGravity(Gravity.CENTER);
        valueTV.setTextColor(Color.DKGRAY);
        valueTV.setBackground(drawable);
        tr.addView(valueTV);

        valueTV = new TextView(this);
        valueTV.setWidth(0);
        valueTV.setText("ఏకాదశీ");
        valueTV.setPadding(10,0,10,0);
        valueTV.setTextSize(14);
        valueTV.setSingleLine(false);
                /*valueTV.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT))*/;
        valueTV.setGravity(Gravity.CENTER);
        valueTV.setTextColor(Color.DKGRAY);
        valueTV.setBackground(drawable);
        tr.addView(valueTV);

        valueTV = new TextView(this);
        valueTV.setWidth(0);
        valueTV.setText("ద్వాదశీ");
        valueTV.setPadding(10,0,10,0);
        valueTV.setTextSize(14);
        valueTV.setSingleLine(false);
                /*valueTV.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT))*/;
        valueTV.setGravity(Gravity.CENTER);
        valueTV.setTextColor(Color.DKGRAY);
        valueTV.setBackground(drawable);
        tr.addView(valueTV);

        valueTV = new TextView(this);
        valueTV.setWidth(0);
        valueTV.setText("దర్శ / పూర్ణిమా");
        valueTV.setPadding(10,0,10,0);
        valueTV.setTextSize(14);
        valueTV.setSingleLine(false);
                /*valueTV.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT))*/;
        valueTV.setGravity(Gravity.CENTER);
        valueTV.setTextColor(Color.DKGRAY);
        valueTV.setBackground(drawable);
        tr.addView(valueTV);

        valueTV = new TextView(this);
        valueTV.setWidth(0);
        valueTV.setText("విశ్ణు పఞ్చక (శ్రవణా)");
        valueTV.setPadding(10,0,10,0);
        valueTV.setTextSize(14);
        valueTV.setSingleLine(false);
                /*valueTV.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT))*/;
        valueTV.setGravity(Gravity.CENTER);
        valueTV.setTextColor(Color.DKGRAY);
        valueTV.setBackground(drawable);
        tr.addView(valueTV);

        t2.setColumnStretchable(0, true);
        t2.setColumnStretchable(1, true);
        t2.setColumnStretchable(2, true);
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