package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Network.response.ShippingInfoResponse;

public interface ShippingInfoDeleteInteractor {
    interface CallBack {

        void onShippingInfoDeleted(ShippingInfoResponse shippingInfoResponse);

        void onShippingInfoDeleteError();
    }
}
