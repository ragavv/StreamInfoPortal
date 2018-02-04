package com.hpm.sp.streaminfoportal.Events;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.*;
import java.net.*;

public class EventFetchHandler extends AsyncTask<String, Void, JSONObject> {
    String url = "";
    TextView textView;
    public static final String TAG = EventFetchHandler.class.getSimpleName();

    public EventFetchHandler(String urlString, TextView textView) {
        url = urlString;
        this.textView = textView;
    }

    public EventFetchHandler(String urlString) {
        url = urlString;
    }

    public JSONObject getJSONFromUrl() {

        try {
            HttpURLConnection myConn = (HttpURLConnection) new URL(url).openConnection();
            myConn.setRequestMethod("GET");
            myConn.setDoOutput(true);
            myConn.connect();
            InputStream in = new BufferedInputStream(myConn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            Log.d(TAG, "getJSONFromUrl: " + reader.readLine());
            return new JSONObject(reader.readLine());
        } catch (Exception ex) {
            System.out.println(ex);
            try {
                JSONObject tempJSON = new JSONObject("{\"result\":[{\"id\":\"1\",\"name\":\"Network error\",\"location\":\"\",\"author\":\"\"}]}");
                return tempJSON;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        return getJSONFromUrl();
    }

    protected void onPostExecute(JSONObject result) {
        if (result.length() > 0) {
            EventActivity.jsonObject = result;
            if (!(textView == null)) {
                try {
                    textView.setText((String) result.getJSONArray("result").getJSONObject(0).get("name"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            //do nothing
        }
    }
}
