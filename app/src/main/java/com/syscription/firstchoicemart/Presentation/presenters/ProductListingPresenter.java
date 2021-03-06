package com.syscription.firstchoicemart.Presentation.presenters;

import com.syscription.firstchoicemart.Network.response.ProductListingResponse;
import com.syscription.firstchoicemart.Presentation.ui.activities.ProductListingView;
import com.syscription.firstchoicemart.domain.executor.Executor;
import com.syscription.firstchoicemart.domain.executor.MainThread;
import com.syscription.firstchoicemart.domain.interactors.ProductListingInteractor;
import com.syscription.firstchoicemart.domain.interactors.impl.ProductListingInteractorImpl;

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

    }

    @Override
    public void onProductZeroDownload(ProductListingResponse productListingResponse) {

    }

    @Override
    public void onProductFirstDownloaded(ProductListingResponse productListingResponse) {

    }

    @Override
    public void onProductSecondDownloaded(ProductListingResponse productListingResponse) {

    }

    @Override
    public void onProductThirdDownloaded(ProductListingResponse productListingResponse) {

    }

    @Override
    public void onProductFourthDownloaded(ProductListingResponse productListingResponse) {

    }

    @Override
    public void onProductFifthDownloaded(ProductListingResponse productListingResponse) {

    }

    @Override
    public void onProductSixthDownloaded(ProductListingResponse productListingResponse) {

    }

    @Override
    public void onProductSeventhDownloaded(ProductListingResponse productListingResponse) {

    }

    @Override
    public void onProductEigthDownloaded(ProductListingResponse productListingResponse) {

    }

    @Override
    public void onProductNinthDownloaded(ProductListingResponse productListingResponse) {

    }

    @Override
    public void onProductTenthDownloaded(ProductListingResponse productListingResponse) {

    }

    @Override
    public void onProductDownloadError() {

    }
}
