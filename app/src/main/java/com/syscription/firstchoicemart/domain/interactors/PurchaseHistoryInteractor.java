package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Models.PurchaseHistory;

import java.util.List;

public interface PurchaseHistoryInteractor {
    interface CallBack {

        void onPurchaseHistoryLodaded(List<PurchaseHistory> purchaseHistories);

        void onPurchaseHistoryLodadedError();
    }
}
