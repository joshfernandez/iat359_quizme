package com.example.jam.joshfernandez_quizme;

import android.content.Intent;
import android.graphics.Typeface;
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

    private int current_position, set_size;
    private String[] current_flashcard;
    private String current_term, current_definition;
    private boolean is_definition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practise_with_flashcards);


        /*
            PART A - Initialize UI elements.
        */

        textViewFlashcardPosition = (TextView) findViewById(R.id.textViewFlashcardPosition);
        textViewPractiseMain = (TextView) findViewById(R.id.textViewPractiseMain);
        buttonPrevious = (Button) findViewById(R.id.buttonPrevious);
        buttonFlip = (Button) findViewById(R.id.buttonFlip);
        buttonNext = (Button) findViewById(R.id.buttonNext);


        /*
            PART B - Retrieve flashcard set.
        */

        Intent data = getIntent();
        arrayListFlashcards = data.getStringArrayListExtra("Flashcard Set");


        /*
            PART C - Initialize the first flashcard on screen.
        */

        set_size = arrayListFlashcards.size();
        current_position = 0;
        is_definition = false; // Show the term.

        setCurrentPosition(current_position);
        getNewFlashcard(current_position);
        showPracticeView();


        /*
            PART D - Set button onClickListeners.
        */

        buttonPrevious.setOnClickListener((v) -> {
            if(current_position - 1 >= 0)
            {
                current_position--;
            }
            else
            {
                current_position = set_size - 1;
            }
            setCurrentPosition(current_position);
            getNewFlashcard(current_position);
            switchBackToTerm();
            showPracticeView();
        });

        buttonNext.setOnClickListener((v) -> {
            if(current_position + 1 < set_size)
            {
                current_position++;
            }
            else
            {
                current_position = 0;
            }
            setCurrentPosition(current_position);
            getNewFlashcard(current_position);
            switchBackToTerm();
            showPracticeView();
        });

        buttonFlip.setOnClickListener((v) -> {
            switchTermAndDefinition();
            showPracticeView();
        });
    }

    public void getNewFlashcard(int flashcard_position) {
        current_flashcard = arrayListFlashcards.get(flashcard_position).split(",");
        current_term = current_flashcard[0].trim();
        current_definition = current_flashcard[1].trim();
    }

    public void showPracticeView() {
        if(is_definition)
        {
            showCurrentDefinition(current_definition);
        }
        else
        {
            showCurrentTerm(current_term);
        }
    }

    public void showCurrentTerm(String term) {
        textViewPractiseMain.setText(term);
        textViewPractiseMain.setTextSize(60);
        textViewPractiseMain.setTypeface(Typeface.DEFAULT_BOLD);
    }

    public void showCurrentDefinition(String definition) {
        textViewPractiseMain.setText(definition);
        textViewPractiseMain.setTextSize(36);
        textViewPractiseMain.setTypeface(Typeface.DEFAULT);
    }

    public void setCurrentPosition(int flashcard_position) {
        String view_position = Integer.toString(current_position + 1) + " / " + Integer.toString(set_size);
        textViewFlashcardPosition.setText(view_position);
    }

    public void switchTermAndDefinition() {
        if(is_definition)
        {
            is_definition = false; // Switch definition back as a term.
        }
        else
        {
            is_definition = true; // Switch term as a definition.
        }
    }

    public void switchBackToTerm() {
       is_definition = false; // Switch the text view back as a term.
    }

}
