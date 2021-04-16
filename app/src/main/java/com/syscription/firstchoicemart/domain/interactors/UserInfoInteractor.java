package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Models.User;

public interface UserInfoInteractor {
    interface CallBack {

        void onUserInfoLodaded(User user);

        void onUserInfoError();
    }
}
