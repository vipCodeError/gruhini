package com.syscription.firstchoicemart.domain.interactors.impl;

import android.util.Log;

import com.syscription.firstchoicemart.Network.ApiClient;
import com.syscription.firstchoicemart.Network.response.RemoveCartResponse;
import com.syscription.firstchoicemart.Network.services.RemoveCartApiInterface;
import com.syscription.firstchoicemart.domain.executor.Executor;
import com.syscription.firstchoicemart.domain.executor.MainThread;
import com.syscription.firstchoicemart.domain.interactors.RemoveCartInteractor;
import com.syscription.firstchoicemart.domain.interactors.base.AbstractInteractor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoveCartInteractorImpl extends AbstractInteractor {
    private final RemoveCartInteractor.CallBack mCallback;
    private RemoveCartApiInterface apiService;
    private final int cart_id;
    private final String token;

    public RemoveCartInteractorImpl(Executor threadExecutor, MainThread mainThread, RemoveCartInteractor.CallBack callBack, int cart_id, String token) {
        super(threadExecutor, mainThread);
        mCallback = callBack;
        this.cart_id = cart_id;
        this.token = "Bearer "+token;
    }

    @Override
    public void run() {
        apiService = ApiClient.getClient().create(RemoveCartApiInterface.class);
        Call<RemoveCartResponse> call = apiService.removeCartItem(token,"carts/"+cart_id);

        call.enqueue(new Callback<RemoveCartResponse>() {
            @Override
            public void onResponse(Call<RemoveCartResponse> call, Response<RemoveCartResponse> response) {
                try {
                    mCallback.onCartItemRemoved(response.body());
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RemoveCartResponse> call, Throwable t) {
                mCallback.onCartItemRemovedError();
            }
        });

    }
}
