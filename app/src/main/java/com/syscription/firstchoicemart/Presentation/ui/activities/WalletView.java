package com.syscription.firstchoicemart.Presentation.ui.activities;

import com.syscription.firstchoicemart.Models.Wallet;

import java.util.List;

public interface WalletView {
    void setWalletBalance(Double balance);
    void setWalletHistory(List<Wallet> walletList);
}
