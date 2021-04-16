package com.syscription.firstchoicemart.Network.services;

import com.syscription.firstchoicemart.Network.response.PurchaseHistoryDetailsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Url;

public interface PurchaseHistoryDetailsApiInterface {
    @GET
    Call<PurchaseHistoryDetailsResponse> getOrderItems(@Header("Authorization") String authHeader, @Url String url);
}
