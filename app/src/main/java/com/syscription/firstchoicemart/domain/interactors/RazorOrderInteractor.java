package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Network.response.OrderResponse;

public interface RazorOrderInteractor {
    interface CallBack {
        void incomingOrderId(OrderResponse orderResponse);

        void onOrderSubmitError();
    }
}
