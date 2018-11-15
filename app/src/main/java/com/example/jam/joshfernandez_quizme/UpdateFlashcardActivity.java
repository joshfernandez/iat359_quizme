package com.example.jam.joshfernandez_quizme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class UpdateFlashcardActivity extends AppCompatActivity {

    private EditText editTextUpdateTerm, editTextUpdateDefinition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_flashcard);

        editTextUpdateTerm = (EditText) findViewById(R.id.editTextUpdateTerm);
        editTextUpdateDefinition = (EditText) findViewById(R.id.editTextUpdateDefinition);

        Intent data = getIntent();

        editTextUpdateTerm.setText(data.getStringExtra("Term"));
        editTextUpdateDefinition.setText(data.getStringExtra("Definition"));
    }
}
