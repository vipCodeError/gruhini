package com.syscription.firstchoicemart.Network.services;

import com.syscription.firstchoicemart.Network.response.ShippingInfoResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ShippingInfoDeleteApiInterface {
    @FormUrlEncoded
    @POST("user/shipping/delete")
    Call<ShippingInfoResponse> deleteShippingAddress(@Header("Authorization") String authHeader, @Field("id") int id);
}
