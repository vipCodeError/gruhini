package com.syscription.firstchoicemart.Presentation.ui.activities;

import com.syscription.firstchoicemart.Models.Review;

import java.util.List;

public interface ProductReviewView {
    void onReviewsLoaded(List<Review> reviews);
}
