package com.nevent.model.client;

import com.nevent.model.client.Client;
import com.nevent.model.ticket.Ticket;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

public class Reservation {
    private Client client;
    private Date dateOfReservation;
    private Set<Ticket> tickets;

    public Reservation(Client client, Date dateOfReservation, Set<Ticket> tickets) {
        this.client = client;
        this.dateOfReservation = dateOfReservation;
        this.tickets = tickets;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getDateOfReservation() {
        return dateOfReservation;
    }

    public void setDateOfReservation(Date dateOfReservation) {
        this.dateOfReservation = dateOfReservation;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }



    @Override
    public String toString() {
        return "Reservation{" +
                "client=" + client +
                ", dateOfReservation=" + dateOfReservation +
                ", tickets=" + tickets +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(client, that.client) && Objects.equals(dateOfReservation, that.dateOfReservation) &&  Objects.equals(tickets, that.tickets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client, dateOfReservation,  tickets);
    }
}
