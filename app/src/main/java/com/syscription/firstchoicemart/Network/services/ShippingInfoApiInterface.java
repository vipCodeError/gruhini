package com.syscription.firstchoicemart.Network.services;

import com.syscription.firstchoicemart.Models.ShippingAddress;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ShippingInfoApiInterface {
    @FormUrlEncoded
    @POST("user/shipping/shipinfo")
    Call<List<ShippingAddress>> getShippingAddress(@Header("Authorization") String authHeader, @Field("user_id") int id);
}
