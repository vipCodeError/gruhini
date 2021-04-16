package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Network.response.CouponResponse;

public interface CouponInteractor {
    interface CallBack {

        void onCouponApplied(CouponResponse couponResponse);

        void onCouponAppliedError();
    }
}
