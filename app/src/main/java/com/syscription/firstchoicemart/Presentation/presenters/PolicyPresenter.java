package com.syscription.firstchoicemart.Presentation.presenters;

import com.syscription.firstchoicemart.Models.PolicyContent;
import com.syscription.firstchoicemart.Presentation.ui.activities.PolicyView;
import com.syscription.firstchoicemart.domain.executor.Executor;
import com.syscription.firstchoicemart.domain.executor.MainThread;
import com.syscription.firstchoicemart.domain.interactors.PolicyInteractor;
import com.syscription.firstchoicemart.domain.interactors.impl.PolicyInteractorImpl;

public class PolicyPresenter extends AbstractPresenter implements PolicyInteractor.CallBack {
    private final PolicyView policyView;
    private final int type = 0;

    public PolicyPresenter(Executor executor, MainThread mainThread, PolicyView policyView) {
        super(executor, mainThread);
        this.policyView = policyView;
    }

    public void getPolicy(String url){
        new PolicyInteractorImpl(mExecutor, mMainThread, this, url).execute();
    }

    @Override
    public void onPolicyLoaded(PolicyContent policyContent) {
        if (policyView != null){
            policyView.onPolicyContentLoaded(policyContent);
        }
    }

    @Override
    public void onPolicyLoadError() {

    }
}
