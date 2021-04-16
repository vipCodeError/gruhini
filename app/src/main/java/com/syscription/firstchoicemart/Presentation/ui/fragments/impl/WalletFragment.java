package com.syscription.firstchoicemart.Presentation.ui.fragments.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.syscription.firstchoicemart.Models.Wallet;
import com.syscription.firstchoicemart.Network.response.AuthResponse;
import com.syscription.firstchoicemart.Presentation.presenters.WalletPresenter;
import com.syscription.firstchoicemart.Presentation.ui.activities.WalletView;
import com.syscription.firstchoicemart.Presentation.ui.activities.impl.LoginActivity;
import com.syscription.firstchoicemart.Presentation.ui.adapters.WalletHistoryAdapter;
import com.syscription.firstchoicemart.R;
import com.syscription.firstchoicemart.Threading.MainThreadImpl;
import com.syscription.firstchoicemart.Utils.AppConfig;
import com.syscription.firstchoicemart.Utils.RecyclerViewMargin;
import com.syscription.firstchoicemart.Utils.UserPrefs;
import com.syscription.firstchoicemart.domain.executor.impl.ThreadExecutor;

import java.util.List;

public class WalletFragment extends Fragment implements WalletView {
    private AuthResponse authResponse;
    private TextView tv_balance;

    private  View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_wallet, container, false);
        tv_balance = view.findViewById(R.id.balance);
        authResponse = new UserPrefs(requireActivity()).getAuthPreferenceObjectJson("auth_response");
        if(authResponse != null && authResponse.getUser() != null){
            new WalletPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this).getWalletBalance(authResponse.getUser().getId(), authResponse.getAccessToken());
        }
        else {
            startActivityForResult(new Intent(requireActivity(), LoginActivity.class), 100);
          //  getActivity().setTitle("My Wallet");
        }

        return  view;
    }


    @Override
    public void setWalletBalance(Double balance) {
        tv_balance.setText(AppConfig.convertPrice(requireActivity(), balance));
        new WalletPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this).getWalletHistory(authResponse.getUser().getId(), authResponse.getAccessToken());
    }

    @Override
    public void setWalletHistory(List<Wallet> walletList) {
        RecyclerView recyclerView = view.findViewById(R.id.rv_wallet_history);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        RecyclerViewMargin decoration = new RecyclerViewMargin(AppConfig.convertDpToPx(requireActivity(),10), 1);
        recyclerView.addItemDecoration(decoration);
        WalletHistoryAdapter adapter = new WalletHistoryAdapter(requireActivity(), walletList);
        recyclerView.setAdapter(adapter);
    }
}