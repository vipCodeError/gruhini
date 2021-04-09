package com.activeitzone.activeecommercecms.domain.interactors;

import com.activeitzone.activeecommercecms.Network.response.AddToWishlistResponse;
import com.activeitzone.activeecommercecms.Network.response.PreferDataResponse;
import com.activeitzone.activeecommercecms.Network.response.ProductResponse;

public interface PreferCatInteractor {
    interface CallBack {

        void onPreferCaTDataArrived(PreferDataResponse preferDataResponse);

        void onPreferItemAddedError();
    }
}
