package com.example.jam.joshfernandez_quizme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainMenuActivity extends AppCompatActivity {

    private Button buttonCreateNewFlashcardSet, buttonUpdateSettings;
    private String DEFAULT = "NULL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        buttonCreateNewFlashcardSet = (Button)findViewById(R.id.buttonCreateNewFlashcardSet);
        buttonUpdateSettings = (Button)findViewById(R.id.buttonUpdateSettings);

        buttonCreateNewFlashcardSet.setOnClickListener((v)->{
            Toast.makeText(this, "Proceed to Display Flashcards Activity", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainMenuActivity.this, DisplayFlashcardsActivity.class);
            startActivity(intent);
        });

        buttonUpdateSettings.setOnClickListener((v)->{
            Toast.makeText(this, "Proceed to Settings Activity", Toast.LENGTH_SHORT).show();
            //Intent intent = new Intent(MainMenuActivity.this, SettingsActivity.class);
            //startActivity(intent);
        });
    }
}
