package com.syscription.firstchoicemart.Network.services;

import com.syscription.firstchoicemart.Network.response.ReviewResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ReviewApiInterface {
    @GET
    Call<ReviewResponse> getReviews(@Url String url);
}
