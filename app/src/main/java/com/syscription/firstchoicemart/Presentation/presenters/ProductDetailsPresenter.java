package com.syscription.firstchoicemart.Presentation.presenters;

import com.syscription.firstchoicemart.Models.Product;
import com.syscription.firstchoicemart.Models.ProductDetails;
import com.syscription.firstchoicemart.Network.response.AddToCartResponse;
import com.syscription.firstchoicemart.Network.response.AddToWishlistResponse;
import com.syscription.firstchoicemart.Network.response.CheckWishlistResponse;
import com.syscription.firstchoicemart.Network.response.ProductResponse;
import com.syscription.firstchoicemart.Network.response.RemoveWishlistResponse;
import com.syscription.firstchoicemart.Presentation.ui.activities.ProductDetailsView;
import com.syscription.firstchoicemart.domain.executor.Executor;
import com.syscription.firstchoicemart.domain.executor.MainThread;
import com.syscription.firstchoicemart.domain.interactors.AddToCartInteractor;
import com.syscription.firstchoicemart.domain.interactors.AddToWishlistInteractor;
import com.syscription.firstchoicemart.domain.interactors.CheckWishlistInteractor;
import com.syscription.firstchoicemart.domain.interactors.ProductDetailsInteractor;
import com.syscription.firstchoicemart.domain.interactors.ProductInteractor;
import com.syscription.firstchoicemart.domain.interactors.RemoveWishlistInteractor;
import com.syscription.firstchoicemart.domain.interactors.impl.AddToCartInteractorImpl;
import com.syscription.firstchoicemart.domain.interactors.impl.AddToWishlistInteractorImpl;
import com.syscription.firstchoicemart.domain.interactors.impl.CheckWishlistInteractorImpl;
import com.syscription.firstchoicemart.domain.interactors.impl.ProductDetailsInteractorImpl;
import com.syscription.firstchoicemart.domain.interactors.impl.ProductInteractorImpl;
import com.syscription.firstchoicemart.domain.interactors.impl.RemoveWishlistInteractorImpl;

import java.util.List;

public class ProductDetailsPresenter extends AbstractPresenter implements ProductDetailsInteractor.CallBack, ProductInteractor.CallBack, AddToCartInteractor.CallBack, AddToWishlistInteractor.CallBack, CheckWishlistInteractor.CallBack, RemoveWishlistInteractor.CallBack {
    private final ProductDetailsView productDetailsView;
    private int type = 0;

    public ProductDetailsPresenter(Executor executor, MainThread mainThread, ProductDetailsView productDetailsView) {
        super(executor, mainThread);
        this.productDetailsView = productDetailsView;
    }

    public void getProductDetails(String url) {
        new ProductDetailsInteractorImpl(mExecutor, mMainThread, this, url).execute();
    }

    public void getRelatedProducts(String url){
        type = 0;
        new ProductInteractorImpl(mExecutor, mMainThread, this, url).execute();
    }

    public void getTopSellingProducts(String url){
        type = 1;
        new ProductInteractorImpl(mExecutor, mMainThread, this, url).execute();
    }

    public void addToCart(String token, int user_id, int product_id, String variant){
        new AddToCartInteractorImpl(mExecutor, mMainThread, this, token, user_id, product_id, variant).execute();
    }

    public void addToWishlist(String token, int user_id, int product_id){
        new AddToWishlistInteractorImpl(mExecutor, mMainThread, this, token, user_id, product_id).execute();
    }

    public void checkOnWishlist(String token, int user_id, int product_id){
        new CheckWishlistInteractorImpl(mExecutor, mMainThread, this, token, user_id, product_id).execute();
    }

    public void removeFromWishlist(String token, int wishlist_id){
        new RemoveWishlistInteractorImpl(mExecutor, mMainThread, this, wishlist_id, token).execute();
    }

    @Override
    public void onProductDetailsDownloaded(ProductDetails productDetails) {
        if (productDetailsView != null){
            productDetailsView.setProductDetails(productDetails);
        }
    }

    @Override
    public void onProductDetailsDownloadError() {

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
        if (productDetailsView != null && type == 0){
            productDetailsView.setRelatedProducts(products);
        }
        else if(productDetailsView != null && type == 1){
            productDetailsView.setTopSellingProducts(products);
        }
    }

    @Override
    public void onProductDownloadError() {

    }

    @Override
    public void onCartItemAdded(AddToCartResponse addToCartResponse) {
        if (productDetailsView != null){
            productDetailsView.setAddToCartMessage(addToCartResponse);
        }
    }

    @Override
    public void onCartItemAddedError() {

    }

    @Override
    public void onWishlistItemAdded(AddToWishlistResponse addToWishlistResponse) {
        if (productDetailsView != null){
            productDetailsView.setAddToWishlistMessage(addToWishlistResponse);
        }
    }

    @Override
    public void onWishlistItemAddedError() {

    }

    @Override
    public void onWishlistChecked(CheckWishlistResponse checkWishlistResponse) {
        if (productDetailsView != null){
            productDetailsView.onCheckWishlist(checkWishlistResponse);
        }
    }

    @Override
    public void onWishlistCheckedError() {

    }

    @Override
    public void onWishlistItemRemoved(RemoveWishlistResponse removeWishlistResponse) {
        if(productDetailsView != null){
            productDetailsView.onRemoveFromWishlist(removeWishlistResponse);
        }
    }

    @Override
    public void onWishlistItemRemovedError() {

    }
}
