package com.nevent.model.event;

import com.nevent.model.performer.Comedian;

import java.util.Map;
import java.util.Set;

public class StandUpShow extends Event {
    Set<Comedian> comedians;
    Map<Comedian, Integer> schedule;
    Map<Comedian, String> rolesInShow;

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
}
