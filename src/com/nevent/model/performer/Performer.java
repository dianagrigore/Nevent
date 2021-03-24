package com.nevent.model.performer;

import com.nevent.model.event.Event;

import java.util.Set;

public class Performer {
    private String name;
    private String description;
    Set<Event> currentEvents;

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
}
