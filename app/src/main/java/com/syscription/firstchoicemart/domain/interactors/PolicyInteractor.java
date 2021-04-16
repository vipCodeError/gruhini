package com.syscription.firstchoicemart.domain.interactors;

import com.syscription.firstchoicemart.Models.PolicyContent;

public interface PolicyInteractor {
    interface CallBack {

        void onPolicyLoaded(PolicyContent policyContent);

        void onPolicyLoadError();
    }
}
