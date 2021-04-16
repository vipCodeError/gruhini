package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Models.Category;

import java.util.List;

public interface HomeCategoriesInteractor {
    interface CallBack {

        void onHomeCategoriesDownloaded(List<Category> categories);

        void onHomeCategoriesDownloadError();
    }
}
