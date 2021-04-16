package com.syscription.firstchoicemart.Presentation.presenters;

import com.syscription.firstchoicemart.Models.Review;
import com.syscription.firstchoicemart.Presentation.ui.activities.ProductReviewView;
import com.syscription.firstchoicemart.domain.executor.Executor;
import com.syscription.firstchoicemart.domain.executor.MainThread;
import com.syscription.firstchoicemart.domain.interactors.ReviewInteractor;
import com.syscription.firstchoicemart.domain.interactors.impl.ReviewInteractorImpl;

import java.util.List;

public class ProductReviewPresenter extends AbstractPresenter implements ReviewInteractor.CallBack {
    private final ProductReviewView productReviewView;

    public ProductReviewPresenter(Executor executor, MainThread mainThread, ProductReviewView productReviewView) {
        super(executor, mainThread);
        this.productReviewView = productReviewView;
    }

    public void sendUpdateProfileRequest(String url) {
        new ReviewInteractorImpl(mExecutor, mMainThread, this, url).execute();
    }


    @Override
    public void onReviewLodaded(List<Review> reviews) {
        if (productReviewView != null){
            productReviewView.onReviewsLoaded(reviews);
        }
    }

    @Override
    public void onReviewError() {

    }
}
