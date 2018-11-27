package com.example.jam.joshfernandez_quizme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextName, editTextPswd;
    private Button buttonLogin;
    private String DEFAULT = "NULL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextName = (EditText) findViewById(R.id.editTextLoginName);
        editTextPswd = (EditText) findViewById(R.id.editTextLoginPswd);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);

        // The username and password entered by the user will be compared with the data in the preferences file
        // And if there is a match, the login button lets the user access another activity

        buttonLogin.setOnClickListener((v) -> {
            SharedPreferences sharedPreferences = getSharedPreferences("UserRegistrationData", MODE_PRIVATE);
            String name = sharedPreferences.getString("name", DEFAULT);
            String password = sharedPreferences.getString("password", DEFAULT);

            if ((name.equals(editTextName.getText().toString())) && password.equals(editTextPswd.getText().toString())) {
                Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Username and/or password is invalid. Please register again.", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();

                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

}
