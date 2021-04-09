package com.activeitzone.activeecommercecms.domain.interactors;

import com.activeitzone.activeecommercecms.Models.Product;
import com.activeitzone.activeecommercecms.Network.response.ProductResponse;

import java.util.List;

public interface ProductInteractor {
    interface CallBack {

        void onProductDownloaded(ProductResponse productListingResponse);

        void onProductZeroDownload(ProductResponse productListingResponse);

        void onProductFirstDownloaded(ProductResponse productListingResponse);

        void onProductSecondDownloaded(ProductResponse productListingResponse);

        void onProductThirdDownloaded(ProductResponse productListingResponse);

        void onProductFourthDownloaded(ProductResponse productListingResponse);

        void onProductFifthDownloaded(ProductResponse productListingResponse);

        void onProductSixthDownloaded(ProductResponse productListingResponse);

        void onProductSeventhDownloaded(ProductResponse productListingResponse);

        void onProductEigthDownloaded(ProductResponse productListingResponse);

        void onProductNinthDownloaded(ProductResponse productListingResponse);

        void onProductTenthDownloaded(ProductResponse productListingResponse);

        void onProductDownloaded(List<Product> products);

        void onProductDownloadError();
    }
}
