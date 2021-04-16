package com.syscription.firstchoicemart.Presentation.presenters;

import com.syscription.firstchoicemart.Models.SubCategory;
import com.syscription.firstchoicemart.Presentation.ui.activities.SubCategoryView;
import com.syscription.firstchoicemart.domain.executor.Executor;
import com.syscription.firstchoicemart.domain.executor.MainThread;
import com.syscription.firstchoicemart.domain.interactors.SubSubCategoryInteractor;
import com.syscription.firstchoicemart.domain.interactors.impl.SubSubCategoryInteractorImpl;

import java.util.List;

public class SubSubCategoryPresenter extends AbstractPresenter implements SubSubCategoryInteractor.CallBack {
    private final SubCategoryView subSubCategoryView;

    public SubSubCategoryPresenter(Executor executor, MainThread mainThread, SubCategoryView subSubCategoryView) {
        super(executor, mainThread);
        this.subSubCategoryView =subSubCategoryView;
    }

    public void getSubSubCategories(String url){
        new SubSubCategoryInteractorImpl(mExecutor, mMainThread, this, url).execute();
    }

    @Override
    public void onSubSubCategoriesDownloaded(List<SubCategory> subCategories) {
        if (subSubCategoryView != null){
            subSubCategoryView.setSubCategories(subCategories);
        }
    }

    @Override
    public void onSubSubCategoriesDownloadError() {

    }
}
