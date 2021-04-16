package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Models.Product;

import java.util.List;

public interface BestSellingInteractor {
    interface CallBack {

        void onBestSellingProductDownloaded(List<Product> products);

        void onBestSellingProductDownloadError();
    }
}
