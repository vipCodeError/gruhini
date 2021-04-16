package com.syscription.firstchoicemart.Presentation.ui.activities;

import com.syscription.firstchoicemart.Models.Product;
import com.syscription.firstchoicemart.Models.ProductDetails;
import com.syscription.firstchoicemart.Network.response.AddToCartResponse;
import com.syscription.firstchoicemart.Network.response.AddToWishlistResponse;
import com.syscription.firstchoicemart.Network.response.CheckWishlistResponse;
import com.syscription.firstchoicemart.Network.response.RemoveWishlistResponse;

import java.util.List;

public interface ProductDetailsView {
    void setProductDetails(ProductDetails productDetails);
    void setRelatedProducts(List<Product> relatedProducts);
    void setTopSellingProducts(List<Product> topSellingProducts);
    void setAddToCartMessage(AddToCartResponse addToCartResponse);
    void setAddToWishlistMessage(AddToWishlistResponse addToWishlistMessage);
    void onCheckWishlist(CheckWishlistResponse checkWishlistResponse);
    void onRemoveFromWishlist(RemoveWishlistResponse removeWishlistResponse);
}
