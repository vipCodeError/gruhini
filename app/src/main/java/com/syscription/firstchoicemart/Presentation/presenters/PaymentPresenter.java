package com.syscription.firstchoicemart.Presentation.presenters;

import com.syscription.firstchoicemart.Network.response.CouponResponse;
import com.syscription.firstchoicemart.Network.response.OrderResponse;
import com.syscription.firstchoicemart.Network.response.StripeClientSecretResponse;
import com.syscription.firstchoicemart.Presentation.ui.activities.CodOrderView;
import com.syscription.firstchoicemart.Presentation.ui.activities.PaymentView;
import com.syscription.firstchoicemart.Presentation.ui.activities.RazorPayView;
import com.syscription.firstchoicemart.Presentation.ui.activities.StripePaymentView;
import com.syscription.firstchoicemart.domain.executor.Executor;
import com.syscription.firstchoicemart.domain.executor.MainThread;
import com.syscription.firstchoicemart.domain.interactors.CODInteractor;
import com.syscription.firstchoicemart.domain.interactors.CouponInteractor;
import com.syscription.firstchoicemart.domain.interactors.OrderInteractor;
import com.syscription.firstchoicemart.domain.interactors.PaypalInteractor;
import com.syscription.firstchoicemart.domain.interactors.RazorOrderInteractor;
import com.syscription.firstchoicemart.domain.interactors.StripeInteractor;
import com.syscription.firstchoicemart.domain.interactors.WalletInteractor;
import com.syscription.firstchoicemart.domain.interactors.impl.CODInteractorImpl;
import com.syscription.firstchoicemart.domain.interactors.impl.CouponInteractorImpl;
import com.syscription.firstchoicemart.domain.interactors.impl.OrderInteractorImpl;
import com.syscription.firstchoicemart.domain.interactors.impl.PaypalInteractorImpl;
import com.syscription.firstchoicemart.domain.interactors.impl.RazorPayCreateOrderId;
import com.syscription.firstchoicemart.domain.interactors.impl.RazorPayOrderDataImpl;
import com.syscription.firstchoicemart.domain.interactors.impl.StripeInteractorImpl;
import com.syscription.firstchoicemart.domain.interactors.impl.WalletInteractorImpl;
import com.google.gson.JsonObject;

public class PaymentPresenter extends AbstractPresenter implements RazorOrderInteractor.CallBack, CouponInteractor.CallBack, PaypalInteractor.CallBack, StripeInteractor.CallBack, CODInteractor.CallBack, OrderInteractor.CallBack, WalletInteractor.CallBack {
    private PaymentView paymentView;
    private StripePaymentView stripePaymentView;
    private RazorPayView razorPayView;
    private CodOrderView codOrderView;


    public PaymentPresenter(Executor executor, MainThread mainThread, PaymentView paymentView) {
        super(executor, mainThread);
        this.paymentView = paymentView;
    }

    public PaymentPresenter(Executor executor, MainThread mainThread, StripePaymentView stripePaymentView) {
        super(executor, mainThread);
        this.stripePaymentView = stripePaymentView;
    }

    public PaymentPresenter(Executor executor, MainThread mainThread, RazorPayView razorPayView) {
        super(executor, mainThread);
        this.razorPayView = razorPayView;
    }

    public PaymentPresenter(Executor executor, MainThread mainThread, CodOrderView codOrderView) {
        super(executor, mainThread);
        this.codOrderView = codOrderView;
    }

    public void applyCoupon(int user_id, String code, String token) {
        new CouponInteractorImpl(mExecutor, mMainThread, this, user_id, code, token).execute();
    }

    public void submitPaypalOrder(String token, JsonObject jsonObject) {
        new PaypalInteractorImpl(mExecutor, mMainThread, this, token, jsonObject).execute();
    }

    public void submitStripeRequest(String token, JsonObject jsonObject) {
        new StripeInteractorImpl(mExecutor, mMainThread, this, token, jsonObject).execute();
    }

    public void submitWalletOrder(String token, JsonObject jsonObject) {
        new WalletInteractorImpl(mExecutor, mMainThread, this, token, jsonObject).execute();
    }

    public void submitCODOrder(String token, JsonObject jsonObject) {
        new CODInteractorImpl(mExecutor, mMainThread, this, token, jsonObject).execute();
    }

    public void submitOrder(String token, JsonObject jsonObject) {
        new OrderInteractorImpl(mExecutor, mMainThread, this, token, jsonObject).execute();
    }

    public void razorPayOrderData(String token, JsonObject jsonObject){
        new RazorPayOrderDataImpl(mExecutor, mMainThread, this, token, jsonObject).execute();
    }

    public void razorPayOrderId(String token, JsonObject jsonObject){
        new RazorPayCreateOrderId(mExecutor, mMainThread, this, token, jsonObject).execute();
    }

    @Override
    public void onCouponApplied(CouponResponse couponResponse) {
        if (paymentView != null){
            paymentView.onCouponApplied(couponResponse);
        }
    }

    @Override
    public void onCouponAppliedError() {

    }

    @Override
    public void onPayaplOrderSubmitted(OrderResponse orderResponse) {
        if (paymentView != null){
            paymentView.onOrderSubmitted(orderResponse);
        }
    }

    @Override
    public void onPayaplOrderSubmitError() {

    }

    @Override
    public void ononClientSecretReceived(StripeClientSecretResponse stripeClientSecretResponse) {
        if (stripePaymentView != null){
            stripePaymentView.onClientSecretReceived(stripeClientSecretResponse);
        }
    }

    @Override
    public void ononClientSecretReceiveError() {

    }

    @Override
    public void onCODOrderSubmitted(OrderResponse orderResponse) {
        if (codOrderView != null){
            codOrderView.onCodOrderSubmitted(orderResponse);
        }
    }

    @Override
    public void onCODOrderSubmitError() {

    }

    @Override
    public void onOrderSubmitted(OrderResponse orderResponse) {
        if (paymentView != null){
            paymentView.onOrderSubmitted(orderResponse);
        }
    }

    @Override
    public void incomingOrderId(OrderResponse orderResponse) {
        if (razorPayView != null){
            razorPayView.onIncomingOrderId(orderResponse);
        }
    }

    @Override
    public void onOrderSubmitError() {

    }

    @Override
    public void onWalletOrderSubmitted(OrderResponse orderResponse) {
        if (paymentView != null){
            paymentView.onOrderSubmitted(orderResponse);
        }
    }

    @Override
    public void onWalletOrderSubmitError() {

    }
}
