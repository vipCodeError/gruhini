package com.syscription.firstchoicemart.domain.interactors.impl;

import android.util.Log;

import com.syscription.firstchoicemart.Models.ShippingAddress;
import com.syscription.firstchoicemart.Network.ApiClient;
import com.syscription.firstchoicemart.Network.services.ShippingInfoApiInterface;
import com.syscription.firstchoicemart.domain.executor.Executor;
import com.syscription.firstchoicemart.domain.executor.MainThread;
import com.syscription.firstchoicemart.domain.interactors.ShippingInfoInteractor;
import com.syscription.firstchoicemart.domain.interactors.base.AbstractInteractor;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShippingInfoInteractorImpl extends AbstractInteractor {
    private final ShippingInfoInteractor.CallBack mCallback;
    private ShippingInfoApiInterface apiService;
    private final int user_id;
    private final String auth_token;

    public ShippingInfoInteractorImpl(Executor threadExecutor, MainThread mainThread, ShippingInfoInteractor.CallBack callBack, int user_id, String auth_token) {
        super(threadExecutor, mainThread);
        mCallback = callBack;
        this.user_id = user_id;
        this.auth_token = "Bearer "+auth_token;
    }

    @Override
    public void run() {
        apiService = ApiClient.getClient().create(ShippingInfoApiInterface.class);

        Call<List<ShippingAddress>> call = apiService.getShippingAddress(auth_token, user_id);

        call.enqueue(new Callback<List<ShippingAddress>>() {
            @Override
            public void onResponse(Call<List<ShippingAddress>> call, Response<List<ShippingAddress>> response) {
                try {
                    mCallback.onShippingInfoRetrived(response.body());
                } catch (Exception e) {
                    Log.e("Shipping Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<ShippingAddress>> call, Throwable t) {
                Log.d("Shipping Test", t.getMessage());
                mCallback.onShippingInfoRetrivedError();
            }
        });

    }
}
