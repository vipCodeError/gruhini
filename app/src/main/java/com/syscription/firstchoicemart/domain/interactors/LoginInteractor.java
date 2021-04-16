package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Network.response.AuthResponse;

public interface LoginInteractor {
    interface CallBack {

        void onValidLogin(AuthResponse authResponse);

        void onLoginError();
    }
}
