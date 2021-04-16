package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Network.response.VariantResponse;

public interface BuyingOptionInteractor {
    interface CallBack {

        void onGetVariantPrice(VariantResponse variantResponse);

        void onGetVariantPriceError();
    }
}
