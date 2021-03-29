package com.nevent.model.performer;

import com.nevent.model.event.Event;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Performer {
    private String performerId;
    private String name;
    private static Integer numberOfPerformers = 0;
    private String description;
    Set<Event> currentEvents;

    public Performer(String name, String description) {
        this.performerId = "PERFORM" + (++numberOfPerformers).toString();
        this.name = name;
        this.description = description;
        this.currentEvents = new HashSet<>();
    }

    public String getPerformerId() {
        return performerId;
    }

    public void setPerformerId(String performerId) {
        this.performerId = performerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Event> getCurrentEvents() {
        return currentEvents;
    }

    public void setCurrentEvents(Set<Event> currentEvents) {
        this.currentEvents = currentEvents;
    }

    public void getPortrait(){
        System.out.println("id: " + this.getPerformerId() + "\n" + "name: " + this.getName()
                + "\n" + this.getDescription());
    }

    @Override
    public String toString() {
        return "Performer{" +
                "performerId='" + performerId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", currentEvents=" + currentEvents +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Performer performer = (Performer) o;
        return Objects.equals(performerId, performer.performerId) && Objects.equals(name, performer.name) && Objects.equals(description, performer.description) && Objects.equals(currentEvents, performer.currentEvents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(performerId, name, description, currentEvents);
    }
}