package com.syscription.firstchoicemart.Presentation.ui.activities.impl.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

public class RazorPayActivity  extends AppCompatActivity implements PaymentResultListener {

    public static final String TAG = RazorPayActivity.class.getSimpleName();
    Checkout checkout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onPaymentSuccess(String s) {

    }

    @Override
    public void onPaymentError(int i, String s) {

    }



}
