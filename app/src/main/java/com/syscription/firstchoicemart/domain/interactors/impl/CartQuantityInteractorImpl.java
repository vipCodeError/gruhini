package com.syscription.firstchoicemart.domain.interactors.impl;

import android.util.Log;

import com.syscription.firstchoicemart.Network.ApiClient;
import com.syscription.firstchoicemart.Network.response.CartQuantityUpdateResponse;
import com.syscription.firstchoicemart.Network.services.CartQuantityApiInterface;
import com.syscription.firstchoicemart.domain.executor.Executor;
import com.syscription.firstchoicemart.domain.executor.MainThread;
import com.syscription.firstchoicemart.domain.interactors.CartQuantityInteractor;
import com.syscription.firstchoicemart.domain.interactors.base.AbstractInteractor;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartQuantityInteractorImpl extends AbstractInteractor {
    private final CartQuantityInteractor.CallBack mCallback;
    private CartQuantityApiInterface apiService;
    private final int id;
    private final int quantity;
    private final String auth_token;

    public CartQuantityInteractorImpl(Executor threadExecutor, MainThread mainThread, CartQuantityInteractor.CallBack callBack, int id, int quantity, String auth_token) {
        super(threadExecutor, mainThread);
        mCallback = callBack;
        this.id = id;
        this.quantity = quantity;
        this.auth_token = "Bearer "+auth_token;
    }

    @Override
    public void run() {
        apiService = ApiClient.getClient().create(CartQuantityApiInterface.class);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", id);
        jsonObject.addProperty("quantity", quantity);

        Call<CartQuantityUpdateResponse> call = apiService.sendUpdateCartQuantutyRequest(auth_token, jsonObject);

        call.enqueue(new Callback<CartQuantityUpdateResponse>() {
            @Override
            public void onResponse(Call<CartQuantityUpdateResponse> call, Response<CartQuantityUpdateResponse> response) {
                try {
                    //Log.d("Test", response.body().getVariant());
                    mCallback.onCartQuantityUpdated(response.body());
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<CartQuantityUpdateResponse> call, Throwable t) {
                //Log.d("Test", String.valueOf(call.isExecuted()));
                mCallback.onCartQuantityUpdatedError();
            }
        });

    }
}
