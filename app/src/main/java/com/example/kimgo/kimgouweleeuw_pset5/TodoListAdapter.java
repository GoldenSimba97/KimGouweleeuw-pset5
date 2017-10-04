package com.example.kimgo.kimgouweleeuw_pset5;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * TodoListAdapter created by kimgo on 2-10-2017.
 * Custom adapter for displaying of to-do lists.
 */

class TodoListAdapter extends ArrayAdapter<TodoList> {
    private ArrayList<TodoList> todoLists;

    TodoListAdapter(Context context, ArrayList<TodoList> todoList) {
        super(context, 0, todoList);
        this.todoLists = todoList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        TodoList todoListItem = todoLists.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_todo, parent, false);
        }
        TextView tvTitle = convertView.findViewById(R.id.tvTitle);
        assert todoListItem != null;
        tvTitle.setText(todoListItem.title);
        return convertView;
    }
}

