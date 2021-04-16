package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Models.Category;

import java.util.List;

public interface AllCategoryInteractor {
    interface CallBack {

        void onAllCategoriesDownloaded(List<Category> categories);

        void onAllCategoriesDownloadError();
    }
}
