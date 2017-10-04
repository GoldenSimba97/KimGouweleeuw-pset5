package com.example.kimgo.kimgouweleeuw_pset5;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Context context;
    DBHelper helper;
    TodoList toDo;
    ArrayList<TodoList> todoList;
    ListView lvItems;
    EditText newTodo;
    MainActivity mainAct;
    TodoListAdapter todoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainAct = this;

        newTodo = (EditText) findViewById(R.id.editTodo);
        newTodo.setHint("Add new to-do list");

        // Create the databasehelper
        context = this;
        helper = DBHelper.getsInstance(context);

//        todoList = helper.readList();

        lvItems = (ListView) findViewById(R.id.listViewID);

        findViewById(R.id.addTodo).setOnClickListener(new addToDo());
        lvItems.setOnItemClickListener(new setDone());
        lvItems.setOnItemLongClickListener(new deleteTodo());

        makeTodoAdapter();
    }


    // Set to-do to done and color it green if clicked
    private class setDone implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id) {
            todoListAdapter = new TodoListAdapter(mainAct, todoList);
            toDo = todoListAdapter.getItem(position);

            Intent intent = new Intent(view.getContext(), SecondActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("listTitle", toDo.getTitle());
            bundle.putInt("listID", toDo.getID());
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }


    // Delete to-do if it is long clicked
    private class deleteTodo implements AdapterView.OnItemLongClickListener {
        TodoList toDo;
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view,
                                       int position, long id) {
            todoListAdapter = new TodoListAdapter(mainAct, todoList);
            toDo = todoListAdapter.getItem(position);

            showPopUp(toDo);
            return true;
        }
    }


    private void showPopUp(final TodoList toDo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mainAct);
        builder.setCancelable(true);
        builder.setPositiveButton("Delete this list", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                helper.deleteList(toDo);
//                todoList = helper.readList();
                makeTodoAdapter();
            }
        });
        builder.setNegativeButton("Change list title", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changeTitle(toDo);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void changeTitle(final TodoList toDo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mainAct);
        builder.setCancelable(true);
        builder.setMessage("Please enter a new title");
        final EditText input = new EditText(MainActivity.this);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newTitle = input.getText().toString();
                newTitle = newTitle.substring(0, 1).toUpperCase() + newTitle.substring(1);
                toDo.setTitle(newTitle);
                helper.updateList(toDo);
//                todoList = helper.readList();
                makeTodoAdapter();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public String createString() {
        String addTodo = newTodo.getText().toString();
        if (!addTodo.isEmpty()) {
            addTodo = addTodo.substring(0, 1).toUpperCase() + addTodo.substring(1);
        }
        return addTodo;
    }


    // Add to-do to to-do-list
    private class addToDo implements View.OnClickListener {
        @Override public void onClick(View view) {
            if (!createString().isEmpty()) {
                toDo = new TodoList(createString());
                helper.createList(toDo);
                newTodo.getText().clear();
//                todoList = helper.readList();
                makeTodoAdapter();
            }
        }
    }


    // Display all to-dos in the database in a listview
    public void makeTodoAdapter() {
        todoList = helper.readList();
        todoListAdapter = new TodoListAdapter(this, todoList);
        lvItems = (ListView) findViewById(R.id.listViewID);
        assert lvItems != null;
        lvItems.setAdapter(todoListAdapter);
    }

}
