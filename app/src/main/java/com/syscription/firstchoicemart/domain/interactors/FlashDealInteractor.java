package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Models.FlashDeal;

public interface FlashDealInteractor {
    interface CallBack {

        void onFlashDealProductDownloaded(FlashDeal flashDeal);

        void onFlashDealProductDownloadError();
    }
}