package com.thyago.mediarecorder;

import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private TextView mEventPosition;
    private TextView mViewPosition;
    private TextView mSlideToCancel;

    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;
    private String mFileName;

    public boolean isMotionEventInsideView(MotionEvent event, View view) {
        return (
            event.getX() >= 0 && event.getX() <= view.getWidth() &&
            event.getY() >= 0 && event.getY() <= view.getHeight()
        );
    }

    public void startRecording() {
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/" + UUID.randomUUID() + ".3gp";

        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mRecorder.setOutputFile(mFileName);
        try {
            mRecorder.prepare();
            mRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(LOG_TAG, "Recording at " + mFileName);
    }

    public void discardRecording() {
        mRecorder.stop();
        mRecorder.release();

        Log.d(LOG_TAG, "Discarding " + mFileName);
    }

    public void sendRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;

        try {
//            mPlayer = MediaPlayer.create(this, Uri.parse(mFileName));
            mPlayer = new MediaPlayer();
            mPlayer.setDataSource(this, Uri.parse(mFileName));
            mPlayer.prepare();
            mPlayer.start();
            mPlayer.setVolume(1f, 1f);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d(LOG_TAG, "Sending " + mFileName);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEventPosition = (TextView) findViewById(R.id.event_position_text_view);
        mViewPosition = (TextView) findViewById(R.id.view_position_text_view);
        mSlideToCancel = (TextView) findViewById(R.id.slide_to_cancel_text_view);

        Button button = (Button) findViewById(R.id.say_something_button);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mViewPosition.setText("View: (x: " + v.getX() + ", y: " + v.getY() + ")");
                mEventPosition.setText("Event: (x: " + event.getX() + ", y: " + event.getY() + ")");

                if (isMotionEventInsideView(event, v)) {
                    mViewPosition.setTextColor(getResources().getColor(R.color.red));
                    mEventPosition.setTextColor(getResources().getColor(R.color.red));

                    Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.NORMAL);
                    mSlideToCancel.setTypeface(boldTypeface);
                } else {
                    mViewPosition.setTextColor(getResources().getColor(R.color.black));
                    mEventPosition.setTextColor(getResources().getColor(R.color.black));

                    Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
                    mSlideToCancel.setTypeface(boldTypeface);
                }

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startRecording();
                        mSlideToCancel.setVisibility(View.VISIBLE);
                        break;
                    case MotionEvent.ACTION_UP:
                        mSlideToCancel.setVisibility(View.INVISIBLE);
                        if (isMotionEventInsideView(event, v)) {
                            sendRecording();
                        } else {
                            discardRecording();
                        }
                        break;
                }

                return false;
            }
        });
    }
}
