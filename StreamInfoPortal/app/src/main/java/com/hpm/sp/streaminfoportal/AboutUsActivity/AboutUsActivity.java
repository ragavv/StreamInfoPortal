package com.hpm.sp.streaminfoportal.AboutUsActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hpm.sp.streaminfoportal.R;


public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getApplicationContext(); // or activity.getApplicationContext()
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();

        String myVersionName = "not available"; // initialize String

        try {
            myVersionName = packageManager.getPackageInfo(packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        StringBuilder sbr = new StringBuilder();
        setContentView(R.layout.activity_about_us);

        /*

        " can be sent to below email Ids: \n\n"
        "samsthanavyasarajamatha@gmail.com \n"
        "harivayu79@gmail.com\n\n
         */

        TextView aboutUsTextView = findViewById(R.id.aboutUsText);
        String aboutText = aboutUsTextView.getText().toString();
        sbr.append(aboutText);
        sbr.append("\n\n Any queries / suggestions on this app \n");
        aboutUsTextView.setText(aboutText.toString());


        /*TextView emailTextView = findViewById(R.id.emailText);
        emailTextView.setText(Html.fromHtml(getString(R.string.email_us)));
        emailTextView.setMovementMethod(LinkMovementMethod.getInstance());*/
        TextView emailTextView = findViewById(R.id.emailText2);
        emailTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + getString(R.string.emailId)));
                    intent.putExtra(Intent.EXTRA_SUBJECT, "SVM Panchanga - Feedback/Suggestion");
                    startActivity(intent);
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }});

        TextView versionTextView = findViewById(R.id.versionText);
        sbr = new StringBuilder();
        sbr.append("\n\n\n AppVersion::" +myVersionName);
        versionTextView.setText(sbr.toString());


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
