package com.syscription.firstchoicemart.Presentation.presenters;

import com.syscription.firstchoicemart.Models.OrderDetail;
import com.syscription.firstchoicemart.Presentation.ui.activities.PurchaseHistoryDetailsView;
import com.syscription.firstchoicemart.domain.executor.Executor;
import com.syscription.firstchoicemart.domain.executor.MainThread;
import com.syscription.firstchoicemart.domain.interactors.PurchaseHistoryDetailsInteractor;
import com.syscription.firstchoicemart.domain.interactors.impl.PurchaseHistoryDetailsInteractorImpl;

import java.util.List;

public class PurchaseHistoryDetailsPresenter extends AbstractPresenter implements PurchaseHistoryDetailsInteractor.CallBack {

    private final PurchaseHistoryDetailsView purchaseHistoryDetailsView;

    public PurchaseHistoryDetailsPresenter(Executor executor, MainThread mainThread, PurchaseHistoryDetailsView purchaseHistoryDetailsView) {
        super(executor, mainThread);
        this.purchaseHistoryDetailsView = purchaseHistoryDetailsView;
    }

    public void getOrderDetails(String token, String url){
        new PurchaseHistoryDetailsInteractorImpl(mExecutor, mMainThread, this, url, token).execute();
    }

    @Override
    public void onOrderDetailsLoaded(List<OrderDetail> orderDetails) {
        if (purchaseHistoryDetailsView != null){
            purchaseHistoryDetailsView.onOrderDetailsLoaded(orderDetails);
        }
    }

    @Override
    public void onOrderDetailsLoadError() {

    }
}
