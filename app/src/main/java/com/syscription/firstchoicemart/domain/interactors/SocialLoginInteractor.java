package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Network.response.AuthResponse;

public interface SocialLoginInteractor {
    interface CallBack {

        void onValidLogin(AuthResponse authResponse);

        void onLoginError();
    }
}
