package com.syscription.firstchoicemart.Presentation.ui.fragments;

import com.syscription.firstchoicemart.Models.CartModel;
import com.syscription.firstchoicemart.Network.response.CartQuantityUpdateResponse;
import com.syscription.firstchoicemart.Network.response.RemoveCartResponse;

import java.util.List;

public interface CartView {
    void setCartItems(List<CartModel> cartItems);
    void showRemoveCartMessage(RemoveCartResponse removeCartResponse);
    void showCartQuantityUpdateMessage(CartQuantityUpdateResponse cartQuantityUpdateResponse);
}
