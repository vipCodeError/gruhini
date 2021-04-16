package com.syscription.firstchoicemart.Network.services;

import com.syscription.firstchoicemart.Network.response.ProductResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TodaysDealApiInterface {
    @GET("products/todays-deal")
    Call<ProductResponse> getTodaysDeal();
}
