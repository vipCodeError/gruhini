package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Network.response.OrderResponse;

public interface WalletInteractor {
    interface CallBack {

        void onWalletOrderSubmitted(OrderResponse orderResponse);

        void onWalletOrderSubmitError();
    }
}
