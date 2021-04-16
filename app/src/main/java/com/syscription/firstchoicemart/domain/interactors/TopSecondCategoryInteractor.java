package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Models.Category;

import java.util.List;

public interface TopSecondCategoryInteractor {
    interface CallBack {

        void onTopSecondCategoriesDownloaded(List<Category> categories);

        void onTopSecondCategoriesDownloadError();
    }
}