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

    public Concert(
                   String description,
                   Integer ageRestriction,
                   Integer duration,
                   Location location,
                   Date dateTime,
                   Map<String, Double> pricePerTicketType,
                   Performer opener,
                   Performer mainAct,
                   Integer performanceTimeOpener,
                   Integer performanceTimeMainAct) {
        super(description, ageRestriction, duration, location, dateTime, pricePerTicketType);
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
    public void getPresentation(){
        super.getPresentation();
        System.out.println("This is a concert. " + this.getOpener().getName() + " will play at the start for " +
        this.getPerformanceTimeOpener() + " minutes. \nThen, the main act: " + this.getMainAct() + " will continue" +
                "the show for " + this.getPerformanceTimeMainAct() + " more minutes.\n");
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
        return Objects.hash(super.hashCode(), opener, mainAct, performanceTimeOpener, performanceTimeMainAct);
    }
}