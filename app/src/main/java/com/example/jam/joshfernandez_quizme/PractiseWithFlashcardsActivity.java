package com.example.jam.joshfernandez_quizme;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PractiseWithFlashcardsActivity extends AppCompatActivity {

    private TextView textViewFlashcardPosition, textViewPractiseMain;
    private Button buttonPrevious, buttonFlip, buttonNext, buttonShuffle, buttonSeeFirst;
    ArrayList<String> arrayListFlashcards = new ArrayList<String>();
    ArrayList<String> arrayListCurrent = new ArrayList<String>();

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
        buttonShuffle = (Button) findViewById(R.id.buttonShuffle);
        buttonSeeFirst = (Button) findViewById(R.id.buttonSeeFirst);


        /*
            PART B - Retrieve flashcard set.
        */

        Intent data = getIntent();
        arrayListFlashcards = data.getStringArrayListExtra("Flashcard Set");


        /*
            PART C - Initialize the first flashcard on screen.
        */

        arrayListCurrent = arrayListFlashcards;

        set_size = arrayListCurrent.size();
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

        buttonShuffle.setOnClickListener((v) -> {
            arrayListCurrent = shuffleArray(arrayListFlashcards);
            current_position = 0;

            switchBackToTerm();
            setCurrentPosition(current_position);
            getNewFlashcard(current_position);
            showPracticeView();
        });


        /*
            PART E - Set textView onClickListener.
        */

        textViewPractiseMain.setOnClickListener((v) -> {
            switchTermAndDefinition();
            showPracticeView();
        });


    }

    public void getNewFlashcard(int flashcard_position) {
        current_flashcard = arrayListCurrent.get(flashcard_position).split(",");
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

    // Source: https://stackoverflow.com/questions/2450954/how-to-randomize-shuffle-a-javascript-array
    public ArrayList<String> shuffleArray(ArrayList<String> arrList) {
        int currentIndex = arrList.size();
        String temporaryValue;
        int randomIndex;

        // While there remain elements to shuffle...
        while (0 != currentIndex) {

            // Pick a remaining element...
            double draftRandomIndex = Math.floor(Math.random() * currentIndex);
            randomIndex = (int) draftRandomIndex ;
            currentIndex -= 1;

            // And swap it with the current element.
            temporaryValue = arrList.get(currentIndex);
            arrList.set(currentIndex, arrList.get(randomIndex));
            arrList.set(randomIndex, temporaryValue);
        }

        return arrList;
    }

}
