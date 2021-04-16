package com.syscription.firstchoicemart.domain.interactors.impl;

import android.util.Log;

import com.syscription.firstchoicemart.Network.ApiClient;
import com.syscription.firstchoicemart.Network.response.ShippingInfoResponse;
import com.syscription.firstchoicemart.Network.services.ShippingInfoDeleteApiInterface;
import com.syscription.firstchoicemart.domain.executor.Executor;
import com.syscription.firstchoicemart.domain.executor.MainThread;
import com.syscription.firstchoicemart.domain.interactors.ShippingInfoDeleteInteractor;
import com.syscription.firstchoicemart.domain.interactors.base.AbstractInteractor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShippingInfoDeleteInteractorImpl extends AbstractInteractor {
    private final ShippingInfoDeleteInteractor.CallBack mCallback;
    private ShippingInfoDeleteApiInterface apiService;
    private final int address_id;
    private final String auth_token;

    public ShippingInfoDeleteInteractorImpl(Executor threadExecutor, MainThread mainThread, ShippingInfoDeleteInteractor.CallBack callBack, int address_id, String auth_token) {
        super(threadExecutor, mainThread);
        mCallback = callBack;
        this.address_id = address_id;
        this.auth_token = "Bearer "+auth_token;
    }

    @Override
    public void run() {
        apiService = ApiClient.getClient().create(ShippingInfoDeleteApiInterface.class);
        Call<ShippingInfoResponse> call = apiService.deleteShippingAddress(auth_token, address_id);
        call.enqueue(new Callback<ShippingInfoResponse>() {
            @Override
            public void onResponse(Call<ShippingInfoResponse> call, Response<ShippingInfoResponse> response) {
                try {
                    mCallback.onShippingInfoDeleted(response.body());
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ShippingInfoResponse> call, Throwable t) {
                Log.d("Test", t.getMessage());
                mCallback.onShippingInfoDeleteError();
            }
        });

    }
}
