package com.syscription.firstchoicemart.domain.interactors.impl;

import android.util.Log;

import com.syscription.firstchoicemart.Network.ApiClient;
import com.syscription.firstchoicemart.Network.response.CheckWishlistResponse;
import com.syscription.firstchoicemart.Network.services.CheckWishlistApiInterface;
import com.syscription.firstchoicemart.domain.executor.Executor;
import com.syscription.firstchoicemart.domain.executor.MainThread;
import com.syscription.firstchoicemart.domain.interactors.CheckWishlistInteractor;
import com.syscription.firstchoicemart.domain.interactors.base.AbstractInteractor;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckWishlistInteractorImpl extends AbstractInteractor {
    private final CheckWishlistInteractor.CallBack mCallback;
    private CheckWishlistApiInterface apiService;
    private final int user_id;
    private final int product_id;
    private final String auth_token;

    public CheckWishlistInteractorImpl(Executor threadExecutor, MainThread mainThread, CheckWishlistInteractor.CallBack callBack, String auth_token, int user_id, int product_id) {
        super(threadExecutor, mainThread);
        mCallback = callBack;
        this.user_id = user_id;
        this.product_id = product_id;
        this.auth_token = "Bearer "+auth_token;
    }

    @Override
    public void run() {
        apiService = ApiClient.getClient().create(CheckWishlistApiInterface.class);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", user_id);
        jsonObject.addProperty("product_id", product_id);

        Call<CheckWishlistResponse> call = apiService.checkWishlistRequest(auth_token, jsonObject);

        call.enqueue(new Callback<CheckWishlistResponse>() {
            @Override
            public void onResponse(Call<CheckWishlistResponse> call, Response<CheckWishlistResponse> response) {
                try {
                    //Log.d("Test", response.body().getVariant());
                    mCallback.onWishlistChecked(response.body());
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<CheckWishlistResponse> call, Throwable t) {
                //Log.d("Test", String.valueOf(call.isExecuted()));
                mCallback.onWishlistCheckedError();
            }
        });

    }
}
