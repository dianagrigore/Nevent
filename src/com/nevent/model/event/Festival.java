package com.nevent.model.event;

import com.nevent.model.performer.Performer;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Festival extends Event {
    Set<Performer> performers;
    String name;
    Map<Performer, Integer> actLength;
    Map<Performer, Date> dateOfPerformance;
    List<String> stages;

    public Set<Performer> getPerformers() {
        return performers;
    }

    public void setPerformers(Set<Performer> performers) {
        this.performers = performers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Performer, Integer> getActLength() {
        return actLength;
    }

    public void setActLength(Map<Performer, Integer> actLength) {
        this.actLength = actLength;
    }

    public Map<Performer, Date> getDateOfPerformance() {
        return dateOfPerformance;
    }

    public void setDateOfPerformance(Map<Performer, Date> dateOfPerformance) {
        this.dateOfPerformance = dateOfPerformance;
    }

    public List<String> getStages() {
        return stages;
    }

    public void setStages(List<String> stages) {
        this.stages = stages;
    }
}
