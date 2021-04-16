package com.syscription.firstchoicemart.Presentation.ui.activities.impl.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.syscription.firstchoicemart.Presentation.ui.activities.impl.DrawerActivityNew;
import com.syscription.firstchoicemart.R;

public class OrderPlacedMessage extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status_lay);

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(3500);
                    startActivity(new Intent(OrderPlacedMessage.this, DrawerActivityNew.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();
                } catch (InterruptedException e) {
                }
            }
        }).start();
    }
}
