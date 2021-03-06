package com.example.kimgo.kimgouweleeuw_pset5;

import java.io.Serializable;

/**
 * Contact created by kimgo on 1-10-2017.
 * Holds all functions for constructing and
 * using the Contact object with information
 * on the to-do title and if it is completed.
 */

class Contact implements Serializable {
    String title;
    private int completed;
    private int _id;
    private int listID;

    // Constructor for to-do from database
    Contact(int list_id, String todoTitle) {
        listID = list_id;
        title = todoTitle;
    }

    Contact(String todoTitle, int todoCompleted, int todoID, int list_id) {
        title = todoTitle;
        completed = todoCompleted;
        _id = todoID;
        listID = list_id;
    }

    // Get the to-do title
    String getTitle() { return title; }

    // Get if the to-do is completed
    int getCompleted() { return completed; }

    // Get the to-do id
    int getID() { return _id; }

    // Get the list id
    int getListID() { return listID; }

    // Set if to-do is completed
    void setCompleted() { completed = 1; }

    // Set new to-do title
    void setTitle(String newTitle) { title = newTitle; }
}
