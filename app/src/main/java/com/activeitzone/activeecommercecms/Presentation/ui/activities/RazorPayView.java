package com.activeitzone.activeecommercecms.Presentation.ui.activities;

import com.activeitzone.activeecommercecms.Network.response.CouponResponse;
import com.activeitzone.activeecommercecms.Network.response.OrderResponse;

public interface RazorPayView {
    void onIncomingOrderId(OrderResponse orderResponse);
}
