package com.hpm.sp.streaminfoportal.Network;

/**
 * Created by kumardivyarajat on 02/07/16.
 */
public class AppConfig {

    private boolean isHttps;
    private String baseUrl;
    private boolean isLoggingEnabled;
    private int loadCount;
    private String baseUrlWithTrailingSlash;



    public AppConfig(boolean isHttps, String baseUrl, boolean isLoggingEnabled, int loadCount) {
        this.isHttps = isHttps;
        this.baseUrl = (isHttps ? "https://" : "http://") + baseUrl;
        this.isLoggingEnabled = isLoggingEnabled;
        this.loadCount = loadCount;
        this.baseUrlWithTrailingSlash = this.baseUrl +"/";
    }

    public AppConfig() {

    }

    public String getBaseUrlWithTrailingSlash() {
        return baseUrlWithTrailingSlash;
    }

    public int getLoadCount() {
        return loadCount;
    }

    public boolean isHttps() {
        return isHttps;
    }


    public String getBaseUrl() {
        return baseUrl;
    }

    public boolean isLoggingEnabled() {
        return isLoggingEnabled;
    }


}
