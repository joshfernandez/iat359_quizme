package com.example.jam.joshfernandez_quizme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MyDatabase {

    private final MyHelper helper;
    private SQLiteDatabase db;
    private Context context;

    public MyDatabase(Context c) {
        context = c;
        helper = new MyHelper(context);
    }

    public long insertData(String term, String definition) {
        db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.TERM, term);
        contentValues.put(Constants.DEFINITION, definition);

        long id = db.insert(Constants.TABLE_NAME, null, contentValues);
        return id;
    }

    public long updateData(String term, String definition, int position) {
        db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.TERM, term);
        contentValues.put(Constants.DEFINITION, definition);

        long id = db.update(Constants.TABLE_NAME, contentValues, "_id=" + position, null);
        return id;
    }

    public long deleteData(int position) {
        db = helper.getWritableDatabase();

        long id = db.delete(Constants.TABLE_NAME, "_id=" + position, null);
        return id;
    }

    public Cursor getData() {
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] columns = {Constants.UID, Constants.TERM, Constants.DEFINITION};
        Cursor cursor = db.query(Constants.TABLE_NAME, columns, null, null, null, null, null);
        return cursor;
    }


    public String getSelectedData(String term) {
        //select flashcards from database of type 'term'
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {Constants.TERM, Constants.DEFINITION};

        String selection = Constants.TERM + "='" + term + "'";  //Constants.TERM = 'term'
        Cursor cursor = db.query(Constants.TABLE_NAME, columns, selection, null, null, null, null);

        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {

            int index1 = cursor.getColumnIndex(Constants.TERM);
            int index2 = cursor.getColumnIndex(Constants.DEFINITION);

            String flashcardTerm = cursor.getString(index1);
            String flashcardDefinition = cursor.getString(index2);
            buffer.append(flashcardTerm + ": " + flashcardDefinition + "\n");
        }
        return buffer.toString();
    }

}

