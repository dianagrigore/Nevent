package com.nevent.model.event;


import com.nevent.model.location.Location;
import com.nevent.model.performer.Comedian;

;

import java.util.*;

public class StandUpShow extends Event {
    private Set<Comedian> comedians;
    private Map<Comedian, Integer> schedule;
    private Map<Comedian, String> rolesInShow;

    public StandUpShow(String description, Integer ageRestriction, Integer duration, Location location, Date dateTime, Map<String, Double> pricePerTicketType, Set<Comedian> comedians, Map<Comedian, Integer> schedule, Map<Comedian, String> rolesInShow) {
        super(description, ageRestriction, duration, location, dateTime, pricePerTicketType);
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
    public void getPresentation(){
        super.getPresentation();
        System.out.println("This is a stand-up comedy show. \n");
        for(Comedian comedian : this.getComedians()){
            System.out.println(comedian.getDescription());
            System.out.println("is a " + this.getRolesInShow().get(comedian) + " and will perform for" +
                    this.getSchedule().get(comedian));
            System.out.println("\n");
        }
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
