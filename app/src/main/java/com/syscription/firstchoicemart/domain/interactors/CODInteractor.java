package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Network.response.OrderResponse;

public interface CODInteractor {
    interface CallBack {

        void onCODOrderSubmitted(OrderResponse orderResponse);

        void onCODOrderSubmitError();
    }
}
