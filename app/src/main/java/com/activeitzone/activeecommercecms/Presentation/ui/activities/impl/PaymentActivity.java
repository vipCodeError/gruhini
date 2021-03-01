package com.activeitzone.activeecommercecms.Presentation.ui.activities.impl;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.activeitzone.activeecommercecms.Models.PaymentModel;
import com.activeitzone.activeecommercecms.Network.response.AuthResponse;
import com.activeitzone.activeecommercecms.Network.response.CouponResponse;
import com.activeitzone.activeecommercecms.Network.response.OrderResponse;
import com.activeitzone.activeecommercecms.Presentation.presenters.PaymentPresenter;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.CodOrderView;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.PaymentView;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.RazorPayView;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.impl.ui.OrderPlacedMessage;
import com.activeitzone.activeecommercecms.Presentation.ui.activities.impl.ui.RazorPayActivity;
import com.activeitzone.activeecommercecms.Presentation.ui.adapters.PaymentSelectAdapter;
import com.activeitzone.activeecommercecms.Presentation.ui.listeners.PaymentSelectListener;
import com.activeitzone.activeecommercecms.R;
import com.activeitzone.activeecommercecms.Threading.MainThreadImpl;
import com.activeitzone.activeecommercecms.Utils.AppConfig;
import com.activeitzone.activeecommercecms.Utils.CustomToast;
import com.activeitzone.activeecommercecms.Utils.UserPrefs;
import com.activeitzone.activeecommercecms.domain.executor.impl.ThreadExecutor;
import com.activeitzone.activeecommercecms.domain.interactors.CODInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.RazorOrderInteractor;
import com.activeitzone.activeecommercecms.domain.interactors.impl.CODInteractorImpl;
import com.activeitzone.activeecommercecms.domain.interactors.impl.RazorPayOrderDataImpl;
import com.braintreepayments.api.BraintreeFragment;
import com.braintreepayments.api.PayPal;
import com.braintreepayments.api.exceptions.BraintreeError;
import com.braintreepayments.api.exceptions.ErrorWithResponse;
import com.braintreepayments.api.exceptions.InvalidArgumentException;
import com.braintreepayments.api.interfaces.BraintreeErrorListener;
import com.braintreepayments.api.interfaces.PaymentMethodNonceCreatedListener;
import com.braintreepayments.api.models.PayPalRequest;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.google.gson.JsonObject;
import com.razorpay.Checkout;
import com.razorpay.Order;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultListener;
import com.razorpay.PaymentResultWithDataListener;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PaymentActivity extends BaseActivity implements CodOrderView, RazorPayView, PaymentResultWithDataListener,PaymentSelectListener , PaymentView, PaymentMethodNonceCreatedListener, BraintreeErrorListener {
    private String payment_method;
    private Button place_order;
    private BraintreeFragment mBraintreeFragment;
    private EditText coupon_code;
    private Button apply_coupon;
    private Double total, shipping, tax, coupon_discount = 0.0;
    private String shipping_address;
    private TextView total_amount;
    private ProgressDialog progressDialog;
    private AuthResponse authResponse;
    private Toolbar mToolBar;
    JsonObject jsonObject = new JsonObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Checkout.preload(getApplicationContext());

        total = getIntent().getDoubleExtra("total", 0.0);
        shipping = getIntent().getDoubleExtra("shipping", 0.0);
        tax = getIntent().getDoubleExtra("tax", 0.0);
        shipping_address = getIntent().getStringExtra("shipping_address");

        authResponse = new UserPrefs(this).getAuthPreferenceObjectJson("auth_response");

        mToolBar = findViewById(R.id.toolbar);

        setSupportActionBar(mToolBar);
        mToolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);


        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getSupportActionBar().setTitle("Checkout");
        setPaymentOptions();

        progressDialog = new ProgressDialog(this);
        place_order = findViewById(R.id.place_order);
        coupon_code = findViewById(R.id.coupon_code);
        apply_coupon = findViewById(R.id.apply_coupon);
        total_amount = findViewById(R.id.total_amount);

        total_amount.setText(AppConfig.convertPrice(this, total));

        place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(payment_method != null){
                    if (payment_method.equals("RazorPay")){
                        JsonObject jsonCreateOrder = new JsonObject();
                        jsonCreateOrder.addProperty("amount", 1);
                        new PaymentPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), (RazorPayView) PaymentActivity.this).razorPayOrderId(authResponse.getAccessToken(), jsonCreateOrder);
                    }

                    else if (payment_method.equals("card")){
                        Intent intent = new Intent(PaymentActivity.this, StripePaymentActivity.class);
                        intent.putExtra("total", total);
                        intent.putExtra("shipping", shipping);
                        intent.putExtra("tax", tax);
                        intent.putExtra("coupon_discount", coupon_discount);
                        startActivityForResult(intent, 500);
                    }

                    else if(payment_method.equals("cod")){

                        AlertDialog.Builder confirmCodeDialog = new AlertDialog.Builder(PaymentActivity.this);
                        confirmCodeDialog.setTitle("COD Confirm Message");
                        confirmCodeDialog.setMessage("Do you want confirm placed your order ?");
                        confirmCodeDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                progressDialog.show();
                                checkout_done(null);
                            }
                        });
                        confirmCodeDialog.setNeutralButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        confirmCodeDialog.show();

                    }
                    else if(payment_method.equals("wallet")){
                        checkout_done(null);
                    }
                }
                else {
                    CustomToast.showToast(PaymentActivity.this, "Please select a payment method", R.color.colorWarning);
                }
            }
        });

        apply_coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = coupon_code.getText().toString();
                if(code.length() > 0){
                    AuthResponse authResponse = new UserPrefs(PaymentActivity.this).getAuthPreferenceObjectJson("auth_response");
                    if(authResponse != null && authResponse.getUser() != null){
                        new PaymentPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), (PaymentView) PaymentActivity.this).applyCoupon(authResponse.getUser().getId(), code, authResponse.getAccessToken());
                    }
                    else {

                    }
                }
                else {
                    CustomToast.showToast(PaymentActivity.this, "Please enter coupon code first", R.color.colorWarning);
                }
            }
        });
    }

    private void setPaymentOptions(){
        List<PaymentModel> paymentModels = new ArrayList<>();

        paymentModels.add(new PaymentModel(R.drawable.ic_razorpay_glyph, "RazorPay", "Checkout with RazorPay"));
        paymentModels.add(new PaymentModel(R.drawable.cardpayment, "card", "Checkout with Card"));
        paymentModels.add(new PaymentModel(R.drawable.cod, "cod", "Cash on Delivery"));

        RecyclerView recyclerView = findViewById(R.id.payment_select_list);
        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        PaymentSelectAdapter adapter = new PaymentSelectAdapter(this, paymentModels, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onPaymentMethodNonceCreated(PaymentMethodNonce paymentMethodNonce) {
        // Send nonce to server
        String nonce = paymentMethodNonce.getNonce();
        checkout_done(nonce);
    }

    @Override
    public void onError(Exception error) {
        Log.d("PayPal", error.getMessage());
        if (error instanceof ErrorWithResponse) {
            ErrorWithResponse errorWithResponse = (ErrorWithResponse) error;
            BraintreeError cardErrors = errorWithResponse.errorFor("creditCard");
            if (cardErrors != null) {
                // There is an issue with the credit card.
                BraintreeError expirationMonthError = cardErrors.errorFor("expirationMonth");
                if (expirationMonthError != null) {
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            String paymentIntentID = data.getStringExtra("paymentIntentID");
            checkout_done(paymentIntentID);
        }
        if (resultCode == Activity.RESULT_CANCELED) {

        }

    }

    private void checkout_done(String paymentID){
        progressDialog.setMessage("Checkout is processing. Please wait.");
        progressDialog.show();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("shipping_address", shipping_address);
        jsonObject.addProperty("payment_type", payment_method);
        jsonObject.addProperty("payment_status", payment_method.equals("cod") ? "unpaid" : "paid");
        jsonObject.addProperty("user_id", authResponse.getUser().getId());
        jsonObject.addProperty("grand_total", total);
        jsonObject.addProperty("coupon_discount", coupon_discount);
        jsonObject.addProperty("coupon_code", "");



        if(payment_method.equals("card")){
            new PaymentPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), (PaymentView) this).submitOrder(authResponse.getAccessToken(), jsonObject);
        }

        if (payment_method.equals("cod")){
            new PaymentPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), (CodOrderView) this).submitCODOrder(authResponse.getAccessToken(), jsonObject);
        }
    }

    @Override
    public void onPaymentItemClick(PaymentModel paymentModel) {
        payment_method = paymentModel.getPayment_method();
    }

    @Override
    public void onCouponApplied(CouponResponse couponResponse) {
        if (couponResponse.getSuccess()){
            CustomToast.showToast(this, couponResponse.getMessage(), R.color.colorSuccess);
            coupon_discount = couponResponse.getDiscount();
            total -= coupon_discount;
            total_amount.setText(AppConfig.convertPrice(this, total));
        }
        else {
            CustomToast.showToast(this, couponResponse.getMessage(), R.color.colorWarning);
        }
    }

    @Override
    public void onOrderSubmitted(OrderResponse orderResponse) {
        progressDialog.dismiss();
        if (orderResponse.getSuccess()){
            startActivity(new Intent(PaymentActivity.this, OrderPlacedMessage.class));
            finish();
        }
        else {
            CustomToast.showToast(this, orderResponse.getMessage(), R.color.colorDanger);
        }
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {

        jsonObject.addProperty("shipping_address", shipping_address);
        jsonObject.addProperty("payment_type", payment_method);
        jsonObject.addProperty("payment_status", payment_method.equals("cod") ? "unpaid" : "paid");
        jsonObject.addProperty("user_id", authResponse.getUser().getId());
        jsonObject.addProperty("seller_id", authResponse.getUser().getId());
        jsonObject.addProperty("grand_total", total);
        jsonObject.addProperty("coupon_discount", coupon_discount);
        jsonObject.addProperty("coupon_code", "");
        jsonObject.addProperty("payment_details", paymentData.getPaymentId());
        jsonObject.addProperty("payment_order_id", paymentData.getOrderId());
        jsonObject.addProperty("payment_signature", paymentData.getSignature());
        jsonObject.addProperty("amount", total);
        jsonObject.addProperty("payment_methods", payment_method);
        jsonObject.addProperty("txn_code", paymentData.getPaymentId());


        new PaymentPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), (PaymentView) this).submitOrder(authResponse.getAccessToken(), jsonObject);
        new PaymentPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), (RazorPayView) this).razorPayOrderData(authResponse.getAccessToken(), jsonObject);

    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        progressDialog.dismiss();
    }

    @Override
    public void onIncomingOrderId(OrderResponse orderResponse) {
        jsonObject.addProperty("order_id", orderResponse.getMessage());
        try {
            startRazorPayMent();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCodOrderSubmitted(OrderResponse orderResponse) {
        progressDialog.dismiss();
        if (orderResponse.getSuccess()){
            startActivity(new Intent(PaymentActivity.this, OrderPlacedMessage.class));
            finish();
        }
        else {
            CustomToast.showToast(this, orderResponse.getMessage(), R.color.colorDanger);
        }
    }



    public void startRazorPayMent() throws JSONException {
        progressDialog.show();
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_live_AdguWiXXNnIRTF");
        checkout.setImage(R.mipmap.ic_launcher_round);

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    JSONObject options = new JSONObject();
                    options.put("name", "Shopify Culture");
                    options.put("description", "Demoing Charges");
                    //You can omit the image option to fetch the image from dashboard
                    options.put("image", "https://s3.amazonaws.com/zp-mobile/images/rzp.png");
                    options.put("currency", "INR");
                    options.put("amount", 1.00 * 100);
                    options.put("order_id", jsonObject.get("order_id").getAsString());//from response of step 3.

                    JSONObject preFill = new JSONObject();
                    preFill.put("email", "test@razorpay.com");
                    preFill.put("contact", "8237798961");

                    options.put("prefill", preFill);

                    checkout.open(PaymentActivity.this, options);
                } catch (Exception e) {
                    Toast.makeText(PaymentActivity.this, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                            .show();
                    e.printStackTrace();
                }
            }
        }).start();



    }
}
