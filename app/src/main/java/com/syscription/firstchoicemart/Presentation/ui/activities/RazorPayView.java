package com.syscription.firstchoicemart.Presentation.ui.activities;

import com.syscription.firstchoicemart.Network.response.OrderResponse;

public interface RazorPayView {
    void onIncomingOrderId(OrderResponse orderResponse);
}
