package com.example.jam.joshfernandez_quizme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateFlashcardActivity extends AppCompatActivity {

    private Button buttonAddNewFlashcard;
    private EditText editTextFlashcardTerm, editTextFlashcardDefinition;
    private String DEFAULT = "NULL";

    String term, definition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_flashcard);

        buttonAddNewFlashcard = (Button)findViewById(R.id.buttonAddNewFlashcard);
        editTextFlashcardTerm = (EditText)findViewById(R.id.editTextFlashcardTerm);
        editTextFlashcardDefinition = (EditText)findViewById(R.id.editTextFlashcardDefinition);

        buttonAddNewFlashcard.setOnClickListener((v)->{

            term = editTextFlashcardTerm.getText().toString();
            definition = editTextFlashcardDefinition.getText().toString();
            Toast.makeText(this, "Going back to Display Flashcards Activity with" + term + ": " + definition, Toast.LENGTH_SHORT).show();

            Intent i = getIntent(); // Getting the intent that has started this activity
            i.putExtra("Term Given", term);
            i.putExtra("Definition Given", definition);

            editTextFlashcardTerm.setText("");
            editTextFlashcardDefinition.setText("");

            setResult(RESULT_OK, i);
            finish();

        });

    }

}
