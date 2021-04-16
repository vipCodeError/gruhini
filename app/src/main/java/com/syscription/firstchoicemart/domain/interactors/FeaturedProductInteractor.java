package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Models.Product;

import java.util.List;

public interface FeaturedProductInteractor {
    interface CallBack {

        void onFeaturedProductDownloaded(List<Product> products);

        void onFeaturedProductDownloadError();
    }
}
