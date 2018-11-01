package com.example.jam.joshfernandez_quizme;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    private TextView textViewProfileName;
    private String DEFAULT = "NULL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        textViewProfileName = (TextView)findViewById(R.id.textViewProfileName);

        SharedPreferences sharedPreferences = getSharedPreferences("UserRegistrationData", MODE_PRIVATE);
        String name = sharedPreferences.getString("name", DEFAULT);

        textViewProfileName.setText("Hello, " + name + "!");
    }
}
