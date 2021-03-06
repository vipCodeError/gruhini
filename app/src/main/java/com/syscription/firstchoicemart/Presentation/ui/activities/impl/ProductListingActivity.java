package com.syscription.firstchoicemart.Presentation.ui.activities.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.syscription.firstchoicemart.Models.Product;
import com.syscription.firstchoicemart.Network.response.ProductListingResponse;
import com.syscription.firstchoicemart.Presentation.presenters.ProductListingPresenter;
import com.syscription.firstchoicemart.Presentation.ui.activities.ProductListingView;
import com.syscription.firstchoicemart.Presentation.ui.adapters.ProductListingAdapter;
import com.syscription.firstchoicemart.Presentation.ui.listeners.EndlessRecyclerOnScrollListener;
import com.syscription.firstchoicemart.Presentation.ui.listeners.ProductClickListener;
import com.syscription.firstchoicemart.R;
import com.syscription.firstchoicemart.Threading.MainThreadImpl;
import com.syscription.firstchoicemart.Utils.RecyclerViewMargin;
import com.syscription.firstchoicemart.domain.executor.impl.ThreadExecutor;

import java.util.ArrayList;
import java.util.List;

public class ProductListingActivity extends BaseActivity implements ProductListingView, ProductClickListener {

    private final List<Product> mProducts = new ArrayList<>();
    private ProductListingResponse productListingResponse = null;
    private ProductListingPresenter productListingPresenter;
    private ProductListingAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView products_empty_text;
    private Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_listing);

        String title = getIntent().getStringExtra("title");
        String url = getIntent().getStringExtra("url");

        // initializeActionBar();
        mToolBar = findViewById(R.id.toolbar);

        setSupportActionBar(mToolBar);
        mToolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(title);

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        adapter = new ProductListingAdapter(getApplicationContext(), mProducts, this);
        recyclerView = findViewById(R.id.product_list);
        progressBar = findViewById(R.id.item_progress_bar);
        products_empty_text = findViewById(R.id.products_empty_text);

        GridLayoutManager horizontalLayoutManager
                = new GridLayoutManager(ProductListingActivity.this, 2);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        //adapter.setClickListener(this);
        RecyclerViewMargin decoration = new RecyclerViewMargin(convertDpToPx(this,10), 2);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                    addDataToList(productListingResponse);
            }
        });

        progressBar.setVisibility(View.VISIBLE);

        productListingPresenter = new ProductListingPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this);
        productListingPresenter.getProducts(url);
    }

    @Override
    public void setProducts(ProductListingResponse productListingResponse) {
        mProducts.addAll(productListingResponse.getData());
        this.productListingResponse = productListingResponse;
        progressBar.setVisibility(View.GONE);
        adapter.notifyDataSetChanged();

        if (mProducts.size() <= 0){
            products_empty_text.setVisibility(View.VISIBLE);
        }
    }

    public void addDataToList(ProductListingResponse productListingResponse){
        if (productListingResponse != null && productListingResponse.getMeta() != null && !productListingResponse.getMeta().getCurrentPage().equals(productListingResponse.getMeta().getLastPage())){
            progressBar.setVisibility(View.VISIBLE);
            productListingPresenter.getProducts(productListingResponse.getLinks().getNext());
        }
    }

    @Override
    public void onProductItemClick(Product product) {
        Intent intent = new Intent(this, ProductDetailsActivity.class);
        intent.putExtra("product_name", product.getName());
        intent.putExtra("link", product.getLinks().getDetails());
        intent.putExtra("top_selling", product.getLinks().getRelated());
        startActivity(intent);
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public int convertDpToPx(Context context, float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }
}
