package com.example.jam.joshfernandez_quizme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;

public class DisplayFlashcardsActivity extends AppCompatActivity {

    private Button buttonCreateNewFlashcard, buttonDeleteFlashcardSet;
    private String DEFAULT = "NULL";

    private RecyclerView recyclerViewFlashcards;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager myLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_flashcards);

        /*
            PART A - Prepare UI elements and listeners.
         */

        buttonCreateNewFlashcard = (Button)findViewById(R.id.buttonCreateNewFlashcard);
        buttonDeleteFlashcardSet = (Button)findViewById(R.id.buttonDeleteFlashcardSet);

        buttonCreateNewFlashcard.setOnClickListener((v)->{
            Toast.makeText(this, "Proceed to Create Flashcard Activity", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DisplayFlashcardsActivity.this, CreateFlashcardActivity.class);
            startActivity(intent);
        });

        buttonDeleteFlashcardSet.setOnClickListener((v)->{

            /*
                1. Show a window to confirm that the user wants to delete the set.
                2. If they hit OK, delete the set and go back to main menu.
             */

            Toast.makeText(this, "Going back to Main Menu Activity", Toast.LENGTH_SHORT).show();
            //Intent intent = new Intent(MainMenuActivity.this, SettingsActivity.class);
            //startActivity(intent);
        });


        /*
            PART B - Prepare RecyclerView.
         */

        //recyclerViewFlashcards = (RecyclerView) findViewById(R.id.recyclerViewFlashcards);
        //
        //// Initialize myAdapter.
        //myAdapter = new MyAdapter(listSensorNames, this);
        //recyclerViewFlashcards.setAdapter(myAdapter);
        //
        //// Use a Linear Layout manager.
        //myLayoutManager = new LinearLayoutManager(this);
        //recyclerViewFlashcards.setLayoutManager(myLayoutManager);

    }
}
