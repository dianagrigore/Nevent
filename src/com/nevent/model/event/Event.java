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

    public Event(String id,
                 String description,
                 Integer ageRestriction,
                 Integer duration,
                 Location location,
                 List<Ticket> soldTickets,
                 ArrayList<Reservation> reservations,
                 Date dateTime,
                 Map<String, Double> pricePerTicketType) {
        this.id = id;
        this.description = description;
        this.ageRestriction = ageRestriction;
        this.duration = duration;
        this.location = location;
        this.soldTickets = soldTickets;
        this.reservations = reservations;
        this.dateTime = dateTime;
        this.pricePerTicketType = pricePerTicketType;
    }


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id) && Objects.equals(description, event.description) && Objects.equals(ageRestriction, event.ageRestriction) && Objects.equals(duration, event.duration) && Objects.equals(location, event.location) && Objects.equals(soldTickets, event.soldTickets) && Objects.equals(reservations, event.reservations) && Objects.equals(dateTime, event.dateTime) && Objects.equals(pricePerTicketType, event.pricePerTicketType);
    }

    @Override
    public int hashCode() {
        int hashCode = 31;
        int prime = 17;
        hashCode = id == null ? 0 : prime * id.hashCode();
        hashCode += description == null ? 0 : prime * description.hashCode();
        hashCode += ageRestriction == null ? 0 : prime * ageRestriction.hashCode();
        hashCode += duration == null ? 0 : prime * duration.hashCode();
        hashCode += location == null ? 0 : prime * location.hashCode();
        hashCode += soldTickets == null ? 0 : prime * soldTickets.hashCode();
        hashCode += reservations == null ? 0 : prime * reservations.hashCode();
        hashCode += dateTime == null ? 0 : prime * dateTime.hashCode();
        hashCode += pricePerTicketType == null ? 0 : prime * pricePerTicketType.hashCode();
        return hashCode;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", ageRestriction=" + ageRestriction +
                ", duration=" + duration +
                ", location=" + location +
                ", soldTickets=" + soldTickets +
                ", reservations=" + reservations +
                ", dateTime=" + dateTime +
                ", pricePerTicketType=" + pricePerTicketType +
                '}';
    }
}
