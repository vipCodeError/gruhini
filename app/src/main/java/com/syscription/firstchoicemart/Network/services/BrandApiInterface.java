package com.syscription.firstchoicemart.Network.services;

import com.syscription.firstchoicemart.Network.response.BrandResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BrandApiInterface {
    @GET("brands")
    Call<BrandResponse> getBrands();
}
