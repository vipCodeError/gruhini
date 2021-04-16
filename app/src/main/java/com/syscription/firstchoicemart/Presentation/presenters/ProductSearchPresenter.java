package com.syscription.firstchoicemart.Presentation.presenters;

import com.syscription.firstchoicemart.Network.response.ProductSearchResponse;
import com.syscription.firstchoicemart.Presentation.ui.fragments.ProductSearchView;
import com.syscription.firstchoicemart.domain.executor.Executor;
import com.syscription.firstchoicemart.domain.executor.MainThread;
import com.syscription.firstchoicemart.domain.interactors.ProductSearchInteractor;
import com.syscription.firstchoicemart.domain.interactors.impl.ProductSearchInteractorImpl;

public class ProductSearchPresenter extends AbstractPresenter implements ProductSearchInteractor.CallBack {
    private final ProductSearchView ProductSearchView;

    public ProductSearchPresenter(Executor executor, MainThread mainThread, ProductSearchView ProductSearchView) {
        super(executor, mainThread);
        this.ProductSearchView = ProductSearchView;
    }

    public void getSearchedProducts(String url) {
        new ProductSearchInteractorImpl(mExecutor, mMainThread, this, url).execute();
    }


    @Override
    public void onProductSearched(ProductSearchResponse productSearchResponse) {
        if (ProductSearchView != null){
            ProductSearchView.setSearchedProduct(productSearchResponse);
        }
    }

    @Override
    public void onProductSearchedError() {

    }
}
