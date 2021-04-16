package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Network.response.ProductListingResponse;

public interface ProductListingInteractor {
    interface CallBack {

        void onProductDownloaded(ProductListingResponse productListingResponse);

        void onProductZeroDownload(ProductListingResponse productListingResponse);

        void onProductFirstDownloaded(ProductListingResponse productListingResponse);

        void onProductSecondDownloaded(ProductListingResponse productListingResponse);

        void onProductThirdDownloaded(ProductListingResponse productListingResponse);

        void onProductFourthDownloaded(ProductListingResponse productListingResponse);

        void onProductFifthDownloaded(ProductListingResponse productListingResponse);

        void onProductSixthDownloaded(ProductListingResponse productListingResponse);

        void onProductSeventhDownloaded(ProductListingResponse productListingResponse);

        void onProductEigthDownloaded(ProductListingResponse productListingResponse);

        void onProductNinthDownloaded(ProductListingResponse productListingResponse);

        void onProductTenthDownloaded(ProductListingResponse productListingResponse);

        void onProductDownloadError();
    }
}
