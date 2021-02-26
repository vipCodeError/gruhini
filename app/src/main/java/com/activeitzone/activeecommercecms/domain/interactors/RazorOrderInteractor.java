package com.activeitzone.activeecommercecms.domain.interactors;

import com.activeitzone.activeecommercecms.Network.response.OrderResponse;

public interface RazorOrderInteractor {
    interface CallBack {
        void incomingOrderId(OrderResponse orderResponse);

        void onOrderSubmitError();
    }
}
