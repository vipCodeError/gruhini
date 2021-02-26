package com.activeitzone.activeecommercecms.Presentation.ui.fragments.impl;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.activeitzone.activeecommercecms.Models.AuctionProduct;
import com.activeitzone.activeecommercecms.Models.Banner;
import com.activeitzone.activeecommercecms.Models.Brand;
import com.activeitzone.activeecommercecms.Models.CartModel;
import com.activeitzone.activeecommercecms.Models.Category;
import com.activeitzone.activeecommercecms.Models.FlashDeal;
import com.activeitzone.activeecommercecms.Models.Product;
import com.activeitzone.activeecommercecms.Models.SliderImage;
import com.activeitzone.activeecommercecms.Models.SubCategory;
import com.activeitzone.activeecommercecms.Network.response.AppSettingsResponse;
import com.activeitzone.activeecommercecms.Network.response.AuctionBidResponse;
import com.activeitzone.activeecommercecms.Network.response.AuthResponse;
import com.activeitzone.activeecommercecms.Network.response.CartQuantityUpdateResponse;
import com.activeitzone.activeecommercecms.Network.response.ProductListingResponse;
import com.activeitzone.activeecommercecms.Network.response.RemoveCartResponse;
import com.activeitzone.activeecommercecms.Presentation.presenters.CartPresenter;
import com.activeitzone.activeecommercecms.Presentation.presenters.HomePresenter;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.ProductListingView;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.SubCategoryView;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.impl.DrawerActivityNew;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.impl.LoginActivity;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.impl.MainActivity;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.impl.ProductDetailsActivity;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.impl.ProductListingActivity;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.impl.ui.ProductSearchActivity;
import com.activeitzone.activeecommercecms.Presentation.ui.adapters.AuctionProductAdapter;
import com.activeitzone.activeecommercecms.Presentation.ui.adapters.BestSellingAdapter;
import com.activeitzone.activeecommercecms.Presentation.ui.adapters.BrandAdapter;
import com.activeitzone.activeecommercecms.Presentation.ui.adapters.FeaturedProductAdapter;
import com.activeitzone.activeecommercecms.Presentation.ui.adapters.ProductListingAdapter;
import com.activeitzone.activeecommercecms.Presentation.ui.adapters.ProductListtingHomeAdapter;
import com.activeitzone.activeecommercecms.Presentation.ui.adapters.TodaysDealAdapter;
import com.activeitzone.activeecommercecms.Presentation.ui.adapters.TopCategoryAdapter;
import com.activeitzone.activeecommercecms.Presentation.ui.fragments.CartView;
import com.activeitzone.activeecommercecms.Presentation.ui.fragments.HomeView;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.AuctionClickListener;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.BrandClickListener;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.CategoryClickListener;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.ProductClickListener;
import com.activeitzone.activeecommercecms.R;
import com.activeitzone.activeecommercecms.Threading.MainThreadImpl;
import com.activeitzone.activeecommercecms.Utils.AppConfig;
import com.activeitzone.activeecommercecms.Utils.CustomToast;
import com.activeitzone.activeecommercecms.Utils.UserPrefs;
import com.activeitzone.activeecommercecms.domain.executor.impl.ThreadExecutor;
import com.bumptech.glide.Glide;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.JsonObject;
import com.kingfisher.easyviewindicator.AnyViewIndicator;
import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cn.iwgang.countdownview.CountdownView;

public class HomeFragment extends Fragment implements CartView, HomeView, CategoryClickListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener, ProductClickListener, BrandClickListener, AuctionClickListener {

    private static final int RECOGNIZER_REQ_CODE = 1234;
    private static final int RECOGNIZER_CHECK_CODE = 2345;
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
    private TextView flash_deals_text;
    private CountdownView mCvCountdownView;
    private ImageView micSearch;
    RelativeLayout searchLayout;

    private CartPresenter cartPresenter;

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
        homePresenter.getBanners();


        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().startActivity(new Intent(requireActivity(), ProductSearchActivity.class));
            }
        });
        authResponse = new UserPrefs(requireActivity()).getAuthPreferenceObjectJson("auth_response");
        cartPresenter = new CartPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this);

        if(authResponse != null && authResponse.getUser() != null){
            cartPresenter.getCartItems(authResponse.getUser().getId(), authResponse.getAccessToken());
        }
        else {

        }

        micSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
                startActivityForResult(intent, RECOGNIZER_CHECK_CODE);
            }
        });

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
                    //.image(AppConfig.ASSET_URL + sliderImage.getPhoto())
                    .image("https://image.freepik.com/free-vector/pomegranate-cosmetic-bottles-beauty-banner_33099-2264.jpg")
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            bannerOne.addSlider(textSliderView);
            bannerTwo.addSlider(textSliderView);
            bannerThree.addSlider(textSliderView);
            bannerFour.addSlider(textSliderView);
            //Log.d("Sliders", AppConfig.ASSET_URL + sliderImage.getPhoto());
        }

        setBannertProperty(bannerOne);
        setBannertProperty(bannerTwo);
        setBannertProperty(bannerThree);
        setBannertProperty(bannerFour);

        // Glide.with(getContext()).load(AppConfig.ASSET_URL + sliderImages.get(0).getPhoto()).transform(new BlurTransformation(75, 1)).into(imageSliderShadow);
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
                = new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        FeaturedProductAdapter adapter = new FeaturedProductAdapter(getActivity(), products, this);
        recyclerView.addItemDecoration( new LayoutMarginDecoration( 2,  AppConfig.convertDpToPx(getContext(), 10)) );
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setTopCategories(List<Category> categories) {
        RecyclerView recyclerView = v.findViewById(R.id.top_categories);
        GridLayoutManager horizontalLayoutManager
                = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        TopCategoryAdapter adapter = new TopCategoryAdapter(getActivity(), categories, HomeFragment.this);
        recyclerView.addItemDecoration( new LayoutMarginDecoration( 1,  AppConfig.convertDpToPx(getContext(), 10)) );
        recyclerView.setAdapter(adapter);

        categoriesListPresenter = new HomePresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this);
        categoriesListPresenter.getCategoriesWiseManProduct(categories.get(5).getLinks().getProducts());
        categoriesListPresenter.getCategoriesWiseWomanProduct(categories.get(10).getLinks().getProducts());
        categoriesListPresenter.getCategoriesBeautyProduct(categories.get(1).getLinks().getProducts());
        categoriesListPresenter.getCategoriesBooksProduct(categories.get(2).getLinks().getProducts());
        categoriesListPresenter.getCategoriesBabyProduct(categories.get(8).getLinks().getProducts());
        categoriesListPresenter.getCategoriesbBagLuggageProduct(categories.get(0).getLinks().getProducts());
        categoriesListPresenter.getCategoriesElectronicsProduct(categories.get(9).getLinks().getProducts());
        categoriesListPresenter.getCategoriesMobilePcProduct(categories.get(6).getLinks().getProducts());
        categoriesListPresenter.getCategoriesKitchenHomesProduct(categories.get(4).getLinks().getProducts());
        categoriesListPresenter.getCategoriesSportsFitnessProduct(categories.get(7).getLinks().getProducts());


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
    public void setMensWearProduct(ProductListingResponse productListingResponse) {
        RecyclerView recyclerView = v.findViewById(R.id.mens_product_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        ProductListtingHomeAdapter adapter = new ProductListtingHomeAdapter(getActivity(), productListingResponse.getData(), this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setWomansWearProduct(ProductListingResponse productListingResponse) {
        RecyclerView recyclerView = v.findViewById(R.id.woman_product_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        ProductListtingHomeAdapter adapter = new ProductListtingHomeAdapter(getActivity(), productListingResponse.getData(), this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setElectronicsProduct(ProductListingResponse productListingResponse) {
        RecyclerView recyclerView = v.findViewById(R.id.mobile_pc_product_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        ProductListtingHomeAdapter adapter = new ProductListtingHomeAdapter(getActivity(), productListingResponse.getData(), this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setBeautyProduct(ProductListingResponse productListingResponse) {
        RecyclerView recyclerView = v.findViewById(R.id.beauty_product_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        ProductListtingHomeAdapter adapter = new ProductListtingHomeAdapter(getActivity(), productListingResponse.getData(), this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setBagLuggageProduct(ProductListingResponse productListingResponse) {
        RecyclerView recyclerView = v.findViewById(R.id.bag_luggage_product_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        ProductListtingHomeAdapter adapter = new ProductListtingHomeAdapter(getActivity(), productListingResponse.getData(), this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setBooksProduct(ProductListingResponse productListingResponse) {
        RecyclerView recyclerView = v.findViewById(R.id.kticehn_product_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        ProductListtingHomeAdapter adapter = new ProductListtingHomeAdapter(getActivity(), productListingResponse.getData(), this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setKitchenHomeAppliance(ProductListingResponse productListingResponse) {
        RecyclerView recyclerView = v.findViewById(R.id.kticehn_product_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        ProductListtingHomeAdapter adapter = new ProductListtingHomeAdapter(getActivity(), productListingResponse.getData(), this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setMobilePcProduct(ProductListingResponse productListingResponse) {
        RecyclerView recyclerView = v.findViewById(R.id.mobile_pc_product_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        ProductListtingHomeAdapter adapter = new ProductListtingHomeAdapter(getActivity(), productListingResponse.getData(), this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setSportsFitness(ProductListingResponse productListingResponse) {
        RecyclerView recyclerView = v.findViewById(R.id.sports_and_fitness_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        ProductListtingHomeAdapter adapter = new ProductListtingHomeAdapter(getActivity(), productListingResponse.getData(), this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setBabyProduct(ProductListingResponse productListingResponse) {
        RecyclerView recyclerView = v.findViewById(R.id.baby_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        ProductListtingHomeAdapter adapter = new ProductListtingHomeAdapter(getActivity(), productListingResponse.getData(), this);
        recyclerView.setAdapter(adapter);
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
}