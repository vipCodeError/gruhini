package com.activeitzone.activeecommercecms.domain.interactors;

import com.activeitzone.activeecommercecms.Network.response.ProductListingResponse;

public interface ProductListingInteractor {
    interface CallBack {

        void onProductDownloaded(ProductListingResponse productListingResponse);

        void onProductMensDownloaded(ProductListingResponse productListingResponse);

        void onProductWomanDownloaded(ProductListingResponse productListingResponse);

        void onProductBeautyDownloaded(ProductListingResponse productListingResponse);

        void onProductSportsAndFitnessDownloaded(ProductListingResponse productListingResponse);

        void onProductToyBabyDownloaded(ProductListingResponse productListingResponse);

        void onProductElectonicsDownloaded(ProductListingResponse productListingResponse);

        void onProductMobilePcDownloaded(ProductListingResponse productListingResponse);

        void onProductKitchenAndHomeDownloaded(ProductListingResponse productListingResponse);

        void onProductBagAndLuggageDownloaded(ProductListingResponse productListingResponse);

        void onProductBooksDownloaded(ProductListingResponse productListingResponse);

        void onProductDownloadError();
    }
}
