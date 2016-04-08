package com.thyago.complexserviceui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class AskAndWaitSomethingActivity extends AppCompatActivity {

    private CountDownTimer mTimer;
    private Button mButton;
    private TextView mWaitTextView;

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
                waitForSomething();
                new AskForSomethingAsync().execute();
            }
        });
    }

    private class AskForSomethingAsync extends AsyncTask<String, Void, Boolean> {

        private final String LOG_TAG = AskForSomethingAsync.class.getSimpleName();

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Log.e(LOG_TAG, "Thread Interrupted!");
            }

            int randomInt = new Random().nextInt();
            return (randomInt % 2 == 0);
        }

        @Override
        protected void onPostExecute(Boolean result) {
//            stopWaiting(result);
            Log.d(LOG_TAG, "Will start the service");
            delegateConfirmation();
        }
    }

    private void waitForSomething() {
        mTimer = new CountDownTimer(50000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mWaitTextView.setText("> " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                mWaitTextView.setText("Time is up!");
            }
        };
        mTimer.start();
    }

    private void stopWaiting(boolean success) {
        mTimer.cancel();
        mWaitTextView.setText(success ? "Success" : "Failure");
        mButton.setEnabled(true);
    }

    private void delegateConfirmation() {
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
    }
}
