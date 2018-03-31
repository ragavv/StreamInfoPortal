package com.hpm.sp.streaminfoportal;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.text.format.DateUtils;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by kumardivyarajat on 04/02/18.
 */

public class Utils {
    public Boolean isConnectedToNetwork(Context context) {

        /*ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Boolean isConnected = connectivityManager != null && (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);
        if (!isConnected) {
            Toast.makeText(context, "No internet connection", Toast.LENGTH_LONG).show();
        }*/
        Boolean isConnected = true;
        return isConnected;
    }


    public CharSequence getRelativeDateSeq(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        long time = 0;
        try {
            time = sdf.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long now = System.currentTimeMillis();

        return DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);
    }

    public CharSequence getDateFromString(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date time = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            time = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return simpleDateFormat.format(time);
    }

    public String getGMTDateString(Date date) {
        final SimpleDateFormat sdf =
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(date);
    }

    public String getGMTDateString(Date date, String format) {
        final SimpleDateFormat sdf =
                new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(date);
    }

    public String getDateString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(date);
    }

    public String getDateWithMonth(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        return sdf.format(date);
    }

    public String getDateWithMonthAndYear(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
        return sdf.format(date);
    }

    public String getDateString(Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        String date = dateFormat.format(calendar.getTime());
        return date;
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static String getNextMonth(Date date)
    {
        Calendar currentMonth = Calendar.getInstance();
        currentMonth.setTime(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM yyyy");
        // Increment month
        currentMonth.add(Calendar.MONTH, 1);
        return dateFormat.format(currentMonth.getTime());
    }

    public static String getPreviousMonth(Date date)
    {
        Calendar currentMonth = Calendar.getInstance();
        currentMonth.setTime(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM yyyy");
        // Decrement month
        currentMonth.add(Calendar.MONTH, -1);
        return dateFormat.format(currentMonth.getTime());
    }

    public static Date getMinDate()
    {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(2018, 02, 18, 0, 0, 0);
        return cal.getTime();
    }

    public static Date getMaxDate()
    {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(2019, 03, 05, 0, 0, 0);
        return cal.getTime();
    }
}




