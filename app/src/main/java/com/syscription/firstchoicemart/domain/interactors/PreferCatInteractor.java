package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Network.response.PreferDataResponse;

public interface PreferCatInteractor {
    interface CallBack {

        void onPreferCaTDataArrived(PreferDataResponse preferDataResponse);

        void onPreferItemAddedError();
    }
}
