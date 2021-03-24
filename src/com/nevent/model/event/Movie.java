package com.nevent.model.event;

import com.nevent.model.performer.Performer;

import java.util.Map;

public class Movie extends Event {
    private String genre;
    private String name;
    private String director;
    private Map<Performer, String> cast;

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

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Map<Performer, String> getCast() {
        return cast;
    }

    public void setCast(Map<Performer, String> cast) {
        this.cast = cast;
    }
}
