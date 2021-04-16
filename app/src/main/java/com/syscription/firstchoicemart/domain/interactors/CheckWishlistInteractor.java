package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Network.response.CheckWishlistResponse;

public interface CheckWishlistInteractor {
    interface CallBack {

        void onWishlistChecked(CheckWishlistResponse checkWishlistResponse);

        void onWishlistCheckedError();
    }
}
