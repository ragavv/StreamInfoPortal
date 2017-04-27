package com.hpm.sp.streaminfoportal;

import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStream;
import java.io.*;
import java.net.*;

public class EventFetchHandler extends AsyncTask<String, Void, JSONObject>{
    String url = "";
    TextView textView;

    public EventFetchHandler(String urlString, TextView textView) {
        url = urlString;
        this.textView = textView;
    }

    public EventFetchHandler(String urlString) {
        url = urlString;
    }

    public JSONObject getJSONFromUrl() {

        try{

            HttpURLConnection myConn = (HttpURLConnection) new URL(url).openConnection();
            myConn.setRequestMethod("GET");
            myConn.setConnectTimeout(25000);
            myConn.setReadTimeout(2500);
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
        if(result.length() > 0)
        {
            EventActivity.jsonObject = result;
            System.out.println(result);
            if(!(textView == null))
            {
                try {
                    textView.setText((String)result.getJSONArray("result").getJSONObject(0).get("name"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else
        {
            //do nothing
        }
    }
}
