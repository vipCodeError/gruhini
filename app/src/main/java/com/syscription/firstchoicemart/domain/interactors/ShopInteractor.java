package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Models.Shop;

public interface ShopInteractor {
    interface CallBack {

        void onShopLoaded(Shop shop);

        void onShopLoadError();
    }
}
