package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Network.response.OrderResponse;

public interface PaypalInteractor {
    interface CallBack {

        void onPayaplOrderSubmitted(OrderResponse orderResponse);

        void onPayaplOrderSubmitError();
    }
}
