package com.nevent.model.client;

import com.nevent.model.client.Client;
import com.nevent.model.ticket.Ticket;

import java.util.Date;
import java.util.Set;

public class Reservation {
    private Client client;
    private Date dateOfReservation;
    private Date dateOfExpiration;
    private Set<Ticket> tickets;

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

    public Date getDateOfExpiration() {
        return dateOfExpiration;
    }

    public void setDateOfExpiration(Date dateOfExpiration) {
        this.dateOfExpiration = dateOfExpiration;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }
}
