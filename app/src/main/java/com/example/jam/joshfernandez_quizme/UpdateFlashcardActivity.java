package com.example.jam.joshfernandez_quizme;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

public class UpdateFlashcardActivity extends AppCompatActivity {

    String term_old, definition_old, term_new, definition_new;
    private Button buttonUpdateFlashcard, buttonDeleteFlashcard, buttonLookUp;
    private EditText editTextUpdateTerm, editTextUpdateDefinition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_flashcard);


        /*
            PART A - Prepare UI elements and listeners.
        */

        buttonUpdateFlashcard = (Button) findViewById(R.id.buttonUpdateFlashcard);
        buttonDeleteFlashcard = (Button) findViewById(R.id.buttonDeleteFlashcard);
        buttonLookUp = (Button) findViewById(R.id.buttonLookUp);
        editTextUpdateTerm = (EditText) findViewById(R.id.editTextUpdateTerm);
        editTextUpdateDefinition = (EditText) findViewById(R.id.editTextUpdateDefinition);


        /*
            PART B - Retrieve intent and define term and definition.
        */

        Intent data = getIntent();

        term_old = data.getStringExtra("Term");
        definition_old = data.getStringExtra("Definition");

        editTextUpdateTerm.setText(term_old);
        editTextUpdateDefinition.setText(definition_old);


        /*
            PART C - Set button onClickListeners.
        */

        buttonLookUp.setOnClickListener((v) -> {
            String term_given = editTextUpdateTerm.getText().toString();
            Uri web_page = Uri.parse("http://www.merriam-webster.com/dictionary/" + term_given);
            Intent webIntent = new Intent(Intent.ACTION_VIEW, web_page);
            startActivity(webIntent);
        });

        buttonUpdateFlashcard.setOnClickListener((v) -> {

            // Retrieve the strings inside each of the TextViews
            term_new = editTextUpdateTerm.getText().toString();
            definition_new = editTextUpdateDefinition.getText().toString();

            // Removes leading and trailing whitespace
            term_new.trim();
            definition_new.trim();

            Intent i = getIntent(); // Getting the intent that has started this activity
            i.putExtra("Update Flashcard", "Update Flashcard");
            i.putExtra("Term To Be Updated", term_old);
            i.putExtra("Term Given", term_new);
            i.putExtra("Definition Given", definition_new);

            editTextUpdateTerm.setText("");
            editTextUpdateDefinition.setText("");

            setResult(RESULT_OK, i);
            finish();

        });

        buttonDeleteFlashcard.setOnClickListener((v) -> {

            Intent i = getIntent(); // Getting the intent that has started this activity
            i.putExtra("Delete Flashcard", "Delete Flashcard");
            i.putExtra("Term To Be Deleted", term_old);

            editTextUpdateTerm.setText("");
            editTextUpdateDefinition.setText("");

            setResult(RESULT_OK, i);
            finish();

        });
    }
}
