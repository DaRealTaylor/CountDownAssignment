package com.example.countdownassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private TextView countDown;
    private Button setButton;
    private Button startButton;
    private Button resetButton;

    private boolean isActive;

    private long milliTimeLeft;
    private long mStartTime;
    private CountDownTimer countdowntimer;
    private long endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.edit_text);
        countDown = findViewById(R.id.countDown);

        setButton = findViewById(R.id.button_set);
        startButton = findViewById(R.id.start);
        resetButton = findViewById(R.id.reset);

        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = editText.getText().toString();
                if (input.length() == 0){
                    Toast.makeText(MainActivity.this, "Field cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                long milliinput = Long.parseLong(input) * 60000;
                if (milliinput == 0){
                    Toast.makeText(MainActivity.this, "Enter a positive number", Toast.LENGTH_SHORT).show();
                    return;
                }

                setTime(milliinput);
                editText.setText("");
            }
        });

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

    private void setTime(long milliseconds){
        mStartTime = milliseconds;
        resetCountdown();
    }

    public void startTimer(){
        endTime = System.currentTimeMillis() + milliTimeLeft;

        countdowntimer = new CountDownTimer(milliTimeLeft, 1000) {
            @Override
            public void onTick(long l) {
                milliTimeLeft = l;
                UpdateTimer();
            }

            @Override
            public void onFinish() {
                isActive = false;
                resetCountdown();
                updateButtons();
//                startButton.setText("Start");
            }
        };
        countdowntimer.start();
        isActive = true;
        updateButtons();
    }

    void stopTimer(){
        countdowntimer.cancel();
        isActive = false;
        updateButtons();
//        startButton.setText("START");
//        resetButton.setVisibility(View.VISIBLE);
    }

    public void UpdateTimer(){
        int minute = (int) milliTimeLeft / 60000;
        int seconds = (int) milliTimeLeft % 60000 / 1000;

        String timeLeft = String.format(Locale.getDefault(), "%02d:%02d", minute, seconds);
        countDown.setText(timeLeft);
    }

    private void updateButtons(){
        if (isActive){
            resetButton.setVisibility(View.INVISIBLE);
            startButton.setText("Pause");
        } else {
            startButton.setText("Start");

            if (milliTimeLeft < 1000) {
                startButton.setVisibility(View.INVISIBLE);
            } else {
                startButton.setVisibility(View.VISIBLE);
            }

            if (milliTimeLeft < mStartTime) {
                resetButton.setVisibility(View.VISIBLE);
            } else {
                resetButton.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void resetCountdown(){
        milliTimeLeft = mStartTime;
        UpdateTimer();
        updateButtons();
//        resetButton.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("millisecondsLeft", milliTimeLeft);
        outState.putBoolean("isRunning", isActive);
        outState.putLong("endTime", endTime);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        milliTimeLeft = savedInstanceState.getLong("millisecondsLeft");
        isActive = savedInstanceState.getBoolean("isRunning");
        updateButtons();
        UpdateTimer();

        if (isActive){
            endTime = savedInstanceState.getLong("endTime");
            milliTimeLeft = endTime - System.currentTimeMillis();
            startTimer();
        }
    }
}