package com.hpm.sp.streaminfoportal.Network;

import com.hpm.sp.streaminfoportal.Models.ResultDataObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryName;

/**
 * Created by kumardivyarajat on 1/21/18.
 */

public interface ApiInterface {

    @GET("events")
    Call<ResultDataObject> getAllEvents(
            @Query("_sort") String sortBy,
            @Query("dateTime_lte") String endTime,
            @Query("dateTime_gte") String currentTime
    );

    @GET("pravachana")
    Call<ResultDataObject> getAllVideos();

    @GET("guru?_sort=name")
    Call<ResultDataObject> getAllGurus();

    @GET("/")
    Call<ResultDataObject> getEpaatha();

    @GET("panchanga")
    Call<ResultDataObject> getAllPanchangas();

    @GET("article?_sort=-publishedDate")
    Call<ResultDataObject> getAllArticles();

    @GET("ekadashi")
    Call<ResultDataObject> getAllEkadashis();

    @GET("panchanga")
    Call<ResultDataObject> getPanchangaForToday(@Query("date") String date);

    @GET("branch?_sort=city")
    Call<ResultDataObject> getAllBranches();
}
