package com.syscription.firstchoicemart.Presentation.ui.fragments;

import com.syscription.firstchoicemart.Models.AuctionProduct;
import com.syscription.firstchoicemart.Models.Banner;
import com.syscription.firstchoicemart.Models.Brand;
import com.syscription.firstchoicemart.Models.Category;
import com.syscription.firstchoicemart.Models.FlashDeal;
import com.syscription.firstchoicemart.Models.Product;
import com.syscription.firstchoicemart.Models.SliderImage;
import com.syscription.firstchoicemart.Network.response.AppSettingsResponse;
import com.syscription.firstchoicemart.Network.response.AuctionBidResponse;
import com.syscription.firstchoicemart.Network.response.PreferDataResponse;
import com.syscription.firstchoicemart.Network.response.ProductListingResponse;
import com.syscription.firstchoicemart.Network.response.ProductResponse;

import java.util.List;

public interface HomeView {
    void onAppSettingsLoaded(AppSettingsResponse appSettingsResponse);

    void setSliderImages(List<SliderImage> sliderImages);

    void setHomeCategories(List<Category> categories);

    void setTodaysDeal(List<Product> products);

    void setFlashDeal(FlashDeal flashDeal);

    void setBanners(List<Banner> banners);

    void setBestSelling(List<Product> products);

    void setFeaturedProducts(List<Product> products);

    void setTopCategories(List<Category> categories);

    void setPopularBrands(List<Brand> brands);

    void setAuctionProducts(List<AuctionProduct> auctionProducts);

    void onAuctionBidSubmitted(AuctionBidResponse auctionBidResponse);

    void setZerothProduct(ProductListingResponse productListingResponse);

    void setFirstProduct(ProductListingResponse productListingResponse);

    void setSecondProduct(ProductListingResponse productListingResponse);

    void setThirdProduct(ProductListingResponse productListingResponse);

    void setFourthProduct(ProductListingResponse productListingResponse);

    void setFifthProduct(ProductListingResponse productListingResponse);

    void setSixthProduct(ProductListingResponse productListingResponse);

    void setSeventhProduct(ProductListingResponse productListingResponse);

    void setEighthProduct(ProductListingResponse productListingResponse);

    void setNinthFitness(ProductListingResponse productListingResponse);

    void setTenthProduct(ProductListingResponse productListingResponse);

    void setEleventhProduct(ProductListingResponse productListingResponse);

    void setTwelthProduct(ProductListingResponse productListingResponse);

    void setThirteenProduct(ProductListingResponse productListingResponse);

    void setFourteenProduct(ProductListingResponse productListingResponse);

    void getAllPreferData(PreferDataResponse preferDataResponse);

    void getPreferDataBySubShown(ProductResponse productListingResponse);

    void setTopSecondCategories(List<Category> categories);
}