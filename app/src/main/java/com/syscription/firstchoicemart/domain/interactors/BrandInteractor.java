package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Models.Brand;

import java.util.List;

public interface BrandInteractor {
    interface CallBack {

        void onBrandsDownloaded(List<Brand> brands);

        void onBrandsDownloadError();
    }
}
