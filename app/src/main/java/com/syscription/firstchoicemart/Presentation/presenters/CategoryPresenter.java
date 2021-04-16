package com.syscription.firstchoicemart.Presentation.presenters;

import com.syscription.firstchoicemart.Models.Category;
import com.syscription.firstchoicemart.Presentation.ui.fragments.CategoryView;
import com.syscription.firstchoicemart.domain.executor.Executor;
import com.syscription.firstchoicemart.domain.executor.MainThread;
import com.syscription.firstchoicemart.domain.interactors.AllCategoryInteractor;
import com.syscription.firstchoicemart.domain.interactors.impl.AllCategoriesInteractorImpl;

import java.util.List;

public class CategoryPresenter extends AbstractPresenter implements AllCategoryInteractor.CallBack {

    private final CategoryView categoryView;

    public CategoryPresenter(Executor executor, MainThread mainThread, CategoryView categoryView) {
        super(executor, mainThread);
        this.categoryView = categoryView;
    }

    public void getAllCategories() {
        new AllCategoriesInteractorImpl(mExecutor, mMainThread, this).execute();
    }

    @Override
    public void onAllCategoriesDownloaded(List<Category> categories) {
        if (categoryView != null) {
            categoryView.setAllCategories(categories);
        }
    }

    @Override
    public void onAllCategoriesDownloadError() {

    }
}
