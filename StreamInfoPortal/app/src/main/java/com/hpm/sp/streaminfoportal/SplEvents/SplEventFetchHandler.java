package com.hpm.sp.streaminfoportal.SplEvents;

import android.os.AsyncTask;
import android.widget.TextView;

import com.hpm.sp.streaminfoportal.R;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStream;
import java.io.*;
import java.net.*;

public class SplEventFetchHandler extends AsyncTask<String, Void, JSONObject>{
    String url = "";
    TextView textView;

    public SplEventFetchHandler(String urlString, TextView textView) {
        url = urlString;
        this.textView = textView;
    }

    public SplEventFetchHandler(String urlString) {
        url = urlString;
    }

    public JSONObject getJSONFromUrl() {

        try{

            HttpURLConnection myConn = (HttpURLConnection) new URL(url).openConnection();
            myConn.setRequestMethod("GET");
            //myConn.setDoOutput(true);
            myConn.connect();
            InputStream in = null;
            int status = myConn.getResponseCode();

            if (status != HttpURLConnection.HTTP_OK)
                in = new BufferedInputStream(myConn.getErrorStream());
            else
                in = new BufferedInputStream(myConn.getInputStream());
            //InputStream in = new BufferedInputStream(myConn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            return new JSONObject(reader.readLine());
        }
        catch (Exception ex)
        {
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
        if(result.length() > 0)
        {
            SplEventActivity.jsonObject = result;
            if(!(textView == null))
            {
                try {
                    //textView.setText((String)result.getJSONArray("result").getJSONObject(0).get("name"));
                    textView.setText((String)result.getJSONArray("name").getJSONObject(0).get("content"));
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
