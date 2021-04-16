package com.syscription.firstchoicemart.Network.services;

import com.syscription.firstchoicemart.Network.response.AppSettingsResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AppSettingsApiInterface {
    @GET("settings")
    Call<AppSettingsResponse> getAppSettings();
}
