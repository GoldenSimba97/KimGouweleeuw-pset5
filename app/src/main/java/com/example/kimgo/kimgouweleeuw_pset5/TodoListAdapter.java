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
 * Created by kimgo on 2-10-2017.
 */

public class TodoListAdapter extends ArrayAdapter<TodoList> {
    ArrayList<TodoList> todoLists;
//    private Context context;
//    private MainActivity mainAct;

    public TodoListAdapter(Context context, ArrayList<TodoList> todoList) {
        super(context, 0, todoList);
        this.todoLists = todoList;
//        this.mainAct = (MainActivity) context;
//        this.context = context;
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
//        String titleTodo = toDo.getTitle();
//        tvTitle.setText(titleTodo);
        tvTitle.setText(todoListItem.title);
//        if (toDo.getCompleted() == 1) {
//            convertView.setBackgroundColor(Color.parseColor("#00C853"));
//        }
        return convertView;
    }
}

