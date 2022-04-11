package com.example.myform;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {


        String place = intent.getStringExtra("place");
        intent.putExtra("place", place);
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification().setContentText("Your it's Spinner Value is: "+place);
        notificationHelper.getManager().notify(1, nb.build());



    }
}
