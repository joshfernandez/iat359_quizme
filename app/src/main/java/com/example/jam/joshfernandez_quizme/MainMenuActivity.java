package com.example.jam.joshfernandez_quizme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenuActivity extends AppCompatActivity {

    private Button buttonCreateNewFlashcardSet, buttonUpdateSettings;
    private TextView textViewWelcomeTitle, textViewWelcomeDescription;
    private String DEFAULT = "NULL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        buttonCreateNewFlashcardSet = (Button) findViewById(R.id.buttonCreateNewFlashcardSet);
        buttonUpdateSettings = (Button) findViewById(R.id.buttonUpdateSettings);

        buttonCreateNewFlashcardSet.setOnClickListener((v) -> {
            Intent intent = new Intent(MainMenuActivity.this, DisplayFlashcardsActivity.class);
            startActivity(intent);
        });

        buttonUpdateSettings.setOnClickListener((v) -> {
            Intent intent = new Intent(MainMenuActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        textViewWelcomeTitle = (TextView) findViewById(R.id.textViewWelcomeTitle);
        textViewWelcomeDescription = (TextView) findViewById(R.id.textViewWelcomeDescription);

        String welcome_title = "Welcome to the QuizMe flashcard application!";
        String welcome_description =
                "It is intended for students like you to review terms, definitions, and concepts in a fun and interactive way. " +
                        "QuizMe has flashcards and games to help you study for quizzes and tests.\n\n" +
                        "To begin, tap on \"Create New Flashcard Set\" below.";

        textViewWelcomeTitle.setText(welcome_title);
        textViewWelcomeDescription.setText(welcome_description);
    }
}
