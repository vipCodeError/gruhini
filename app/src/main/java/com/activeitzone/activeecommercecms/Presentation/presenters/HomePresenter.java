package com.activeitzone.activeecommercecms.Presentation.presenters;

import com.activeitzone.activeecommercecms.Models.AuctionProduct;
import com.activeitzone.activeecommercecms.Models.Banner;
import com.activeitzone.activeecommercecms.Models.Brand;
import com.activeitzone.activeecommercecms.Models.Category;
import com.activeitzone.activeecommercecms.Models.FlashDeal;
import com.activeitzone.activeecommercecms.Models.Product;
import com.activeitzone.activeecommercecms.Models.SliderImage;
import com.activeitzone.activeecommercecms.Models.SubCategory;
import com.activeitzone.activeecommercecms.Network.response.AppSettingsResponse;
import com.activeitzone.activeecommercecms.Network.response.AuctionBidResponse;
import com.activeitzone.activeecommercecms.Network.response.ProductListingResponse;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.ProductListingView;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.SubCategoryView;
import com.activeitzone.activeecommercecms.Presentation.ui.fragments.HomeView;
import com.activeitzone.activeecommercecms.domain.executor.Executor;
import com.activeitzone.activeecommercecms.domain.executor.MainThread;
import com.activeitzone.activeecommercecms.domain.interactors.AppSettingsInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.AuctionBidInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.AuctionProductInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.BannerInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.BestSellingInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.BrandInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.FeaturedProductInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.FlashDealInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.HomeCategoriesInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.ProductListingHomeInteractorImpl;
import com.activeitzone.activeecommercecms.domain.interactors.ProductListingInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.SliderInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.SubSubCategoryInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.TodaysDealInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.TopCategoryInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.impl.AppSettingsInteractorImpl;
import com.activeitzone.activeecommercecms.domain.interactors.impl.AuctionBidInteractorImpl;
import com.activeitzone.activeecommercecms.domain.interactors.impl.AuctionProductInteractorImpl;
import com.activeitzone.activeecommercecms.domain.interactors.impl.BannerInteractorImpl;
import com.activeitzone.activeecommercecms.domain.interactors.impl.BestSellingInteractorImpl;
import com.activeitzone.activeecommercecms.domain.interactors.impl.BrandInteractorImpl;
import com.activeitzone.activeecommercecms.domain.interactors.impl.FeaturedProductInteractorImpl;
import com.activeitzone.activeecommercecms.domain.interactors.impl.FlashDealInteractorImpl;
import com.activeitzone.activeecommercecms.domain.interactors.impl.HomeCategoriesInteractorImpl;
import com.activeitzone.activeecommercecms.domain.interactors.impl.ProductListingInteractorImpl;
import com.activeitzone.activeecommercecms.domain.interactors.impl.SliderInteractorImpl;
import com.activeitzone.activeecommercecms.domain.interactors.impl.SubSubCategoryInteractorImpl;
import com.activeitzone.activeecommercecms.domain.interactors.impl.TodaysDealInteractorImpl;
import com.activeitzone.activeecommercecms.domain.interactors.impl.TopCategoriesInteractorImpl;
import com.google.gson.JsonObject;

import java.util.List;

public class HomePresenter extends AbstractPresenter implements ProductListingInteractor.CallBack, AppSettingsInteractor.CallBack, SliderInteractor.CallBack, HomeCategoriesInteractor.CallBack, TodaysDealInteractor.CallBack, FlashDealInteractor.CallBack, BestSellingInteractor.CallBack, BannerInteractor.CallBack, FeaturedProductInteractor.CallBack, BrandInteractor.CallBack, TopCategoryInteractor.CallBack, AuctionProductInteractor.CallBack, AuctionBidInteractor.CallBack {
    HomeView homeView;


    public HomePresenter(Executor executor, MainThread mainThread, HomeView homeView) {
        super(executor, mainThread);
        this.homeView = homeView;
    }

    public void getAppSettings(){
        new AppSettingsInteractorImpl(mExecutor, mMainThread, this).execute();
    }

    public void getSliderImages() {
        new SliderInteractorImpl(mExecutor, mMainThread, this).execute();
    }

    public void getHomeCategories() {
        new HomeCategoriesInteractorImpl(mExecutor, mMainThread, this).execute();
    }

    public void getTodaysDeal() {
        new TodaysDealInteractorImpl(mExecutor, mMainThread, this).execute();
    }

    public void getFlashDeal() {
        new FlashDealInteractorImpl(mExecutor, mMainThread, this).execute();
    }

    public void getBestSelling() {
        new BestSellingInteractorImpl(mExecutor, mMainThread, this).execute();
    }

    public void getBanners() {
        new BannerInteractorImpl(mExecutor, mMainThread, this).execute();
    }

    public void getFeaturedProducts() {
        new FeaturedProductInteractorImpl(mExecutor, mMainThread, this).execute();
    }

    public void getTopCategories() {
        new TopCategoriesInteractorImpl(mExecutor, mMainThread, this).execute();
    }

    public void getPopularbrands() {
        new BrandInteractorImpl(mExecutor, mMainThread, this).execute();
    }

    public void getAuctionProducts() {
        new AuctionProductInteractorImpl(mExecutor, mMainThread, this).execute();
    }

    public void getCategoriesWiseManProduct(String url){
        new ProductListingHomeInteractorImpl(mExecutor, mMainThread, this, url, 5).execute();
    }

    public void getCategoriesWiseWomanProduct(String url){
        new ProductListingHomeInteractorImpl(mExecutor, mMainThread, this, url, 10).execute();
    }

    public void getCategoriesBeautyProduct(String url){
        new ProductListingHomeInteractorImpl(mExecutor, mMainThread, this, url, 1).execute();
    }

    public void getCategoriesbBagLuggageProduct(String url){
        new ProductListingHomeInteractorImpl(mExecutor, mMainThread, this, url, 0).execute();
    }

    public void getCategoriesBooksProduct(String url){
        new ProductListingHomeInteractorImpl(mExecutor, mMainThread, this, url, 2).execute();
    }

    public void getCategoriesKitchenHomesProduct(String url){
        new ProductListingHomeInteractorImpl(mExecutor, mMainThread, this, url, 4).execute();
    }

    public void getCategoriesMobilePcProduct(String url){
        new ProductListingHomeInteractorImpl(mExecutor, mMainThread, this, url, 6).execute();
    }

    public void getCategoriesSportsFitnessProduct(String url){
        new ProductListingHomeInteractorImpl(mExecutor, mMainThread, this, url, 7).execute();
    }

    public void getCategoriesBabyProduct(String url){
        new ProductListingHomeInteractorImpl(mExecutor, mMainThread, this, url, 8).execute();
    }

    public void getCategoriesElectronicsProduct(String url){
        new ProductListingHomeInteractorImpl(mExecutor, mMainThread, this, url, 9).execute();
    }


    public void submitBid(JsonObject jsonObject, String token){
        new AuctionBidInteractorImpl(mExecutor, mMainThread, this, jsonObject, token).execute();
    }

    @Override
    public void onAppSettingsLoaded(AppSettingsResponse appSettingsResponse) {
        if (homeView != null){
            homeView.onAppSettingsLoaded(appSettingsResponse);
        }
    }

    @Override
    public void onAppSettingsLoadedError() {

    }

    @Override
    public void onSliderDownloaded(List<SliderImage> sliderImages) {
        if (homeView != null) {
            homeView.setSliderImages(sliderImages);
        }
    }

    @Override
    public void onSliderDownloadError() {

    }

    @Override
    public void onHomeCategoriesDownloaded(List<Category> categories) {
        if (homeView != null) {
            homeView.setHomeCategories(categories);
        }
    }

    @Override
    public void onHomeCategoriesDownloadError() {

    }

    @Override
    public void onTodaysDealProductDownloaded(List<Product> products) {
        if (homeView != null) {
            homeView.setTodaysDeal(products);
        }
    }

    @Override
    public void onTodaysDealProductDownloadError() {

    }

    @Override
    public void onBestSellingProductDownloaded(List<Product> products) {
        if (homeView != null) {
            homeView.setBestSelling(products);
        }
    }

    @Override
    public void onBestSellingProductDownloadError() {

    }

    @Override
    public void onFeaturedProductDownloaded(List<Product> products) {
        if (homeView != null) {
            homeView.setFeaturedProducts(products);
        }
    }

    @Override
    public void onFeaturedProductDownloadError() {

    }

    @Override
    public void onTopCategoriesDownloaded(List<Category> categories) {
        if (homeView != null) {
            homeView.setTopCategories(categories);
        }
    }

    @Override
    public void onTopCategoriesDownloadError() {

    }

    @Override
    public void onAuctionProductDownloaded(List<AuctionProduct> auctionProducts) {
        if (homeView != null) {
            homeView.setAuctionProducts(auctionProducts);
        }
    }

    @Override
    public void onAuctionProductDownloadError() {

    }

    @Override
    public void onBannersDownloaded(List<Banner> banners) {
        if (homeView != null){
            homeView.setBanners(banners);
        }
    }

    @Override
    public void onBannersDownloadError() {

    }

    @Override
    public void onBrandsDownloaded(List<Brand> brands) {
        if (homeView != null){
            homeView.setPopularBrands(brands);
        }
    }

    @Override
    public void onBrandsDownloadError() {

    }

    @Override
    public void onBidSubmitted(AuctionBidResponse auctionBidResponse) {
        if (homeView != null){
            homeView.onAuctionBidSubmitted(auctionBidResponse);
        }
    }

    @Override
    public void onBidSubmittedError() {

    }

    @Override
    public void onFlashDealProductDownloaded(FlashDeal flashDeal) {
        if (homeView != null) {
            homeView.setFlashDeal(flashDeal);
        }
    }

    @Override
    public void onFlashDealProductDownloadError() {

    }


    @Override
    public void onProductDownloaded(ProductListingResponse productListingResponse) {

    }

    @Override
    public void onProductMensDownloaded(ProductListingResponse productListingResponse) {
        homeView.setMensWearProduct(productListingResponse);
    }

    @Override
    public void onProductWomanDownloaded(ProductListingResponse productListingResponse) {
        homeView.setWomansWearProduct(productListingResponse);
    }

    @Override
    public void onProductBeautyDownloaded(ProductListingResponse productListingResponse) {
        homeView.setBeautyProduct(productListingResponse);
    }

    @Override
    public void onProductSportsAndFitnessDownloaded(ProductListingResponse productListingResponse) {
        homeView.setSportsFitness(productListingResponse);
    }

    @Override
    public void onProductToyBabyDownloaded(ProductListingResponse productListingResponse) {
        homeView.setBabyProduct(productListingResponse);
    }

    @Override
    public void onProductElectonicsDownloaded(ProductListingResponse productListingResponse) {
        homeView.setElectronicsProduct(productListingResponse);
    }

    @Override
    public void onProductMobilePcDownloaded(ProductListingResponse productListingResponse) {
        homeView.setMobilePcProduct(productListingResponse);
    }

    @Override
    public void onProductKitchenAndHomeDownloaded(ProductListingResponse productListingResponse) {
        homeView.setKitchenHomeAppliance(productListingResponse);
    }

    @Override
    public void onProductBagAndLuggageDownloaded(ProductListingResponse productListingResponse) {
        homeView.setBagLuggageProduct(productListingResponse);
    }

    @Override
    public void onProductBooksDownloaded(ProductListingResponse productListingResponse) {
        homeView.setBooksProduct(productListingResponse);
    }

    @Override
    public void onProductDownloadError() {

    }
}