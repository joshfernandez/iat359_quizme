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
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayFlashcardsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    static final int REQUEST_CREATE_FLASHCARD = 0; // This is the request code for requesting result from CreateFlashcard activity
    static final int REQUEST_UPDATE_FLASHCARD = 1; // This is the request code for requesting result from UpdateFlashcard activity
    ArrayList<String> arrayListFlashcards = new ArrayList<String>();
    private Button buttonCreateNewFlashcard, buttonPlayHeadsUp, buttonPractise;
    private String DEFAULT = "NULL";
    private RecyclerView recyclerViewFlashcards;
    private RecyclerView.LayoutManager myLayoutManager;
    private MyDatabase myDatabase;
    private MyAdapter myAdapter;
    private MyHelper myHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_flashcards);

        /*
            PART A - Prepare UI elements and listeners.
         */

        buttonCreateNewFlashcard = (Button) findViewById(R.id.buttonCreateNewFlashcard);
        buttonPlayHeadsUp = (Button) findViewById(R.id.buttonPlayHeadsUp);
        buttonPractise = (Button) findViewById(R.id.buttonPractise);

        buttonCreateNewFlashcard.setOnClickListener((v) -> {
            Intent intent = new Intent(DisplayFlashcardsActivity.this, CreateFlashcardActivity.class);
            startActivityForResult(intent, REQUEST_CREATE_FLASHCARD);
        });

        buttonPlayHeadsUp.setOnClickListener((v) -> {
            if (arrayListFlashcards.size() > 0) { // We can only proceed to play Heads Up! if there are flashcards in the set.
                Intent intent = new Intent(DisplayFlashcardsActivity.this, HeadsUpActivity.class);
                intent.putExtra("Flashcard Set", arrayListFlashcards);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Sorry! We cannot play Heads Up! on an empty flashcard set.", Toast.LENGTH_LONG).show();
            }

        });

        buttonPractise.setOnClickListener((v) -> {
            if (arrayListFlashcards.size() > 0) { // We can only proceed to practise if there are flashcards in the set.
                Intent intent = new Intent(DisplayFlashcardsActivity.this, PractiseWithFlashcardsActivity.class);
                intent.putExtra("Flashcard Set", arrayListFlashcards);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Sorry! We cannot practise on an empty flashcard set.", Toast.LENGTH_LONG).show();
            }
        });


        /*
            PART B - Prepare RecyclerView.
         */

        recyclerViewFlashcards = (RecyclerView) findViewById(R.id.recyclerViewFlashcards);

        // Use a Linear Layout manager.
        myLayoutManager = new LinearLayoutManager(this);
        recyclerViewFlashcards.setLayoutManager(myLayoutManager);


        /*
            PART C - Prepare SQLite Database.
         */

        myDatabase = new MyDatabase(this);
        myHelper = new MyHelper(this);

        Cursor cursor = myDatabase.getData();

        int index1 = cursor.getColumnIndex(Constants.TERM);
        int index2 = cursor.getColumnIndex(Constants.DEFINITION);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String flashcardTerm = cursor.getString(index1);
            String flashcardDefinition = cursor.getString(index2);

            String s = flashcardTerm + ", " + flashcardDefinition;
            arrayListFlashcards.add(s);
            cursor.moveToNext();
        }

        myAdapter = new MyAdapter(arrayListFlashcards, this);
        recyclerViewFlashcards.setAdapter(myAdapter);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //LinearLayout clickedRow = (LinearLayout) view;
        //TextView flashcardTermTextView = (TextView) view.findViewById(R.id.flashcardTermEntry);
        //TextView flashcardDefinitionTextView = (TextView) view.findViewById(R.id.flashcardDefinitionEntry);
        //
        //Toast.makeText(this,
        //        "row " + (1 + position) + ":  " + flashcardTermTextView.getText() + " --> " + flashcardDefinitionTextView.getText(),
        //        Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CREATE_FLASHCARD) //check that we're processing the response from CreateFlashcard
        {
            if (resultCode == RESULT_OK) //make sure the request was successful
            {

                if (data.hasExtra("Term Given")) {
                    String term_given = data.getExtras().getString("Term Given");
                    String definition_given = data.getExtras().getString("Definition Given");

                    addFlashcard(term_given, definition_given);
                    updateRecyclerViewFlashcards();
                }

            }
        } else if (requestCode == REQUEST_UPDATE_FLASHCARD) //check that we're processing the response from UpdateFlashcard
        {
            if (resultCode == RESULT_OK) //make sure the request was successful
            {

                if (data.hasExtra("Update Flashcard")) {
                    String term_to_be_updated = data.getExtras().getString("Term To Be Updated");
                    String term_given = data.getExtras().getString("Term Given");
                    String definition_given = data.getExtras().getString("Definition Given");

                    updateFlashcard(term_to_be_updated, term_given, definition_given);
                    updateRecyclerViewFlashcards();
                } else if (data.hasExtra("Delete Flashcard")) {
                    String term_given = data.getExtras().getString("Term To Be Deleted");
                    deleteFlashcard(term_given);
                    updateRecyclerViewFlashcards();
                }

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void addFlashcard(String term, String definition) {
        // First, we need to take care of duplicate flashcards.
        if (!myDatabase.getSelectedData(term).isEmpty()) {
            Toast.makeText(this, "ERROR: You cannot add a duplicate flashcard of the same term " + term, Toast.LENGTH_LONG).show();
        } else {
            long id = myDatabase.insertData(term, definition);

            if (id < 0) {
                Log.e("Add Flashcard", "Flashcard creation failed for " + term);
            } else {
                // Creation successful - nothing happens
            }
        }
    }

    public void updateFlashcard(String term_old, String term_new, String definition) {
        long id = myDatabase.updateData(term_old, term_new, definition);

        if (id < 0) {
            Log.e("Update Flashcard", "Flashcard update failed for " + term_old + " --> " + term_new);
        } else {
            // Update successful - nothing happens
        }
    }

    public void deleteFlashcard(String term) {
        long id = myDatabase.deleteData(term);

        if (id < 0) {
            Log.e("Delete Flashcard", "Flashcard deletion failed for " + term);
        } else {
            // Deletion successful - nothing happens
        }
    }

    public void updateRecyclerViewFlashcards() {
        arrayListFlashcards.clear();
        myAdapter.notifyDataSetChanged(); // advise the adapter that the data set has changed

        Cursor cursor = myDatabase.getData();

        int index1 = cursor.getColumnIndex(Constants.TERM);
        int index2 = cursor.getColumnIndex(Constants.DEFINITION);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String flashcardTerm = cursor.getString(index1);
            String flashcardDefinition = cursor.getString(index2);

            String s = flashcardTerm + ", " + flashcardDefinition;
            arrayListFlashcards.add(s);
            cursor.moveToNext();
        }

        Log.v("Update recyclerViewFlashcards", "" + arrayListFlashcards);

        myAdapter.notifyDataSetChanged();  // advise the adapter that the data set has changed
    }


}

