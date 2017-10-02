package com.example.kimgo.kimgouweleeuw_pset5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * DBHelper created by kimgo on 1-10-2017.
 */

class DBHelper extends SQLiteOpenHelper {

    // Static strings
    private static final String DATABASE_NAME = "ContactDB.db";
    private static final int DATABASE_VERSION = 2;
    private static final String KEY_ID = "_id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_COMPLETED = "completed";
    private static final String KEY_LIST = "list";
    private static final String KEY_LISTID = "list_id";
    private static final String TABLE = "contactTable";
    private static final String TABLE2 = "listTable";


    // Constructor
    DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DB2 = "CREATE TABLE " + TABLE2 + "("
                + KEY_LISTID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_LIST + " TEXT NOT NULL);";
        String CREATE_DB = "CREATE TABLE " + TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_TITLE + " TEXT NOT NULL, "
                + KEY_COMPLETED + " TEXT NOT NULL, "
                + KEY_LISTID + " INTEGER);";
        db.execSQL(CREATE_DB);
        db.execSQL(CREATE_DB2);
//        Log.d("hello", "hello");
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }


    void createTodo(Contact toDo) {
        SQLiteDatabase db = getWritableDatabase();
//        onUpgrade(db, 2, 3);
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, toDo.getTitle());
        values.put(KEY_COMPLETED, toDo.getCompleted());
        db.insert(TABLE, null, values);
        db.close();
    }


    void createList(TodoList todoList) {
        SQLiteDatabase db = getWritableDatabase();
//        onUpgrade(db, 2, 3);
        ContentValues values = new ContentValues();
        values.put(KEY_LIST, todoList.getTitle());
        db.insert(TABLE2, null, values);
        db.close();
    }


    ArrayList<Contact> readTodo() {
        SQLiteDatabase db = getReadableDatabase();

        // A list of costum objects to store our data
        ArrayList<Contact> toDos = new ArrayList<>();

        // Create a query to give to the cursor
        String query = "SELECT " + KEY_ID + ", " + KEY_TITLE + ", " + KEY_COMPLETED + " FROM " + TABLE;
        Cursor cursor = db.rawQuery(query, null);

        // Set cursor to the beginning of our database
        if (cursor.moveToFirst()) {
            do {
                // Add id, done-status and to-do from current row to to-do-list
                String title = cursor.getString(cursor.getColumnIndex(KEY_TITLE));
                int completed = cursor.getInt(cursor.getColumnIndex(KEY_COMPLETED));
                int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));

                // Create a contact object with the newly retrieved data
                Contact toDo = new Contact(title, completed, id);
                toDos.add(toDo);
            }
            // While there is still a next entry
            while (cursor.moveToNext());
        }

        // Close the database and the cursor
        cursor.close();
        db.close();
        return toDos;
    }

    ArrayList<TodoList> readList() {
        SQLiteDatabase db = getReadableDatabase();

        // A list of costum objects to store our data
        ArrayList<TodoList> todoLists = new ArrayList<>();

        // Create a query to give to the cursor
        String query = "SELECT " + KEY_LISTID + ", " + KEY_LIST + " FROM " + TABLE2;
        Cursor cursor = db.rawQuery(query, null);

        // Set cursor to the beginning of our database
        if (cursor.moveToFirst()) {
            do {
                // Add id, done-status and to-do from current row to to-do-list
                String title = cursor.getString(cursor.getColumnIndex(KEY_LIST));
                int id = cursor.getInt(cursor.getColumnIndex(KEY_LISTID));

                // Create a TodoList object with the newly retrieved data
                TodoList list = new TodoList(title, id);
                todoLists.add(list);
            }
            // While there is still a next entry
            while (cursor.moveToNext());
        }

        // Close the database and the cursor
        cursor.close();
        db.close();
        return todoLists;
    }


    int updateTodo(Contact toDo) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, toDo.getTitle());
        values.put(KEY_COMPLETED, toDo.getCompleted());

        // Return whether it has succeeded or not
        return db.update(TABLE, values, KEY_ID + " = ? ", new String[] { String.valueOf(toDo.getID())});
    }

    int updateList(TodoList todoListItem) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LIST, todoListItem.getTitle());

        // Return whether it has succeeded or not
        return db.update(TABLE2, values, KEY_LISTID + " = ? ", new String[] { String.valueOf(todoListItem.getID())});
    }


    void deleteTodo(Contact toDo) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE, " " + KEY_ID + " = ? ", new String[] { String.valueOf(toDo.getID())});
        db.close();
    }

    void deleteList(TodoList todoListItem) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE2, " " + KEY_LISTID + " = ? ", new String[] { String.valueOf(todoListItem.getID())});
        db.close();
    }

}
