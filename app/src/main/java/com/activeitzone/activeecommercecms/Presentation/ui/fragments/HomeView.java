package com.activeitzone.activeecommercecms.Presentation.ui.fragments;

import com.activeitzone.activeecommercecms.Models.AuctionProduct;
import com.activeitzone.activeecommercecms.Models.Banner;
import com.activeitzone.activeecommercecms.Models.Brand;
import com.activeitzone.activeecommercecms.Models.Category;
import com.activeitzone.activeecommercecms.Models.FlashDeal;
import com.activeitzone.activeecommercecms.Models.Product;
import com.activeitzone.activeecommercecms.Models.SliderImage;
import com.activeitzone.activeecommercecms.Network.response.AppSettingsResponse;
import com.activeitzone.activeecommercecms.Network.response.AuctionBidResponse;
import com.activeitzone.activeecommercecms.Network.response.PreferDataResponse;
import com.activeitzone.activeecommercecms.Network.response.ProductListingResponse;
import com.activeitzone.activeecommercecms.Network.response.ProductResponse;

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

    void getAllPreferData(PreferDataResponse preferDataResponse);

    void getPreferDataBySubShown(ProductResponse productListingResponse);
}