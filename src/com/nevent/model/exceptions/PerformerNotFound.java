package com.nevent.model.exceptions;

public class PerformerNotFound extends Exception{
    public PerformerNotFound() {
        super("I couldn't find a performer with this ID");
    }
}
