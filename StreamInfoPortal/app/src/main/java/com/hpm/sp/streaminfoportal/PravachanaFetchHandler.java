package com.hpm.sp.streaminfoportal;

/**
 * Created by mahesh on 26/04/17.
 */

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PravachanaFetchHandler extends AsyncTask<String, Void, JSONObject> {
    String url = "";
    TextView textView;

    public PravachanaFetchHandler(String urlString, TextView textView) {
        url = urlString;
        this.textView = textView;
    }

    public PravachanaFetchHandler(String urlString) {
        url = urlString;
    }

    public JSONObject getJSONFromUrl() {

        try{

            HttpURLConnection myConn = (HttpURLConnection) new URL(url).openConnection();
            myConn.setRequestMethod("GET");
            myConn.setConnectTimeout(15000);
            myConn.setReadTimeout(1500);
            myConn.setDoOutput(true);
            myConn.connect();
            InputStream in = new BufferedInputStream(myConn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            return new JSONObject(reader.readLine());
        }
        catch (Exception ex)
        {
            System.out.println(ex);
            return null;
        }

    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        return getJSONFromUrl();
    }

    protected void onPostExecute(JSONObject result) {
        if(!(textView == null))
        {
            try {
                textView.setText((String)result.getJSONArray("result").getJSONObject(0).get("text"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {

        }
    }
}
