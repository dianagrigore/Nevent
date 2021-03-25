package com.nevent.model.event;

import com.nevent.model.client.Reservation;
import com.nevent.model.location.Location;
import com.nevent.model.performer.Actor;
import com.nevent.model.ticket.Ticket;

import java.util.*;

public class TheatrePlay extends Event {
    private String genre;
    private String name;
    private String directorName;
    private String dressCode;
    private Map<Actor, String> cast;

    public TheatrePlay(String id,
                       String description,
                       Integer ageRestriction,
                       Integer duration,
                       Location location,
                       List<Ticket> soldTickets,
                       ArrayList<Reservation> reservations,
                       Date dateTime,
                       Map<String, Double> pricePerTicketType,
                       String genre,
                       String name,
                       String directorName,
                       String dressCode,
                       Map<Actor, String> cast) {
        super(id, description, ageRestriction, duration, location, soldTickets, reservations, dateTime, pricePerTicketType);
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

    public Map<Actor, String> getCast() {
        return cast;
    }

    public void setCast(Map<Actor, String> cast) {
        this.cast = cast;
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
        int hashCode = 31;
        int prime = 17;
        hashCode = genre == null ? 0 : prime * genre.hashCode();
        hashCode += name == null ? 0 : prime * name.hashCode();
        hashCode += directorName == null ? 0 : prime * directorName.hashCode();
        hashCode += dressCode == null ? 0 : prime * dressCode.hashCode();
        hashCode += cast == null ? 0 : prime * cast.hashCode();
        hashCode += super.hashCode();
        return hashCode;
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
}
