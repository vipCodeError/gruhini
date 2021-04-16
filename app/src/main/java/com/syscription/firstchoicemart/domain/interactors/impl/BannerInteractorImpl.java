package com.syscription.firstchoicemart.domain.interactors.impl;

import android.util.Log;

import com.syscription.firstchoicemart.Network.ApiClient;
import com.syscription.firstchoicemart.Network.response.BannerResponse;
import com.syscription.firstchoicemart.Network.services.BannerApiInterface;
import com.syscription.firstchoicemart.domain.executor.Executor;
import com.syscription.firstchoicemart.domain.executor.MainThread;
import com.syscription.firstchoicemart.domain.interactors.BannerInteractor;
import com.syscription.firstchoicemart.domain.interactors.base.AbstractInteractor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BannerInteractorImpl extends AbstractInteractor {
    private final BannerInteractor.CallBack mCallback;
    private BannerApiInterface apiService;

    public BannerInteractorImpl(Executor threadExecutor, MainThread mainThread, BannerInteractor.CallBack callBack) {
        super(threadExecutor, mainThread);
        mCallback = callBack;
    }

    @Override
    public void run() {
        apiService = ApiClient.getClient().create(BannerApiInterface.class);
        Call<BannerResponse> call = apiService.getBanners();

        call.enqueue(new Callback<BannerResponse>() {
            @Override
            public void onResponse(Call<BannerResponse> call, Response<BannerResponse> response) {
                try {
                    mCallback.onBannersDownloaded(response.body().getData());
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<BannerResponse> call, Throwable t) {
                mCallback.onBannersDownloadError();
            }
        });

    }
}
