package com.nevent.model.exceptions;

public class LocationNotFound extends Exception{
    public LocationNotFound() {
        super("I couldn't find a location with this ID");
    }
}
