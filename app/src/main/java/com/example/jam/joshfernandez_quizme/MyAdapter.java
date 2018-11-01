package com.example.jam.joshfernandez_quizme;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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
        View v = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        //TextView tv = (TextView) holder.itemView;
        TextView tv = holder.myTextView;
        tv.setText(list.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView myTextView;
        Context holderContext;

        public MyViewHolder(View itemView) {
            super(itemView);
            myTextView = (TextView) itemView;
            itemView.setOnClickListener(this);
            holderContext = itemView.getContext();
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(holderContext,
                    "You have clicked the " + ((TextView) view).getText().toString() + ".",
                    Toast.LENGTH_LONG).show();
        }
    }

}

