package com.syscription.firstchoicemart.domain.interactors.impl;

import android.util.Log;

import com.syscription.firstchoicemart.Network.ApiClient;
import com.syscription.firstchoicemart.Network.response.AuctionResponse;
import com.syscription.firstchoicemart.Network.services.AuctionProductApiInterface;
import com.syscription.firstchoicemart.domain.executor.Executor;
import com.syscription.firstchoicemart.domain.executor.MainThread;
import com.syscription.firstchoicemart.domain.interactors.AuctionProductInteractor;
import com.syscription.firstchoicemart.domain.interactors.base.AbstractInteractor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuctionProductInteractorImpl extends AbstractInteractor {
    private final AuctionProductInteractor.CallBack mCallback;
    private AuctionProductApiInterface apiService;

    public AuctionProductInteractorImpl(Executor threadExecutor, MainThread mainThread, AuctionProductInteractor.CallBack callBack) {
        super(threadExecutor, mainThread);
        mCallback = callBack;
    }

    @Override
    public void run() {
        apiService = ApiClient.getClient().create(AuctionProductApiInterface.class);
        Call<AuctionResponse> call = apiService.getAuctionProducts();

        call.enqueue(new Callback<AuctionResponse>() {
            @Override
            public void onResponse(Call<AuctionResponse> call, Response<AuctionResponse> response) {
                try {
                    mCallback.onAuctionProductDownloaded(response.body().getData());
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<AuctionResponse> call, Throwable t) {
                mCallback.onAuctionProductDownloadError();
            }
        });

    }
}
