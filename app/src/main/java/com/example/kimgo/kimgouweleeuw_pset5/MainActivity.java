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
        helper = new DBHelper(context);

//        toDo = new TodoList("Hello");
//        helper.createList(toDo);

        todoList = helper.readList();

//        if (todoList.isEmpty()) {
//            Contact newTodo = new Contact("Add a new to-do by typing your to-do and clicking the ADD-TO-DO button");
//            helper.create(newTodo);
//
//            Contact done = new Contact("Click on your to-do to mark it as done and it will become green", 1);
//            helper.create(done);
//
//            Contact delete = new Contact("Click and hold your to-do to delete it");
//            helper.create(delete);
//
//            todoList = helper.read();
//        }

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
//            view.setBackgroundColor(Color.parseColor("#00C853"));
//            assert toDo != null;
//            toDo.setCompleted();
//            helper.update(toDo);
            Intent intent = new Intent(view.getContext(), SecondActivity.class);
            Bundle bundle = new Bundle();

//            bundle.putSerializable("listID", toDo);
            Log.d("title2", toDo.getTitle());
            bundle.putString("listTitle", toDo.getTitle());
            bundle.putInt("listID", toDo.getID());
            intent.putExtras(bundle);
//            intent.putExtra("listID", toDo);
            startActivity(intent);
//            finish();
        }
    }


    // Delete to-do if it is long clicked
    private class deleteTodo implements AdapterView.OnItemLongClickListener {
//        Contact toDo;
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view,
                                       int position, long id) {
            todoListAdapter = new TodoListAdapter(mainAct, todoList);
            toDo = todoListAdapter.getItem(position);

            AlertDialog.Builder builder = new AlertDialog.Builder(mainAct);
            builder.setCancelable(true);
            builder.setMessage("Are you sure you want to delete this to-do list?");
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    helper.deleteList(toDo);
                    todoList = helper.readList();
                    makeTodoAdapter();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    makeTodoAdapter();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }
    }


    // Add to-do to to-do-list
    private class addToDo implements View.OnClickListener {
        @Override public void onClick(View view) {
            String addTodo = newTodo.getText().toString();
            if (!addTodo.isEmpty()) {
                addTodo = addTodo.substring(0, 1).toUpperCase() + addTodo.substring(1);
                toDo = new TodoList(addTodo);
                helper.createList(toDo);
                newTodo.getText().clear();
                todoList = helper.readList();
                makeTodoAdapter();
            }
        }
    }


//    // Create costum adapter for displaying the listview
//    private class TodoAdapter extends ArrayAdapter<Contact> {
//        TodoAdapter(Context context, ArrayList<Contact> todoList) {
//            super(context, 0, todoList);
//        }
//
//        @NonNull
//        @Override
//        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
//            Contact toDo = getItem(position);
//            if (convertView == null) {
//                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_todo, parent, false);
//            }
//            TextView tvTitle = convertView.findViewById(R.id.tvTitle);
//            assert toDo != null;
//            tvTitle.setText(toDo.title);
//            if (toDo.getCompleted() == 1) {
//                convertView.setBackgroundColor(Color.parseColor("#00C853"));
//            }
//            return convertView;
//        }
//    }


    // Display all to-dos in the database in a listview
    public void makeTodoAdapter() {
        todoListAdapter = new TodoListAdapter(this, todoList);
        lvItems = (ListView) findViewById(R.id.listViewID);
        assert lvItems != null;
        lvItems.setAdapter(todoListAdapter);
    }

}
