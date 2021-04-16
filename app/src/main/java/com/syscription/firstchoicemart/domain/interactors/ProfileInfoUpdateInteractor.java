package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Network.response.ProfileInfoUpdateResponse;

public interface ProfileInfoUpdateInteractor {
    interface CallBack {

        void onProfileInfoUpdated(ProfileInfoUpdateResponse profileInfoUpdateResponse);

        void onProfileInfoUpdatedError();
    }
}
