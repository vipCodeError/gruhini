package com.activeitzone.activeecommercecms.Network.services;

import com.activeitzone.activeecommercecms.Models.ShippingAddress;
import com.activeitzone.activeecommercecms.Network.response.ShippingResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ShippingInfoApiInterface {
    @FormUrlEncoded
    @POST("user/shipping/shipinfo")
    Call<List<ShippingAddress>> getShippingAddress(@Header("Authorization") String authHeader, @Field("user_id") int id);
}
