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
import com.activeitzone.activeecommercecms.Network.response.ProductListingResponse;

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

    void setMensWearProduct(ProductListingResponse productListingResponse);

    void setWomansWearProduct(ProductListingResponse productListingResponse);

    void setElectronicsProduct(ProductListingResponse productListingResponse);

    void setBeautyProduct(ProductListingResponse productListingResponse);

    void setBagLuggageProduct(ProductListingResponse productListingResponse);

    void setBooksProduct(ProductListingResponse productListingResponse);

    void setKitchenHomeAppliance(ProductListingResponse productListingResponse);

    void setMobilePcProduct(ProductListingResponse productListingResponse);

    void setSportsFitness(ProductListingResponse productListingResponse);

    void setBabyProduct(ProductListingResponse productListingResponse);


}