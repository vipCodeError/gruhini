package com.syscription.firstchoicemart.Presentation.ui.fragments.impl;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.syscription.firstchoicemart.Models.PurchaseHistory;
import com.syscription.firstchoicemart.Network.response.AuthResponse;
import com.syscription.firstchoicemart.Presentation.presenters.PurchaseHistoryPresenter;
import com.syscription.firstchoicemart.Presentation.ui.activities.PurchaseHistoryView;
import com.syscription.firstchoicemart.Presentation.ui.activities.impl.LoginActivity;
import com.syscription.firstchoicemart.Presentation.ui.activities.impl.PurchaseHistoryDetailsActivity;
import com.syscription.firstchoicemart.Presentation.ui.adapters.PurchaseHistoryAdapter;
import com.syscription.firstchoicemart.Presentation.ui.listeners.PurchaseHistoryCliclListener;
import com.syscription.firstchoicemart.R;
import com.syscription.firstchoicemart.Threading.MainThreadImpl;
import com.syscription.firstchoicemart.Utils.AppConfig;
import com.syscription.firstchoicemart.Utils.RecyclerViewMargin;
import com.syscription.firstchoicemart.Utils.UserPrefs;
import com.syscription.firstchoicemart.domain.executor.impl.ThreadExecutor;

import java.util.List;

public class PurchaseHistoryFragment extends Fragment implements PurchaseHistoryView, PurchaseHistoryCliclListener {
    private AuthResponse authResponse;
    private PurchaseHistoryPresenter purchaseHistoryPresenter;
    private ProgressBar progressBar;
    private TextView purchase_history_empty_text;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_purchase_history, container, false);

        progressBar = view.findViewById(R.id.item_progress_bar);
        purchase_history_empty_text = view.findViewById(R.id.purchase_history_empty_text);

        purchaseHistoryPresenter = new PurchaseHistoryPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this);

        authResponse = new UserPrefs(requireActivity()).getAuthPreferenceObjectJson("auth_response");
        if(authResponse != null && authResponse.getUser() != null){
            progressBar.setVisibility(View.VISIBLE);
            purchaseHistoryPresenter.getPurchaseHistoryItems(authResponse.getUser().getId(), authResponse.getAccessToken());
        }  else {
            startActivityForResult(new Intent(getActivity(), LoginActivity.class), 100);
            //getActivity().setTitle("Purchase History");
        }


        return view;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_purchase_history);
//
//        initializeActionBar();
//        setTitle("Purchase History");
//    }

    @Override
    public void onPurchaseHistoryLoaded(List<PurchaseHistory> purchaseHistoryList) {
        progressBar.setVisibility(View.GONE);
        if (purchaseHistoryList.size() > 0){
            RecyclerView recyclerView = view.findViewById(R.id.purchase_history_list);
            GridLayoutManager horizontalLayoutManager
                    = new GridLayoutManager(requireActivity(), 1);
            recyclerView.setLayoutManager(horizontalLayoutManager);
            PurchaseHistoryAdapter adapter = new PurchaseHistoryAdapter(requireActivity(), purchaseHistoryList, this);
            RecyclerViewMargin decoration = new RecyclerViewMargin(AppConfig.convertDpToPx(requireActivity(),10), 1);
            recyclerView.addItemDecoration(decoration);
            recyclerView.setAdapter(adapter);
        }
        else {
            purchase_history_empty_text.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPurchaseHistoryItemClick(PurchaseHistory purchaseHistory) {
        Intent intent = new Intent(requireActivity(), PurchaseHistoryDetailsActivity.class);
        intent.putExtra("purchase_history", purchaseHistory);
        startActivity(intent);
    }
}
