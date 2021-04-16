package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Models.Banner;

import java.util.List;

public interface BannerInteractor {
    interface CallBack {

        void onBannersDownloaded(List<Banner> banners);

        void onBannersDownloadError();
    }
}
