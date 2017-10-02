package com.example.kimgo.kimgouweleeuw_pset5;

/**
 * Created by kimgo on 2-10-2017.
 */

public class TodoList {
    String title;
    private int _id;

    // Constructor for to-do from database
    TodoList(String listTitle) {
        title = listTitle;
    }

    TodoList(String listTitle, int listID) {
        title = listTitle;
        _id = listID;
    }

    // Get the to-do title
    String getTitle() { return title; }

    // Get the to-do id
    int getID() { return _id; }
}
