package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Models.Wallet;

import java.util.List;

public interface WalletHistoryInteractor {
    interface CallBack {

        void onWalletHistoryLodaded(List<Wallet> walletList);

        void onWalletHistoryLoadError();
    }
}
