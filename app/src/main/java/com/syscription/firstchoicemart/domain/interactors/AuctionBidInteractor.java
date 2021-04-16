package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Network.response.AuctionBidResponse;

public interface AuctionBidInteractor {
    interface CallBack {

        void onBidSubmitted(AuctionBidResponse auctionBidResponse);

        void onBidSubmittedError();
    }
}
