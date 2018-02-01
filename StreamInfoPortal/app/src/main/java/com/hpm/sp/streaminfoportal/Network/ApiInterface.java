package com.hpm.sp.streaminfoportal.Network;

import com.hpm.sp.streaminfoportal.Models.ResultDataObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by kumardivyarajat on 1/21/18.
 */

public interface ApiInterface {

    @GET("events")
    Call<ResultDataObject> getAllEvents();


    @GET("pravachanaList.php")
    Call<ResultDataObject> getAllVideos();
}
