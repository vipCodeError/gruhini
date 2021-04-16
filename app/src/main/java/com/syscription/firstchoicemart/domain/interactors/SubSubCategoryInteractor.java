package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Models.SubCategory;

import java.util.List;

public interface SubSubCategoryInteractor {
    interface CallBack {

        void onSubSubCategoriesDownloaded(List<SubCategory> subCategories);

        void onSubSubCategoriesDownloadError();
    }
}
