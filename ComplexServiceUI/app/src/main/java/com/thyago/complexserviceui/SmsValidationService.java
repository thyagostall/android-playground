package com.thyago.complexserviceui;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by thyago on 4/7/16.
 */
public class SmsValidationService extends IntentService {

    private static final String LOG_TAG = SmsValidationService.class.getSimpleName();

    public SmsValidationService() {
        super(SmsValidationService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(LOG_TAG, "Service Started. Will start working...");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
        }
        Log.d(LOG_TAG, "Work done!");
        EventBus.getDefault().post(new SmsServiceResult(true));
    }
}
