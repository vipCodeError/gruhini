package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Models.OrderDetail;

import java.util.List;

public interface PurchaseHistoryDetailsInteractor {
    interface CallBack {

        void onOrderDetailsLoaded(List<OrderDetail> orderDetails);

        void onOrderDetailsLoadError();
    }
}
