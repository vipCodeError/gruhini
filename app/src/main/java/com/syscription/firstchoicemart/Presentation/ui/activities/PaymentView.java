package com.syscription.firstchoicemart.Presentation.ui.activities;

import com.syscription.firstchoicemart.Network.response.CouponResponse;
import com.syscription.firstchoicemart.Network.response.OrderResponse;

public interface PaymentView {
    void onCouponApplied(CouponResponse couponResponse);
    void onOrderSubmitted(OrderResponse orderResponse);
}
