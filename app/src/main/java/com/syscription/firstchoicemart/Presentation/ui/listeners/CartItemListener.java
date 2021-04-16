package com.syscription.firstchoicemart.Presentation.ui.listeners;

import com.syscription.firstchoicemart.Models.CartModel;

public interface CartItemListener {
    void onCartRemove(CartModel cartModel);
    void onQuantityUpdate(int quantity, CartModel cartModel);
}
