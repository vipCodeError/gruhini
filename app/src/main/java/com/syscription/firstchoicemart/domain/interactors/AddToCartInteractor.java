package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Network.response.AddToCartResponse;

public interface AddToCartInteractor {
    interface CallBack {

        void onCartItemAdded(AddToCartResponse addToCartResponse);

        void onCartItemAddedError();
    }
}
