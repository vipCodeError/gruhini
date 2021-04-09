package com.activeitzone.activeecommercecms.domain.interactors.impl;

import android.util.Log;

import com.activeitzone.activeecommercecms.Models.PreferCatModel;
import com.activeitzone.activeecommercecms.Network.ApiClient;
import com.activeitzone.activeecommercecms.Network.response.AddToCartResponse;
import com.activeitzone.activeecommercecms.Network.response.PreferDataResponse;
import com.activeitzone.activeecommercecms.Network.response.ProductResponse;
import com.activeitzone.activeecommercecms.Network.services.PreferCatApiInterface;
import com.activeitzone.activeecommercecms.domain.executor.Executor;
import com.activeitzone.activeecommercecms.domain.executor.MainThread;
import com.activeitzone.activeecommercecms.domain.interactors.PreferCatInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.PreferCatIsShownInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.ProductInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.ProductListingInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.base.AbstractInteractor;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreferCategoriesInteractorImpl extends AbstractInteractor {
    PreferCatApiInterface apiService;
    private final ProductInteractor.CallBack mCallback;
    private PreferCatModel preferCatModel;
    int whichInteractor ;


    public PreferCategoriesInteractorImpl(Executor threadExecutor, MainThread mainThread, ProductInteractor.CallBack mCallBack, PreferCatModel preferCatModel,  int whichInteractor) {
        super(threadExecutor, mainThread);
        this.mCallback = mCallBack;
        this.preferCatModel = preferCatModel;
        this.whichInteractor = whichInteractor;
    }

    @Override
    public void run() {
        apiService = ApiClient.getClient().create(PreferCatApiInterface.class);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("is_sub_shown", preferCatModel.subShown);
        jsonObject.addProperty("cat_id", preferCatModel.catId);
        jsonObject.addProperty("sub_id", preferCatModel.subId);

        Call<ProductResponse> call = apiService.getPreferCategoryDataBySubShown(jsonObject);

        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                try {
                    //Log.d("Test", response.body().getVariant());
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
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                //Log.d("Test", String.valueOf(call.isExecuted()));
               // mCallback.onPreferCatItemAddedError();
            }
        });

    }
}
