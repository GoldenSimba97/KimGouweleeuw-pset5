package com.example.kimgo.kimgouweleeuw_pset5;

/**
 * Created by kimgo on 2-10-2017.
 */

public class TodoList {
    String title;
    private int _id;

    // Constructor for to-do list from database
    TodoList(String listTitle) {
        title = listTitle;
    }

    TodoList(String listTitle, int listID) {
        title = listTitle;
        _id = listID;
    }

    // Get the list title
    String getTitle() { return title; }

    // Get the list id
    int getID() { return _id; }

    // Set the title
    void setTitle(String newTitle) { title = newTitle; }
}
