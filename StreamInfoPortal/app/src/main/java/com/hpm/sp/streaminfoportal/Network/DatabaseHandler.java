package com.hpm.sp.streaminfoportal.Models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.List;
import java.util.ArrayList;
import java.io.*;
import java.sql.SQLException;
import android.text.Html;

/**
 * Created by ragavendaran.v on 16/11/2016.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase mDataBase;
    private final Context mContext;

    // Database Name
    private static final String DB_NAME = "panchanga.db";

    private static String DB_PATH = "";

    // Translates table name
    private static final String TABLE_TRANSLATOR = "About";

    // Translates Table Columns names
    private static final String KEY_ID = "Key";
    private static final String VALUE_ID = "Value";

    public DatabaseHandler(Context context) throws SQLException {
        super(context, DB_NAME, null, DATABASE_VERSION);
        //DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";

        this.mContext = context;
        String packageName = context.getPackageName();
        DB_PATH = String.format("//data//data//%s//databases//", packageName);
        //DB_PATH=mContext.getDatabasePath(DB_NAME).getPath();
        openDataBase();
    }

    public SQLiteDatabase getDb() {
        return mDataBase;
    }

    // Creating Tables
    public void createDataBase() {
        boolean mDataBaseExist = checkDataBase();
        if (!mDataBaseExist) {
            this.getWritableDatabase();
            try {
                //Copy the database from assests
                copyDataBase(mContext);
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    private boolean checkDataBase() {

        File dbFile = new File(DB_PATH + DB_NAME);
        return false;
       /* System.out.println(mContext.getDatabasePath(DB_NAME).getPath());
        SQLiteDatabase checkDb = null;
        String path = DB_PATH;
        checkDb = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.OPEN_READONLY);
        if (checkDb != null) {
            checkDb.close();
        }
        return checkDb != null;*/
    }

    private void copyDataBase(Context mContext) throws IOException {
        InputStream externalDbStream = mContext.getAssets().open(DB_NAME);
        //Path to the created empty database on your Android device
        String outFileName = DB_PATH + DB_NAME;
        //Now create a stream for writing the database byte by byte
        OutputStream localDbStream = new FileOutputStream(outFileName);
        //Copying the database
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = externalDbStream.read(buffer)) > 0) {
            localDbStream.write(buffer, 0, bytesRead);
        }
        //Don’t forget to close the streams
        localDbStream.close();
        externalDbStream.close();
    }

    public SQLiteDatabase openDataBase() throws SQLException {
        String path = DB_PATH + DB_NAME;
        if (mDataBase == null) {
            createDataBase();
            mDataBase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.OPEN_READWRITE);

        }
        return mDataBase;
    }

    @Override
    public synchronized void close() {
        if (mDataBase != null) {
            mDataBase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    // Getting All Contacts
    public PanchangaObject getPanchangaForSelectedDate(String value) {
        PanchangaObject panchangObj = new PanchangaObject();
        String selectQuery = "SELECT  * FROM panchanga where Sequence=" + Integer.parseInt(value);
        System.out.println(value);
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println(this.mContext.getDatabasePath(DB_NAME).getAbsolutePath());
        System.out.println(db.isOpen());
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                panchangObj.setSamvatsara(cursor.getString(2));
                panchangObj.setAyana(cursor.getString(3));
                panchangObj.setRuthu(cursor.getString(4));
                panchangObj.setMaasa(cursor.getString(5));
                panchangObj.setPaksha(cursor.getString(6));
                panchangObj.setThithi(cursor.getString(7));
                panchangObj.setVaasara(cursor.getString(18));
                panchangObj.setNakshathra(cursor.getString(8));
                panchangObj.setYoga(cursor.getString(9));
                panchangObj.setKarana(cursor.getString(10));
                panchangObj.setShThithi(cursor.getString(11));
                panchangObj.setnEndTime(cursor.getString(15));
                panchangObj.settEndTime(cursor.getString(14));
                panchangObj.setEvents(cursor.getString(19));
                panchangObj.setRaahu(cursor.getString(12));
                panchangObj.setYama(cursor.getString(13));
            } while (cursor.moveToNext());
            // Select All Query
        }
        cursor.close();
        return panchangObj;
    }


}
