package com.nevent.model.event;

import com.nevent.model.client.Reservation;
import com.nevent.model.location.Location;
import com.nevent.model.ticket.Ticket;

import java.util.*;

public abstract class Event {
    private String id;
    private String description;
    private Integer ageRestriction;
    private Integer duration;
    private Location location;
    private List<Ticket> soldTickets;
    private ArrayList<Reservation> reservations;
    private Date dateTime;
    private Map<String, Double> pricePerTicketType;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(Integer ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Map<String, Double> getPricePerTicketType() {
        return pricePerTicketType;
    }

    public void setPricePerTicketType(Map<String, Double> pricePerTicketType) {
        this.pricePerTicketType = pricePerTicketType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", ageRestriction=" + ageRestriction +
                ", duration=" + duration +
                ", dateTime=" + dateTime +
                ", pricePerTicketType=" + pricePerTicketType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id.equals(event.id) && Objects.equals(description, event.description) && Objects.equals(ageRestriction, event.ageRestriction) && Objects.equals(duration, event.duration) && Objects.equals(dateTime, event.dateTime) && Objects.equals(pricePerTicketType, event.pricePerTicketType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, ageRestriction, duration, dateTime, pricePerTicketType);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Ticket> getSoldTickets() {
        return soldTickets;
    }

    public void setSoldTickets(List<Ticket> soldTickets) {
        this.soldTickets = soldTickets;
    }

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(ArrayList<Reservation> reservations) {
        this.reservations = reservations;
    }
}
