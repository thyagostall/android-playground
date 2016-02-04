package com.thyago.mediarecorder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private TextView mEventPosition;
    private TextView mViewPosition;

    public boolean isMotionEventInsideView(MotionEvent event, View view) {
        return (
            event.getX() >= 0 && event.getX() <= view.getWidth() &&
            event.getY() >= 0 && event.getY() <= view.getHeight()
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEventPosition = (TextView) findViewById(R.id.event_position_text_view);
        mViewPosition = (TextView) findViewById(R.id.view_position_text_view);

        Button button = (Button) findViewById(R.id.say_something_button);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mViewPosition.setText("View: (x: " + v.getX() + ", y: " + v.getY() + ")");
                mEventPosition.setText("Event: (x: " + event.getX() + ", y: " + event.getY() + ")");

                if (isMotionEventInsideView(event, v)) {
                    mViewPosition.setTextColor(getResources().getColor(R.color.red));
                    mEventPosition.setTextColor(getResources().getColor(R.color.red));
                } else {
                    mViewPosition.setTextColor(getResources().getColor(R.color.black));
                    mEventPosition.setTextColor(getResources().getColor(R.color.black));
                }
                return false;
            }
        });
    }
}
