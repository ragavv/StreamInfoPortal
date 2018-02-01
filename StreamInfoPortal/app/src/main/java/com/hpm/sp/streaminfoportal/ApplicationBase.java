package com.hpm.sp.streaminfoportal;

import android.app.Application;

import com.hpm.sp.streaminfoportal.Network.AppConfig;
import com.hpm.sp.streaminfoportal.Network.NetworkHelper;

/**
 * Created by kumardivyarajat on 1/21/18.
 */

public class ApplicationBase extends Application {

    public ApplicationBase() {

    }
    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects (excluding content providers) have been created.
     * Implementations should be as quick as possible (for example using
     * lazy initialization of state) since the time spent in this function
     * directly impacts the performance of starting the first activity,
     * service, or receiver in a process.
     * If you override this method, be sure to call super.onCreate().
     */
    @Override
    public void onCreate() {
        super.onCreate();
        AppConfig appConfig = new AppConfig(BuildConfig.URL_SCHEME_IS_HTTPS, BuildConfig.URL_SERVER, BuildConfig.NETWORK_IS_LOGGIG_ENABLED, 1);
        NetworkHelper.initialize(appConfig);
    }
}
