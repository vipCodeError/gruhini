package com.activeitzone.activeecommercecms.Network.services;

import com.activeitzone.activeecommercecms.Network.response.OrderResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RazorPaymentApiInterface {
    @Headers("Content-Type: application/json")
    @POST("payments/pay/razorpay")
    Call<OrderResponse> sendRazorData(@Header("Authorization") String authHeader, @Body JsonObject jsonObject);

    @Headers("Content-Type: application/json")
    @POST("payments/pay/razorpayorder")
    Call<OrderResponse> createRazorOrderId(@Header("Authorization") String authHeader, @Body JsonObject jsonObject);

}
