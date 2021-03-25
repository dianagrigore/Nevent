package com.nevent.model.event;

import com.nevent.model.client.Reservation;
import com.nevent.model.location.Location;
import com.nevent.model.performer.Performer;
import com.nevent.model.ticket.Ticket;

import java.util.*;

public class Concert extends Event {
    Performer opener;
    Performer mainAct;
    Integer performanceTimeOpener;
    Integer performanceTimeMainAct;

    public Concert(String id,
                   String description,
                   Integer ageRestriction,
                   Integer duration,
                   Location location,
                   List<Ticket> soldTickets,
                   ArrayList<Reservation> reservations,
                   Date dateTime,
                   Map<String, Double> pricePerTicketType,
                   Performer opener,
                   Performer mainAct,
                   Integer performanceTimeOpener,
                   Integer performanceTimeMainAct) {
        super(id, description, ageRestriction, duration, location, soldTickets, reservations, dateTime, pricePerTicketType);
        this.opener = opener;
        this.mainAct = mainAct;
        this.performanceTimeOpener = performanceTimeOpener;
        this.performanceTimeMainAct = performanceTimeMainAct;
    }

    public Integer getPerformanceTimeOpener() {
        return performanceTimeOpener;
    }

    public void setPerformanceTimeOpener(Integer performanceTimeOpener) {
        this.performanceTimeOpener = performanceTimeOpener;
    }

    public Integer getPerformanceTimeMainAct() {
        return performanceTimeMainAct;
    }

    public void setPerformanceTimeMainAct(Integer performanceTimeMainAct) {
        this.performanceTimeMainAct = performanceTimeMainAct;
    }

    public Performer getOpener() {
        return opener;
    }

    public void setOpener(Performer opener) {
        this.opener = opener;
    }

    public Performer getMainAct() {
        return mainAct;
    }

    public void setMainAct(Performer mainAct) {
        this.mainAct = mainAct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Concert concert = (Concert) o;
        return Objects.equals(opener, concert.opener) && Objects.equals(mainAct, concert.mainAct) && Objects.equals(performanceTimeOpener, concert.performanceTimeOpener) && Objects.equals(performanceTimeMainAct, concert.performanceTimeMainAct);
    }

    @Override
    public int hashCode() {
        int hashCode = 31;
        int prime = 17;
        hashCode = opener == null ? 0 : prime * opener.hashCode();
        hashCode += mainAct == null ? 0 : prime * mainAct.hashCode();
        hashCode += performanceTimeOpener == null ? 0 : prime * performanceTimeOpener.hashCode();
        hashCode += performanceTimeMainAct == null ? 0 : prime * performanceTimeMainAct.hashCode();
        hashCode += super.hashCode();
        return hashCode;
    }

    @Override
    public String toString() {
        return "Concert{" +
                "opener=" + opener +
                ", mainAct=" + mainAct +
                ", performanceTimeOpener=" + performanceTimeOpener +
                ", performanceTimeMainAct=" + performanceTimeMainAct +
                "} " + super.toString();
    }
}
