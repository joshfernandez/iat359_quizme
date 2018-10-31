package com.example.jam.joshfernandez_quizme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private Button buttonReg;
    private EditText editTextName, editTextPswd;
    private String DEFAULT = "NULL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        buttonReg = (Button) findViewById(R.id.buttonReg);
        editTextName = (EditText) findViewById(R.id.editTextRegName);
        editTextPswd = (EditText) findViewById(R.id.editTextRegPswd);

        SharedPreferences sharedPreferences = getSharedPreferences("UserRegistrationData", MODE_PRIVATE);
        String name = sharedPreferences.getString("name", DEFAULT);
        String password = sharedPreferences.getString("password", DEFAULT);

        // If the user accesses the application for the first time, they should be presented
        // with a screen asking them to enter a username and password
        if (!name.equals(DEFAULT)) {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        buttonReg.setOnClickListener((v) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("name", editTextName.getText().toString());
            editor.putString("password", editTextPswd.getText().toString());
            editor.commit();

            Toast.makeText(this, "Proceed to Login Activity", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });

    }
}
