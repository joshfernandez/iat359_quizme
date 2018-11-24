package com.example.jam.joshfernandez_quizme;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayFlashcardsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    static final int REQUEST_CREATE_FLASHCARD = 0; // This is the request code for requesting result from CreateFlashcard activity
    static final int REQUEST_UPDATE_FLASHCARD = 1; // This is the request code for requesting result from UpdateFlashcard activity
    static final int DEFAULT_INTEGER = 0;
    ArrayList<String> mArrayList = new ArrayList<String>();
    private Button buttonCreateNewFlashcard, buttonDeleteFlashcardSet, buttonPlayHeadsUp;
    private String DEFAULT = "NULL";
    private RecyclerView recyclerViewFlashcards;
    private RecyclerView.LayoutManager myLayoutManager;
    private MyDatabase db;
    private MyAdapter myAdapter;
    private MyHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_flashcards);

        /*
            PART A - Prepare UI elements and listeners.
         */

        buttonCreateNewFlashcard = (Button) findViewById(R.id.buttonCreateNewFlashcard);
        buttonDeleteFlashcardSet = (Button) findViewById(R.id.buttonDeleteFlashcardSet);
        buttonPlayHeadsUp = (Button) findViewById(R.id.buttonPlayHeadsUp);

        buttonCreateNewFlashcard.setOnClickListener((v) -> {
            Toast.makeText(this, "Proceed to Create Flashcard Activity", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DisplayFlashcardsActivity.this, CreateFlashcardActivity.class);
            startActivityForResult(intent, REQUEST_CREATE_FLASHCARD);
        });

        buttonPlayHeadsUp.setOnClickListener((v) -> {
            Toast.makeText(this, "Proceed to Heads Up Activity", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DisplayFlashcardsActivity.this, HeadsUpActivity.class);
            startActivity(intent);
        });

        buttonDeleteFlashcardSet.setOnClickListener((v) -> {

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
        //myAdapter = new MyAdapter(courses, this);
        //recyclerViewFlashcards.setAdapter(myAdapter);

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
                "row " + (1 + position) + ":  " + flashcardTermTextView.getText() + " --> " + flashcardDefinitionTextView.getText(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CREATE_FLASHCARD) //check that we're processing the response from CreateFlashcard
        {
            if (resultCode == RESULT_OK) //make sure the request was successful
            {

                if (data.hasExtra("Term Given"))
                {
                    Toast.makeText(this, "DisplayFlashcards Successful. Flashcard will be added.", Toast.LENGTH_SHORT).show();
                    String term_given = data.getExtras().getString("Term Given");
                    String definition_given = data.getExtras().getString("Definition Given");

                    addFlashcard(term_given, definition_given);
                    updateRecyclerViewFlashcards();
                }

            }
        }
        else if (requestCode == REQUEST_UPDATE_FLASHCARD) //check that we're processing the response from UpdateFlashcard
        {
            if (resultCode == RESULT_OK) //make sure the request was successful
            {

                if (data.hasExtra("Term Given"))
                {
                    Toast.makeText(this, "DisplayFlashcards Successful. Flashcard will be updated.", Toast.LENGTH_SHORT).show();
                    String term_given = data.getExtras().getString("Term Given");
                    String definition_given = data.getExtras().getString("Definition Given");
                    int position_given = data.getExtras().getInt("Position Given", DEFAULT_INTEGER);

                    updateFlashcard(term_given, definition_given, position_given);
                    updateRecyclerViewFlashcards();
                }
                else if (data.hasExtra("Delete Flashcard"))
                {
                    Toast.makeText(this, "DisplayFlashcards Successful. Flashcard will be deleted.", Toast.LENGTH_SHORT).show();
                    int position_given = data.getExtras().getInt("Position Given", DEFAULT_INTEGER);
                    deleteFlashcard(position_given);
                    updateRecyclerViewFlashcards();
                }

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void addFlashcard(String term, String definition) {
        Toast.makeText(this, "Adding " + term + ": " + definition, Toast.LENGTH_SHORT).show();
        long id = db.insertData(term, definition);

        if (id < 0) {
            Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateFlashcard(String term, String definition, int position) {
        Toast.makeText(this, "Updating " + term + ": " + definition + " at position " + position, Toast.LENGTH_SHORT).show();
        long id = db.updateData(term, definition, position + 1);

        if (id < 0) {
            Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteFlashcard(int position) {
        Toast.makeText(this, "Deleting flashcard at position " + position, Toast.LENGTH_SHORT).show();
        long id = db.deleteData(position + 1);

        if (id < 0) {
            Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateRecyclerViewFlashcards() {
        mArrayList.clear();
        myAdapter.notifyDataSetChanged(); // advise the adapter that the data set has changed

        Cursor cursor = db.getData();

        int index1 = cursor.getColumnIndex(Constants.TERM);
        int index2 = cursor.getColumnIndex(Constants.DEFINITION);

        //ArrayList<String> mArrayList = new ArrayList<String>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String flashcardTerm = cursor.getString(index1);
            String flashcardDefinition = cursor.getString(index2);

            String s = flashcardTerm + ", " + flashcardDefinition;
            mArrayList.add(s);
            cursor.moveToNext();
        }

        Log.v("h2", "" + mArrayList);

        myAdapter.notifyDataSetChanged();  // advise the adapter that the data set has changed
    }


}

