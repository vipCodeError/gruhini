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

    void setZerothProduct(ProductResponse productListingResponse);

    void setFirstProduct(ProductResponse productListingResponse);

    void setSecondProduct(ProductResponse productListingResponse);

    void setThirdProduct(ProductResponse productListingResponse);

    void setFourthProduct(ProductResponse productListingResponse);

    void setFifthProduct(ProductResponse productListingResponse);

    void setSixthProduct(ProductResponse productListingResponse);

    void setSeventhProduct(ProductResponse productListingResponse);

    void setEighthProduct(ProductResponse productListingResponse);

    void setNinthFitness(ProductResponse productListingResponse);

    void setTenthProduct(ProductResponse productListingResponse);

    void setEleventhProduct(ProductResponse productListingResponse);

    void setTwelthProduct(ProductResponse productListingResponse);

    void setThirteenProduct(ProductResponse productListingResponse);

    void setFourteenProduct(ProductResponse productListingResponse);

    void getAllPreferData(PreferDataResponse preferDataResponse);

    void getPreferDataBySubShown(ProductResponse productListingResponse);

    void setTopSecondCategories(List<Category> categories);
}