package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Network.response.RegistrationResponse;

public interface RegisterInteractor {
    interface CallBack {

        void onRegistrationDone(RegistrationResponse registrationResponse);

        void onRegistrationError();
    }
}
