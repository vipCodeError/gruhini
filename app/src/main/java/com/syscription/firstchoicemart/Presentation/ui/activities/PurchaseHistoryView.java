package com.syscription.firstchoicemart.Presentation.ui.activities;

import com.syscription.firstchoicemart.Models.PurchaseHistory;

import java.util.List;

public interface PurchaseHistoryView {
    void onPurchaseHistoryLoaded(List<PurchaseHistory> purchaseHistoryList);
}
