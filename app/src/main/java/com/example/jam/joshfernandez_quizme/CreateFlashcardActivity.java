package com.example.jam.joshfernandez_quizme;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

public class CreateFlashcardActivity extends AppCompatActivity {

    String term, definition;
    private Button buttonAddNewFlashcard, buttonLookUp;
    private EditText editTextFlashcardTerm, editTextFlashcardDefinition;
    private String DEFAULT = "NULL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_flashcard);

        buttonAddNewFlashcard = (Button) findViewById(R.id.buttonAddNewFlashcard);
        buttonLookUp = (Button) findViewById(R.id.buttonLookUp);
        editTextFlashcardTerm = (EditText) findViewById(R.id.editTextFlashcardTerm);
        editTextFlashcardDefinition = (EditText) findViewById(R.id.editTextFlashcardDefinition);

        buttonLookUp.setOnClickListener((v) -> {
            String term_given = editTextFlashcardTerm.getText().toString();
            Uri web_page = Uri.parse("http://www.merriam-webster.com/dictionary/" + term_given);
            Intent webIntent = new Intent(Intent.ACTION_VIEW, web_page);
            startActivity(webIntent);
        });

        buttonAddNewFlashcard.setOnClickListener((v) -> {

            term = editTextFlashcardTerm.getText().toString();
            definition = editTextFlashcardDefinition.getText().toString();

            // Removes leading and trailing whitespace
            term.trim();
            definition.trim();

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
