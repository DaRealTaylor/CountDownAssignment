package com.example.countdownassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final long StartTime = 600000;
    private long milliTimeLeft = 600000; //10 minutes
    private TextView countDown;
    private Button startButton;
    private Button resetButton;
    private boolean isActive;
    private CountDownTimer countdowntimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countDown = findViewById(R.id.countDown);
        startButton = findViewById(R.id.start);
        resetButton = findViewById(R.id.reset);

        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startStop();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetCountdown();
            }
        });
    }

    public void startStop(){
        if (isActive){
            stopTimer();
        } else {
            startTimer();
        }
    }

    public void startTimer(){
        countdowntimer = new CountDownTimer(milliTimeLeft, 1000) {
            @Override
            public void onTick(long l) {
                milliTimeLeft = l;
                UpdateTimer();
            }

            @Override
            public void onFinish() {

            }
        };
        countdowntimer.start();
        startButton.setText("PAUSE");
        isActive = true;
    }

    void stopTimer(){
        countdowntimer.cancel();
        isActive = false;
        startButton.setText("START");
        resetButton.setVisibility(View.VISIBLE);
    }

    public void UpdateTimer(){
        int minute = (int) milliTimeLeft / 60000;
        int seconds = (int) milliTimeLeft % 60000 / 1000;

        String timeLeft = String.format(Locale.getDefault(), "%02d:%02d", minute, seconds);
//
//        timeLeft = "" + minute;
//        timeLeft += ":";
//        if (seconds <10) timeLeft += "0";
//        timeLeft += seconds;

        countDown.setText(timeLeft);
    }

    private void resetCountdown(){
        milliTimeLeft = StartTime;
        UpdateTimer();
        resetButton.setVisibility(View.INVISIBLE);
    }
}