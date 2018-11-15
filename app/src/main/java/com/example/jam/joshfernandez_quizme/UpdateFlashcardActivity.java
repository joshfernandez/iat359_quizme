package com.example.jam.joshfernandez_quizme;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateFlashcardActivity extends AppCompatActivity {

    private Button buttonUpdateFlashcard, buttonLookUp;
    private EditText editTextUpdateTerm, editTextUpdateDefinition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_flashcard);

        buttonUpdateFlashcard = (Button) findViewById(R.id.buttonUpdateFlashcard);
        buttonLookUp = (Button) findViewById(R.id.buttonLookUp);
        editTextUpdateTerm = (EditText) findViewById(R.id.editTextUpdateTerm);
        editTextUpdateDefinition = (EditText) findViewById(R.id.editTextUpdateDefinition);

        Intent data = getIntent();

        editTextUpdateTerm.setText(data.getStringExtra("Term"));
        editTextUpdateDefinition.setText(data.getStringExtra("Definition"));

        buttonLookUp.setOnClickListener((v) -> {
            String term_given = editTextUpdateTerm.getText().toString();

            Toast.makeText(this, "Proceed to the dictionary for " + term_given, Toast.LENGTH_SHORT).show();
            Uri webpage = Uri.parse("http://www.merriam-webster.com/dictionary/" + term_given);
            Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
            startActivity(webIntent);
        });
    }
}
