package com.activeitzone.activeecommercecms.Presentation.ui.activities.impl;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.activeitzone.activeecommercecms.R;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {

    ImageView logosImg, storeImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_splash);
        logosImg = findViewById(R.id.logo_img);
        storeImg = findViewById(R.id.grocrey_store_img);

        AnimatorSet animatorSet = new AnimatorSet();

        ValueAnimator logoAnimator = ValueAnimator.ofFloat(1, 1.2f);
        logoAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                logosImg.setScaleX(value);
                logosImg.setScaleY(value);

            }
        });

        ValueAnimator storeAnimator = ValueAnimator.ofFloat(1, 1.1f);
        storeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                storeImg.setScaleX(value);
                storeImg.setScaleY(value);

            }
        });

        animatorSet.play(logoAnimator).with(storeAnimator);
        animatorSet.setDuration(3000);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.start();

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler(Looper.myLooper()).postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashActivity.this, DrawerActivityNew.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, 1500);
    }
}
