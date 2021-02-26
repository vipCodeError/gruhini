package com.activeitzone.activeecommercecms.Presentation.ui.activities.impl;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeitzone.activeecommercecms.Models.CartModel;
import com.activeitzone.activeecommercecms.Network.response.AppSettingsResponse;
import com.activeitzone.activeecommercecms.Network.response.AuthResponse;
import com.activeitzone.activeecommercecms.Network.response.CartQuantityUpdateResponse;
import com.activeitzone.activeecommercecms.Network.response.LogoutResponse;
import com.activeitzone.activeecommercecms.Network.response.RemoveCartResponse;
import com.activeitzone.activeecommercecms.Presentation.presenters.CartPresenter;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.CartCountListener;
import com.activeitzone.activeecommercecms.Presentation.ui.fragments.AccountView;
import com.activeitzone.activeecommercecms.Presentation.ui.fragments.CartView;
import com.activeitzone.activeecommercecms.Presentation.ui.fragments.impl.About_UsFragment;
import com.activeitzone.activeecommercecms.Presentation.ui.fragments.impl.AccountFragment;
import com.activeitzone.activeecommercecms.Presentation.ui.fragments.impl.CartFragment;
import com.activeitzone.activeecommercecms.Presentation.ui.fragments.impl.CategoriesFragment;
import com.activeitzone.activeecommercecms.Presentation.ui.fragments.impl.Contact_usFragment;
import com.activeitzone.activeecommercecms.Presentation.ui.fragments.impl.HomeFragment;
import com.activeitzone.activeecommercecms.Presentation.ui.fragments.impl.LogoutFragment;
import com.activeitzone.activeecommercecms.Presentation.ui.fragments.impl.PurchaseHistoryFragment;
import com.activeitzone.activeecommercecms.Presentation.ui.fragments.impl.WalletFragment;
import com.activeitzone.activeecommercecms.Presentation.ui.fragments.impl.WishlistFragment;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.CartItemListener;
import com.activeitzone.activeecommercecms.R;
import com.activeitzone.activeecommercecms.Threading.MainThreadImpl;
import com.activeitzone.activeecommercecms.Utils.CustomToast;
import com.activeitzone.activeecommercecms.Utils.UserPrefs;
import com.activeitzone.activeecommercecms.domain.executor.impl.ThreadExecutor;
import com.activeitzone.activeecommercecms.domain.interactors.AppSettingsInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.impl.AppSettingsInteractorImpl;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class DrawerActivityNew extends AppCompatActivity implements AccountView, AppSettingsInteractor.CallBack, CartView {

    private Fragment homeFragment = new HomeFragment();
    private Fragment categoriesFragment = new CategoriesFragment();
    private Fragment cartFragment = new CartFragment();
    private Fragment accountFragment = new AccountFragment();
    private Fragment wishListFragment = new WishlistFragment();
    private Fragment purchaseHistoryFragment = new PurchaseHistoryFragment();
    private Fragment walletFragment = new WalletFragment();
    private Fragment contact_usfragment = new Contact_usFragment();
    private Fragment about_usfragment = new About_UsFragment();
    private Fragment logoutfragment = new LogoutFragment();

    final FragmentManager fm = getSupportFragmentManager();
    private Fragment active = homeFragment;
    private DrawerLayout drawerLayout;
    private TextView title, cartCount;
    ImageView preferenceBtn;

    private AuthResponse authResponse;
    private CartPresenter cartPresenter;

    private AppBarConfiguration mAppBarConfiguration;
    int logoutCounter = 0;

    List<CartModel> cartModelList;

    public CartCountListener setCartCountListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_new);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        if (getIntent().getStringExtra("position") != null){
            if (getIntent().getStringExtra("position").equals("cart")){
                loadCartFragment();
            }else {
                // load home fragment
                loadHomeFragment();
            }
        }else {
            loadHomeFragment();
        }

        cartModelList = new ArrayList<>();

        // user logged response
        authResponse = new UserPrefs(this).getAuthPreferenceObjectJson("auth_response");

        NavigationView navigationView = findViewById(R.id.nav_view);
        NavigationView navigationLogout= findViewById(R.id.logout_navigation);

        drawerLayout = findViewById(R.id.new_drawer_layout);
        Toolbar toolbar = findViewById(R.id.nav_title);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // set custom view
        getSupportActionBar().setCustomView(R.layout.custom_appbar_new);

        //custom actionbar view access
        ImageButton imgBtn = getSupportActionBar().getCustomView().findViewById(R.id.drawer_icons);
        title = getSupportActionBar().getCustomView().findViewById(R.id.nav_title);
        cartCount = getSupportActionBar().getCustomView().findViewById(R.id.cart_count);
        ImageView cartBtn = getSupportActionBar().getCustomView().findViewById(R.id.action_bar_cart);

        imgBtn.setOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                drawerLayout.closeDrawer(GravityCompat.END, true);
            }else {
                drawerLayout.openDrawer(GravityCompat.START, true);
            }
        });

        cartPresenter = new CartPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this);

        if(authResponse != null && authResponse.getUser() != null){
            cartPresenter.getCartItems(authResponse.getUser().getId(), authResponse.getAccessToken());
        }
        else {

        }

        setCartCountListener = count -> cartCount.setText(count + "");

        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   navView.setSelectedItemId(R.id.nav_user_cart);
                loadFragment(cartFragment);
               // doIncrease();

            }
        });


        preferenceBtn = navigationView.getHeaderView(0).findViewById(R.id.preference);
        preferenceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DrawerActivityNew.this, AccountInfoActivity.class));
            }
        });

        TextView welcomeTxt = navigationView.getHeaderView(0).findViewById(R.id.user_welcome);
        TextView loginTxt = navigationView.getHeaderView(0).findViewById(R.id.login_txt);

        if(authResponse == null){
            loginTxt.setVisibility(View.VISIBLE);
            navigationLogout.setVisibility(View.GONE);
            welcomeTxt.setVisibility(View.GONE);
            navigationView.getHeaderView(0).findViewById(R.id.imageView).setVisibility(View.VISIBLE);
            preferenceBtn.setVisibility(View.GONE);
        }else{
            loginTxt.setVisibility(View.GONE);
            navigationLogout.setVisibility(View.VISIBLE);
            navigationView.getHeaderView(0).findViewById(R.id.imageView).setVisibility(View.GONE);
            welcomeTxt.setVisibility(View.VISIBLE);
            welcomeTxt.setText("Hey " + authResponse.getUser().getName());
            preferenceBtn.setVisibility(View.VISIBLE);
        }

        if (getUserResponse() == null){
            navigationLogout.setVisibility(View.GONE);

        }else {
            navigationLogout.setVisibility(View.VISIBLE);
        }

        navigationView.getHeaderView(0).findViewById(R.id.login_txt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DrawerActivityNew.this, LoginActivity.class));
            }
        });
        navigationView.setItemIconTintList(null);

        //logout navigation
        navigationLogout.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.logout_user){
                    AlertDialog.Builder logoutDialog = new AlertDialog.Builder(DrawerActivityNew.this);
                    logoutDialog.setTitle("Message");
                    logoutDialog.setMessage("Do you want to logout ?");
                    logoutDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(authResponse != null && authResponse.getUser() != null){
                                new UserPrefs(DrawerActivityNew.this).clearPreference();

                                new AppSettingsInteractorImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), DrawerActivityNew.this).execute();
                                finish();
                                startActivity(DrawerActivityNew.this.getIntent());
                            }
                        }
                    });

                    logoutDialog.setNegativeButton("later", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    logoutDialog.show();
                }
                return true;
            }
        });

        // navigation selection item
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_home){
                    title.setText("Shopy Culture");
                    loadFragment(homeFragment);
                }else if(item.getItemId() == R.id.nav_categories){
                     title.setText("Categories");
                    loadFragment(categoriesFragment);
                }
                else if(item.getItemId() == R.id.nav_user_cart){
                   title.setText("cart");
                    loadFragment(cartFragment);
                }
//                else if(item.getItemId() == R.id.nav_account){
//                    loadFragment(accountFragment);
//                }

                else if(item.getItemId() == R.id.wishlist){
                    title.setText("Wishlist");
                    loadFragment(wishListFragment);
                }

                else if(item.getItemId() == R.id.purchase_history){
                    title.setText("Purchase History");
                    loadFragment(purchaseHistoryFragment);
                }
//                else if(item.getItemId() == R.id.my_wallet){
//                    title.setText("My Wallet");
//                    loadFragment(walletFragment);
//                }
                else if(item.getItemId() == R.id.about_us){
                    title.setText("About Us");
                    loadFragment(about_usfragment);
                } else if(item.getItemId() == R.id.contact_us){
                    title.setText("Contact Us");
                    loadFragment(contact_usfragment);
                }


                return true;
            }
        });

       // NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
       // NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
       // NavigationUI.setupWithNavController(navigationView, navController);
    }


    @Override
    public boolean onSupportNavigateUp() {

        return true;
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            if(fragment == homeFragment){
                closeDrawer();
                homeFragment = new HomeFragment();
                fm.beginTransaction().remove(fragment).commitAllowingStateLoss();
                fm.beginTransaction().add(R.id.nav_host_fragment, homeFragment, "cart").hide(homeFragment).commitAllowingStateLoss();
                fm.beginTransaction().hide(active).show(homeFragment).commitAllowingStateLoss();
                active = homeFragment;
            }

            else if(fragment == categoriesFragment){
                closeDrawer();
                categoriesFragment = new CategoriesFragment();
                fm.beginTransaction().remove(fragment).commitAllowingStateLoss();
                fm.beginTransaction().add(R.id.nav_host_fragment, categoriesFragment, "categories").hide(categoriesFragment).commitAllowingStateLoss();
                fm.beginTransaction().hide(active).show(categoriesFragment).commitAllowingStateLoss();
                active = categoriesFragment;
            }

            else if(fragment == cartFragment){
                  closeDrawer();
                cartFragment = new CartFragment();
                fm.beginTransaction().remove(fragment).commitAllowingStateLoss();
                fm.beginTransaction().add(R.id.nav_host_fragment, cartFragment, "cart").hide(cartFragment).commitAllowingStateLoss();
                fm.beginTransaction().hide(active).show(cartFragment).commitAllowingStateLoss();
                active = cartFragment;

            }
            else if (fragment == accountFragment){
                  closeDrawer();
                accountFragment = new AccountFragment();
                fm.beginTransaction().remove(fragment).commitAllowingStateLoss();
                fm.beginTransaction().add(R.id.nav_host_fragment, accountFragment, "account").hide(accountFragment).commitAllowingStateLoss();
                fm.beginTransaction().hide(active).show(accountFragment).commitAllowingStateLoss();
                active = accountFragment;
            } else if (fragment == wishListFragment){
                closeDrawer();
                if(authResponse != null && authResponse.getUser() != null){
                    wishListFragment = new WishlistFragment();
                    fm.beginTransaction().remove(fragment).commitAllowingStateLoss();
                    fm.beginTransaction().add(R.id.nav_host_fragment, wishListFragment, "Wishlist").hide(wishListFragment).commitAllowingStateLoss();
                    fm.beginTransaction().hide(active).show(wishListFragment).commitAllowingStateLoss();
                    active = wishListFragment;
                }
                else {
                    startActivityForResult(new Intent(DrawerActivityNew.this, LoginActivity.class), 100);
                }

            }else if (fragment == purchaseHistoryFragment){
                closeDrawer();
                if(authResponse != null && authResponse.getUser() != null){
                    purchaseHistoryFragment = new PurchaseHistoryFragment();
                    fm.beginTransaction().remove(fragment).commitAllowingStateLoss();
                    fm.beginTransaction().add(R.id.nav_host_fragment, purchaseHistoryFragment, "PurchaseHistory").hide(purchaseHistoryFragment).commitAllowingStateLoss();
                    fm.beginTransaction().hide(active).show(purchaseHistoryFragment).commitAllowingStateLoss();
                    active = purchaseHistoryFragment;
                }
                else {
                    startActivityForResult(new Intent(DrawerActivityNew.this, LoginActivity.class), 100);
                }


            }
            else if (fragment == walletFragment){
                closeDrawer();
                walletFragment = new WalletFragment();
                fm.beginTransaction().remove(fragment).commitAllowingStateLoss();
                fm.beginTransaction().add(R.id.nav_host_fragment, walletFragment, "Wallet").hide(walletFragment).commitAllowingStateLoss();
                fm.beginTransaction().hide(active).show(walletFragment).commitAllowingStateLoss();
                active = walletFragment;

            } else if (fragment == contact_usfragment){
                closeDrawer();
                contact_usfragment= new Contact_usFragment();
                fm.beginTransaction().remove(fragment).commitAllowingStateLoss();
                fm.beginTransaction().add(R.id.nav_host_fragment, contact_usfragment, "Contact_us").hide(contact_usfragment).commitAllowingStateLoss();
                fm.beginTransaction().hide(active).show(contact_usfragment).commitAllowingStateLoss();
                active = contact_usfragment;

            }else if (fragment == about_usfragment){
                closeDrawer();
                about_usfragment= new About_UsFragment();
                fm.beginTransaction().remove(fragment).commitAllowingStateLoss();
                fm.beginTransaction().add(R.id.nav_host_fragment, about_usfragment, "Contact_us").hide(about_usfragment).commitAllowingStateLoss();
                fm.beginTransaction().hide(active).show(about_usfragment).commitAllowingStateLoss();
                active = about_usfragment;
            }else if (fragment == logoutfragment){
                closeDrawer();
                logoutfragment= new LogoutFragment();
                fm.beginTransaction().remove(fragment).commitAllowingStateLoss();
                fm.beginTransaction().add(R.id.nav_host_fragment, logoutfragment, "Contact_us").hide(logoutfragment).commitAllowingStateLoss();
                fm.beginTransaction().hide(active).show(logoutfragment).commitAllowingStateLoss();
                active = logoutfragment;

            }
            else{
                fm.beginTransaction().hide(active).show(fragment).commit();
                active = fragment;
            }
        }

        return false;
    }

    @Override
    public void onAppSettingsLoaded(AppSettingsResponse appSettingsResponse) {
        UserPrefs userPrefs = new UserPrefs(this);
        userPrefs.setAppSettingsPreferenceObject(appSettingsResponse, "app_settings_response");

        accountFragment = new AccountFragment();
        fm.beginTransaction().remove(accountFragment).commitAllowingStateLoss();
        fm.beginTransaction().add(R.id.nav_host_fragment, accountFragment, "account").hide(accountFragment).commitAllowingStateLoss();
        fm.beginTransaction().hide(active).show(accountFragment).commitAllowingStateLoss();
        active = accountFragment;
    }

    @Override
    public void onAppSettingsLoadedError() {

    }

    public void loadHomeFragment(){
        homeFragment = new HomeFragment();
        fm.beginTransaction().add(R.id.nav_host_fragment, homeFragment, "home").hide(cartFragment).commitAllowingStateLoss();
        active = homeFragment;
    }
    
    public void loadCartFragment(){
        fm.beginTransaction().add(R.id.nav_host_fragment, cartFragment, "cart").hide(homeFragment).commitAllowingStateLoss();
        active = cartFragment;
    }

    public void closeDrawer(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawers();
        }
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();


        if (logoutCounter == 0) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                drawerLayout.closeDrawers();
            }else {
                drawerLayout.openDrawer(GravityCompat.START, true);
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    logoutCounter = 0;
                }
            }).start();

        } else if (logoutCounter == 1) {
            Toast.makeText(DrawerActivityNew.this, "Please press back to exit !!!", Toast.LENGTH_SHORT).show();

        } else  if(logoutCounter == 2){
            finish();
        }


        logoutCounter++;
    }

    public AuthResponse getUserResponse(){
        UserPrefs userPrefs = new UserPrefs(getApplicationContext());
        AuthResponse jDataa = userPrefs.getAuthPreferenceObjectJson("auth_response");
        return jDataa;
    }


    @Override
    public void setCartItems(List<CartModel> cartItems) {
        if (cartItems.size() > 0){
            cartModelList.addAll(cartItems);

//            for (CartModel  cartModel : cartModelList){
//                ttlQty += cartModel.getQuantity();
//            }

            setCartCountListener.setCartCount(cartModelList.size());

        }
        else {
           //  cart_empty_text.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showRemoveCartMessage(RemoveCartResponse removeCartResponse) {

    }

    @Override
    public void showCartQuantityUpdateMessage(CartQuantityUpdateResponse cartQuantityUpdateResponse) {

    }

    @Override
    public void showLogoutMessage(LogoutResponse logoutResponse) {
        CustomToast.showToast(this, logoutResponse.getMessage(), R.color.colorInfo);
        new UserPrefs(this).clearPreference();
    }
}