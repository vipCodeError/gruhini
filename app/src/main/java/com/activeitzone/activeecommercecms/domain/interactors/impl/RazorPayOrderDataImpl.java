package com.activeitzone.activeecommercecms.domain.interactors.impl;

import android.util.Log;

import com.activeitzone.activeecommercecms.Network.ApiClient;
import com.activeitzone.activeecommercecms.Network.response.OrderResponse;
import com.activeitzone.activeecommercecms.Network.services.OrderApiInterface;
import com.activeitzone.activeecommercecms.Network.services.RazorPaymentApiInterface;
import com.activeitzone.activeecommercecms.domain.executor.Executor;
import com.activeitzone.activeecommercecms.domain.executor.MainThread;
import com.activeitzone.activeecommercecms.domain.interactors.OrderInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.base.AbstractInteractor;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RazorPayOrderDataImpl extends AbstractInteractor {
    private final OrderInteractor.CallBack mCallback;
    private RazorPaymentApiInterface apiService;
    private final JsonObject jsonObject;
    private final String auth_token;

    public RazorPayOrderDataImpl(Executor threadExecutor, MainThread mainThread, OrderInteractor.CallBack callBack, String auth_token, JsonObject jsonObject) {
        super(threadExecutor, mainThread);
        mCallback = callBack;
        this.auth_token = "Bearer "+auth_token;
        this.jsonObject = jsonObject;
    }

    @Override
    public void run() {
        apiService = ApiClient.getClient().create(RazorPaymentApiInterface.class);

        Call<OrderResponse> call = apiService.sendRazorData(auth_token, jsonObject);

        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                try {
                    //Log.d("Test", response.body().getVariant());
                    // mCallback.onOrderSubmitted(response.body());
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                //Log.d("Test", String.valueOf(call.isExecuted()));
                mCallback.onOrderSubmitError();
            }
        });

    }
}

