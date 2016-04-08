package com.thyago.complexserviceui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by thyago on 4/7/16.
 */
public class SmsReceiver extends BroadcastReceiver {

    private static final String LOG_TAG = SmsReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(LOG_TAG, "Received: " + intent.getAction());
        if (action.equals("android.provider.Telephony.SMS_RECEIVED")){
            Log.d(LOG_TAG, "SMS");
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                Log.d(LOG_TAG, "Broadcast event was stopped");
            }
        }

        context.unregisterReceiver(this);
    }

}
