package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Network.response.AddToWishlistResponse;

public interface AddToWishlistInteractor {
    interface CallBack {

        void onWishlistItemAdded(AddToWishlistResponse addToWishlistResponse);

        void onWishlistItemAddedError();
    }
}
