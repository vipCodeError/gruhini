package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Models.Review;

import java.util.List;

public interface ReviewInteractor {
    interface CallBack {

        void onReviewLodaded(List<Review> reviews);

        void onReviewError();
    }
}
