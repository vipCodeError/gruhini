package com.syscription.firstchoicemart.Network.services;

import com.syscription.firstchoicemart.Network.response.BannerResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BannerApiInterface {
    @GET("banners")
    Call<BannerResponse> getBanners();
}
