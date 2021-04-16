package com.syscription.firstchoicemart.Presentation.presenters;

import com.syscription.firstchoicemart.Network.response.RegistrationResponse;
import com.syscription.firstchoicemart.Presentation.ui.activities.RegisterView;
import com.syscription.firstchoicemart.domain.executor.Executor;
import com.syscription.firstchoicemart.domain.executor.MainThread;
import com.syscription.firstchoicemart.domain.interactors.RegisterInteractor;
import com.syscription.firstchoicemart.domain.interactors.impl.RegisterInteractorImpl;
import com.google.gson.JsonObject;

public class RegisterPresenter extends AbstractPresenter implements RegisterInteractor.CallBack {

    private final RegisterView registerView;

    public RegisterPresenter(Executor executor, MainThread mainThread, RegisterView registerView) {
        super(executor, mainThread);
        this.registerView = registerView;
    }

    public void validateRegistration(JsonObject jsonObject) {
        new RegisterInteractorImpl(mExecutor, mMainThread, this, jsonObject).execute();
    }

    @Override
    public void onRegistrationDone(RegistrationResponse registrationResponse) {
        if (registerView != null){
            registerView.setRegistrationResponse(registrationResponse);
        }
    }

    @Override
    public void onRegistrationError() {

    }
}
