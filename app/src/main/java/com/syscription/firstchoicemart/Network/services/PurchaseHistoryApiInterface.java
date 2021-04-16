package com.syscription.firstchoicemart.Network.services;

import com.syscription.firstchoicemart.Network.response.PurchaseHistoryResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Url;

public interface PurchaseHistoryApiInterface {
    @GET
    Call<PurchaseHistoryResponse> getPurchaseHistories(@Header("Authorization") String authHeader, @Url String url);
}
