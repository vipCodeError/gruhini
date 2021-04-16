package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Models.WishlistModel;

import java.util.List;

public interface WishlistInteractor {
    interface CallBack {

        void onWishlistLodaded(List<WishlistModel> wishlistModels);

        void onWishlistError();
    }
}
