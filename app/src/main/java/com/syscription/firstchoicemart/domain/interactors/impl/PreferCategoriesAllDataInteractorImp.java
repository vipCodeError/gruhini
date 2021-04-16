package com.syscription.firstchoicemart.domain.interactors.impl;

import android.util.Log;

import com.syscription.firstchoicemart.Network.ApiClient;
import com.syscription.firstchoicemart.Network.response.PreferDataResponse;
import com.syscription.firstchoicemart.Network.services.PreferCatApiInterface;
import com.syscription.firstchoicemart.domain.executor.Executor;
import com.syscription.firstchoicemart.domain.executor.MainThread;
import com.syscription.firstchoicemart.domain.interactors.PreferCatInteractor;
import com.syscription.firstchoicemart.domain.interactors.base.AbstractInteractor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreferCategoriesAllDataInteractorImp  extends AbstractInteractor {

    PreferCatApiInterface apiService;
    PreferCatInteractor.CallBack mCallBack;

    public PreferCategoriesAllDataInteractorImp(Executor threadExecutor, MainThread mainThread, PreferCatInteractor.CallBack mCallBack) {
        super(threadExecutor, mainThread);
        this.mCallBack = mCallBack;
    }

    @Override
    public void run() {
        apiService = ApiClient.getClient().create(PreferCatApiInterface.class);

        Call<PreferDataResponse> call = apiService.getPreferCategoryData();

        call.enqueue(new Callback<PreferDataResponse>() {
            @Override
            public void onResponse(Call<PreferDataResponse> call, Response<PreferDataResponse> response) {
                try {
                    //Log.d("Test", response.body().getVariant());
                    mCallBack.onPreferCaTDataArrived(response.body());
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<PreferDataResponse> call, Throwable t) {
                //Log.d("Test", String.valueOf(call.isExecuted()));
                mCallBack.onPreferItemAddedError();
            }
        });

    }
}
