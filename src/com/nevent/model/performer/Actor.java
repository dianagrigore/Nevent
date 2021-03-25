package com.nevent.model.performer;

import com.nevent.model.event.Event;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Actor extends Performer{
    private List<String> awards;
    private List<String> pastProductions;

    public Actor(String performerId, String name, String description, Set<Event> currentEvents,
                 List<String> awards, List<String> pastProductions) {
        super(performerId, name, description, currentEvents);
        this.awards = awards;
        this.pastProductions = pastProductions;
    }

    public List<String> getAwards() {
        return awards;
    }

    public void setAwards(List<String> awards) {
        this.awards = awards;
    }

    public List<String> getPastProductions() {
        return pastProductions;
    }

    public void setPastProductions(List<String> pastProductions) {
        this.pastProductions = pastProductions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Actor actor = (Actor) o;
        return Objects.equals(awards, actor.awards) && Objects.equals(pastProductions, actor.pastProductions);
    }

    @Override
    public int hashCode() {
        int hashCode = 31;
        int prime = 17;
        hashCode = awards == null ? 0 : prime * awards.hashCode();
        hashCode += pastProductions == null ? 0 : prime * pastProductions.hashCode();
        hashCode += super.hashCode();
        return hashCode;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "awards=" + awards +
                ", pastProductions=" + pastProductions +
                ", currentEvents=" + currentEvents +
                "} " + super.toString();
    }
}
