package com.syscription.firstchoicemart.Presentation.ui.activities.impl.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.syscription.firstchoicemart.Models.SearchProduct;
import com.syscription.firstchoicemart.Network.response.ProductSearchResponse;
import com.syscription.firstchoicemart.Presentation.presenters.ProductSearchPresenter;
import com.syscription.firstchoicemart.Presentation.ui.activities.impl.ProductDetailsActivity;
import com.syscription.firstchoicemart.Presentation.ui.adapters.ProductSearchAdapter;
import com.syscription.firstchoicemart.Presentation.ui.fragments.ProductSearchView;
import com.syscription.firstchoicemart.Presentation.ui.listeners.EndlessRecyclerOnScrollListener;
import com.syscription.firstchoicemart.Presentation.ui.listeners.SearchProductClickListener;
import com.syscription.firstchoicemart.R;
import com.syscription.firstchoicemart.Threading.MainThreadImpl;
import com.syscription.firstchoicemart.Utils.AppConfig;
import com.syscription.firstchoicemart.domain.executor.impl.ThreadExecutor;
import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration;

import java.util.ArrayList;
import java.util.List;

public class ProductSearchActivity extends AppCompatActivity implements ProductSearchView , SearchProductClickListener {
    private View v;

    private final List<SearchProduct> mProducts = new ArrayList<>();
    private SearchView searchView;
    private RadioGroup radioScope;
    private ProductSearchAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ProductSearchPresenter productSearchPresenter;
    private String url = AppConfig.BASE_URL+"products/search?key=&scope=product&page=1";
    private String key = "";
    private final String scope = "product";
    private ProductSearchResponse mProductSearchResponse = null;
    private ImageView categoriesBtn;

    private Toolbar mToolBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search);


        mToolBar = findViewById(R.id.toolbar);

        setSupportActionBar(mToolBar);
        mToolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        categoriesBtn = findViewById(R.id.category_wise);

        recyclerView = findViewById(R.id.product_list);

        recyclerView.addItemDecoration( new LayoutMarginDecoration( 1,  AppConfig.convertDpToPx(this, 10)) );

        progressBar = findViewById(R.id.item_progress_bar);

        searchView = findViewById(R.id.search_key);
        //radioScope = v.findViewById(R.id.scope_radio);
        productSearchPresenter = new ProductSearchPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this);


        if(getIntent().getStringExtra("voice_str") != null){
            String voiceStr = getIntent().getStringExtra("voice_str");
            searchView.setQuery(voiceStr, false);
            searchProduct(voiceStr, scope);
        }else {
            searchProduct("", "product");
        }

        //searchView.setQueryHint("I'm looking for...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();     // Close keyboard on pressing IME_ACTION_SEARCH option
                key = query;
                searchProduct(key, scope);
                //load search query
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                //Log.d("Test", "QueryTextChange: "+ newText);
                if (newText.length() == 0){
                    key = "";
                    searchProduct(key, scope);
                }
                else {
                    key = newText;
                    searchProduct(key, scope);
                }
                return true;
            }
        });

        categoriesBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LayoutInflater factory = LayoutInflater.from(ProductSearchActivity.this);
                final View customDialogView = factory.inflate(R.layout.custom_cat_lay, null);
                AlertDialog.Builder catDialog = new AlertDialog.Builder(ProductSearchActivity.this);
                catDialog.setView(customDialogView);

                //IndicatorSeekBar belowTenSeekbar = customDialogView.findViewById(R.id.below_ten_indicator);
                AppCompatRadioButton lowToHighCheckBox = customDialogView.findViewById(R.id.low_to_high);
                AppCompatRadioButton highToLowCheckBox = customDialogView.findViewById(R.id.high_to_low);
                AppCompatRadioButton newArrivalCheckBox = customDialogView.findViewById(R.id.new_arrival);
                AppCompatRadioButton popularityCheckBox = customDialogView.findViewById(R.id.popularity);
                //EditText firstRangeEditText = customDialogView.findViewById(R.id.first_range_edit_text);
                //EditText secondRangeEditText = customDialogView.findViewById(R.id.second_range_edit_text);

              //  int progress = belowTenSeekbar.getProgress();

                lowToHighCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                            highToLowCheckBox.setChecked(false);
                            newArrivalCheckBox.setChecked(false);
                            popularityCheckBox.setChecked(false);
                        }
                    }
                });

                highToLowCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                            lowToHighCheckBox.setChecked(false);
                            newArrivalCheckBox.setChecked(false);
                            popularityCheckBox.setChecked(false);
                        }
                    }
                });

                newArrivalCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        lowToHighCheckBox.setChecked(false);
                        popularityCheckBox.setChecked(false);
                        highToLowCheckBox.setChecked(false);
                    }
                });

                popularityCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        lowToHighCheckBox.setChecked(false);
                        newArrivalCheckBox.setChecked(false);
                        highToLowCheckBox.setChecked(false);
                    }
                });

                catDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (lowToHighCheckBox.isChecked()){
                            searchProduct(key, "price_low_to_high");
                        }else if(highToLowCheckBox.isChecked()){
                            searchProduct(key, "price_high_to_low");
                        } else if(newArrivalCheckBox.isChecked()){
                            searchProduct(key, "new_arrival");
                        } else if(popularityCheckBox.isChecked()){
                            searchProduct(key, "popularity");
                        }

                    }
                });

                catDialog.show();
            }
        });
    }


    private void searchProduct(String key, String scope){
        //Log.d("Test", scope);
        url = url.replace("key="+url.split("key=")[1].split("&")[0], "key="+key);
        url = url.replace("scope="+url.split("scope=")[1].split("&")[0], "scope="+scope.toLowerCase());

        //Log.d("Test", url);

        mProducts.removeAll(mProducts);

        GridLayoutManager horizontalLayoutManager
                = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(horizontalLayoutManager);

        adapter = new ProductSearchAdapter(this, mProducts, this);

        recyclerView.addOnScrollListener(       new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                addDataToList(mProductSearchResponse);
            }
        });

        recyclerView.setAdapter(adapter);

        progressBar.setVisibility(View.VISIBLE);

        productSearchPresenter.getSearchedProducts(url);
    }

    public void addDataToList(ProductSearchResponse productSearchResponse){
        if (productSearchResponse != null && productSearchResponse.getMeta() != null && !productSearchResponse.getMeta().getCurrentPage().equals(productSearchResponse.getMeta().getLastPage())){
            progressBar.setVisibility(View.VISIBLE);
            productSearchPresenter.getSearchedProducts(productSearchResponse.getLinks().getNext());
        }
    }

    @Override
    public void onProductItemClick(SearchProduct product) {
        Intent intent = new Intent(this, ProductDetailsActivity.class);
        intent.putExtra("product_name", product.getName());
        intent.putExtra("link", product.getLinks().getDetails());
        intent.putExtra("top_selling", product.getLinks().getRelated());
        startActivity(intent);
    }

    @Override
    public void setSearchedProduct(ProductSearchResponse productSearchResponse) {
        Log.d("Test", String.valueOf(productSearchResponse.getMeta().getTotal()));
        mProducts.addAll(productSearchResponse.getData());
        mProductSearchResponse = productSearchResponse;
        progressBar.setVisibility(View.GONE);
        adapter.notifyDataSetChanged();
    }
}
