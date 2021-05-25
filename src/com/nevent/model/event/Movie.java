package com.nevent.model.event;

import com.nevent.model.location.Location;
import com.nevent.model.performer.Performer;

import java.util.*;

public class Movie extends Event {
    private String genre;
    private String name;
    private String director;
    private Map<Performer, String> cast;

    public Movie(String description, Integer ageRestriction, Integer duration, Location location, Date dateTime, Map<String, Double> pricePerTicketType, String genre, String name, String director, Map<Performer, String> cast) {
        super(description, ageRestriction, duration, location, dateTime, pricePerTicketType);
        this.genre = genre;
        this.name = name;
        this.director = director;
        this.cast = cast;
    }
    public Movie(String id, String description, Integer ageRestriction, Integer duration, Location location, Date dateTime, Map<String, Double> pricePerTicketType, String genre, String name, String director, Map<Performer, String> cast) {
        super(id, description, ageRestriction, duration, location, dateTime, pricePerTicketType);
        this.genre = genre;
        this.name = name;
        this.director = director;
        this.cast = cast;
    }

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

    @Override
    public void getPresentation(){
        super.getPresentation();
        System.out.println("This is a movie. Genre: " + this.getGenre() + "\nName: " + this.getName()+ "\nDirected by "
                + this.getDirector() + "\nCast: ");
        for(Performer actor : this.getCast().keySet()){
            System.out.println(actor.getName() + " plays " + this.getCast().get(actor) + "\n");
        }
    }


    @Override
    public String toString() {
        return "Movie{" +
                "genre='" + genre + '\'' +
                ", name='" + name + '\'' +
                ", director='" + director + '\'' +
                ", cast=" + cast +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Movie movie = (Movie) o;
        return Objects.equals(genre, movie.genre) && Objects.equals(name, movie.name) && Objects.equals(director, movie.director) && Objects.equals(cast, movie.cast);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), genre, name, director, cast);
    }
}
