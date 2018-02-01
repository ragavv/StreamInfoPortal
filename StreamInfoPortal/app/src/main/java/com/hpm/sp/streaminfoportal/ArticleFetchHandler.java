package com.hpm.sp.streaminfoportal;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStream;
import java.io.*;
import java.net.*;

public class ArticleFetchHandler extends AsyncTask<String, Void, JSONObject>{
    String url = "";
    public static final String TAG = ArticleFetchHandler.class.getSimpleName();

    public ArticleFetchHandler(String urlString) {
        url = urlString;
    }


    public JSONObject getJSONFromUrl() {

        try{
            Log.d(TAG, "getJSONFromUrl: URL -> " + url);
            HttpURLConnection myConn = (HttpURLConnection) new URL(url).openConnection();
            myConn.setRequestMethod("GET");
            myConn.setDoOutput(true);
            myConn.connect();
            InputStream in = new BufferedInputStream(myConn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            JSONObject myJSONObject = new JSONObject(reader.readLine());
            TatvaArticlesActivity.jsonObject = myJSONObject;
            return myJSONObject;
        }
        catch (Exception ex)
        {
            System.out.println(ex);
            return new JSONObject();
        }

    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        return getJSONFromUrl();
    }

    protected void onPostExecute(JSONObject result) {
        TatvaArticlesActivity.jsonObject = result;
    }
}
