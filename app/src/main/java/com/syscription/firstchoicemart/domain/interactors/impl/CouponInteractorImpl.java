package com.syscription.firstchoicemart.domain.interactors.impl;

import android.util.Log;

import com.syscription.firstchoicemart.Network.ApiClient;
import com.syscription.firstchoicemart.Network.response.CouponResponse;
import com.syscription.firstchoicemart.Network.services.CouponApiInterface;
import com.syscription.firstchoicemart.domain.executor.Executor;
import com.syscription.firstchoicemart.domain.executor.MainThread;
import com.syscription.firstchoicemart.domain.interactors.CouponInteractor;
import com.syscription.firstchoicemart.domain.interactors.base.AbstractInteractor;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CouponInteractorImpl extends AbstractInteractor {
    private final CouponInteractor.CallBack mCallback;
    private CouponApiInterface apiService;
    private final int user_id;
    private final String coupon_code;
    private final String auth_token;

    public CouponInteractorImpl(Executor threadExecutor, MainThread mainThread, CouponInteractor.CallBack callBack, int user_id, String coupon_code, String auth_token) {
        super(threadExecutor, mainThread);
        mCallback = callBack;
        this.user_id = user_id;
        this.coupon_code = coupon_code;
        this.auth_token = "Bearer "+auth_token;
    }

    @Override
    public void run() {
        apiService = ApiClient.getClient().create(CouponApiInterface.class);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", user_id);
        jsonObject.addProperty("code", coupon_code);

        Call<CouponResponse> call = apiService.getCouponResponse(auth_token, jsonObject);

        call.enqueue(new Callback<CouponResponse>() {
            @Override
            public void onResponse(Call<CouponResponse> call, Response<CouponResponse> response) {
                try {
                    //Log.d("Test", response.body().getVariant());
                    mCallback.onCouponApplied(response.body());
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<CouponResponse> call, Throwable t) {
                //Log.d("Test", String.valueOf(call.isExecuted()));
                mCallback.onCouponAppliedError();
            }
        });

    }
}
