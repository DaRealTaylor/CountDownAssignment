package com.example.countdownassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

    //Starts and stop the timer
    public void startStop(){
        if (isActive){
            stopTimer();
        } else {
            startTimer();
        }
    }

    //Sets the time to the user's input
    private void setTime(long milliseconds){
        mStartTime = milliseconds;
        resetCountdown();
        closeKeyboard();
    }

    //Starts the countdown timer at an interval of 1 second per tick
    public void startTimer(){
        endTime = System.currentTimeMillis() + milliTimeLeft;
        countdowntimer = new CountDownTimer(milliTimeLeft, 1000) {

            //Ticks down the timer
            @Override
            public void onTick(long l) {
                milliTimeLeft = l;
                UpdateTimer();
            }

            //Stops the countdown upon reaching 00:00:00 and resets it to initial set time
            @Override
            public void onFinish() {
                isActive = false;
                resetCountdown();
                UpdateInterface();
            }
        };
        countdowntimer.start();
        isActive = true;
        UpdateInterface();
    }

    //Stops the countdown once called
    void stopTimer(){
        countdowntimer.cancel();
        isActive = false;
        UpdateInterface();
    }

    //Update and prints the remaining time left on the countdown
    public void UpdateTimer(){
        int hours = (int) (milliTimeLeft / 1000) / 3600;
        int minute = (int) ((milliTimeLeft / 1000) % 3600) / 60;
        int seconds = (int) milliTimeLeft / 1000 % 60;

        String timeLeft = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minute, seconds);
        countDown.setText(timeLeft);
    }

    //Updates the interface by making buttons VISIBLE or INVISIBLE
    //Changes buttons text from START to PAUSE depending if app is paused or not
    private void UpdateInterface(){
        if (isActive){
            editText.setVisibility(View.INVISIBLE);
            setButton.setVisibility(View.INVISIBLE);
            resetButton.setVisibility(View.INVISIBLE);
            startButton.setText("Pause");
        } else {
            startButton.setText("Start");
            editText.setVisibility(View.VISIBLE);
            setButton.setVisibility(View.VISIBLE);

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

    //Minimizes the phone keyboard once they press SET
    private void closeKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    //resets the countdown to initially set time
    private void resetCountdown(){
        milliTimeLeft = mStartTime;
        UpdateTimer();
        UpdateInterface();
    }

    //The following code saves the state the app is in and allows it to continue running after rotation
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
        UpdateInterface();
        UpdateTimer();

        if (isActive){
            endTime = savedInstanceState.getLong("endTime");
            milliTimeLeft = endTime - System.currentTimeMillis();
            startTimer();
        }
    }

    //Opens the settings screen upon click
    public void settingsClicked(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivityForResult(intent, SettingsActivity.SETTINGS_REQUEST);
    }
}