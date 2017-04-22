package com.hpm.sp.streaminfoportal;

import android.os.AsyncTask;
import org.json.JSONObject;
import java.io.InputStream;
import java.io.*;
import java.net.*;

public class PanchangaFetchHandler extends AsyncTask<String, Void, JSONObject>{
    String url = "";

    public PanchangaFetchHandler(String urlString) {
        url = urlString;
    }


    public JSONObject getJSONFromUrl() {

        try{

            HttpURLConnection myConn = (HttpURLConnection) new URL(url).openConnection();
            myConn.setRequestMethod("GET");
            myConn.setConnectTimeout(1000);
            myConn.setReadTimeout(1500);
            myConn.setDoOutput(true);
            myConn.connect();
            InputStream in = new BufferedInputStream(myConn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            //System.out.println(reader.readLine());

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
        TatvaArticlesActivity.jsonObject = result;
    }
}
