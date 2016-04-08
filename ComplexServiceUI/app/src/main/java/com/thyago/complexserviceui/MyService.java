package com.thyago.complexserviceui;

import android.app.IntentService;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

/**
 * Created by thyago on 4/7/16.
 */
public class MyService extends IntentService {

    private static final String LOG_TAG = MyService.class.getSimpleName();

    public MyService() {
        super(MyService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        IntentFilter fp = new IntentFilter();
        fp.addAction("android.provider.Telephony.SMS_RECEIVED");
        fp.setPriority(18);
        registerReceiver(new SmsReceiver(), fp);

        Log.d(LOG_TAG, "Waiting for the SMS here");
    }
}
