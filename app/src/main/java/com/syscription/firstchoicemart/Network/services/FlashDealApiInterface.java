package com.syscription.firstchoicemart.Network.services;

import com.syscription.firstchoicemart.Network.response.FlashDealResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FlashDealApiInterface {
    @GET("products/flash-deal")
    Call<FlashDealResponse> getFlashDeal();
}