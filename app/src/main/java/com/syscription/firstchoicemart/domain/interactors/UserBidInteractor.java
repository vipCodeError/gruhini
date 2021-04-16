package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Models.UserBid;

import java.util.List;

public interface UserBidInteractor {
    interface CallBack {

        void onUserBidLodaded(List<UserBid> userBids);

        void onUserBidError();
    }
}
