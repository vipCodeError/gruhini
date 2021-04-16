package com.syscription.firstchoicemart.Presentation.presenters;

import com.syscription.firstchoicemart.Network.response.LogoutResponse;
import com.syscription.firstchoicemart.Presentation.ui.fragments.AccountView;
import com.syscription.firstchoicemart.domain.executor.Executor;
import com.syscription.firstchoicemart.domain.executor.MainThread;
import com.syscription.firstchoicemart.domain.interactors.LogoutInteractor;
import com.syscription.firstchoicemart.domain.interactors.impl.LogoutInteractorImpl;

public class AccountPresenter extends AbstractPresenter implements LogoutInteractor.CallBack {

    private final AccountView accountView;

    public AccountPresenter(Executor executor, MainThread mainThread, AccountView accountView) {
        super(executor, mainThread);
        this.accountView = accountView;
    }

    public void performLogout(String token){
        new LogoutInteractorImpl(mExecutor, mMainThread, this, token).execute();
    }

    @Override
    public void onLoggedOut(LogoutResponse logoutResponse) {
        if(accountView != null){
            accountView.showLogoutMessage(logoutResponse);
        }
    }

    @Override
    public void onLoggedOutError() {

    }
}
