package com.nevent.model.event;

import com.nevent.model.client.Reservation;
import com.nevent.model.location.Location;
import com.nevent.model.performer.Performer;
import com.nevent.model.ticket.Ticket;

import java.util.*;

public class Festival extends Event {
    Set<Performer> performers;
    String name;
    Map<Performer, Integer> actLength;
    Map<Performer, Date> dateOfPerformance;
    List<String> stages;

    public Festival(String id,
                    String description,
                    Integer ageRestriction,
                    Integer duration,
                    Location location,
                    List<Ticket> soldTickets,
                    ArrayList<Reservation> reservations,
                    Date dateTime,
                    Map<String, Double> pricePerTicketType,
                    Set<Performer> performers,
                    String name,
                    Map<Performer, Integer> actLength,
                    Map<Performer, Date> dateOfPerformance,
                    List<String> stages) {
        super(id, description, ageRestriction, duration, location, soldTickets, reservations, dateTime, pricePerTicketType);
        this.performers = performers;
        this.name = name;
        this.actLength = actLength;
        this.dateOfPerformance = dateOfPerformance;
        this.stages = stages;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Festival festival = (Festival) o;
        return Objects.equals(performers, festival.performers) && Objects.equals(name, festival.name) && Objects.equals(actLength, festival.actLength) && Objects.equals(dateOfPerformance, festival.dateOfPerformance) && Objects.equals(stages, festival.stages);
    }

    @Override
    public int hashCode() {
        int hashCode = 31;
        int prime = 17;
        hashCode = performers == null ? 0 : prime * performers.hashCode();
        hashCode += name == null ? 0 : prime * name.hashCode();
        hashCode += actLength == null ? 0 : prime * actLength.hashCode();
        hashCode += dateOfPerformance == null ? 0 : prime * dateOfPerformance.hashCode();
        hashCode += stages == null ? 0 : prime * stages.hashCode();
        hashCode += super.hashCode();
        return hashCode;
    }

    @Override
    public String toString() {
        return "Festival{" +
                "performers=" + performers +
                ", name='" + name + '\'' +
                ", actLength=" + actLength +
                ", dateOfPerformance=" + dateOfPerformance +
                ", stages=" + stages +
                "} " + super.toString();
    }
}
