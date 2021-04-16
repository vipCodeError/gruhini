package com.syscription.firstchoicemart.domain.interactors.impl;

import android.util.Log;

import com.syscription.firstchoicemart.Models.PreferCatModel;
import com.syscription.firstchoicemart.Network.ApiClient;
import com.syscription.firstchoicemart.Network.response.ProductResponse;
import com.syscription.firstchoicemart.Network.services.PreferCatApiInterface;
import com.syscription.firstchoicemart.domain.executor.Executor;
import com.syscription.firstchoicemart.domain.executor.MainThread;
import com.syscription.firstchoicemart.domain.interactors.ProductInteractor;
import com.syscription.firstchoicemart.domain.interactors.base.AbstractInteractor;
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
                    if(whichInteractor == 0){
                        mCallback.onProductZeroDownload(response.body());
                    } else if(whichInteractor == 1){
                        mCallback.onProductFirstDownloaded(response.body());
                    }  else if(whichInteractor == 2){
                        mCallback.onProductSecondDownloaded(response.body());
                    } else if(whichInteractor == 3){
                        mCallback.onProductThirdDownloaded(response.body());
                    } else if(whichInteractor == 4){
                        mCallback.onProductFourthDownloaded(response.body());
                    } else if(whichInteractor == 5){
                        mCallback.onProductFifthDownloaded(response.body());
                    }  else if(whichInteractor == 6){
                        mCallback.onProductSixthDownloaded(response.body());
                    } else if(whichInteractor == 7){
                        mCallback.onProductSeventhDownloaded(response.body());
                    } else if(whichInteractor == 8){
                        mCallback.onProductEigthDownloaded(response.body());
                    } else if(whichInteractor == 9){
                        mCallback.onProductNinthDownloaded(response.body());
                    } else if(whichInteractor == 10){
                        mCallback.onProductTenthDownloaded(response.body());
                    } else if(whichInteractor == 11){
                        mCallback.onProductEleventhDownloaded(response.body());
                    } else if(whichInteractor == 12){
                        mCallback.onProductTwelthDownloaded(response.body());
                    } else if(whichInteractor == 13){
                        mCallback.onProductThirteenDownloaded(response.body());
                    } else if(whichInteractor == 14){
                        mCallback.onProductFourteenDownloaded(response.body());
                    } else if(whichInteractor == 15){
                        mCallback.onProductFifteenDownloaded(response.body());
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
