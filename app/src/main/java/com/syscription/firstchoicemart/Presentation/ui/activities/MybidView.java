package com.syscription.firstchoicemart.Presentation.ui.activities;

import com.syscription.firstchoicemart.Models.UserBid;
import com.syscription.firstchoicemart.Network.response.AuctionBidResponse;

import java.util.List;

public interface MybidView {
    void setUserBids(List<UserBid> userBids);
    void onAuctionBidSubmitted(AuctionBidResponse auctionBidResponse);
}
