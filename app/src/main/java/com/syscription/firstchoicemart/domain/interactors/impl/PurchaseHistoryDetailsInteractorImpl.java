package com.syscription.firstchoicemart.domain.interactors.impl;

import android.util.Log;

import com.syscription.firstchoicemart.Network.ApiClient;
import com.syscription.firstchoicemart.Network.response.PurchaseHistoryDetailsResponse;
import com.syscription.firstchoicemart.Network.services.PurchaseHistoryDetailsApiInterface;
import com.syscription.firstchoicemart.domain.executor.Executor;
import com.syscription.firstchoicemart.domain.executor.MainThread;
import com.syscription.firstchoicemart.domain.interactors.PurchaseHistoryDetailsInteractor;
import com.syscription.firstchoicemart.domain.interactors.base.AbstractInteractor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseHistoryDetailsInteractorImpl extends AbstractInteractor {
    private final PurchaseHistoryDetailsInteractor.CallBack mCallback;
    private PurchaseHistoryDetailsApiInterface apiService;
    private final String url;
    private final String token;

    public PurchaseHistoryDetailsInteractorImpl(Executor threadExecutor, MainThread mainThread, PurchaseHistoryDetailsInteractor.CallBack callBack, String url, String token) {
        super(threadExecutor, mainThread);
        mCallback = callBack;
        this.url = url;
        this.token = "Bearer "+token;
    }

    @Override
    public void run() {
        apiService = ApiClient.getClient().create(PurchaseHistoryDetailsApiInterface.class);
        Call<PurchaseHistoryDetailsResponse> call = apiService.getOrderItems(token, url);

        call.enqueue(new Callback<PurchaseHistoryDetailsResponse>() {
            @Override
            public void onResponse(Call<PurchaseHistoryDetailsResponse> call, Response<PurchaseHistoryDetailsResponse> response) {
                try {
                    mCallback.onOrderDetailsLoaded(response.body().getData());
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<PurchaseHistoryDetailsResponse> call, Throwable t) {
                mCallback.onOrderDetailsLoadError();
            }
        });

    }
}
