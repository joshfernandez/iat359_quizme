package com.example.jam.joshfernandez_quizme;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import static com.example.jam.joshfernandez_quizme.R.layout.row;

public class DisplayFlashcardsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Button buttonCreateNewFlashcard, buttonDeleteFlashcardSet;
    private String DEFAULT = "NULL";

    private RecyclerView recyclerViewFlashcards;
    private RecyclerView.LayoutManager myLayoutManager;

    private MyDatabase db;
    private MyAdapter myAdapter;
    private MyHelper helper;
    ArrayList<String> mArrayList = new ArrayList<String>();

    static final int REQUEST_CREATE_FLASHCARD = 0; // This is the request code for requesting result from CreateFlashcard activity

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
            startActivityForResult(intent, REQUEST_CREATE_FLASHCARD);
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
                "row " + (1+position) + ":  " + flashcardTermTextView.getText() + " --> " + flashcardDefinitionTextView.getText(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==REQUEST_CREATE_FLASHCARD) //check that we're processing the response from CreateFlashcard
        {
            if(resultCode==RESULT_OK) //make sure the request was successful
            {

                if (data.hasExtra("Term Given"));
                {
                    Toast.makeText(this, "DisplayFlashcards Successful. Flashcard will be added.", Toast.LENGTH_SHORT).show();
                    String term_given = data.getExtras().getString("Term Given");
                    String definition_given = data.getExtras().getString("Definition Given");

                    addFlashcard(term_given, definition_given);
                }


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

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void addFlashcard(String term, String definition)
    {
        Toast.makeText(this, term + ": " + definition, Toast.LENGTH_SHORT).show();
        long id = db.insertData(term, definition);

        if (id < 0)
        {
            Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
        }
    }


}

