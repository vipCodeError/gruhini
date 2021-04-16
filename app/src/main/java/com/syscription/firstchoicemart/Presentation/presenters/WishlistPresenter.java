package com.syscription.firstchoicemart.Presentation.presenters;

import com.syscription.firstchoicemart.Models.WishlistModel;
import com.syscription.firstchoicemart.Presentation.ui.activities.WishlistView;
import com.syscription.firstchoicemart.domain.executor.Executor;
import com.syscription.firstchoicemart.domain.executor.MainThread;
import com.syscription.firstchoicemart.domain.interactors.WishlistInteractor;
import com.syscription.firstchoicemart.domain.interactors.impl.WishlistInteractorImpl;

import java.util.List;

public class WishlistPresenter extends AbstractPresenter implements WishlistInteractor.CallBack {
    private final WishlistView wishlistView;

    public WishlistPresenter(Executor executor, MainThread mainThread, WishlistView wishlistView) {
        super(executor, mainThread);
        this.wishlistView = wishlistView;
    }

    public void getWishlistItems(int id, String token) {
        new WishlistInteractorImpl(mExecutor, mMainThread, this, id, token).execute();
    }

    public void removeWishlistItem(int id, String token){
        new WishlistInteractorImpl(mExecutor, mMainThread, this, id, token).execute();
    }

    @Override
    public void onWishlistLodaded(List<WishlistModel> wishlistModels) {
        if(wishlistView != null){
            wishlistView.setWishlistProducts(wishlistModels);
        }
    }

    @Override
    public void onWishlistError() {

    }
}
