package com.hfad.stopwatch;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class StopwatchActivity extends Activity {
    private int seconds = 0;
    private boolean running;
    private boolean isRun;

    //Build Instance
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            isRun = savedInstanceState.getBoolean("isRun");
        }
        setTimer();
    }

    //Instance State
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("isRun", isRun);
    }

    //Resuming the stopwatch 
    @Override
    protected void onResume() {
        super.onResume();
        if (isRun) {
            running = true;
        }
    }

    //Pause the stopwatch
    @Override
    protected void onPause() {
        super.onPause();
        isRun = running;
        running = false;
    }

    //Start the stopwatch
    public void onStart(View view) {
        running = true;
    }

    //Stop the stopwatch
    public void onStop(View view) {
        running = false;
    }

    //Reset the stopwatch
    public void onReset(View view) {
        running = false;
        seconds = 0;
    }

    //Seconds number of the timer
    private void setTimer() {
        final TextView timeView = (TextView)findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 600) / 60;
                int secs = seconds % 60;
                String time = String.format(Locale.getDefault(),
                        "%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }
}
