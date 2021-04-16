package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Network.response.ProductSearchResponse;

public interface ProductSearchInteractor {
    interface CallBack {

        void onProductSearched(ProductSearchResponse productSearchResponse);

        void onProductSearchedError();
    }
}
