package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Network.response.CartQuantityUpdateResponse;

public interface CartQuantityInteractor {
    interface CallBack {

        void onCartQuantityUpdated(CartQuantityUpdateResponse cartQuantityUpdateResponse);

        void onCartQuantityUpdatedError();
    }
}
