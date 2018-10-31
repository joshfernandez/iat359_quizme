package com.example.jam.joshfernandez_quizme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateFlashcardActivity extends AppCompatActivity {

    private Button buttonAddNewFlashcard;
    private EditText editTextFlashcardTerm, editTextFlashcardDefinition;
    private String DEFAULT = "NULL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_flashcard);

        buttonAddNewFlashcard = (Button)findViewById(R.id.buttonAddNewFlashcard);
        editTextFlashcardTerm = (EditText)findViewById(R.id.editTextFlashcardTerm);
        editTextFlashcardDefinition = (EditText)findViewById(R.id.editTextFlashcardDefinition);

        buttonAddNewFlashcard.setOnClickListener((v)->{
            Toast.makeText(this, "Going back to Display Flashcards Activity", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CreateFlashcardActivity.this, DisplayFlashcardsActivity.class);
            startActivity(intent);
        });

    }
}
