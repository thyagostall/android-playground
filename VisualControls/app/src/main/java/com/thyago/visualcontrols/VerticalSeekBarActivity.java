package com.thyago.visualcontrols;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;

public class VerticalSeekBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_seek_bar);

        SeekBar seekBar = (SeekBar) findViewById(R.id.vertical_seek_bar);
        seekBar.setMax(10);
        seekBar.setProgress(5);
    }
}
