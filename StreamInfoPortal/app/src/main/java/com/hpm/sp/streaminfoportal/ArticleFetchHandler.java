package com.hpm.sp.streaminfoportal;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStream;
import java.io.*;
import java.net.*;

public class ArticleFetchHandler extends AsyncTask<String, Void, JSONObject>{
    String url = "";

    public ArticleFetchHandler(String urlString) {
        url = urlString;
    }


    public JSONObject getJSONFromUrl() {

        try{

            HttpURLConnection myConn = (HttpURLConnection) new URL(url).openConnection();
            myConn.setRequestMethod("GET");
            myConn.setDoOutput(true);
            myConn.connect();
            InputStream in = new BufferedInputStream(myConn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            //System.out.println(reader.readLine());
            JSONObject myJSONObject = new JSONObject(reader.readLine());
            System.out.println(myJSONObject);
            TatvaArticlesActivity.jsonObject = myJSONObject;
//            return new JSONObject(reader.readLine());
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
