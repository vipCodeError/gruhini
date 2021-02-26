package com.activeitzone.activeecommercecms.Presentation.presenters;

import com.activeitzone.activeecommercecms.Network.response.ProductListingResponse;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.ProductListingView;
import com.activeitzone.activeecommercecms.domain.executor.Executor;
import com.activeitzone.activeecommercecms.domain.executor.MainThread;
import com.activeitzone.activeecommercecms.domain.interactors.ProductListingInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.impl.ProductListingInteractorImpl;

public class ProductListingPresenter extends AbstractPresenter implements ProductListingInteractor.CallBack {
    private final ProductListingView productListingView;

    public ProductListingPresenter(Executor executor, MainThread mainThread, ProductListingView productListingView) {
        super(executor, mainThread);
        this.productListingView = productListingView;
    }

    public void getProducts(String url) {
        new ProductListingInteractorImpl(mExecutor, mMainThread, this, url).execute();
    }


    @Override
    public void onProductDownloaded(ProductListingResponse productListingResponse) {
        if (productListingView != null){
            productListingView.setProducts(productListingResponse);
        }
    }

    @Override
    public void onProductMensDownloaded(ProductListingResponse productListingResponse) {

    }

    @Override
    public void onProductWomanDownloaded(ProductListingResponse productListingResponse) {

    }

    @Override
    public void onProductBeautyDownloaded(ProductListingResponse productListingResponse) {

    }

    @Override
    public void onProductSportsAndFitnessDownloaded(ProductListingResponse productListingResponse) {

    }

    @Override
    public void onProductToyBabyDownloaded(ProductListingResponse productListingResponse) {

    }

    @Override
    public void onProductElectonicsDownloaded(ProductListingResponse productListingResponse) {

    }

    @Override
    public void onProductMobilePcDownloaded(ProductListingResponse productListingResponse) {

    }

    @Override
    public void onProductKitchenAndHomeDownloaded(ProductListingResponse productListingResponse) {

    }

    @Override
    public void onProductBagAndLuggageDownloaded(ProductListingResponse productListingResponse) {

    }

    @Override
    public void onProductBooksDownloaded(ProductListingResponse productListingResponse) {

    }

    @Override
    public void onProductDownloadError() {

    }
}
