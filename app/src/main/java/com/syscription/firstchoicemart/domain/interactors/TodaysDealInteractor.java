package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Models.Product;

import java.util.List;

public interface TodaysDealInteractor {
    interface CallBack {

        void onTodaysDealProductDownloaded(List<Product> products);

        void onTodaysDealProductDownloadError();
    }
}
