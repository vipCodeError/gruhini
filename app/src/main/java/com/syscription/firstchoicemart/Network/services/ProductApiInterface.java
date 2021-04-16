package com.syscription.firstchoicemart.Network.services;

import com.syscription.firstchoicemart.Network.response.ProductResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ProductApiInterface {
    @GET
    Call<ProductResponse> getProducts(@Url String url);
}
