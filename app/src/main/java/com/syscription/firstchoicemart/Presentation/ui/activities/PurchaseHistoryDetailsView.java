package com.syscription.firstchoicemart.Presentation.ui.activities;

import com.syscription.firstchoicemart.Models.OrderDetail;

import java.util.List;

public interface PurchaseHistoryDetailsView {
    void onOrderDetailsLoaded(List<OrderDetail> orderDetailList);
}
