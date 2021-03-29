package com.nevent.model.event;

import com.nevent.model.location.Location;;
import com.nevent.model.performer.Performer;

import java.util.*;

public class TheatrePlay extends Event {
    private String genre;
    private String name;
    private String directorName;
    private String dressCode;
    private Map<Performer, String> cast;

    public TheatrePlay(String description, Integer ageRestriction, Integer duration, Location location, Date dateTime, Map<String, Double> pricePerTicketType, String genre, String name, String directorName, String dressCode, Map<Performer, String> cast) {
        super(description, ageRestriction, duration, location, dateTime, pricePerTicketType);
        this.genre = genre;
        this.name = name;
        this.directorName = directorName;
        this.dressCode = dressCode;
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

    public Map<Performer, String> getCast() {
        return cast;
    }

    public void setCast(Map<Performer, String> cast) {
        this.cast = cast;
    }

    @Override
    public void getPresentation(){
        super.getPresentation();
        System.out.println("This is a theatre play. Genre: " + this.getGenre() + "\nName: " + this.getName()+ " directed by "
                + this.getDirectorName() + "\n Attention: You need to obey the following dressCode " + this.getDressCode() + "\n");
        System.out.println("Cast: \n");
        for(Performer actor : this.getCast().keySet()){
            System.out.println(actor.getName() + " plays " + this.getCast().get(actor) + "\n");
        }
    }


    @Override
    public String toString() {
        return "TheatrePlay{" +
                "genre='" + genre + '\'' +
                ", name='" + name + '\'' +
                ", directorName='" + directorName + '\'' +
                ", dressCode='" + dressCode + '\'' +
                ", cast=" + cast +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TheatrePlay that = (TheatrePlay) o;
        return Objects.equals(genre, that.genre) && Objects.equals(name, that.name) && Objects.equals(directorName, that.directorName) && Objects.equals(dressCode, that.dressCode) && Objects.equals(cast, that.cast);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), genre, name, directorName, dressCode, cast);
    }
}
