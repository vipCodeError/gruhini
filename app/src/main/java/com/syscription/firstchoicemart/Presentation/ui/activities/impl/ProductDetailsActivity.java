package com.syscription.firstchoicemart.Presentation.ui.activities.impl;

import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.syscription.firstchoicemart.Models.Product;
import com.syscription.firstchoicemart.Models.ProductDetails;
import com.syscription.firstchoicemart.Network.response.AddToCartResponse;
import com.syscription.firstchoicemart.Network.response.AddToWishlistResponse;
import com.syscription.firstchoicemart.Network.response.AppSettingsResponse;
import com.syscription.firstchoicemart.Network.response.AuthResponse;
import com.syscription.firstchoicemart.Network.response.CheckWishlistResponse;
import com.syscription.firstchoicemart.Network.response.RemoveWishlistResponse;
import com.syscription.firstchoicemart.Presentation.presenters.ProductDetailsPresenter;
import com.syscription.firstchoicemart.Presentation.ui.activities.ProductDetailsView;
import com.syscription.firstchoicemart.Presentation.ui.adapters.BestSellingofSellerAdapter;
import com.syscription.firstchoicemart.Presentation.ui.adapters.FeaturedProductAdapter;
import com.syscription.firstchoicemart.Presentation.ui.listeners.ProductClickListener;
import com.syscription.firstchoicemart.R;
import com.syscription.firstchoicemart.Threading.MainThreadImpl;
import com.syscription.firstchoicemart.Utils.AppConfig;
import com.syscription.firstchoicemart.Utils.CustomToast;
import com.syscription.firstchoicemart.Utils.UserPrefs;
import com.syscription.firstchoicemart.domain.executor.impl.ThreadExecutor;
import com.bumptech.glide.Glide;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration;

import java.util.List;

public class ProductDetailsActivity extends BaseActivity implements ProductDetailsView, ProductClickListener {
    private String product_name, link, top_selling_link;
    private SliderLayout sliderLayout;
    private TextView name;
    private RatingBar ratingBar;
    private TextView rating_count, price_range;
    private ImageView shop_logo, heart_icon;
    private TextView shop_name;
    private RelativeLayout buying_option, specification, reviews, seller_policy, return_policy, support_policy;
    private RecyclerView related_products, top_selling;
    private NestedScrollView product_details;
    private LinearLayout product_buttons;
    private ProgressBar progress_bar;
    private Button addTocart, buyNow;
    private ProductDetails productDetails = null;
    private ProgressDialog progressDialog;
    private AuthResponse authResponse;
    private ProductDetailsPresenter productDetailsPresenter;
    private CardView shop_info, image_card;
    private boolean isBuyNow = false;

    private Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        mToolBar = findViewById(R.id.toolbar);

        setSupportActionBar(mToolBar);
        mToolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//        cart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                navView.setSelectedItemId(R.id.navigation_cart);
////                loadFragment(cartFragment);
//            }
//        });
//
//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                navView.setSelectedItemId(R.id.navigation_search);
////                loadFragment(searchFragment);
//            }
//        });


        product_name = getIntent().getStringExtra("product_name");
        link = getIntent().getStringExtra("link");
        top_selling_link = getIntent().getStringExtra("top_selling");
        initviews();

        getSupportActionBar().setTitle(product_name);

        progress_bar.setVisibility(View.VISIBLE);
        product_details.setVisibility(View.INVISIBLE);
        product_buttons.setVisibility(View.INVISIBLE);
        image_card.setVisibility(View.GONE);

        productDetailsPresenter = new ProductDetailsPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this);

        productDetailsPresenter.getProductDetails(link);

        seller_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PolicyViewActivity.class);
                intent.putExtra("title", "Seller Policy");
                intent.putExtra("url", "policies/seller");
                startActivity(intent);
            }
        });

        specification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPopupMessage(productDetails.getName(), productDetails.getDescription());
//                Intent intent = new Intent(getApplicationContext(), ProductDescriptionActivity.class);
//                intent.putExtra("product_name", productDetails.getName());
//                intent.putExtra("description", productDetails.getDescription());
//                startActivity(intent);
            }
        });

//        description.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), PolicyViewActivity.class);
//                intent.putExtra("url", "http://shop.activeitzone.com/privacypolicy");
//                startActivity(intent);
//            }
//        });

        reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProductDetailsActivity.this, ProductReviewActivity.class);
                intent.putExtra("url", productDetails.getLinks().getReviews());
                startActivity(intent);
            }
        });

        return_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PolicyViewActivity.class);
                intent.putExtra("title", "Return Policy");
                intent.putExtra("url", "policies/return");
                startActivity(intent);
            }
        });

        support_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PolicyViewActivity.class);
                intent.putExtra("title", "Support Policy");
                intent.putExtra("url", "policies/support");
                startActivity(intent);
            }
        });


        buying_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBuyingOptionActivity();
            }
        });

        addTocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processAddToCart();
            }
        });

        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isBuyNow = true;
                processAddToCart();
            }
        });
    }

    private void processAddToCart(){
        if(productDetails != null && (productDetails.getChoiceOptions().size() > 0 || productDetails.getColors().size() > 0)){
            startBuyingOptionActivity();
        }
        else {
            AuthResponse authResponse = new UserPrefs(getApplicationContext()).getAuthPreferenceObjectJson("auth_response");
            if(authResponse != null && authResponse.getUser() != null){
                progressDialog.setMessage("Adding item to your shopping cart. Please wait.");
                progressDialog.show();
                productDetailsPresenter.addToCart(authResponse.getAccessToken(), authResponse.getUser().getId(), productDetails.getId(), null);
            }
            else {
                startActivityForResult(new Intent(getApplicationContext(), LoginActivity.class), 100);
                finish();
            }
        }
    }

    private void startBuyingOptionActivity(){
        if(productDetails != null && (productDetails.getChoiceOptions().size() > 0 || productDetails.getColors().size() > 0)){
            Intent intent = new Intent(getApplicationContext(), BuyingOptionsActivity.class);
            intent.putExtra("product_details", productDetails);
            startActivity(intent);
        }
        else{
            CustomToast.showToast(this, "This product doesn't have any buying options.", R.color.colorWarning);
        }
    }

    private void initviews(){
        product_details = findViewById(R.id.product_details);
        product_buttons = findViewById(R.id.product_buttons);
        progress_bar = findViewById(R.id.item_progress_bar);
        sliderLayout = findViewById(R.id.imageSlider);
        sliderLayout.stopAutoCycle();
        name = findViewById(R.id.product_name);
        ratingBar = findViewById(R.id.product_rating);
        rating_count = findViewById(R.id.product_rating_count);
        price_range = findViewById(R.id.product_price_range);
        shop_logo = findViewById(R.id.shop_logo);
        heart_icon = findViewById(R.id.heart_icon);
        shop_name = findViewById(R.id.shop_name);
        buying_option = findViewById(R.id.buying_option);
        specification = findViewById(R.id.specification);
        //description = findViewById(R.id.description);
        reviews = findViewById(R.id.reviews);
        seller_policy = findViewById(R.id.seller_policy);
        return_policy = findViewById(R.id.return_policy);
        support_policy = findViewById(R.id.support_policy);
        related_products = findViewById(R.id.related_products);
        top_selling = findViewById(R.id.top_selling);
        addTocart = findViewById(R.id.addToCart);
        buyNow = findViewById(R.id.buyNow);

        progressDialog = new ProgressDialog(this);
        shop_info = findViewById(R.id.shop_info);
        image_card = findViewById(R.id.image_card);
    }

    @Override
    public void setProductDetails(ProductDetails productDetails) {
        this.productDetails = productDetails;
        for (String photo : productDetails.getPhotos()) {
            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView
                    .description("")
                    .image(AppConfig.ASSET_URL + photo)
                    .setScaleType(BaseSliderView.ScaleType.CenterInside);
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        image_card.setVisibility(View.VISIBLE);

        name.setText(productDetails.getName());
        ratingBar.setRating(productDetails.getRating());
        rating_count.setText("("+productDetails.getRatingCount()+")");

        authResponse = new UserPrefs(getApplicationContext()).getAuthPreferenceObjectJson("auth_response");
        if(authResponse != null && authResponse.getUser() != null){
            productDetailsPresenter.checkOnWishlist(authResponse.getAccessToken(), authResponse.getUser().getId(), productDetails.getId());
        }
        else {
            heart_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(new Intent(getApplicationContext(), LoginActivity.class), 100);
                    finish();
                }
            });
        }

        if (!productDetails.getAddedBy().equals("admin")){
            Glide.with(this).load(AppConfig.ASSET_URL + productDetails.getUser().getShopLogo()).into(shop_logo);
            shop_name.setText(productDetails.getUser().getShopName());
            shop_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ProductDetailsActivity.this, SellerShopActivity.class);
                    intent.putExtra("shop_name", productDetails.getUser().getShopName());
                    intent.putExtra("shop_link", productDetails.getUser().getShopLink());
                    startActivity(intent);
                }
            });
        }
        else {
            AppSettingsResponse appSettingsResponse = new UserPrefs(this).getAppSettingsPreferenceObjectJson("app_settings_response");
            if (appSettingsResponse != null){
                Glide.with(this).load(AppConfig.ASSET_URL + appSettingsResponse.getData().get(0).getLogo()).into(shop_logo);
            }
        }

        if(productDetails.getPriceLower().equals(productDetails.getPriceHigher())){
            price_range.setText(AppConfig.convertPrice(this, productDetails.getPriceLower()));
        }
        else {
            price_range.setText(AppConfig.convertPrice(this, productDetails.getPriceLower())+"-"+AppConfig.convertPrice(this, productDetails.getPriceHigher()));
        }

        progress_bar.setVisibility(View.GONE);
        product_details.setVisibility(View.VISIBLE);
        product_buttons.setVisibility(View.VISIBLE);

        new ProductDetailsPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this).getRelatedProducts(productDetails.getLinks().getRelated());

        new ProductDetailsPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this).getTopSellingProducts(top_selling_link);
    }

    @Override
    public void setRelatedProducts(List<Product> relatedProducts) {
        RecyclerView recyclerView = findViewById(R.id.related_products);
        GridLayoutManager horizontalLayoutManager
                = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        FeaturedProductAdapter adapter = new FeaturedProductAdapter(this, relatedProducts, this);
        recyclerView.addItemDecoration( new LayoutMarginDecoration( 1,  AppConfig.convertDpToPx(getApplicationContext(), 10)) );
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setTopSellingProducts(List<Product> topSellingProducts) {
        RecyclerView recyclerView = findViewById(R.id.top_selling);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        BestSellingofSellerAdapter adapter = new BestSellingofSellerAdapter(this, topSellingProducts, this);
        recyclerView.addItemDecoration( new LayoutMarginDecoration( 1,  AppConfig.convertDpToPx(getApplicationContext(), 10)) );
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setAddToCartMessage(AddToCartResponse addToCartResponse) {
        progressDialog.dismiss();
        if (isBuyNow){
            Intent intent = new Intent(this, DrawerActivityNew.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("message", addToCartResponse.getMessage());
            intent.putExtra("position", "cart");
            startActivity(intent);
            finish();
        }
        else {
            CustomToast.showToast(this, addToCartResponse.getMessage(), R.color.colorSuccess);
        }
    }

    @Override
    public void setAddToWishlistMessage(AddToWishlistResponse addToWishlistMessage) {
        CustomToast.showToast(this, addToWishlistMessage.getMessage(), R.color.colorSuccess);
        heart_icon.setImageResource(R.drawable.ic_heart_filled);
        productDetailsPresenter.checkOnWishlist(authResponse.getAccessToken(), authResponse.getUser().getId(), productDetails.getId());
    }

    @Override
    public void onCheckWishlist(CheckWishlistResponse checkWishlistResponse) {
        if(checkWishlistResponse.getIsInWishlist()){
            heart_icon.setImageResource(R.drawable.ic_heart_filled);
            heart_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ValueAnimator valueAnimator = ValueAnimator.ofFloat(1.2f, 1f);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float value = (float) animation.getAnimatedValue();
                            heart_icon.setScaleX(value);
                            heart_icon.setScaleY(value);
                        }
                    });

                    valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                    valueAnimator.setDuration(200);
                    valueAnimator.start();
                    productDetailsPresenter.removeFromWishlist(authResponse.getAccessToken(), checkWishlistResponse.getWishlistId());
                }
            });
        }
        else{
            heart_icon.setImageResource(R.drawable.ic_heart);
            heart_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ValueAnimator valueAnimator = ValueAnimator.ofFloat(1, 1.2f);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float value = (float) animation.getAnimatedValue();
                            heart_icon.setScaleX(value);
                            heart_icon.setScaleY(value);
                        }
                    });

                    valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                    valueAnimator.setDuration(200);
                    valueAnimator.start();
                    productDetailsPresenter.addToWishlist(authResponse.getAccessToken(), authResponse.getUser().getId(), productDetails.getId());
                }
            });
        }
    }

    @Override
    public void onRemoveFromWishlist(RemoveWishlistResponse removeWishlistResponse) {
        heart_icon.setImageResource(R.drawable.ic_heart);
        CustomToast.showToast(this, removeWishlistResponse.getMessage(), R.color.colorSuccess);
        productDetailsPresenter.checkOnWishlist(authResponse.getAccessToken(), authResponse.getUser().getId(), productDetails.getId());
    }

    @Override
    public void onProductItemClick(Product product) {
        Intent intent = new Intent(this, ProductDetailsActivity.class);
        intent.putExtra("product_name", product.getName());
        intent.putExtra("link", product.getLinks().getDetails());
        intent.putExtra("top_selling", product.getLinks().getRelated());
        startActivity(intent);
    }

    public void dialogPopupMessage(String title, String desc){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(title);
        alertDialog.setMessage(desc);
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();
    }
}
