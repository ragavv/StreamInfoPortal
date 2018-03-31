package com.hpm.sp.streaminfoportal.Network;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.hpm.sp.streaminfoportal.BuildConfig;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;

public class OpenLocalPDF {

    private static String TAG = OpenLocalPDF.class.getSimpleName();

    private WeakReference<Context> contextWeakReference;
    private String fileName;

    public OpenLocalPDF(Context context, String fileName) {
        this.contextWeakReference = new WeakReference<>(context);
        this.fileName = fileName.endsWith("pdf") ? fileName : fileName + ".pdf";
    }

    public void execute() {

        Context context = contextWeakReference.get();
        if (context != null) {
            new CopyFileAsyncTask().execute();
        }

    }


    private class CopyFileAsyncTask extends AsyncTask<Void, Void, File> {


        final String appDirectoryName = BuildConfig.APPLICATION_ID;
        String packageName = contextWeakReference.get().getPackageName();
        String fileRoot = String.format("//data//data//%s//files//", packageName);
        /*final File fileRoot = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), appDirectoryName);*/

        @Override
        protected File doInBackground(Void... params) {
            final File file = new File(fileRoot, fileName);

            /*Context context = contextWeakReference.get();

            AssetManager assetManager = context.getAssets();

            final File file = new File(fileRoot, fileName);


            InputStream in = null;
            OutputStream out = null;
            try {

                file.getParentFile().mkdirs();
                String[] files = assetManager.list("");

                if (file.exists()) {
                    file.delete();
                    Log.d(TAG, "File exists");
                }
                try {
                    if (!file.createNewFile()) {
                        Log.i("Test", "This file is already exist: " + file.getAbsolutePath());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                in = assetManager.open("raw/charama_kannada.pdf");
                Log.d(TAG, "In");

                out = new FileOutputStream(file);
                Log.d(TAG, "Out");

                Log.d(TAG, "Copy file");
                copyFile(in, out);

                Log.d(TAG, "Close");
                in.close();

                out.flush();
                out.close();

            } catch (Exception e)
            {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }*/

            return file;
        }

        private void copyFile(InputStream in, OutputStream out) throws IOException
        {
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1)
            {
                out.write(buffer, 0, read);
            }
        }

        @Override
        protected void onPostExecute(File file) {
            super.onPostExecute(file);

            Context context = contextWeakReference.get();
            String authorities = context.getPackageName() + ".fileprovider";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri articleUri = Uri.parse("https://drive.google.com/open?id=1hcqGaO_1ZZ6xRtmJF6WKlx1yMBDp9hbr");
            intent.setDataAndType(articleUri, "application/pdf");
            context.startActivity(Intent.createChooser(intent,"open"));

        }
    }
}