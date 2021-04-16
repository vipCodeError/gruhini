package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Models.ProductDetails;

public interface ProductDetailsInteractor {
    interface CallBack {

        void onProductDetailsDownloaded(ProductDetails productDetails);

        void onProductDetailsDownloadError();
    }
}
