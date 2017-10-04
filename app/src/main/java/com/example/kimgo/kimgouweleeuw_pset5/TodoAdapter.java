package com.example.kimgo.kimgouweleeuw_pset5;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * TodoAdapter created by kimgo on 1-10-2017.
 */

class TodoAdapter extends ArrayAdapter<Contact> {
    private ArrayList<Contact> todos;

    TodoAdapter(Context context, ArrayList<Contact> todoList) {
        super(context, 0, todoList);
        this.todos = todoList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_todo, parent, false);
        }
        TextView tvTitle = convertView.findViewById(R.id.tvTitle);
        tvTitle.setText(todos.get(position).title);
        if (todos.get(position).getCompleted() == 1) {
            convertView.setBackgroundColor(Color.parseColor("#00C853"));
        }
        return convertView;
    }
}
