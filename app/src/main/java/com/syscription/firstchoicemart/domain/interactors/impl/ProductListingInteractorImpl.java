package com.syscription.firstchoicemart.domain.interactors.impl;

import android.util.Log;

import com.syscription.firstchoicemart.Network.ApiClient;
import com.syscription.firstchoicemart.Network.response.ProductListingResponse;
import com.syscription.firstchoicemart.Network.services.ProductListingApiInterface;
import com.syscription.firstchoicemart.domain.executor.Executor;
import com.syscription.firstchoicemart.domain.executor.MainThread;
import com.syscription.firstchoicemart.domain.interactors.ProductListingInteractor;
import com.syscription.firstchoicemart.domain.interactors.base.AbstractInteractor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListingInteractorImpl extends AbstractInteractor {
    private final ProductListingInteractor.CallBack mCallback;
    private ProductListingApiInterface apiService;
    private final String url;

    public ProductListingInteractorImpl(Executor threadExecutor, MainThread mainThread, ProductListingInteractor.CallBack callBack, String url) {
        super(threadExecutor, mainThread);
        mCallback = callBack;
        this.url = url;
    }

    @Override
    public void run() {
        apiService = ApiClient.getClient().create(ProductListingApiInterface.class);
        Call<ProductListingResponse> call = apiService.getProducts(url);

        call.enqueue(new Callback<ProductListingResponse>() {
            @Override
            public void onResponse(Call<ProductListingResponse> call, Response<ProductListingResponse> response) {
                try {
                    mCallback.onProductDownloaded(response.body());
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ProductListingResponse> call, Throwable t) {
                mCallback.onProductDownloadError();
            }
        });

    }
}
