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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.syscription.firstchoicemart.Models.WishlistModel;
import com.syscription.firstchoicemart.Models.WishlistProduct;
import com.syscription.firstchoicemart.Network.response.AuthResponse;
import com.syscription.firstchoicemart.Presentation.presenters.WishlistPresenter;
import com.syscription.firstchoicemart.Presentation.ui.activities.WishlistView;
import com.syscription.firstchoicemart.Presentation.ui.activities.impl.ProductDetailsActivity;
import com.syscription.firstchoicemart.Presentation.ui.adapters.WishlistAdapter;
import com.syscription.firstchoicemart.Presentation.ui.listeners.WishlistProductClickListener;
import com.syscription.firstchoicemart.R;
import com.syscription.firstchoicemart.Threading.MainThreadImpl;
import com.syscription.firstchoicemart.Utils.AppConfig;
import com.syscription.firstchoicemart.Utils.RecyclerViewMargin;
import com.syscription.firstchoicemart.Utils.UserPrefs;
import com.syscription.firstchoicemart.domain.executor.impl.ThreadExecutor;

import java.util.List;

public class WishlistFragment extends Fragment implements WishlistView, WishlistProductClickListener {
    private AuthResponse authResponse;
    private WishlistPresenter wishlistPresenter;
    private ProgressBar progressBar;
    private TextView wishlist_empty_text;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Toast.makeText(container.getContext(), "onCreateView", Toast.LENGTH_SHORT).show();
        view = inflater.inflate(R.layout.activity_wishlist, container,false);
        progressBar = view.findViewById(R.id.item_progress_bar);
        wishlist_empty_text = view.findViewById(R.id.wishlist_empty_text);

        wishlistPresenter = new WishlistPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this);

        authResponse = new UserPrefs(requireContext()).getAuthPreferenceObjectJson("auth_response");


        return view;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_wishlist);
//
//        // initializeActionBar();
//        setTitle("My Wishlist");
//
//
//    }

    @Override
    public void setWishlistProducts(List<WishlistModel> wishlistModels) {
        progressBar.setVisibility(View.GONE);
        if (wishlistModels.size() > 0){
            WishlistAdapter adapter = new WishlistAdapter(requireActivity(), wishlistModels, this);
            RecyclerView recyclerView = view.findViewById(R.id.product_list);
            LinearLayoutManager horizontalLayoutManager
                    = new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(horizontalLayoutManager);
            RecyclerViewMargin decoration = new RecyclerViewMargin(AppConfig.convertDpToPx(requireActivity(),10), 1);
            recyclerView.addItemDecoration(decoration);
            recyclerView.setAdapter(adapter);
        }
        else {
            wishlist_empty_text.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onProductItemClick(WishlistProduct product) {
        Intent intent = new Intent(requireActivity(), ProductDetailsActivity.class);
        intent.putExtra("product_name", product.getName());
        intent.putExtra("link", product.getLinks().getDetails());
        intent.putExtra("top_selling", product.getLinks().getRelated());
        startActivity(intent);
    }
}
