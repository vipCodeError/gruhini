package com.activeitzone.activeecommercecms.domain.interactors;

import com.activeitzone.activeecommercecms.Network.response.PreferDataResponse;
import com.activeitzone.activeecommercecms.Network.response.ProductResponse;

public interface PreferCatIsShownInteractor {
    interface CallBack {

        void onPreferCatIsShownArrived(ProductResponse preferDataResponse);

        void onPreferCatItemAddedError();
    }
}
