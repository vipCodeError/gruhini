package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Network.response.OrderResponse;

public interface OrderInteractor {
    interface CallBack {

        void onOrderSubmitted(OrderResponse orderResponse);

        void onOrderSubmitError();
    }
}
