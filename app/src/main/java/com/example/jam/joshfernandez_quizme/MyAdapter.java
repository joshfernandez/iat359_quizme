package com.example.jam.joshfernandez_quizme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static com.example.jam.joshfernandez_quizme.DisplayFlashcardsActivity.*;
import static com.example.jam.joshfernandez_quizme.R.layout.row;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    public ArrayList<String> list;
    Context appContext;

    public MyAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.appContext = context;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int ViewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(row, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        String[] results = (list.get(position).toString()).split(",");
        holder.termTextView.setText(results[0]);
        holder.definitionTextView.setText(results[1]);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView termTextView;
        public TextView definitionTextView;

        public ConstraintLayout myLayout;

        Context holderContext;

        public MyViewHolder(View itemView) {
            super(itemView);
            myLayout = (ConstraintLayout) itemView;

            termTextView = (TextView) itemView.findViewById(R.id.flashcardTermEntry);
            definitionTextView = (TextView) itemView.findViewById(R.id.flashcardDefinitionEntry);

            itemView.setOnClickListener(this);
            holderContext = itemView.getContext();
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(holderContext,
                    "Proceed to Update Flashcard Activity for " + ((TextView) view.findViewById(R.id.flashcardTermEntry)).getText().toString(),
                    Toast.LENGTH_SHORT).show();

            // The biggest accomplishment of my life!
            Intent intent = new Intent(holderContext, UpdateFlashcardActivity.class);
            intent.putExtra("Term", termTextView.getText());
            intent.putExtra("Definition", definitionTextView.getText());

            // This is also the biggest accomplishment of my life!
            // However, I am aware that this is slightly dangerous code.
            // Source: https://stackoverflow.com/questions/2848775/use-startactivityforresult-from-non-activity
            if (holderContext instanceof Activity) {
                ((Activity) holderContext).startActivityForResult(intent, REQUEST_UPDATE_FLASHCARD);
            } else {
                Log.e("MyAdapter", "holderContext should be an instanceof Activity.");
            }
        }

    }

}

