package com.syscription.firstchoicemart.Presentation.presenters;

import com.syscription.firstchoicemart.Models.Wallet;
import com.syscription.firstchoicemart.Presentation.ui.activities.WalletView;
import com.syscription.firstchoicemart.domain.executor.Executor;
import com.syscription.firstchoicemart.domain.executor.MainThread;
import com.syscription.firstchoicemart.domain.interactors.WalletBalanceInteractor;
import com.syscription.firstchoicemart.domain.interactors.WalletHistoryInteractor;
import com.syscription.firstchoicemart.domain.interactors.impl.WalletBalanceInteractorImpl;
import com.syscription.firstchoicemart.domain.interactors.impl.WalletHistoryInteractorImpl;

import java.util.List;

public class WalletPresenter extends AbstractPresenter implements WalletBalanceInteractor.CallBack, WalletHistoryInteractor.CallBack {
    private final WalletView walletView;

    public WalletPresenter(Executor executor, MainThread mainThread, WalletView walletView) {
        super(executor, mainThread);
        this.walletView = walletView;
    }

    public void getWalletBalance(int id, String token) {
        new WalletBalanceInteractorImpl(mExecutor, mMainThread, this, id, token).execute();
    }

    public void getWalletHistory(int id, String token) {
        new WalletHistoryInteractorImpl(mExecutor, mMainThread, this, id, token).execute();
    }

    @Override
    public void onWalletBalanceLodaded(Double balance) {
        if (walletView != null){
            walletView.setWalletBalance(balance);
        }
    }

    @Override
    public void onWalletBalanceLoadError() {

    }

    @Override
    public void onWalletHistoryLodaded(List<Wallet> walletList) {
        if(walletView != null){
            walletView.setWalletHistory(walletList);
        }
    }

    @Override
    public void onWalletHistoryLoadError() {

    }
}
