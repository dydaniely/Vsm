package com.example.Vstockmonitoring.utils;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;


import com.example.Vstockmonitoring.R;
import com.example.Vstockmonitoring.ui.SMSreceiver;

/**
 * Created by DanielY on 12/2/13.
 */
public class ServiceCommunicator extends Service {

    private SMSreceiver mSMSreceiver;
    private IntentFilter mIntentFilter;
    private NotificationManager mNM;

    private int NOTIFICATION =  R.string.local_service_started;

    @Override
    public void onCreate()
    {
        super.onCreate();
        //SMS event receiver
        mSMSreceiver = new SMSreceiver();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(mSMSreceiver, mIntentFilter);
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        // Display a notification about us starting.  We put an icon in the status bar.
       // showNotification();

    }

    private void showNotification() {
        // In this sample, we'll use the same text for the ticker and the expanded notification
        CharSequence text = getText(R.string.local_service_started);

        // Set the icon, scrolling text and timestamp
        Notification notification = new Notification(R.drawable.appwidget_bg, text,
                System.currentTimeMillis());

        // The PendingIntent to launch our activity if the user selects this notification
      PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this,   ServiceCommunicator.class ), 0);

        // Set the info for the views that show in the notification panel.
        notification.setLatestEventInfo(this, getText(R.string.local_service_label),
                text, contentIntent);

        // Send the notification.
        mNM.notify(NOTIFICATION, notification);
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        // Unregister the SMS receiver
        unregisterReceiver(mSMSreceiver);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
