package com.example.countdownassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;

public class MainActivity extends AppCompatActivity {
    CountDownTimer countDownTimer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    void startTimer(){
        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {

            }
        };
    }

    void stopTimer(){
        if(countDownTimer != null){
            countDownTimer.cancel();
        }
    }
}