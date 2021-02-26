package com.activeitzone.activeecommercecms.Network.services;

import com.activeitzone.activeecommercecms.Network.response.ShippingInfoResponse;
import com.activeitzone.activeecommercecms.Network.response.ShippingResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ShippingInfoDeleteApiInterface {
    @FormUrlEncoded
    @POST("user/shipping/delete")
    Call<ShippingInfoResponse> deleteShippingAddress(@Header("Authorization") String authHeader, @Field("id") int id);
}
