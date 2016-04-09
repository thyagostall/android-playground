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
        context.unregisterReceiver(this);

        Log.d(LOG_TAG, "SMS: Received and Reading");
        Intent serviceIntent = new Intent(context, SmsValidationService.class);
        context.startService(serviceIntent);
    }

}
