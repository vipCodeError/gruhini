package com.activeitzone.activeecommercecms.domain.interactors;

import android.util.Log;

import com.activeitzone.activeecommercecms.Network.ApiClient;
import com.activeitzone.activeecommercecms.Network.response.ProductListingResponse;
import com.activeitzone.activeecommercecms.Network.services.ProductListingApiInterface;
import com.activeitzone.activeecommercecms.domain.executor.Executor;
import com.activeitzone.activeecommercecms.domain.executor.MainThread;
import com.activeitzone.activeecommercecms.domain.interactors.base.AbstractInteractor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListingHomeInteractorImpl  extends AbstractInteractor {
    private final ProductListingInteractor.CallBack mCallback;
    private ProductListingApiInterface apiService;
    private final String url;
    private int whichInteractor;

    public ProductListingHomeInteractorImpl(Executor threadExecutor, MainThread mainThread, ProductListingInteractor.CallBack callBack, String url, int whichInteractor) {
        super(threadExecutor, mainThread);
        mCallback = callBack;
        this.url = url;
        this.whichInteractor = whichInteractor;
    }

    @Override
    public void run() {
        apiService = ApiClient.getClient().create(ProductListingApiInterface.class);
        Call<ProductListingResponse> call = apiService.getProducts(url);

        call.enqueue(new Callback<ProductListingResponse>() {
            @Override
            public void onResponse(Call<ProductListingResponse> call, Response<ProductListingResponse> response) {
                try {
                    if (whichInteractor == 5){
                        mCallback.onProductFifthDownloaded(response.body());
                    }else if(whichInteractor == 10){
                        mCallback.onProductTenthDownloaded(response.body());
                    } else if(whichInteractor == 1){
                        mCallback.onProductFirstDownloaded(response.body());
                    } else if(whichInteractor == 0){
                        mCallback.onProductZeroDownload(response.body());
                    } else if(whichInteractor == 2){
                        mCallback.onProductSecondDownloaded(response.body());
                    } else if(whichInteractor == 3){
                        mCallback.onProductThirdDownloaded(response.body());
                    } else if(whichInteractor == 4){
                        mCallback.onProductFourthDownloaded(response.body());
                    } else if(whichInteractor == 9){
                        mCallback.onProductNinthDownloaded(response.body());
                    } else if(whichInteractor == 8){
                        mCallback.onProductEigthDownloaded(response.body());
                    } else if(whichInteractor == 6){
                        mCallback.onProductSixthDownloaded(response.body());
                    } else if(whichInteractor == 7){
                        mCallback.onProductSeventhDownloaded(response.body());
                    }

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
