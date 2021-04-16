package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Network.response.AppSettingsResponse;

public interface AppSettingsInteractor {
    interface CallBack {

        void onAppSettingsLoaded(AppSettingsResponse appSettingsResponse);

        void onAppSettingsLoadedError();
    }
}
