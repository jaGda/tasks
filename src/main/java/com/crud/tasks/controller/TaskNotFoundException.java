package com.crud.tasks.controller;

public class TaskNotFoundException extends Exception {

    private static final String message = "Wrong id - does not exit!";

    public TaskNotFoundException() {
        super(message);
    }
}
