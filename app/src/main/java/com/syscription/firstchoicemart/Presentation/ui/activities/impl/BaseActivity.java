package com.syscription.firstchoicemart.Presentation.ui.activities.impl;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.syscription.firstchoicemart.R;

public class BaseActivity extends AppCompatActivity {
    protected ImageButton cart, search;
    protected TextView title;

    public void initializeActionBar(){
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        getSupportActionBar().setElevation(0);

        View view =getSupportActionBar().getCustomView();

    }

    public void setTitle(String s){
        title.setText(s);
    }
}
