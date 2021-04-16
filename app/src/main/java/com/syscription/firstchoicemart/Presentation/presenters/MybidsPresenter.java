package com.syscription.firstchoicemart.Presentation.presenters;

import com.syscription.firstchoicemart.Models.UserBid;
import com.syscription.firstchoicemart.Network.response.AuctionBidResponse;
import com.syscription.firstchoicemart.Presentation.ui.activities.MybidView;
import com.syscription.firstchoicemart.domain.executor.Executor;
import com.syscription.firstchoicemart.domain.executor.MainThread;
import com.syscription.firstchoicemart.domain.interactors.AuctionBidInteractor;
import com.syscription.firstchoicemart.domain.interactors.UserBidInteractor;
import com.syscription.firstchoicemart.domain.interactors.impl.AuctionBidInteractorImpl;
import com.syscription.firstchoicemart.domain.interactors.impl.UserBidInteractorImpl;
import com.google.gson.JsonObject;

import java.util.List;

public class MybidsPresenter extends AbstractPresenter implements UserBidInteractor.CallBack, AuctionBidInteractor.CallBack {
    private final MybidView mybidView;

    public MybidsPresenter(Executor executor, MainThread mainThread, MybidView mybidView) {
        super(executor, mainThread);
        this.mybidView = mybidView;
    }

    public void getUserBidsItems(int id, String token) {
        new UserBidInteractorImpl(mExecutor, mMainThread, this, id, token).execute();
    }

    public void submitBid(JsonObject jsonObject, String token){
        new AuctionBidInteractorImpl(mExecutor, mMainThread, this, jsonObject, token).execute();
    }

    @Override
    public void onUserBidLodaded(List<UserBid> userBids) {
        if (mybidView != null){
            mybidView.setUserBids(userBids);
        }
    }

    @Override
    public void onUserBidError() {

    }

    @Override
    public void onBidSubmitted(AuctionBidResponse auctionBidResponse) {
        if (mybidView != null){
            mybidView.onAuctionBidSubmitted(auctionBidResponse);
        }
    }

    @Override
    public void onBidSubmittedError() {

    }
}
