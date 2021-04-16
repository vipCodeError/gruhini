package com.syscription.firstchoicemart.domain.interactors.impl;

import android.util.Log;

import com.syscription.firstchoicemart.Network.ApiClient;
import com.syscription.firstchoicemart.Network.response.WalletHistoryResponse;
import com.syscription.firstchoicemart.Network.services.WalletHistoryApiInterface;
import com.syscription.firstchoicemart.domain.executor.Executor;
import com.syscription.firstchoicemart.domain.executor.MainThread;
import com.syscription.firstchoicemart.domain.interactors.WalletHistoryInteractor;
import com.syscription.firstchoicemart.domain.interactors.base.AbstractInteractor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletHistoryInteractorImpl extends AbstractInteractor {
    private final WalletHistoryInteractor.CallBack mCallback;
    private WalletHistoryApiInterface apiService;
    private final int user_id;
    private final String auth_token;

    public WalletHistoryInteractorImpl(Executor threadExecutor, MainThread mainThread, WalletHistoryInteractor.CallBack callBack, int user_id, String auth_token) {
        super(threadExecutor, mainThread);
        mCallback = callBack;
        this.user_id = user_id;
        this.auth_token = "Bearer "+auth_token;
    }

    @Override
    public void run() {
        apiService = ApiClient.getClient().create(WalletHistoryApiInterface.class);

        Call<WalletHistoryResponse> call = apiService.getWalletHistory(auth_token, user_id);

        call.enqueue(new Callback<WalletHistoryResponse>() {
            @Override
            public void onResponse(Call<WalletHistoryResponse> call, Response<WalletHistoryResponse> response) {
                try {
                    mCallback.onWalletHistoryLodaded(response.body().getData());
                } catch (Exception e) {
                    Log.e("Wallet Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<WalletHistoryResponse> call, Throwable t) {
                Log.d("Wallet Test", t.getMessage());
                mCallback.onWalletHistoryLoadError();
            }
        });

    }
}
