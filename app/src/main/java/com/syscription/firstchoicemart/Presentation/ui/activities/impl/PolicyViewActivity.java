package com.syscription.firstchoicemart.Presentation.ui.activities.impl;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.syscription.firstchoicemart.Models.PolicyContent;
import com.syscription.firstchoicemart.Presentation.presenters.PolicyPresenter;
import com.syscription.firstchoicemart.Presentation.ui.activities.PolicyView;
import com.syscription.firstchoicemart.R;
import com.syscription.firstchoicemart.Threading.MainThreadImpl;
import com.syscription.firstchoicemart.domain.executor.impl.ThreadExecutor;

public class PolicyViewActivity extends BaseActivity implements PolicyView {
    private String title, url;
    private TextView policy_content;
    private Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
        mToolBar = findViewById(R.id.toolbar);

        setSupportActionBar(mToolBar);
        mToolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getSupportActionBar().setTitle(title);

        policy_content = findViewById(R.id.policy_content);

        new PolicyPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this).getPolicy(url);
    }

    @Override
    public void onPolicyContentLoaded(PolicyContent policyContent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            policy_content.setText(Html.fromHtml(policyContent.getContent(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            policy_content.setText(Html.fromHtml(policyContent.getContent()));
        }
    }
}
