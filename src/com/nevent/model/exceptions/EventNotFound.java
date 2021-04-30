package com.nevent.model.exceptions;

public class EventNotFound extends Exception {
    public EventNotFound() {
        super("I couldn't find an event with this ID");
    }
}