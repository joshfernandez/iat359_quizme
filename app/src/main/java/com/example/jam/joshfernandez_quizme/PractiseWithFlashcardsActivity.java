package com.example.jam.joshfernandez_quizme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PractiseWithFlashcardsActivity extends AppCompatActivity {

    private TextView textViewFlashcardPosition, textViewPractiseMain;
    private Button buttonPrevious, buttonFlip, buttonNext;
    ArrayList<String> arrayListFlashcards = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practise_with_flashcards);

        textViewFlashcardPosition = (TextView) findViewById(R.id.textViewFlashcardPosition);
        textViewPractiseMain = (TextView) findViewById(R.id.textViewPractiseMain);
        buttonPrevious = (Button) findViewById(R.id.buttonPrevious);
        buttonFlip = (Button) findViewById(R.id.buttonFlip);
        buttonNext = (Button) findViewById(R.id.buttonNext);

        Intent data = getIntent();
        arrayListFlashcards = data.getStringArrayListExtra("Flashcard Set");

        for(String flashcard : arrayListFlashcards)
        {
            Toast.makeText(this, flashcard, Toast.LENGTH_SHORT).show();
        }
    }
}
