package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Models.CartModel;

import java.util.List;

public interface CartInteractor {
    interface CallBack {

        void onCartLodaded(List<CartModel> cartModel);

        void onCartError();
    }
}
