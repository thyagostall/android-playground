package com.thyago.complexserviceui;

import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Random;

public class AskAndWaitSomethingActivity extends AppCompatActivity {

    private static final String LOG_TAG = AskAndWaitSomethingActivity.class.getSimpleName();
    private CountDownTimer mTimer;
    private Button mButton;
    private TextView mWaitTextView;

    private SmsReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_and_wait_something);

        mWaitTextView = (TextView) findViewById(R.id.waiting_text_view);

        mButton = (Button) findViewById(R.id.ask_for_something);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                startTimer();
                new AskForSomethingAsync().execute();
            }
        });

        EventBus.getDefault().register(this);
    }

    private class AskForSomethingAsync extends AsyncTask<String, Void, Boolean> {

        private final String LOG_TAG = AskForSomethingAsync.class.getSimpleName();

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }

            int randomInt = new Random().nextInt();
            return (randomInt % 2 == 0);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            Log.d(LOG_TAG, "Requested code... Will wait for the code...");
            registerReceiver();
        }
    }

    private void startTimer() {
        mTimer = new CountDownTimer(50000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mWaitTextView.setText(": " + millisUntilFinished / 1000);
//                Log.d(LOG_TAG, "Ticking");
            }

            @Override
            public void onFinish() {
                mWaitTextView.setText("Time is up!");
                unregisterReceiver();
            }
        };
        mTimer.start();
    }

    private void registerReceiver() {
        Log.d(LOG_TAG, "SMS: Registering the receiver");

        IntentFilter fp = new IntentFilter();
        fp.addAction("android.provider.Telephony.SMS_RECEIVED");

        mReceiver = new SmsReceiver();
        registerReceiver(mReceiver, fp);
    }

    private void unregisterReceiver() {
        Log.d(LOG_TAG, "Unregistering SMS Listener");
        try {
            unregisterReceiver(mReceiver);
        } catch (IllegalArgumentException e) {
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mTimer.cancel();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSmsServiceResult(SmsServiceResult smsResult) {
        boolean result = smsResult.result();
        Toast.makeText(this, "OK, the sms was received and confirmed! The dashboard will appear now!", Toast.LENGTH_SHORT).show();
    }
}
