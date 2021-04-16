package com.syscription.firstchoicemart.Presentation.presenters;

import com.syscription.firstchoicemart.Network.response.AuthResponse;
import com.syscription.firstchoicemart.Presentation.ui.activities.LoginView;
import com.syscription.firstchoicemart.domain.executor.Executor;
import com.syscription.firstchoicemart.domain.executor.MainThread;
import com.syscription.firstchoicemart.domain.interactors.LoginInteractor;
import com.syscription.firstchoicemart.domain.interactors.SocialLoginInteractor;
import com.syscription.firstchoicemart.domain.interactors.impl.LoginInteractorImpl;
import com.syscription.firstchoicemart.domain.interactors.impl.SocialLoginInteractorImpl;

public class LoginPresenter extends AbstractPresenter implements LoginInteractor.CallBack, SocialLoginInteractor.CallBack {

    private final LoginView loginView;

    public LoginPresenter(Executor executor, MainThread mainThread, LoginView loginView) {
        super(executor, mainThread);
        this.loginView = loginView;
    }

    public void validLogin(String phone) {
        new LoginInteractorImpl(mExecutor, mMainThread, this, phone).execute();
    }

    public void validSocialLogin(String id, String name, String email) {
        new SocialLoginInteractorImpl(mExecutor, mMainThread, this, id, name, email).execute();
    }

    @Override
    public void onValidLogin(AuthResponse authResponse) {
        if (loginView != null){
            loginView.setLoginResponse(authResponse);
        }
    }

    @Override
    public void onLoginError() {

    }
}
