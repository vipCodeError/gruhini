package com.syscription.firstchoicemart.Presentation.ui.activities;

import com.syscription.firstchoicemart.Models.Product;
import com.syscription.firstchoicemart.Models.Shop;

import java.util.List;

public interface SellerShopView {
    void onShopDetailsLoaded(Shop shop);
    void setFeaturedProducts(List<Product> products);
    void setTopSellingProducts(List<Product> products);
    void setNewProducts(List<Product> products);
}
