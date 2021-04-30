package com.nevent.model.exceptions;

public class ClientNotFound extends Exception {
    public ClientNotFound() {
        super("I couldn't find a client with this ID");
    }
}
