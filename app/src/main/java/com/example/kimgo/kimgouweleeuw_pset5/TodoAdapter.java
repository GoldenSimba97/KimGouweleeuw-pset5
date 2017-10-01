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

public class TodoAdapter extends ArrayAdapter<Contact> {
    ArrayList<Contact> todos;
//    private Context context;
//    private MainActivity mainAct;

    public TodoAdapter(Context context, ArrayList<Contact> todoList) {
        super(context, 0, todoList);
        this.todos = todoList;
//        this.mainAct = (MainActivity) context;
//        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Contact toDo = todos.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_todo, parent, false);
        }
        TextView tvTitle = convertView.findViewById(R.id.tvTitle);
        assert toDo != null;
//        String titleTodo = toDo.getTitle();
//        tvTitle.setText(titleTodo);
        tvTitle.setText(toDo.title);
        if (toDo.getCompleted() == 1) {
            convertView.setBackgroundColor(Color.parseColor("#00C853"));
        }
        return convertView;
    }
}
