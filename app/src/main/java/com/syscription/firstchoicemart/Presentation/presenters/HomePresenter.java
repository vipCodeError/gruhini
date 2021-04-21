package com.syscription.firstchoicemart.Presentation.presenters;

import com.syscription.firstchoicemart.Models.AuctionProduct;
import com.syscription.firstchoicemart.Models.Banner;
import com.syscription.firstchoicemart.Models.Brand;
import com.syscription.firstchoicemart.Models.Category;
import com.syscription.firstchoicemart.Models.FlashDeal;
import com.syscription.firstchoicemart.Models.PreferCatModel;
import com.syscription.firstchoicemart.Models.Product;
import com.syscription.firstchoicemart.Models.SliderImage;
import com.syscription.firstchoicemart.Network.response.AppSettingsResponse;
import com.syscription.firstchoicemart.Network.response.AuctionBidResponse;
import com.syscription.firstchoicemart.Network.response.PreferDataResponse;
import com.syscription.firstchoicemart.Network.response.ProductListingResponse;
import com.syscription.firstchoicemart.Network.response.ProductResponse;
import com.syscription.firstchoicemart.Presentation.ui.fragments.HomeView;
import com.syscription.firstchoicemart.domain.executor.Executor;
import com.syscription.firstchoicemart.domain.executor.MainThread;
import com.syscription.firstchoicemart.domain.interactors.AppSettingsInteractor;
import com.syscription.firstchoicemart.domain.interactors.AuctionBidInteractor;
import com.syscription.firstchoicemart.domain.interactors.AuctionProductInteractor;
import com.syscription.firstchoicemart.domain.interactors.BannerInteractor;
import com.syscription.firstchoicemart.domain.interactors.BestSellingInteractor;
import com.syscription.firstchoicemart.domain.interactors.BrandInteractor;
import com.syscription.firstchoicemart.domain.interactors.FeaturedProductInteractor;
import com.syscription.firstchoicemart.domain.interactors.FlashDealInteractor;
import com.syscription.firstchoicemart.domain.interactors.HomeCategoriesInteractor;
import com.syscription.firstchoicemart.domain.interactors.PreferCatInteractor;
import com.syscription.firstchoicemart.domain.interactors.PreferCatIsShownInteractor;
import com.syscription.firstchoicemart.domain.interactors.ProductInteractor;
import com.syscription.firstchoicemart.domain.interactors.ProductListingHomeInteractorImpl;
import com.syscription.firstchoicemart.domain.interactors.ProductListingInteractor;
import com.syscription.firstchoicemart.domain.interactors.SliderInteractor;
import com.syscription.firstchoicemart.domain.interactors.TodaysDealInteractor;
import com.syscription.firstchoicemart.domain.interactors.TopCategoryInteractor;
import com.syscription.firstchoicemart.domain.interactors.TopSecondCategoryInteractor;
import com.syscription.firstchoicemart.domain.interactors.impl.AppSettingsInteractorImpl;
import com.syscription.firstchoicemart.domain.interactors.impl.AuctionBidInteractorImpl;
import com.syscription.firstchoicemart.domain.interactors.impl.AuctionProductInteractorImpl;
import com.syscription.firstchoicemart.domain.interactors.impl.BannerInteractorImpl;
import com.syscription.firstchoicemart.domain.interactors.impl.BestSellingInteractorImpl;
import com.syscription.firstchoicemart.domain.interactors.impl.BrandInteractorImpl;
import com.syscription.firstchoicemart.domain.interactors.impl.FeaturedProductInteractorImpl;
import com.syscription.firstchoicemart.domain.interactors.impl.FlashDealInteractorImpl;
import com.syscription.firstchoicemart.domain.interactors.impl.HomeCategoriesInteractorImpl;
import com.syscription.firstchoicemart.domain.interactors.impl.PreferCategoriesAllDataInteractorImp;
import com.syscription.firstchoicemart.domain.interactors.impl.PreferCategoriesInteractorImpl;
import com.syscription.firstchoicemart.domain.interactors.impl.SliderInteractorImpl;
import com.syscription.firstchoicemart.domain.interactors.impl.TodaysDealInteractorImpl;
import com.syscription.firstchoicemart.domain.interactors.impl.TopCategoriesInteractorImpl;
import com.google.gson.JsonObject;
import com.syscription.firstchoicemart.domain.interactors.impl.TopCategoriesSecondInteractorImpl;

import java.util.List;

public class HomePresenter extends AbstractPresenter implements ProductListingInteractor.CallBack, TopSecondCategoryInteractor.CallBack,  AppSettingsInteractor.CallBack, SliderInteractor.CallBack, HomeCategoriesInteractor.CallBack, TodaysDealInteractor.CallBack, FlashDealInteractor.CallBack, BestSellingInteractor.CallBack, BannerInteractor.CallBack, FeaturedProductInteractor.CallBack, BrandInteractor.CallBack, TopCategoryInteractor.CallBack, AuctionProductInteractor.CallBack, AuctionBidInteractor.CallBack {
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

    public void getTopSecondCategories() {
        new TopCategoriesSecondInteractorImpl(mExecutor, mMainThread, this).execute();
    }

    public void getPopularbrands() {
        new BrandInteractorImpl(mExecutor, mMainThread, this).execute();
    }

    public void getAuctionProducts() {
        new AuctionProductInteractorImpl(mExecutor, mMainThread, this).execute();
    }

//    public void getPreferCategories(){
//        new PreferCategoriesAllDataInteractorImp(mExecutor, mMainThread, this).execute();
//    }

//    public void getPreferCategoreisByIsShown(PreferCatModel preferCatModel){
//        new PreferCategoriesInteractorImpl(mExecutor, mMainThread, this, preferCatModel).execute();
//    }

    public void getCategoriesZerothProduct(String url){
        new ProductListingHomeInteractorImpl(mExecutor, mMainThread, this, url, 0).execute();
    }


    public void getCategoriesFirstProduct(String url){
        new ProductListingHomeInteractorImpl(mExecutor, mMainThread, this, url, 1).execute();
    }

    public void getCategoriesSecondProduct(String url){
        new ProductListingHomeInteractorImpl(mExecutor, mMainThread, this, url, 2).execute();
    }

    public void getCategoriesThirdProduct(String url){
        new ProductListingHomeInteractorImpl(mExecutor, mMainThread, this, url, 3).execute();
    }

    public void getCategoriesbFourthProduct(String url){
        new ProductListingHomeInteractorImpl(mExecutor, mMainThread, this, url, 4).execute();
    }

    public void getCategoriesFiveProduct(String url){
        new ProductListingHomeInteractorImpl(mExecutor, mMainThread, this, url, 5).execute();
    }

    public void getCategoriesSixHomesProduct(String url){
        new ProductListingHomeInteractorImpl(mExecutor, mMainThread, this, url, 6).execute();
    }

    public void getCategoriesSevenProduct(String url){
        new ProductListingHomeInteractorImpl(mExecutor, mMainThread, this, url, 7).execute();
    }

    public void getCategoriesEightFitnessProduct(String url){
        new ProductListingHomeInteractorImpl(mExecutor, mMainThread, this, url, 8).execute();
    }

    public void getCategoriesNighthProduct(String url){
        new ProductListingHomeInteractorImpl(mExecutor, mMainThread, this, url, 9).execute();
    }

    public void getCategoriesTenthProduct(String url){
        new ProductListingHomeInteractorImpl(mExecutor, mMainThread, this, url, 10).execute();
    }


    public void getCategoriesEleventhProduct(String url){
        //new PreferCategoriesInteractorImpl(mExecutor, mMainThread, this, preferCatModel, 11).execute();
        new ProductListingHomeInteractorImpl(mExecutor, mMainThread, this, url, 11).execute();
    }

    public void getCategoriesTwelthProduct(String url){
        //new PreferCategoriesInteractorImpl(mExecutor, mMainThread, this, preferCatModel, 12).execute();
         new ProductListingHomeInteractorImpl(mExecutor, mMainThread, this, url, 12).execute();
    }

    public void getCategoriesThirteenProduct(String url){
        //new PreferCategoriesInteractorImpl(mExecutor, mMainThread, this, preferCatModel, 13).execute();
         new ProductListingHomeInteractorImpl(mExecutor, mMainThread, this, url, 13).execute();
    }

    public void getCategoriesFourteenProduct(String url){
        //new PreferCategoriesInteractorImpl(mExecutor, mMainThread, this, preferCatModel, 14).execute();
         new ProductListingHomeInteractorImpl(mExecutor, mMainThread, this, url, 14).execute();
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
    public void onProductZeroDownload(ProductListingResponse productListingResponse) {
        homeView.setZerothProduct(productListingResponse);
    }

    @Override
    public void onProductFirstDownloaded(ProductListingResponse productListingResponse) {
        homeView.setFirstProduct(productListingResponse);
    }

    @Override
    public void onProductSecondDownloaded(ProductListingResponse productListingResponse) {
        homeView.setSecondProduct(productListingResponse);
    }

    @Override
    public void onProductThirdDownloaded(ProductListingResponse productListingResponse) {
        homeView.setThirdProduct(productListingResponse);
    }

    @Override
    public void onProductFourthDownloaded(ProductListingResponse productListingResponse) {
        homeView.setFourthProduct(productListingResponse);
    }

    @Override
    public void onProductFifthDownloaded(ProductListingResponse productListingResponse) {
        homeView.setFifthProduct(productListingResponse);
    }

    @Override
    public void onProductSixthDownloaded(ProductListingResponse productListingResponse) {
        homeView.setSixthProduct(productListingResponse);
    }

    @Override
    public void onProductSeventhDownloaded(ProductListingResponse productListingResponse) {
        homeView.setSeventhProduct(productListingResponse);
    }

    @Override
    public void onProductEigthDownloaded(ProductListingResponse productListingResponse) {
        homeView.setEighthProduct(productListingResponse);
    }

    @Override
    public void onProductNinthDownloaded(ProductListingResponse productListingResponse) {
        homeView.setNinthFitness(productListingResponse);
    }

    @Override
    public void onProductTenthDownloaded(ProductListingResponse productListingResponse) {
        homeView.setTenthProduct(productListingResponse);
    }

    @Override
    public void onProductDownloadError() {

    }


    @Override
    public void onTopSecondCategoriesDownloaded(List<Category> categories) {
        homeView.setTopSecondCategories(categories);
    }

    @Override
    public void onTopSecondCategoriesDownloadError() {

    }
}