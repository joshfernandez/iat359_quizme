package com.example.jam.joshfernandez_quizme;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PractiseWithFlashcardsActivity extends AppCompatActivity {

    ArrayList<String> arrayListFlashcards = new ArrayList<String>(); // The array of flashcards transmitted from DisplayFlashcardsActivity
    private ArrayList<String> arrayListCurrent = new ArrayList<String>(); // The current array being presented; used for shuffling
    private String seeFirst = "Term"; // The default practice view
    private TextView textViewFlashcardPosition, textViewPractiseMain;
    private Button buttonPrevious, buttonFlip, buttonNext, buttonShuffle, buttonSeeFirst;
    private int current_position, set_size;
    private String[] current_flashcard;
    private String current_term, current_definition;
    private boolean is_definition; // Used to indicate whether the user wants to see the term or definition

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

        arrayListCurrent = arrayListFlashcards; // Make the array of flashcards the array to be presented


        /*
            PART C - Initialize the first flashcard on screen.
        */

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
            /*
                If the view can get a flashcard before the current one, retrieve it.
                If it cannot, it means it reached the start of the flashcard set
                and can return back to the end of the list.
             */
            if (current_position - 1 >= 0) {
                current_position--;
            } else {
                current_position = set_size - 1;
            }

            setCurrentPosition(current_position);
            getNewFlashcard(current_position);
            switchBackToDefault();
            showPracticeView();
        });

        buttonNext.setOnClickListener((v) -> {
            /*
                If the view can get a flashcard after the current one, retrieve it.
                If it cannot, it means it reached the end of the flashcard set
                and can return back to the start of the list.
             */
            if (current_position + 1 < set_size) {
                current_position++;
            } else {
                current_position = 0;
            }

            setCurrentPosition(current_position);
            getNewFlashcard(current_position);
            switchBackToDefault();
            showPracticeView();
        });

        // Flip the view to either term or definition and show it on screen.
        buttonFlip.setOnClickListener((v) -> {
            switchTermAndDefinition();
            showPracticeView();
        });

        // Shuffle the list of flashcards, return back to the starting position,
        // and show the first flashcard on screen.
        buttonShuffle.setOnClickListener((v) -> {
            arrayListCurrent = shuffleArray(arrayListFlashcards);
            current_position = 0;

            switchBackToDefault();
            setCurrentPosition(current_position);
            getNewFlashcard(current_position);
            showPracticeView();
        });

        buttonSeeFirst.setOnClickListener((v) -> {
            if (seeFirst.equals("Term")) { // If the layout is term-first, change it to definition-first

                Toast.makeText(this, "Switching from Term-First to Definition-First", Toast.LENGTH_LONG).show();

                seeFirst = "Definition";
                is_definition = true; // The default should be the definition.

                String changeSeeFirst = "See Term First";
                buttonSeeFirst.setText(changeSeeFirst);


            } else if (seeFirst.equals("Definition")) { // If the layout is definition-first, change it to term-first

                Toast.makeText(this, "Switching from Definition-First to Term-First", Toast.LENGTH_LONG).show();

                seeFirst = "Term";
                is_definition = false; // The default should be the term.

                String changeSeeFirst = "See Definition First";
                buttonSeeFirst.setText(changeSeeFirst);

            }
            showPracticeView(); // Set the flashcard view for the current flashcard already.
        });


        /*
            PART E - Set textView onClickListener to switch between term and definition mode.
        */

        textViewPractiseMain.setOnClickListener((v) -> {
            switchTermAndDefinition();
            showPracticeView();
        });


    }

    /*
        HELPER FUNCTIONS
    */

    // Retrieves a new flashcard string and parses its term and definition
    public void getNewFlashcard(int flashcard_position) {
        current_flashcard = arrayListCurrent.get(flashcard_position).split(",");
        current_term = current_flashcard[0].trim();
        current_definition = current_flashcard[1].trim();
    }

    public void showPracticeView() {
        if (is_definition) {
            showCurrentDefinition(current_definition);
        } else {
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

    // In the format "Current Position / Total Number of Flashcards"
    public void setCurrentPosition(int flashcard_position) {
        String view_position = Integer.toString(current_position + 1) + " / " + Integer.toString(set_size);
        textViewFlashcardPosition.setText(view_position);
    }

    public void switchTermAndDefinition() {
        if (is_definition) {
            is_definition = false; // Switch definition back as a term.
        } else {
            is_definition = true; // Switch term as a definition.
        }
    }

    public void switchBackToDefault() {
        if (seeFirst.equals("Term")) {
            is_definition = false; // Switch the text view back as a term.
        } else if (seeFirst.equals("Definition")) {
            is_definition = true; // Switch the text view back as a definition.
        }
    }

    // Source: https://stackoverflow.com/questions/2450954/how-to-randomize-shuffle-a-javascript-array
    public ArrayList<String> shuffleArray(ArrayList<String> arrList) {
        int currentIndex = arrList.size();
        String temporaryValue = "";
        int randomIndex = 0;

        // While there remain elements to shuffle...
        while (0 != currentIndex) {

            // Pick a remaining element...
            randomIndex = (int) Math.floor(Math.random() * currentIndex);
            currentIndex -= 1;

            // And swap it with the current element.
            temporaryValue = arrList.get(currentIndex);
            arrList.set(currentIndex, arrList.get(randomIndex));
            arrList.set(randomIndex, temporaryValue);
        }

        return arrList;
    }

}
