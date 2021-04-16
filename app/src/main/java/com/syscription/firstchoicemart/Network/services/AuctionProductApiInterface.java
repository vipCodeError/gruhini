package com.syscription.firstchoicemart.Network.services;

import com.syscription.firstchoicemart.Network.response.AuctionResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AuctionProductApiInterface {
    @GET("auctions")
    Call<AuctionResponse> getAuctionProducts();
}
