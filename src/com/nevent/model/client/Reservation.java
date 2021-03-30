package com.nevent.model.client;

import com.nevent.model.ticket.Ticket;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/*Reservation = Booking of a ticket for an event, without buying it
* User can book multiple tickets at once -> to be implemented
* Each ticket reservation belongs to the user that made it
* */
public class Reservation {
    private Date dateOfReservation;
    private List<Ticket> tickets;

    public Reservation(Date dateOfReservation, List<Ticket> tickets) {
        this.dateOfReservation = dateOfReservation;
        this.tickets = tickets;
    }

    public Date getDateOfReservation() {
        return dateOfReservation;
    }

    public void setDateOfReservation(Date dateOfReservation) {
        this.dateOfReservation = dateOfReservation;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }



    @Override
    public String toString() {
        return "Reservation{" +
               "dateOfReservation=" + dateOfReservation +
                ", tickets=" + tickets +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(dateOfReservation, that.dateOfReservation) &&  Objects.equals(tickets, that.tickets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateOfReservation, tickets);
    }
}
