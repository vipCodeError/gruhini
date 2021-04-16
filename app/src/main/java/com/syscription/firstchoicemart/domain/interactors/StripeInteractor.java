package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Network.response.StripeClientSecretResponse;

public interface StripeInteractor {
    interface CallBack {

        void ononClientSecretReceived(StripeClientSecretResponse stripeClientSecretResponse);

        void ononClientSecretReceiveError();
    }
}
