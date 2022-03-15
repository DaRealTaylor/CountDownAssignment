package com.example.countdownassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    CountDownTimer countDownTimer = null;
    private TextView countDowntimer;
    private Button button;
    private boolean isActive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countDowntimer = findViewById(R.id.countDown);
        button = findViewById(R.id.start);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startStop();
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
        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long l) {
                UpdateTimer();
            }

            @Override
            public void onFinish() {

            }
        };
        countDownTimer.start();

        isActive = true;
    }

    void stopTimer(){
        if(countDownTimer != null){
            countDownTimer.cancel();
            isActive = false;
        }
    }

    public void UpdateTimer(){
        int minute = (int) 30000 / 60000;
        int seconds = (int) 30000 / 60000 / 1000;

        String timeLeft;

        timeLeft = minute + ":" + seconds;
        if (seconds <10) timeLeft += "0";
        timeLeft += seconds;

        countDowntimer.setText(timeLeft);
    }
}