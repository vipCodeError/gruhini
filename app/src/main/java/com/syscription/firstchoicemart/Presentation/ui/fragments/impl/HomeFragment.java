package com.syscription.firstchoicemart.Presentation.ui.fragments.impl;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.syscription.firstchoicemart.Models.AuctionProduct;
import com.syscription.firstchoicemart.Models.Banner;
import com.syscription.firstchoicemart.Models.Brand;
import com.syscription.firstchoicemart.Models.CartModel;
import com.syscription.firstchoicemart.Models.Category;
import com.syscription.firstchoicemart.Models.FlashDeal;
import com.syscription.firstchoicemart.Models.PreferCatModel;
import com.syscription.firstchoicemart.Models.Product;
import com.syscription.firstchoicemart.Models.SliderImage;
import com.syscription.firstchoicemart.Network.response.AppSettingsResponse;
import com.syscription.firstchoicemart.Network.response.AuctionBidResponse;
import com.syscription.firstchoicemart.Network.response.AuthResponse;
import com.syscription.firstchoicemart.Network.response.CartQuantityUpdateResponse;
import com.syscription.firstchoicemart.Network.response.PreferDataResponse;
import com.syscription.firstchoicemart.Network.response.ProductListingResponse;
import com.syscription.firstchoicemart.Network.response.ProductResponse;
import com.syscription.firstchoicemart.Network.response.RemoveCartResponse;
import com.syscription.firstchoicemart.Presentation.presenters.CartPresenter;
import com.syscription.firstchoicemart.Presentation.presenters.HomePresenter;
import com.syscription.firstchoicemart.Presentation.ui.activities.impl.DrawerActivityNew;
import com.syscription.firstchoicemart.Presentation.ui.activities.impl.LoginActivity;
import com.syscription.firstchoicemart.Presentation.ui.activities.impl.ProductDetailsActivity;
import com.syscription.firstchoicemart.Presentation.ui.activities.impl.ProductListingActivity;
import com.syscription.firstchoicemart.Presentation.ui.activities.impl.ui.ProductSearchActivity;
import com.syscription.firstchoicemart.Presentation.ui.adapters.AuctionProductAdapter;
import com.syscription.firstchoicemart.Presentation.ui.adapters.BestSellingAdapter;
import com.syscription.firstchoicemart.Presentation.ui.adapters.BrandAdapter;
import com.syscription.firstchoicemart.Presentation.ui.adapters.FeaturedProductAdapter;
import com.syscription.firstchoicemart.Presentation.ui.adapters.ProductListtingHomeAdapter;
import com.syscription.firstchoicemart.Presentation.ui.adapters.TodaysDealAdapter;
import com.syscription.firstchoicemart.Presentation.ui.adapters.TopCategoryAdapter;
import com.syscription.firstchoicemart.Presentation.ui.adapters.TopSecondCategoryAdapter;
import com.syscription.firstchoicemart.Presentation.ui.fragments.CartView;
import com.syscription.firstchoicemart.Presentation.ui.fragments.HomeView;
import com.syscription.firstchoicemart.Presentation.ui.listeners.AuctionClickListener;
import com.syscription.firstchoicemart.Presentation.ui.listeners.BrandClickListener;
import com.syscription.firstchoicemart.Presentation.ui.listeners.CategoryClickListener;
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
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.JsonObject;
import com.kingfisher.easyviewindicator.AnyViewIndicator;
import com.syscription.firstchoicemart.domain.interactors.TopSecondCategoryInteractor;
import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cn.iwgang.countdownview.CountdownView;
import xyz.peridy.shimmerlayout.ShimmerLayout;


public class HomeFragment extends Fragment implements CartView, HomeView, CategoryClickListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener, ProductClickListener, BrandClickListener, AuctionClickListener {

    private static final int RECOGNIZER_REQ_CODE = 1234;
    private static final int RECOGNIZER_CHECK_CODE = 2345;

    ShimmerLayout shimmerLayout;
    private View v;
    List<SliderImage> sliderImages;
    private SliderLayout bannerOne, bannerTwo, bannerThree, bannerFour;
    private HomePresenter homePresenter, categoriesListPresenter;
    private AnyViewIndicator gridIndicator;
    private RelativeLayout auction_product_section, todays_deal_section, flash_deal_section;
    private AuthResponse authResponse;
    private ProgressDialog progressDialog;
    private BottomSheetDialog dialog;
    private RecyclerView auction_recyclerView;
    private final List<AuctionProduct> mAuctionProducts = new ArrayList<>();
    private  AuctionProductAdapter adapter;
    private TextView flash_deals_text, featured_categories_text,
            featured_products_text, firstProTxt,secondProTxt,
            thirdProTxt, fourthProTxt, fifthProTxt,
            sixthProTxt, seventhProTxt, ninth_pro_txt, tenth_pro_txt, eleventh_pro_txt,
            twelth_pro_txt, thirteen_pro_txt, fourtheen_pro_txt, books_txt,
            eighthProTxt,best_selling_text, todayDealTxt, sec_featured_categories_text;

    private ScrollView scrollContainer;

    private CountdownView mCvCountdownView;
    private ImageView micSearch;
    RelativeLayout searchLayout;

    private CartPresenter cartPresenter;

    RelativeLayout rootLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, null);

        //imageSliderShadow = v.findViewById(R.id.imageSliderShadow);
        //gridIndicator = v.findViewById(R.id.anyViewIndicator);
        auction_product_section = v.findViewById(R.id.auction_product_section);
        todays_deal_section = v.findViewById(R.id.todays_deal_section);
        flash_deal_section = v.findViewById(R.id.flash_deal_section);
        flash_deals_text = v.findViewById(R.id.flash_deals_text);
        featured_categories_text = v.findViewById(R.id.featured_categories_text);
        featured_products_text = v.findViewById(R.id.featured_products_text);
        // sec_featured_categories_text = v.findViewById(R.id.sec_featured_categories_text);

        firstProTxt = v.findViewById(R.id.first_pro_txt);
        secondProTxt = v.findViewById(R.id.second_pro_txt);
        thirdProTxt = v.findViewById(R.id.third_pro_txt);
        fourthProTxt = v.findViewById(R.id.fourth_pro_txt);
        fifthProTxt = v.findViewById(R.id.fifth_pro_txt);
        sixthProTxt = v.findViewById(R.id.sixth_pro_txt);
        seventhProTxt = v.findViewById(R.id.seven_pro_txt);
        eighthProTxt = v.findViewById(R.id.eigth_pro_txt);
        ninth_pro_txt = v.findViewById(R.id.ninth_pro_txt);
        tenth_pro_txt = v.findViewById(R.id.tenth_pro_txt);
        eleventh_pro_txt = v.findViewById(R.id.eleventh_pro_txt);
        twelth_pro_txt = v.findViewById(R.id.twelth_pro_txt);
        thirteen_pro_txt = v.findViewById(R.id.thirteen_pro_txt);
        fourtheen_pro_txt = v.findViewById(R.id.fourthteen_pro_txt);

        shimmerLayout = v.findViewById(R.id.shimmer_layout);
        rootLayout = v.findViewById(R.id.root_layout);

        best_selling_text = v.findViewById(R.id.best_selling_text);
        todayDealTxt = v.findViewById(R.id.todays_deals_text);

        scrollContainer = v.findViewById(R.id.scroll_container);

        mCvCountdownView = (CountdownView) v.findViewById(R.id.countdown);
        searchLayout = v.findViewById(R.id.search_lay);
        micSearch = v.findViewById(R.id.mic_search);

        auction_product_section.setVisibility(View.GONE);
        todays_deal_section.setVisibility(View.GONE);
        flash_deal_section.setVisibility(View.GONE);
        progressDialog = new ProgressDialog(getContext());
        auction_recyclerView = v.findViewById(R.id.auction_products);
        GridLayoutManager horizontalLayoutManager
                = new GridLayoutManager(getActivity(), 1, RecyclerView.VERTICAL, false);
        auction_recyclerView.setLayoutManager(horizontalLayoutManager);
        adapter = new AuctionProductAdapter(getActivity(), mAuctionProducts, this);
        auction_recyclerView.addItemDecoration(new LayoutMarginDecoration( 1,  AppConfig.convertDpToPx(getContext(), 10)));
        auction_recyclerView.setAdapter(adapter);

        bannerOne = v.findViewById(R.id.imageSlider);
        bannerTwo = v.findViewById(R.id.imageSlider_1);
        bannerThree = v.findViewById(R.id.imageSlider_2);
        bannerFour = v.findViewById(R.id.imageSlider_3);

        bannerOne.setVisibility(View.GONE);
        bannerOne.stopAutoCycle();

        homePresenter = new HomePresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), (HomeView) this);
        homePresenter.getAppSettings();
        homePresenter.getSliderImages();
        homePresenter.getTopCategories();
        homePresenter.getTopSecondCategories();
        homePresenter.getBanners();

        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().startActivity(new Intent(requireActivity(), ProductSearchActivity.class));
            }
        });

        micSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
                startActivityForResult(intent, RECOGNIZER_CHECK_CODE);
            }
        });

        textMarqueeAnim(firstProTxt);
        textMarqueeAnim(secondProTxt);
        textMarqueeAnim(thirdProTxt);
        textMarqueeAnim(sixthProTxt);
        textMarqueeAnim(seventhProTxt);
        textMarqueeAnim(eighthProTxt);

        titleAnimator(todayDealTxt);

        authResponse = new UserPrefs(requireActivity()).getAuthPreferenceObjectJson("auth_response");
        cartPresenter = new CartPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this);
        if(authResponse != null && authResponse.getUser() != null){
            cartPresenter.getCartItems(authResponse.getUser().getId(), authResponse.getAccessToken());
        }
        return v;
    }

    @Override
    public void onAppSettingsLoaded(AppSettingsResponse appSettingsResponse) {
        UserPrefs userPrefs = new UserPrefs(getContext());
        userPrefs.setAppSettingsPreferenceObject(appSettingsResponse, "app_settings_response");

        homePresenter.getTodaysDeal();
        homePresenter.getFlashDeal();
        homePresenter.getBestSelling();
        homePresenter.getFeaturedProducts();
        homePresenter.getPopularbrands();
        homePresenter.getAuctionProducts();
    }

    @Override
    public void setSliderImages(List<SliderImage> sliderImages) {
        this.sliderImages = sliderImages;
        for (SliderImage sliderImage : sliderImages) {
            TextSliderView textSliderView = new TextSliderView(getContext());
            textSliderView
                    .description("")
                    .image(AppConfig.ASSET_URL + sliderImage.getPhoto())
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            bannerOne.addSlider(textSliderView);
        }

        setBannertProperty(bannerOne);

    }

    @Override
    public void setHomeCategories(List<Category> categories) {
//        RecyclerView recyclerView = v.findViewById(R.id.home_categories);
//        GridLayoutManager horizontalLayoutManager
//                = new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false);
//        recyclerView.setLayoutManager(horizontalLayoutManager);
//        HomeCategoryAdapter adapter = new HomeCategoryAdapter(getActivity(), categories, HomeFragment.this);
//        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setTodaysDeal(List<Product> products) {
        if (products.size() > 0){
            RecyclerView recyclerView = v.findViewById(R.id.todays_deals);
            GridLayoutManager horizontalLayoutManager
                    = new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(horizontalLayoutManager);
            TodaysDealAdapter adapter = new TodaysDealAdapter(getActivity(), products, this);
            recyclerView.addItemDecoration( new LayoutMarginDecoration( 2,  AppConfig.convertDpToPx(getContext(), 10)) );
            recyclerView.setAdapter(adapter);

            todays_deal_section.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setFlashDeal(FlashDeal flashDeal) {
        if (flashDeal.getProducts().getData().size() > 0){
            flash_deals_text.setText(flashDeal.getTitle());

            mCvCountdownView.start((flashDeal.getEndDate()*1000) - System.currentTimeMillis());

            RecyclerView recyclerView = v.findViewById(R.id.flash_deals);
            GridLayoutManager horizontalLayoutManager
                    = new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(horizontalLayoutManager);
            TodaysDealAdapter adapter = new TodaysDealAdapter(getActivity(), flashDeal.getProducts().getData(), this);
            recyclerView.addItemDecoration( new LayoutMarginDecoration( 2,  AppConfig.convertDpToPx(getContext(), 10)) );
            recyclerView.setAdapter(adapter);

            flash_deal_section.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setBanners(List<Banner> banners) {

        TextSliderView textSliderView1 = new TextSliderView(getContext());
        textSliderView1
                .description("")
                .image(AppConfig.ASSET_URL + banners.get(0).getPhoto())
                .setScaleType(BaseSliderView.ScaleType.Fit);

        TextSliderView textSliderView2 = new TextSliderView(getContext());
        textSliderView2
                .description("")
                .image(AppConfig.ASSET_URL + banners.get(1).getPhoto())
                .setScaleType(BaseSliderView.ScaleType.Fit);

        TextSliderView textSliderView3 = new TextSliderView(getContext());
        textSliderView3
                .description("")
                .image(AppConfig.ASSET_URL + banners.get(2).getPhoto())
                .setScaleType(BaseSliderView.ScaleType.Fit);


        bannerTwo.addSlider(textSliderView1);
        bannerThree.addSlider(textSliderView2);
        bannerFour.addSlider(textSliderView3);


        setBannertProperty(bannerTwo);
        setBannertProperty(bannerThree);
        setBannertProperty(bannerFour);
    }

    @Override
    public void setBestSelling(final List<Product> products) {
        RecyclerView recyclerView = v.findViewById(R.id.best_selling);
        GridLayoutManager horizontalLayoutManager
                = new GridLayoutManager(getActivity(), 1, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        //recyclerView.addItemDecoration(new CirclePagerIndicatorDecoration());
        BestSellingAdapter adapter = new BestSellingAdapter(getActivity(), products, this);
        recyclerView.addItemDecoration( new LayoutMarginDecoration( 1,  AppConfig.convertDpToPx(getContext(), 10)) );
        recyclerView.setAdapter(adapter);

        //gridIndicator.setItemCount((int) Math.ceil((float)products.size() / (float)3));
        //gridIndicator.setCurrentPosition(0);
        //LinearSnapHelper gridLayoutSnapHelper = new LinearSnapHelper();
        //gridLayoutSnapHelper.attachToRecyclerView(recyclerView);

//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                switch (newState) {
//                    case RecyclerView.SCROLL_STATE_IDLE:
//                        int position = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
//                        gridIndicator.setCurrentPosition((int) Math.ceil((position) / 3));
//                        break;
//                }
//            }
//        });
    }

    @Override
    public void setFeaturedProducts(List<Product> products) {
        RecyclerView recyclerView = v.findViewById(R.id.featured_products);
        GridLayoutManager horizontalLayoutManager
                = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        FeaturedProductAdapter adapter = new FeaturedProductAdapter(getActivity(), products, this);
        recyclerView.addItemDecoration( new LayoutMarginDecoration( 2,  AppConfig.convertDpToPx(getContext(), 10)) );
        recyclerView.setAdapter(adapter);
        shakeAnimation(featured_products_text);
    }

    @Override
    public void setTopCategories(List<Category> categories) {
        RecyclerView recyclerView = v.findViewById(R.id.top_categories);
        GridLayoutManager horizontalLayoutManager
                = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        TopCategoryAdapter adapter = new TopCategoryAdapter(getActivity(), categories, HomeFragment.this);
        recyclerView.addItemDecoration( new LayoutMarginDecoration( 3,  AppConfig.convertDpToPx(getContext(), 4)) );
        recyclerView.setAdapter(adapter);

        shimmerLayout.setVisibility(View.GONE);
        rootLayout.setVisibility(View.VISIBLE);

    }

    @Override
    public void setPopularBrands(List<Brand> brands) {
        RecyclerView recyclerView = v.findViewById(R.id.popular_brads);
        GridLayoutManager horizontalLayoutManager
                = new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        BrandAdapter adapter = new BrandAdapter(getActivity(), brands, this);
        recyclerView.addItemDecoration( new LayoutMarginDecoration( 2,  AppConfig.convertDpToPx(getContext(), 10)) );
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void setAuctionProducts(List<AuctionProduct> auctionProducts) {
        mAuctionProducts.clear();
        mAuctionProducts.addAll(auctionProducts);
        if (auctionProducts.size() > 0){
            auction_product_section.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
        }
        else {
            auction_product_section.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //Log.d("SliderPosition", String.valueOf(position));
    }

    @Override
    public void onPageSelected(int position) {
        Log.d("SliderPosition", String.valueOf(position));
        //Glide.with(getContext()).load(AppConfig.ASSET_URL + sliderImages.get(position).getPhoto()).transform(new BlurTransformation(75, 1)).into(imageSliderShadow);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //Log.d("SliderPosition", String.valueOf(state));
    }

    @Override
    public void onCategoryItemClick(Category category) {
        Intent intent = new Intent(getContext(), ProductListingActivity.class);
        intent.putExtra("title", category.getName());
        intent.putExtra("url", category.getLinks().getProducts());
        startActivity(intent);
    }

    @Override
    public void onProductItemClick(Product product) {
        Intent intent = new Intent(getContext(), ProductDetailsActivity.class);
        intent.putExtra("product_name", product.getName());
        intent.putExtra("link", product.getLinks().getDetails());
        intent.putExtra("top_selling", product.getLinks().getRelated());
        startActivity(intent);
    }

    @Override
    public void onBrandItemClick(Brand brand) {
        Intent intent = new Intent(getContext(), ProductListingActivity.class);
        intent.putExtra("title", brand.getName());
        intent.putExtra("url", brand.getLinks().getProducts());
        startActivity(intent);
    }

    @Override
    public void onAuctionItemClick(AuctionProduct auctionProduct) {
        View view = getLayoutInflater().inflate(R.layout.fragment_bottom_sheet_dialog_auction, null);
        ImageView image = view.findViewById(R.id.product_image);
        TextView current_bid_amount = view.findViewById(R.id.current_bid_amount);
        TextView total_bids = view.findViewById(R.id.total_bids);
        TextView name = view.findViewById(R.id.product_name);
        EditText bid_amount = view.findViewById(R.id.bid_amount);
        Button place_bid = view.findViewById(R.id.place_bid);

        Glide.with(getContext()).load(AppConfig.ASSET_URL + auctionProduct.getImage()).into(image);
        current_bid_amount.setText(AppConfig.convertPrice(getContext(), auctionProduct.getCurrentPrice()));
        total_bids.setText(auctionProduct.getBidsCount()+" Bids");
        name.setText(auctionProduct.getName());

        dialog = new BottomSheetDialog(getContext());
        dialog.setContentView(view);
        dialog.show();

        place_bid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double amount = (bid_amount.getText().length() > 0) ? Double.valueOf(bid_amount.getText().toString()) : 0.0;
                if (amount > auctionProduct.getCurrentPrice()){
                    authResponse = new UserPrefs(getActivity()).getAuthPreferenceObjectJson("auth_response");
                    if(authResponse != null && authResponse.getUser() != null){
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("auction_product_id", auctionProduct.getId());
                        jsonObject.addProperty("user_id", authResponse.getUser().getId());
                        jsonObject.addProperty("amount", amount);

                        dialog.hide();
                        progressDialog.setMessage("Your bid is being submitted. Please wait.");
                        progressDialog.show();
                        homePresenter.submitBid(jsonObject, authResponse.getAccessToken());
                    }
                    else {
                        startActivityForResult(new Intent(getActivity(), LoginActivity.class), 100);
                    }
                }
                else {
                    CustomToast.showToast(getActivity(), "Your bidding amount must be greater than the current bid", R.color.colorWarning);
                    bid_amount.requestFocus();
                }
            }
        });
    }

    @Override
    public void onAuctionBidSubmitted(AuctionBidResponse auctionBidResponse) {
        progressDialog.dismiss();
        CustomToast.showToast(getActivity(), auctionBidResponse.getMessage(), R.color.colorSuccess);
        homePresenter.getAuctionProducts();
    }

    @Override
    public void setZerothProduct(ProductListingResponse productListingResponse) {
        RecyclerView recyclerView = v.findViewById(R.id.firt_pro_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        ProductListtingHomeAdapter adapter = new ProductListtingHomeAdapter(getActivity(), productListingResponse.getData(), this);
        recyclerView.setAdapter(adapter);
        shakeAnimation(firstProTxt);
    }

    @Override
    public void setFirstProduct(ProductListingResponse productListingResponse) {
        RecyclerView recyclerView = v.findViewById(R.id.second_pro_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        ProductListtingHomeAdapter adapter = new ProductListtingHomeAdapter(getActivity(), productListingResponse.getData(), this);
        recyclerView.setAdapter(adapter);
        shakeAnimation(secondProTxt);
    }

    @Override
    public void setSecondProduct(ProductListingResponse productListingResponse) {
        RecyclerView recyclerView = v.findViewById(R.id.third_pro_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        ProductListtingHomeAdapter adapter = new ProductListtingHomeAdapter(getActivity(), productListingResponse.getData(), this);
        recyclerView.setAdapter(adapter);
        shakeAnimation(thirdProTxt);
    }

    @Override
    public void setThirdProduct(ProductListingResponse productListingResponse) {
        RecyclerView recyclerView = v.findViewById(R.id.fourth_pro_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        ProductListtingHomeAdapter adapter = new ProductListtingHomeAdapter(getActivity(), productListingResponse.getData(), this);
        recyclerView.setAdapter(adapter);
        shakeAnimation(fourthProTxt);
    }

    @Override
    public void setFourthProduct(ProductListingResponse productListingResponse) {
        RecyclerView recyclerView = v.findViewById(R.id.fifth_pro_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        ProductListtingHomeAdapter adapter = new ProductListtingHomeAdapter(getActivity(), productListingResponse.getData(), this);
        recyclerView.setAdapter(adapter);
        shakeAnimation(fifthProTxt);
    }

    @Override
    public void setFifthProduct(ProductListingResponse productListingResponse) {
        RecyclerView recyclerView = v.findViewById(R.id.sixth_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        ProductListtingHomeAdapter adapter = new ProductListtingHomeAdapter(getActivity(), productListingResponse.getData(), this);
        recyclerView.setAdapter(adapter);
        shakeAnimation(sixthProTxt);
    }

    @Override
    public void setSixthProduct(ProductListingResponse productListingResponse) {
        RecyclerView recyclerView = v.findViewById(R.id.seven_pro_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        ProductListtingHomeAdapter adapter = new ProductListtingHomeAdapter(getActivity(), productListingResponse.getData(), this);
        recyclerView.setAdapter(adapter);
        shakeAnimation(seventhProTxt);
    }

    @Override
    public void setSeventhProduct(ProductListingResponse productListingResponse) {
        RecyclerView recyclerView = v.findViewById(R.id.eigth_pro_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        ProductListtingHomeAdapter adapter = new ProductListtingHomeAdapter(getActivity(), productListingResponse.getData(), this);
        recyclerView.setAdapter(adapter);
        shakeAnimation(seventhProTxt);
    }

    @Override
    public void setEighthProduct(ProductListingResponse productListingResponse) {
        RecyclerView recyclerView = v.findViewById(R.id.ninth_pro_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        ProductListtingHomeAdapter adapter = new ProductListtingHomeAdapter(getActivity(), productListingResponse.getData(), this);
        recyclerView.setAdapter(adapter);
        shakeAnimation(eighthProTxt);

    }

    @Override
    public void setNinthFitness(ProductListingResponse productListingResponse) {
        RecyclerView recyclerView = v.findViewById(R.id.ninth_pro_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        ProductListtingHomeAdapter adapter = new ProductListtingHomeAdapter(getActivity(), productListingResponse.getData(), this);
        recyclerView.setAdapter(adapter);
        shakeAnimation(ninth_pro_txt);

    }

    @Override
    public void setTenthProduct(ProductListingResponse productListingResponse) {
        RecyclerView recyclerView = v.findViewById(R.id.tenth_pro_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        ProductListtingHomeAdapter adapter = new ProductListtingHomeAdapter(getActivity(), productListingResponse.getData(), this);
        recyclerView.setAdapter(adapter);
        shakeAnimation(tenth_pro_txt);
    }

    @Override
    public void setEleventhProduct(ProductListingResponse productListingResponse) {
        RecyclerView recyclerView = v.findViewById(R.id.eleventh_pro_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        ProductListtingHomeAdapter adapter = new ProductListtingHomeAdapter(getActivity(), productListingResponse.getData(), this);
        recyclerView.setAdapter(adapter);
        shakeAnimation(eleventh_pro_txt);
    }

    @Override
    public void setTwelthProduct(ProductListingResponse productListingResponse) {
        RecyclerView recyclerView = v.findViewById(R.id.twelth_pro_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        ProductListtingHomeAdapter adapter = new ProductListtingHomeAdapter(getActivity(), productListingResponse.getData(), this);
        recyclerView.setAdapter(adapter);
        shakeAnimation(twelth_pro_txt);
    }

    @Override
    public void setThirteenProduct(ProductListingResponse productListingResponse) {
        RecyclerView recyclerView = v.findViewById(R.id.thirteen_pro_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        ProductListtingHomeAdapter adapter = new ProductListtingHomeAdapter(getActivity(), productListingResponse.getData(), this);
        recyclerView.setAdapter(adapter);
        shakeAnimation(thirteen_pro_txt);
    }

    @Override
    public void setFourteenProduct(ProductListingResponse productListingResponse) {
        RecyclerView recyclerView = v.findViewById(R.id.fourthteen_pro_txt);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        ProductListtingHomeAdapter adapter = new ProductListtingHomeAdapter(getActivity(), productListingResponse.getData(), this);
        recyclerView.setAdapter(adapter);
        shakeAnimation(fourtheen_pro_txt);
    }

    @Override
    public void getAllPreferData(PreferDataResponse preferDataResponse) {
        shakeAnimation(featured_categories_text);



//        categoriesListPresenter.getCategoriesThirdProduct(categories.get(0));
//        categoriesListPresenter.getCategoriesbFourthProduct(categories.get(0));
//        categoriesListPresenter.getCategoriesFiveProduct(categories.get(0));
//        categoriesListPresenter.getCategoriesSixHomesProduct(categories.get(0));
//        categoriesListPresenter.getCategoriesSevenProduct(categories.get(0));
//        categoriesListPresenter.getCategoriesEightFitnessProduct(categories.get(0));


//        firstProTxt.setText(categories.);
//        secondProTxt.setText((categories.get(1).getName().toUpperCase()));
//        thirdProTxt.setText(categories.get(2).getName().toUpperCase());
//        fourthProTxt.setText(categories.get(3).getName().toUpperCase());
//        fifthProTxt.setText(categories.get(4).getName().toUpperCase());
//        sixthProTxt.setText(categories.get(5).getName().toUpperCase());
//        seventhProTxt.setText(categories.get(6).getName().toUpperCase());
//        eighthProTxt.setText(categories.get(7).getName().toUpperCase());
//        ninth_pro_txt.setText(categories.get(8).getName().toUpperCase());


    }

    @Override
    public void getPreferDataBySubShown(ProductResponse productListingResponse) {
        List<Product> categories = productListingResponse.getData();
        // this part of code will fetch data categoires wise for categories wise card

    }

    @Override
    public void setTopSecondCategories(List<Category> categories) {
//        RecyclerView recyclerView = v.findViewById(R.id.top_sec_categories);
//        GridLayoutManager horizontalLayoutManager
//                = new GridLayoutManager(getActivity(), 2,GridLayoutManager.HORIZONTAL, false);
//        recyclerView.setLayoutManager(horizontalLayoutManager);
//        TopSecondCategoryAdapter adapter = new TopSecondCategoryAdapter(getActivity(), categories, HomeFragment.this);
//        recyclerView.addItemDecoration( new LayoutMarginDecoration( 3,  AppConfig.convertDpToPx(getContext(), 4)) );
//        recyclerView.setAdapter(adapter);
//        shimmerLayout.setVisibility(View.GONE);
//        rootLayout.setVisibility(View.VISIBLE);


//        for (PreferCatModel preference : categories) {
//            homePresenter.getPreferCategoreisByIsShown(preference);
//        }

        categoriesListPresenter = new HomePresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this);

        for(int i=0;i<categories.size();i++){
            if (i == 0){
                firstProTxt.setText(categories.get(i).getName());
                categoriesListPresenter.getCategoriesZerothProduct(categories.get(i).getLinks().getProducts());

            }else if(i == 1){
                secondProTxt.setText(categories.get(i).getName());
                categoriesListPresenter.getCategoriesFirstProduct(categories.get(i).getLinks().getProducts());
            } else if(i == 2){
                thirdProTxt.setText(categories.get(i).getName());
                categoriesListPresenter.getCategoriesSecondProduct(categories.get(i).getLinks().getProducts());
            }
            else if(i == 3){
                fourthProTxt.setText(categories.get(i).getName());
                categoriesListPresenter.getCategoriesThirdProduct(categories.get(i).getLinks().getProducts());
            }
            else if(i == 4){
                fifthProTxt.setText(categories.get(i).getName());
                categoriesListPresenter.getCategoriesbFourthProduct(categories.get(i).getLinks().getProducts());
            }

            else if(i == 5){
                sixthProTxt.setText(categories.get(i).getName());
                categoriesListPresenter.getCategoriesFiveProduct(categories.get(i).getLinks().getProducts());
            }

            else if(i == 6){
                seventhProTxt.setText(categories.get(i).getName());
                categoriesListPresenter.getCategoriesSixHomesProduct(categories.get(i).getLinks().getProducts());
            }

            else if(i == 7){
                eighthProTxt.setText(categories.get(i).getName());
                categoriesListPresenter.getCategoriesSevenProduct(categories.get(i).getLinks().getProducts());
            }

            else if(i == 8){
                eighthProTxt.setText(categories.get(i).getName());

                categoriesListPresenter.getCategoriesEightFitnessProduct(categories.get(i).getLinks().getProducts());
            }

            else if(i == 9){
                ninth_pro_txt.setText(categories.get(i).getName());
                categoriesListPresenter.getCategoriesNighthProduct(categories.get(i).getLinks().getProducts());
            }

            else if(i == 10){
                tenth_pro_txt.setText(categories.get(i).getName());
                categoriesListPresenter.getCategoriesTenthProduct(categories.get(i).getLinks().getProducts());
            }

            else if(i == 11){
                eleventh_pro_txt.setText(categories.get(i).getName());
                categoriesListPresenter.getCategoriesEleventhProduct(categories.get(i).getLinks().getProducts());
            }

            else if(i == 12){
                twelth_pro_txt.setText(categories.get(i).getName());
                categoriesListPresenter.getCategoriesTwelthProduct(categories.get(i).getLinks().getProducts());
            }

            else if(i == 13){
                thirteen_pro_txt.setText(categories.get(i).getName());
                categoriesListPresenter.getCategoriesThirteenProduct(categories.get(i).getLinks().getProducts());
            }

            else if(i == 14){
                fourtheen_pro_txt.setText(categories.get(i).getName());
                categoriesListPresenter.getCategoriesFourteenProduct(categories.get(i).getLinks().getProducts());
            }
        }
    }

    @Override
    public void setCartItems(List<CartModel> cartItems) {
        if (cartItems != null){
            ((DrawerActivityNew) requireActivity()).setCartCountListener.setCartCount(cartItems.size());
        }
    }

    @Override
    public void showRemoveCartMessage(RemoveCartResponse removeCartResponse) {

    }

    @Override
    public void showCartQuantityUpdateMessage(CartQuantityUpdateResponse cartQuantityUpdateResponse) {

    }

    public void setBannertProperty( SliderLayout banner){
        bannerOne.setPresetTransformer(SliderLayout.Transformer.Default);
        bannerOne.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        bannerOne.addOnPageChangeListener(this);
        bannerOne.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RECOGNIZER_REQ_CODE) {

            if (data != null){
                String yourResult = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0);
                Intent intent = new Intent(requireActivity(), ProductSearchActivity.class);
                intent.putExtra("voice_str", yourResult);
                startActivity(intent);
            }
        } if (requestCode == RECOGNIZER_CHECK_CODE){
            if(resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS){
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                startActivityForResult(intent, RECOGNIZER_REQ_CODE);
            } else {
                Toast.makeText(requireActivity(), "You did not have TTS installed !!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void titleAnimator(TextView textView){
        Integer colorFrom = getResources().getColor(R.color.carbon_red_800);
        Integer colorTo = getResources().getColor(R.color.carbon_white);
//
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                textView.setTextColor((Integer)animator.getAnimatedValue());
            }

        });
        colorAnimation.setDuration(1200);
        colorAnimation.setRepeatMode(ValueAnimator.REVERSE);
        colorAnimation.setRepeatCount(ValueAnimator.INFINITE);
        colorAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        colorAnimation.start();
    }

    public void shakeAnimation(TextView textView){
        ObjectAnimator
                .ofFloat(textView, "translationX", -1000, 16)
                .setDuration(1000)
                .start();
    }

    public void textMarqueeAnim(TextView textView){
        if(textView.getText().length() > 30){
            Animation anim = AnimationUtils.loadAnimation(requireActivity(), R.anim.marquee_anim);
            textView.startAnimation(anim);
        }
    }
}