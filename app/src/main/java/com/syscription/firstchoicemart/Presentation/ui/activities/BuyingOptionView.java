package com.syscription.firstchoicemart.Presentation.ui.activities;

import com.syscription.firstchoicemart.Network.response.AddToCartResponse;
import com.syscription.firstchoicemart.Network.response.VariantResponse;

public interface BuyingOptionView {
    void setVariantprice(VariantResponse variantResponse);
    void setAddToCartMessage(AddToCartResponse addToCartResponse);
}
