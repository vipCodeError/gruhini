package com.syscription.firstchoicemart.domain.interactors.impl;

import android.util.Log;

import com.syscription.firstchoicemart.Network.ApiClient;
import com.syscription.firstchoicemart.Network.response.AuthResponse;
import com.syscription.firstchoicemart.Network.services.SocialLoginApiInterface;
import com.syscription.firstchoicemart.domain.executor.Executor;
import com.syscription.firstchoicemart.domain.executor.MainThread;
import com.syscription.firstchoicemart.domain.interactors.SocialLoginInteractor;
import com.syscription.firstchoicemart.domain.interactors.base.AbstractInteractor;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SocialLoginInteractorImpl extends AbstractInteractor {
    private final SocialLoginInteractor.CallBack mCallback;
    private SocialLoginApiInterface apiService;
    private final String id;
    private final String name;
    private final String email;


    public SocialLoginInteractorImpl(Executor threadExecutor, MainThread mainThread, SocialLoginInteractor.CallBack callBack, String id, String name, String email) {
        super(threadExecutor, mainThread);
        mCallback = callBack;
        this.id = id;
        this.name = name;
        this.email = email;
    }

    @Override
    public void run() {
        apiService = ApiClient.getClient().create(SocialLoginApiInterface.class);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", id);
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("remember_me", true);

        Call<AuthResponse> call = apiService.sendSocialLoginCredentials(jsonObject);

        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                try {
                    //Log.d("Test", response.body().toString());
                    mCallback.onValidLogin(response.body());
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                //Log.d("Test", String.valueOf(t.getMessage()));
                mCallback.onLoginError();
            }
        });

    }
}
