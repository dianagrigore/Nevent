package com.nevent.model.event;

import com.nevent.model.client.Client;
import com.nevent.model.client.Reservation;
import com.nevent.model.location.Location;
import com.nevent.model.ticket.Ticket;

import java.util.*;
/*
*Class event, holds the interfaces for book and buy + return and cancel ticket + booking
* */
public abstract class Event {
    private String id;
    private String description;
    private Integer ageRestriction;
    private Integer duration;
    private Location location;
    private static Integer numberOfEvents = 0;
    private List<Ticket> soldTickets;
    private ArrayList<Reservation> reservations;
    private Date dateTime;
    private Map<String, Double> pricePerTicketType;

    public Event(String description,
                 Integer ageRestriction,
                 Integer duration,
                 Location location,
                 Date dateTime,
                 Map<String, Double> pricePerTicketType) {
        this.id = "EV" + (++numberOfEvents).toString();
        this.description = description;
        this.ageRestriction = ageRestriction;
        this.duration = duration;
        this.location = location;
        this.soldTickets = new ArrayList<>();
        this.reservations = new ArrayList<>();
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


    public void getPresentation(){
        System.out.println("Event no: " + this.getId() + "\nDescription: " + this.getDescription()
        + "\nAge restriction: " + this.ageRestriction + "\nDuration: "+ this.duration);
        this.getLocation().describeLocation();
        System.out.println("Price per ticket type chart:\n");
        for(String key : pricePerTicketType.keySet()){
            System.out.println(key + " costs " + pricePerTicketType.get(key));
        }
        System.out.println("\n");
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id) && Objects.equals(description, event.description) && Objects.equals(ageRestriction, event.ageRestriction) && Objects.equals(duration, event.duration) && Objects.equals(location, event.location) && Objects.equals(soldTickets, event.soldTickets) && Objects.equals(reservations, event.reservations) && Objects.equals(dateTime, event.dateTime) && Objects.equals(pricePerTicketType, event.pricePerTicketType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, ageRestriction, duration, location, dateTime, pricePerTicketType);
    }
}
