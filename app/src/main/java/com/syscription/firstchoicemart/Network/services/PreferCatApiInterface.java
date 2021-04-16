package com.syscription.firstchoicemart.Network.services;

import com.syscription.firstchoicemart.Network.response.PreferDataResponse;
import com.syscription.firstchoicemart.Network.response.ProductResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PreferCatApiInterface {

    @Headers("Content-Type: application/json")
    @POST("get_prefer_data_by_sub_shown")
    Call<ProductResponse> getPreferCategoryDataBySubShown(@Body JsonObject jsonObjec);

    @GET("get_prefer_data")
    Call<PreferDataResponse> getPreferCategoryData();
}
