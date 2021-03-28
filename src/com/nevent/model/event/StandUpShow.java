package com.nevent.model.event;


import com.nevent.model.location.Location;
import com.nevent.model.performer.Performer;

;

import java.util.*;

public class StandUpShow extends Event {
    Set<Performer> comedians;
    Map<Performer, Integer> schedule;
    Map<Performer, String> rolesInShow;

    public StandUpShow(String description, Integer ageRestriction, Integer duration, Location location, Date dateTime, Map<String, Double> pricePerTicketType, Set<Performer> comedians, Map<Performer, Integer> schedule, Map<Performer, String> rolesInShow) {
        super(description, ageRestriction, duration, location, dateTime, pricePerTicketType);
        this.comedians = comedians;
        this.schedule = schedule;
        this.rolesInShow = rolesInShow;
    }


    public Set<Performer> getComedians() {
        return comedians;
    }

    public void setComedians(Set<Performer> comedians) {
        this.comedians = comedians;
    }

    public Map<Performer, Integer> getSchedule() {
        return schedule;
    }

    public void setSchedule(Map<Performer, Integer> schedule) {
        this.schedule = schedule;
    }

    public Map<Performer, String> getRolesInShow() {
        return rolesInShow;
    }

    public void setRolesInShow(Map<Performer, String> rolesInShow) {
        this.rolesInShow = rolesInShow;
    }


    @Override
    public String toString() {
        return "StandUpShow{" +
                "comedians=" + comedians +
                ", schedule=" + schedule +
                ", rolesInShow=" + rolesInShow +
                "} " + super.toString();
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
        return Objects.hash(super.hashCode(), comedians, schedule, rolesInShow);
    }
}
