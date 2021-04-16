package com.syscription.firstchoicemart.Presentation.ui.activities;

import com.syscription.firstchoicemart.Network.response.StripeClientSecretResponse;

public interface StripePaymentView {
    void onClientSecretReceived(StripeClientSecretResponse stripeClientSecretResponse);
}
