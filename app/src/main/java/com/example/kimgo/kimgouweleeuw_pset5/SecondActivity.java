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
        helper = new DBHelper(context);

        TextView titleList = (TextView) findViewById(R.id.listTitle);

        Bundle extras = getIntent().getExtras();
        String listTitle = extras.getString("listTitle");
        listID = extras.getInt("listID");
        Log.d("title", listTitle);
        titleList.setText(listTitle);

        todoList = helper.readTodo(listID);

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

        findViewById(R.id.addTodo).setOnClickListener(new SecondActivity.addToDo());
        lvItems.setOnItemClickListener(new SecondActivity.setDone());
        lvItems.setOnItemLongClickListener(new SecondActivity.deleteTodo());

        makeTodoAdapter();
    }


    // Set to-do to done and color it green if clicked
    private class setDone implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id) {
            todoAdapter = new TodoAdapter(secondAct, todoList);
            Contact toDo = todoAdapter.getItem(position);
            view.setBackgroundColor(Color.parseColor("#00C853"));
            assert toDo != null;
            toDo.setCompleted();
            helper.updateTodo(toDo);
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

            AlertDialog.Builder builder = new AlertDialog.Builder(secondAct);
            builder.setCancelable(true);
            builder.setMessage("Are you sure you want to delete this to-do?");
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    helper.deleteTodo(toDo);
                    todoList = helper.readTodo(listID);
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
                toDo = new Contact(listID, addTodo);
                helper.createTodo(toDo);
                newTodo.getText().clear();
                todoList = helper.readTodo(listID);
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
        todoAdapter = new TodoAdapter(this, todoList);
        lvItems = (ListView) findViewById(R.id.listViewID);
        assert lvItems != null;
        lvItems.setAdapter(todoAdapter);
    }

}

