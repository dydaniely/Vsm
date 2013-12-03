package com.example.Vstockmonitoring.utils;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.example.Vstockmonitoring.ui.SMSreceiver;

/**
 * Created by DanielY on 12/2/13.
 */
public class ServiceCommunicator extends Service {

    private SMSreceiver mSMSreceiver;
    private IntentFilter mIntentFilter;

    @Override
    public void onCreate()
    {
        super.onCreate();
        //SMS event receiver
        mSMSreceiver = new SMSreceiver();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(mSMSreceiver, mIntentFilter);
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
