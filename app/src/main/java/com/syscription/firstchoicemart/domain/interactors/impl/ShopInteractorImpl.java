package com.syscription.firstchoicemart.domain.interactors.impl;

import android.util.Log;

import com.syscription.firstchoicemart.Network.ApiClient;
import com.syscription.firstchoicemart.Network.response.ShopResponse;
import com.syscription.firstchoicemart.Network.services.ShopApiInterface;
import com.syscription.firstchoicemart.domain.executor.Executor;
import com.syscription.firstchoicemart.domain.executor.MainThread;
import com.syscription.firstchoicemart.domain.interactors.ShopInteractor;
import com.syscription.firstchoicemart.domain.interactors.base.AbstractInteractor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopInteractorImpl extends AbstractInteractor {
    private final ShopInteractor.CallBack mCallback;
    private ShopApiInterface apiService;
    private final String url;

    public ShopInteractorImpl(Executor threadExecutor, MainThread mainThread, ShopInteractor.CallBack callBack, String url) {
        super(threadExecutor, mainThread);
        mCallback = callBack;
        this.url = url;
    }

    @Override
    public void run() {
        apiService = ApiClient.getClient().create(ShopApiInterface.class);
        Call<ShopResponse> call = apiService.getShopDetails(url);

        call.enqueue(new Callback<ShopResponse>() {
            @Override
            public void onResponse(Call<ShopResponse> call, Response<ShopResponse> response) {
                try {
                    mCallback.onShopLoaded(response.body().getData().get(0));
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ShopResponse> call, Throwable t) {
                mCallback.onShopLoadError();
            }
        });

    }
}
