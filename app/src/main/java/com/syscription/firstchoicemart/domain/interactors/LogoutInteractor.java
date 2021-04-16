package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Network.response.LogoutResponse;

public interface LogoutInteractor {
    interface CallBack {

        void onLoggedOut(LogoutResponse logoutResponse);

        void onLoggedOutError();
    }
}
