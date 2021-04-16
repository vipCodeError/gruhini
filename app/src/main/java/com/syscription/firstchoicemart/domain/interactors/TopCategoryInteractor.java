package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Models.Category;

import java.util.List;

public interface TopCategoryInteractor {
    interface CallBack {

        void onTopCategoriesDownloaded(List<Category> categories);

        void onTopCategoriesDownloadError();
    }
}
