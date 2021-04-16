package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Network.response.RemoveWishlistResponse;

public interface RemoveWishlistInteractor {
    interface CallBack {

        void onWishlistItemRemoved(RemoveWishlistResponse removeWishlistResponse);

        void onWishlistItemRemovedError();
    }
}
