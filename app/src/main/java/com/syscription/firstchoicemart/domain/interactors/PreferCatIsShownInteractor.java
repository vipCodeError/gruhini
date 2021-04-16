package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Network.response.ProductResponse;

public interface PreferCatIsShownInteractor {
    interface CallBack {

        void onPreferCatIsShownArrived(ProductResponse preferDataResponse);

        void onPreferCatItemAddedError();
    }
}
