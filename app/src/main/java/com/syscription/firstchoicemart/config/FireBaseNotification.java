package com.syscription.firstchoicemart.config;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.syscription.firstchoicemart.Presentation.ui.activities.impl.DrawerActivityNew;
import com.syscription.firstchoicemart.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FireBaseNotification extends FirebaseMessagingService {

    public static final String TAG = FireBaseNotification.class.getCanonicalName();

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d(TAG, "### Firebase Token: " + token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "first_choice_mart";

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            @SuppressLint("WrongConstant") NotificationChannel notificationChannel
                    = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "FirsChoice_Status", NotificationManager.IMPORTANCE_DEFAULT);

            //Configure Notification Channel
            notificationChannel.setDescription("");
            notificationChannel.enableLights(true);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);

            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
        }

        Intent intent = new Intent(getApplicationContext(), DrawerActivityNew.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,PendingIntent.FLAG_CANCEL_CURRENT);

        if (remoteMessage.getNotification().getImageUrl() != null){
            Glide.with(this).asBitmap().load(remoteMessage.getNotification().getImageUrl()).into(new CustomTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle();
                    style.setSummaryText("");
                    style.bigPicture(resource);

                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(FireBaseNotification.this, NOTIFICATION_CHANNEL_ID)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setAutoCancel(true)
                            .setContentTitle(remoteMessage.getNotification().getTitle())
                            .setContentText(remoteMessage.getNotification().getBody())
                            .setContentIntent(pendingIntent)
                            .setStyle(style)
                            .setPriority(NotificationCompat.DEFAULT_ALL)
                            .setWhen(System.currentTimeMillis());


                    notificationManager.notify(0, notificationBuilder.build());
                }

                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) {

                }
            });
        }else {
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true)
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis());


            notificationManager.notify(0, notificationBuilder.build());
        }



        Log.d(TAG, "### Firebase Message Received: " + remoteMessage);

    }


    private void sendNotificationImages() {

    }
}
