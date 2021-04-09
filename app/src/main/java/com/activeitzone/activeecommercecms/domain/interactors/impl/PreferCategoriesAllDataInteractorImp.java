package com.activeitzone.activeecommercecms.domain.interactors.impl;

import android.util.Log;

import com.activeitzone.activeecommercecms.Network.ApiClient;
import com.activeitzone.activeecommercecms.Network.response.PreferDataResponse;
import com.activeitzone.activeecommercecms.Network.response.ProductResponse;
import com.activeitzone.activeecommercecms.Network.services.PreferCatApiInterface;
import com.activeitzone.activeecommercecms.domain.executor.Executor;
import com.activeitzone.activeecommercecms.domain.executor.MainThread;
import com.activeitzone.activeecommercecms.domain.interactors.PreferCatInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.base.AbstractInteractor;
import com.google.gson.JsonObject;

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
