package com.example.countdownassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {
    public static final int SETTINGS_REQUEST = 1;
    private EditText editText;
    Button doneButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        doneButton = (Button)findViewById(R.id.Done);

    }

    public void doneClicked(View view){
        String text = editText.getText().toString();
        int speed = Integer.parseInt(text);

        Intent intent = new Intent(String.valueOf(speed));
        intent.putExtra("speed", speed);
        setResult(RESULT_OK, intent);
        finish();
    }
}