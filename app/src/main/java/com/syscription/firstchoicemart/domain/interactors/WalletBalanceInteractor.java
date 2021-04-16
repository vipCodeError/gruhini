package com.syscription.firstchoicemart.domain.interactors;

public interface WalletBalanceInteractor {
    interface CallBack {

        void onWalletBalanceLodaded(Double balance);

        void onWalletBalanceLoadError();
    }
}
