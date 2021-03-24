package com.nevent.model.event;

import com.nevent.model.performer.Actor;

import java.util.Map;

public class TheatrePlay extends Event {
    private String genre;
    private String name;
    private String directorName;
    private String dressCode;
    private Map<Actor, String> cast;

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public String getDressCode() {
        return dressCode;
    }

    public void setDressCode(String dressCode) {
        this.dressCode = dressCode;
    }

    public Map<Actor, String> getCast() {
        return cast;
    }

    public void setCast(Map<Actor, String> cast) {
        this.cast = cast;
    }
}
