package com.syscription.firstchoicemart.Network.services;

import com.syscription.firstchoicemart.Network.response.ProductResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FeaturedProductApiInterface {
    @GET("products/featured")
    Call<ProductResponse> getFeaturedPrdocuts();
}
