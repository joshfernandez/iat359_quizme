package com.example.jam.joshfernandez_quizme;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateFlashcardActivity extends AppCompatActivity {

    private Button buttonAddNewFlashcard, buttonLookUp;
    private EditText editTextFlashcardTerm, editTextFlashcardDefinition;
    private String DEFAULT = "NULL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_flashcard);

        buttonAddNewFlashcard = (Button)findViewById(R.id.buttonAddNewFlashcard);
        buttonLookUp = (Button)findViewById(R.id.buttonLookUp);
        editTextFlashcardTerm = (EditText)findViewById(R.id.editTextFlashcardTerm);
        editTextFlashcardDefinition = (EditText)findViewById(R.id.editTextFlashcardDefinition);

        buttonLookUp.setOnClickListener((v)->{
            String term_given = editTextFlashcardTerm.getText().toString();

            Toast.makeText(this, "Proceed to the dictionary for " + term_given, Toast.LENGTH_SHORT).show();
            Uri webpage = Uri.parse("http://www.merriam-webster.com/dictionary/" + term_given);
            Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
            startActivity(webIntent);
        });

        buttonAddNewFlashcard.setOnClickListener((v)->{
            Toast.makeText(this, "Going back to Display Flashcards Activity", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CreateFlashcardActivity.this, DisplayFlashcardsActivity.class);
            startActivity(intent);
        });

    }
}
