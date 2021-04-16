package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Network.response.ShippingInfoResponse;

public interface ShippingInfoCreateInteractor {
    interface CallBack {

        void onShippingInfoCreated(ShippingInfoResponse shippingInfoResponse);

        void onShippingInfoCreateError();
    }
}
