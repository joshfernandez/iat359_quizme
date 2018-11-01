package com.example.jam.joshfernandez_quizme;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class DisplayFlashcardsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Button buttonCreateNewFlashcard, buttonDeleteFlashcardSet;
    private String DEFAULT = "NULL";

    private RecyclerView recyclerViewFlashcards;
    //private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager myLayoutManager;

    private MyDatabase db;
    private MyAdapter myAdapter;
    private MyHelper helper;

    ArrayList<String> courses = new ArrayList<String>(
            Arrays.asList("IAT100", "IAT102", "IAT201", "IAT235", "IAT265", "IAT312", "IAT 339", "IAT 359",
                            "IAT381", "IAT351","IAT336", "IAT337", "IAT401", "IAT111", "IAT222", "IAT333", "IAT444", "IAT555"));

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

        recyclerViewFlashcards = (RecyclerView) findViewById(R.id.recyclerViewFlashcards);

        // Initialize myAdapter.
        myAdapter = new MyAdapter(courses, this);
        recyclerViewFlashcards.setAdapter(myAdapter);

        // Use a Linear Layout manager.
        myLayoutManager = new LinearLayoutManager(this);
        recyclerViewFlashcards.setLayoutManager(myLayoutManager);


        /*
            PART C - Prepare SQLite Database.
         */

        db = new MyDatabase(this);
        helper = new MyHelper(this);

        Cursor cursor = db.getData();

        int index1 = cursor.getColumnIndex(Constants.TERM);
        int index2 = cursor.getColumnIndex(Constants.DEFINITION);

        ArrayList<String> mArrayList = new ArrayList<String>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String flashcardTerm = cursor.getString(index1);
            String flashcardDefinition = cursor.getString(index2);

            String s = flashcardTerm + ", " + flashcardDefinition;
            mArrayList.add(s);
            cursor.moveToNext();
        }

        myAdapter = new MyAdapter(mArrayList, this);
        recyclerViewFlashcards.setAdapter(myAdapter);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LinearLayout clickedRow = (LinearLayout) view;
        TextView flashcardTermTextView = (TextView) view.findViewById(R.id.flashcardTermEntry);
        TextView flashcardDefinitionTextView = (TextView) view.findViewById(R.id.flashcardDefinitionEntry);

        Toast.makeText(this,
                "row " + (1+position) + ":  " + flashcardTermTextView.getText() + " --> " + flashcardDefinitionTextView.getText(),
                Toast.LENGTH_LONG).show();
    }
}
