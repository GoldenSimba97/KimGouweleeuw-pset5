package com.example.kimgo.kimgouweleeuw_pset5;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {
    Context context;
    DBHelper helper;
    Contact toDo;
    ArrayList<Contact> todoList;
    ListView lvItems;
    EditText newTodo;
    SecondActivity secondAct;
    TodoAdapter todoAdapter;
    int listID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        secondAct = this;

        newTodo = (EditText) findViewById(R.id.editTodo);
        newTodo.setHint("Add new to-do");

        // Create the databasehelper
        context = this;
        helper = DBHelper.getsInstance(context);

        TextView titleList = (TextView) findViewById(R.id.listTitle);

        Bundle extras = getIntent().getExtras();
        String listTitle = extras.getString("listTitle");
        listID = extras.getInt("listID");
        titleList.setText(listTitle);

        lvItems = (ListView) findViewById(R.id.listViewID);

        findViewById(R.id.addTodo).setOnClickListener(new SecondActivity.addToDo());
        lvItems.setOnItemClickListener(new SecondActivity.setDone());
        lvItems.setOnItemLongClickListener(new SecondActivity.deleteTodo());

        makeTodoAdapter();
    }

    private void update(Contact toDo) {
        helper.updateTodo(toDo);
    }


    // Set to-do to done and color it green if clicked
    private class setDone implements AdapterView.OnItemClickListener {
        Contact toDo;
        @Override
        public void onItemClick(AdapterView<?> parent, final View view,
                                int position, long id) {
            todoAdapter = new TodoAdapter(secondAct, todoList);
            toDo = todoAdapter.getItem(position);
            view.setBackgroundColor(Color.parseColor("#00C853"));
            assert toDo != null;
            toDo.setCompleted();
            update(toDo);
        }
    }


    // Delete to-do if it is long clicked
    private class deleteTodo implements AdapterView.OnItemLongClickListener {
        Contact toDo;
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view,
                                       int position, long id) {
            todoAdapter = new TodoAdapter(secondAct, todoList);
            toDo = todoAdapter.getItem(position);

            showPopUp(toDo);
            return true;
        }
    }


    private void showPopUp(final Contact toDo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(secondAct);
        builder.setCancelable(true);
        builder.setPositiveButton("Delete this to-do", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                helper.deleteTodo(toDo);
                makeTodoAdapter();
            }
        });
        builder.setNegativeButton("Change to-do title", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changeTitle(toDo);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void changeTitle(final Contact toDo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(secondAct);
        builder.setCancelable(true);
        builder.setMessage("Please enter a new title");
        final EditText input = new EditText(SecondActivity.this);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newTitle = input.getText().toString();
                newTitle = newTitle.substring(0, 1).toUpperCase() + newTitle.substring(1);
                toDo.setTitle(newTitle);
                update(toDo);
                makeTodoAdapter();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    // Add to-do to to-do-list
    private class addToDo implements View.OnClickListener {
        @Override public void onClick(View view) {
            String addTodo = newTodo.getText().toString();
            if (!addTodo.isEmpty()) {
                addTodo = addTodo.substring(0, 1).toUpperCase() + addTodo.substring(1);
                toDo = new Contact(listID, addTodo);
                helper.createTodo(toDo);
                newTodo.getText().clear();
                makeTodoAdapter();
            }
        }
    }


    // Display all to-dos in the database in a listview
    public void makeTodoAdapter() {
        todoList = helper.readTodo(listID);
        todoAdapter = new TodoAdapter(this, todoList);
        lvItems = (ListView) findViewById(R.id.listViewID);
        assert lvItems != null;
        lvItems.setAdapter(todoAdapter);
    }

}

