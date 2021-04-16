package com.syscription.firstchoicemart.domain.interactors.impl;

import android.util.Log;

import com.syscription.firstchoicemart.Network.ApiClient;
import com.syscription.firstchoicemart.Network.response.ProductResponse;
import com.syscription.firstchoicemart.Network.services.TodaysDealApiInterface;
import com.syscription.firstchoicemart.domain.executor.Executor;
import com.syscription.firstchoicemart.domain.executor.MainThread;
import com.syscription.firstchoicemart.domain.interactors.TodaysDealInteractor;
import com.syscription.firstchoicemart.domain.interactors.base.AbstractInteractor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodaysDealInteractorImpl extends AbstractInteractor {
    private final TodaysDealInteractor.CallBack mCallback;
    private TodaysDealApiInterface apiService;

    public TodaysDealInteractorImpl(Executor threadExecutor, MainThread mainThread, TodaysDealInteractor.CallBack callBack) {
        super(threadExecutor, mainThread);
        mCallback = callBack;
    }

    @Override
    public void run() {
        apiService = ApiClient.getClient().create(TodaysDealApiInterface.class);
        Call<ProductResponse> call = apiService.getTodaysDeal();

        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                try {
                    mCallback.onTodaysDealProductDownloaded(response.body().getData());
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                mCallback.onTodaysDealProductDownloadError();
            }
        });

    }
}
