package com.syscription.firstchoicemart.Network.services;

import com.syscription.firstchoicemart.Network.response.SliderImageResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SliderImageApiInterface {
    @GET("sliders")
    Call<SliderImageResponse> getSliderImages();
}
