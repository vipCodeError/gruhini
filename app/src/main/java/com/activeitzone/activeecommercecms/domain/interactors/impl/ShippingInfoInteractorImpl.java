package com.activeitzone.activeecommercecms.domain.interactors.impl;

import android.util.Log;

import com.activeitzone.activeecommercecms.Models.ShippingAddress;
import com.activeitzone.activeecommercecms.Network.ApiClient;
import com.activeitzone.activeecommercecms.Network.response.ShippingResponse;
import com.activeitzone.activeecommercecms.Network.services.ShippingInfoApiInterface;
import com.activeitzone.activeecommercecms.domain.executor.Executor;
import com.activeitzone.activeecommercecms.domain.executor.MainThread;
import com.activeitzone.activeecommercecms.domain.interactors.ShippingInfoInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.base.AbstractInteractor;

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
