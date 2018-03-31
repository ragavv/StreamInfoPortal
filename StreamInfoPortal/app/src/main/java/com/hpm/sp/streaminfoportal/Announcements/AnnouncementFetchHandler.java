package com.hpm.sp.streaminfoportal.Announcements;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AnnouncementFetchHandler extends AsyncTask<String, Void, JSONObject>{
    String url = "";
    TextView textView;

    public AnnouncementFetchHandler(String urlString, TextView textView) {
        url = urlString;
        this.textView = textView;
    }

    public AnnouncementFetchHandler(String urlString) {
        url = urlString;
    }

    public JSONObject getJSONFromUrl() {

        try{

            HttpURLConnection myConn = (HttpURLConnection) new URL(url).openConnection();
            myConn.setRequestMethod("GET");
            //myConn.setDoOutput(true);
            myConn.connect();
            InputStream in = new BufferedInputStream(myConn.getInputStream());
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
            AnnouncementActivity.jsonObject = result;
            if(!(textView == null))
            {
                try {
                    textView.setText((String)result.getJSONArray("result").getJSONObject(0).get("description"));
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
