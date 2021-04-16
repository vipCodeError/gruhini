package com.syscription.firstchoicemart.Presentation.presenters;

import com.syscription.firstchoicemart.Models.Product;
import com.syscription.firstchoicemart.Models.Shop;
import com.syscription.firstchoicemart.Network.response.ProductResponse;
import com.syscription.firstchoicemart.Presentation.ui.activities.SellerShopView;
import com.syscription.firstchoicemart.domain.executor.Executor;
import com.syscription.firstchoicemart.domain.executor.MainThread;
import com.syscription.firstchoicemart.domain.interactors.ProductInteractor;
import com.syscription.firstchoicemart.domain.interactors.ShopInteractor;
import com.syscription.firstchoicemart.domain.interactors.impl.ProductInteractorImpl;
import com.syscription.firstchoicemart.domain.interactors.impl.ShopInteractorImpl;

import java.util.List;

public class ShopPresenter extends AbstractPresenter implements ShopInteractor.CallBack, ProductInteractor.CallBack {
    private final SellerShopView sellerShopView;
    private int type = 0;

    public ShopPresenter(Executor executor, MainThread mainThread, SellerShopView sellerShopView) {
        super(executor, mainThread);
        this.sellerShopView = sellerShopView;
    }

    public void getShopDetails(String url){
        new ShopInteractorImpl(mExecutor, mMainThread, this, url).execute();
    }

    public void getFeaturedProducts(String url){
        type = 0;
        new ProductInteractorImpl(mExecutor, mMainThread, this, url).execute();
    }

    public void getTopSellingProducts(String url){
        type = 1;
        new ProductInteractorImpl(mExecutor, mMainThread, this, url).execute();
    }

    public void getNewProducts(String url){
        type = 2;
        new ProductInteractorImpl(mExecutor, mMainThread, this, url).execute();
    }

    @Override
    public void onShopLoaded(Shop shop) {
        if (sellerShopView != null){
            sellerShopView.onShopDetailsLoaded(shop);
        }
    }

    @Override
    public void onShopLoadError() {

    }

    @Override
    public void onProductDownloaded(ProductResponse productListingResponse) {

    }

    @Override
    public void onProductZeroDownload(ProductResponse productListingResponse) {

    }

    @Override
    public void onProductFirstDownloaded(ProductResponse productListingResponse) {

    }

    @Override
    public void onProductSecondDownloaded(ProductResponse productListingResponse) {

    }

    @Override
    public void onProductThirdDownloaded(ProductResponse productListingResponse) {

    }

    @Override
    public void onProductFourthDownloaded(ProductResponse productListingResponse) {

    }

    @Override
    public void onProductFifthDownloaded(ProductResponse productListingResponse) {

    }

    @Override
    public void onProductSixthDownloaded(ProductResponse productListingResponse) {

    }

    @Override
    public void onProductSeventhDownloaded(ProductResponse productListingResponse) {

    }

    @Override
    public void onProductEigthDownloaded(ProductResponse productListingResponse) {

    }

    @Override
    public void onProductNinthDownloaded(ProductResponse productListingResponse) {

    }

    @Override
    public void onProductTenthDownloaded(ProductResponse productListingResponse) {

    }

    @Override
    public void onProductEleventhDownloaded(ProductResponse productListingResponse) {

    }

    @Override
    public void onProductTwelthDownloaded(ProductResponse productListingResponse) {

    }

    @Override
    public void onProductThirteenDownloaded(ProductResponse productListingResponse) {

    }

    @Override
    public void onProductFourteenDownloaded(ProductResponse productListingResponse) {

    }

    @Override
    public void onProductFifteenDownloaded(ProductResponse productListingResponse) {

    }

    @Override
    public void onProductDownloaded(List<Product> products) {
        if (sellerShopView != null){
            switch (type){
                case 0 :
                    sellerShopView.setFeaturedProducts(products);
                    break;
                case 1:
                    sellerShopView.setTopSellingProducts(products);
                    break;
                case 2:
                    sellerShopView.setNewProducts(products);
                    break;
            }
        }
    }

    @Override
    public void onProductDownloadError() {

    }
}
