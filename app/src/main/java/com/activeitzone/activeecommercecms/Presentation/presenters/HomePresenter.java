package com.activeitzone.activeecommercecms.Presentation.presenters;

import com.activeitzone.activeecommercecms.Models.AuctionProduct;
import com.activeitzone.activeecommercecms.Models.Banner;
import com.activeitzone.activeecommercecms.Models.Brand;
import com.activeitzone.activeecommercecms.Models.Category;
import com.activeitzone.activeecommercecms.Models.FlashDeal;
import com.activeitzone.activeecommercecms.Models.PreferCatModel;
import com.activeitzone.activeecommercecms.Models.Product;
import com.activeitzone.activeecommercecms.Models.SliderImage;
import com.activeitzone.activeecommercecms.Models.SubCategory;
import com.activeitzone.activeecommercecms.Network.response.AppSettingsResponse;
import com.activeitzone.activeecommercecms.Network.response.AuctionBidResponse;
import com.activeitzone.activeecommercecms.Network.response.PreferDataResponse;
import com.activeitzone.activeecommercecms.Network.response.ProductListingResponse;
import com.activeitzone.activeecommercecms.Network.response.ProductResponse;
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
import com.activeitzone.activeecommercecms.domain.interactors.PreferCatInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.PreferCatIsShownInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.ProductInteractor;
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
import com.activeitzone.activeecommercecms.domain.interactors.impl.PreferCategoriesAllDataInteractorImp;
import com.activeitzone.activeecommercecms.domain.interactors.impl.PreferCategoriesInteractorImpl;
import com.activeitzone.activeecommercecms.domain.interactors.impl.ProductListingInteractorImpl;
import com.activeitzone.activeecommercecms.domain.interactors.impl.SliderInteractorImpl;
import com.activeitzone.activeecommercecms.domain.interactors.impl.SubSubCategoryInteractorImpl;
import com.activeitzone.activeecommercecms.domain.interactors.impl.TodaysDealInteractorImpl;
import com.activeitzone.activeecommercecms.domain.interactors.impl.TopCategoriesInteractorImpl;
import com.google.gson.JsonObject;

import java.util.List;

public class HomePresenter extends AbstractPresenter implements PreferCatIsShownInteractor.CallBack, PreferCatInteractor.CallBack, ProductInteractor.CallBack, AppSettingsInteractor.CallBack, SliderInteractor.CallBack, HomeCategoriesInteractor.CallBack, TodaysDealInteractor.CallBack, FlashDealInteractor.CallBack, BestSellingInteractor.CallBack, BannerInteractor.CallBack, FeaturedProductInteractor.CallBack, BrandInteractor.CallBack, TopCategoryInteractor.CallBack, AuctionProductInteractor.CallBack, AuctionBidInteractor.CallBack {
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

    public void getPreferCategories(){
        new PreferCategoriesAllDataInteractorImp(mExecutor, mMainThread, this).execute();
    }

//    public void getPreferCategoreisByIsShown(PreferCatModel preferCatModel){
//        new PreferCategoriesInteractorImpl(mExecutor, mMainThread, this, preferCatModel).execute();
//    }

    public void getCategoriesZerothProduct(PreferCatModel preferCatModel){
        new PreferCategoriesInteractorImpl(mExecutor, mMainThread, this, preferCatModel, 0).execute();
        // new ProductListingHomeInteractorImpl(mExecutor, mMainThread, this, url, 0).execute();
    }

    public void getCategoriesFirstProduct(PreferCatModel preferCatModel){
        new PreferCategoriesInteractorImpl(mExecutor, mMainThread, this, preferCatModel, 1).execute();
        // new ProductListingHomeInteractorImpl(mExecutor, mMainThread, this, url, 0).execute();
    }


    public void getCategoriesSecondProduct(PreferCatModel preferCatModel){
        new PreferCategoriesInteractorImpl(mExecutor, mMainThread, this, preferCatModel, 2).execute();
        // new ProductListingHomeInteractorImpl(mExecutor, mMainThread, this, url, 0).execute();
    }


    public void getCategoriesThirdProduct(PreferCatModel preferCatModel){
        new PreferCategoriesInteractorImpl(mExecutor, mMainThread, this, preferCatModel, 3).execute();
        // new ProductListingHomeInteractorImpl(mExecutor, mMainThread, this, url, 0).execute();
    }


    public void getCategoriesbFourthProduct(PreferCatModel preferCatModel){
        new PreferCategoriesInteractorImpl(mExecutor, mMainThread, this, preferCatModel, 4).execute();
        // new ProductListingHomeInteractorImpl(mExecutor, mMainThread, this, url, 0).execute();
    }


    public void getCategoriesFiveProduct(PreferCatModel preferCatModel){
        new PreferCategoriesInteractorImpl(mExecutor, mMainThread, this, preferCatModel, 5).execute();
        // new ProductListingHomeInteractorImpl(mExecutor, mMainThread, this, url, 0).execute();
    }


    public void getCategoriesSixHomesProduct(PreferCatModel preferCatModel){
        new PreferCategoriesInteractorImpl(mExecutor, mMainThread, this, preferCatModel, 6).execute();
        // new ProductListingHomeInteractorImpl(mExecutor, mMainThread, this, url, 0).execute();
    }


    public void getCategoriesSevenProduct(PreferCatModel preferCatModel){
        new PreferCategoriesInteractorImpl(mExecutor, mMainThread, this, preferCatModel, 7).execute();
        // new ProductListingHomeInteractorImpl(mExecutor, mMainThread, this, url, 0).execute();
    }


    public void getCategoriesEightFitnessProduct(PreferCatModel preferCatModel){
        new PreferCategoriesInteractorImpl(mExecutor, mMainThread, this, preferCatModel, 8).execute();
        // new ProductListingHomeInteractorImpl(mExecutor, mMainThread, this, url, 0).execute();
    }


    public void getCategoriesNighthProduct(PreferCatModel preferCatModel){
        new PreferCategoriesInteractorImpl(mExecutor, mMainThread, this, preferCatModel, 9).execute();
        // new ProductListingHomeInteractorImpl(mExecutor, mMainThread, this, url, 0).execute();
    }


    public void getCategoriesTenthProduct(PreferCatModel preferCatModel){
        new PreferCategoriesInteractorImpl(mExecutor, mMainThread, this, preferCatModel, 10).execute();
        // new ProductListingHomeInteractorImpl(mExecutor, mMainThread, this, url, 0).execute();
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
    public void onProductDownloaded(ProductResponse productListingResponse) {
    }

    @Override
    public void onProductZeroDownload(ProductResponse productListingResponse) {
        homeView.setZerothProduct(productListingResponse);
    }

    @Override
    public void onProductFirstDownloaded(ProductResponse productListingResponse) {
        homeView.setFirstProduct(productListingResponse);
    }

    @Override
    public void onProductSecondDownloaded(ProductResponse productListingResponse) {
        homeView.setSecondProduct(productListingResponse);
    }

    @Override
    public void onProductThirdDownloaded(ProductResponse productListingResponse) {
        homeView.setThirdProduct(productListingResponse);
    }

    @Override
    public void onProductFourthDownloaded(ProductResponse productListingResponse) {
        homeView.setFourthProduct(productListingResponse);
    }

    @Override
    public void onProductFifthDownloaded(ProductResponse productListingResponse) {
        homeView.setFifthProduct(productListingResponse);
    }

    @Override
    public void onProductSixthDownloaded(ProductResponse productListingResponse) {
        homeView.setSixthProduct(productListingResponse);
    }

    @Override
    public void onProductSeventhDownloaded(ProductResponse productListingResponse) {
        homeView.setSeventhProduct(productListingResponse);
    }

    @Override
    public void onProductEigthDownloaded(ProductResponse productListingResponse) {
        homeView.setEighthProduct(productListingResponse);
    }

    @Override
    public void onProductNinthDownloaded(ProductResponse productListingResponse) {
        homeView.setNinthFitness(productListingResponse);
    }

    @Override
    public void onProductTenthDownloaded(ProductResponse productListingResponse) {
        homeView.setNinthFitness(productListingResponse);
    }

    @Override
    public void onProductDownloaded(List<Product> products) {

    }

    @Override
    public void onProductDownloadError() {

    }

    @Override
    public void onPreferCaTDataArrived(PreferDataResponse preferDataResponse) {
        homeView.getAllPreferData(preferDataResponse);
    }

    @Override
    public void onPreferItemAddedError() {

    }

    @Override
    public void onPreferCatIsShownArrived(ProductResponse preferDataResponse) {

    }

    @Override
    public void onPreferCatItemAddedError() {

    }
}