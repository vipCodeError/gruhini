package com.syscription.firstchoicemart.domain.interactors.impl;

import android.util.Log;

import com.syscription.firstchoicemart.Network.ApiClient;
import com.syscription.firstchoicemart.Network.response.WishlistResponse;
import com.syscription.firstchoicemart.Network.services.WishlistApiInterface;
import com.syscription.firstchoicemart.domain.executor.Executor;
import com.syscription.firstchoicemart.domain.executor.MainThread;
import com.syscription.firstchoicemart.domain.interactors.WishlistInteractor;
import com.syscription.firstchoicemart.domain.interactors.base.AbstractInteractor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishlistInteractorImpl extends AbstractInteractor {
    private final WishlistInteractor.CallBack mCallback;
    private WishlistApiInterface apiService;
    private final int user_id;
    private final String token;

    public WishlistInteractorImpl(Executor threadExecutor, MainThread mainThread, WishlistInteractor.CallBack callBack, int id, String token) {
        super(threadExecutor, mainThread);
        mCallback = callBack;
        this.user_id = id;
        this.token = "Bearer "+token;
    }

    @Override
    public void run() {
        apiService = ApiClient.getClient().create(WishlistApiInterface.class);
        Call<WishlistResponse> call = apiService.getWishlistItems(token,"wishlists/"+user_id);

        call.enqueue(new Callback<WishlistResponse>() {
            @Override
            public void onResponse(Call<WishlistResponse> call, Response<WishlistResponse> response) {
                try {
                    mCallback.onWishlistLodaded(response.body().getData());
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<WishlistResponse> call, Throwable t) {
                mCallback.onWishlistError();
            }
        });

    }
}
