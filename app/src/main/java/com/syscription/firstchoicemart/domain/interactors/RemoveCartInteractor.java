package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Network.response.RemoveCartResponse;

public interface RemoveCartInteractor {
    interface CallBack {

        void onCartItemRemoved(RemoveCartResponse removeCartResponse);

        void onCartItemRemovedError();
    }
}
