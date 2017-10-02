package com.example.kimgo.kimgouweleeuw_pset5;

import java.io.Serializable;

/**
 * Contact created by kimgo on 1-10-2017.
 */

class Contact implements Serializable {
    String title;
    private int completed;
    private int _id;
    private int listID;

    // Constructor for to-do from database
    Contact(String todoTitle) {
        title = todoTitle;
        completed = 0;
    }

    Contact(String todoTitle, int todoCompleted) {
        title = todoTitle;
        completed = todoCompleted;
    }

    Contact(String todoTitle, int todoCompleted, int todoID) {
        title = todoTitle;
        completed = todoCompleted;
        _id = todoID;
    }

    // Get the to-do title
    String getTitle() { return title; }

    // Get if the to-do is completed
    int getCompleted() { return completed; }

    // Get the to-do id
    int getID() { return _id; }

    // Set if to-do is completed
    void setCompleted() { completed = 1; }
}
