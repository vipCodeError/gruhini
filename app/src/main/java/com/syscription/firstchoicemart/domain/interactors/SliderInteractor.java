package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Models.SliderImage;

import java.util.List;

public interface SliderInteractor {

    interface CallBack {

        void onSliderDownloaded(List<SliderImage> sliderImages);

        void onSliderDownloadError();
    }
}
