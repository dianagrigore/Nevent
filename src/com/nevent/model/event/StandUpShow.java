package com.nevent.model.event;

import com.nevent.model.client.Reservation;
import com.nevent.model.location.Location;
import com.nevent.model.performer.Comedian;
import com.nevent.model.ticket.Ticket;

import java.util.*;

public class StandUpShow extends Event {
    Set<Comedian> comedians;
    Map<Comedian, Integer> schedule;
    Map<Comedian, String> rolesInShow;

    public StandUpShow(String id,
                       String description,
                       Integer ageRestriction,
                       Integer duration,
                       Location location,
                       List<Ticket> soldTickets,
                       ArrayList<Reservation> reservations,
                       Date dateTime,
                       Map<String, Double> pricePerTicketType,
                       Set<Comedian> comedians,
                       Map<Comedian, Integer> schedule,
                       Map<Comedian, String> rolesInShow) {
        super(id, description, ageRestriction, duration, location, soldTickets, reservations, dateTime, pricePerTicketType);
        this.comedians = comedians;
        this.schedule = schedule;
        this.rolesInShow = rolesInShow;
    }

    public Set<Comedian> getComedians() {
        return comedians;
    }

    public void setComedians(Set<Comedian> comedians) {
        this.comedians = comedians;
    }

    public Map<Comedian, Integer> getSchedule() {
        return schedule;
    }

    public void setSchedule(Map<Comedian, Integer> schedule) {
        this.schedule = schedule;
    }

    public Map<Comedian, String> getRolesInShow() {
        return rolesInShow;
    }

    public void setRolesInShow(Map<Comedian, String> rolesInShow) {
        this.rolesInShow = rolesInShow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StandUpShow that = (StandUpShow) o;
        return Objects.equals(comedians, that.comedians) && Objects.equals(schedule, that.schedule) && Objects.equals(rolesInShow, that.rolesInShow);
    }

    @Override
    public int hashCode() {
        int hashCode = 31;
        int prime = 17;
        hashCode = comedians == null ? 0 : prime * comedians.hashCode();
        hashCode += schedule == null ? 0 : prime * schedule.hashCode();
        hashCode += rolesInShow == null ? 0 : prime * rolesInShow.hashCode();
        hashCode += super.hashCode();
        return hashCode;
    }

    @Override
    public String toString() {
        return "StandUpShow{" +
                "comedians=" + comedians +
                ", schedule=" + schedule +
                ", rolesInShow=" + rolesInShow +
                "} " + super.toString();
    }
}
