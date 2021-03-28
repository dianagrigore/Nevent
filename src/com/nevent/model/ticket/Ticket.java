package com.nevent.model.ticket;

import com.nevent.model.client.Client;
import com.nevent.model.event.Event;
/* Ticket a user can buy, book, return
 */
import java.util.Objects;

public class Ticket {
    private String ticketId;
    private Event event;
    private Client client;
    private String type;

    public Ticket(String ticketId, Event event, Client client, String type) {
        this.ticketId = ticketId;
        this.event = event;
        this.client = client;
        this.type = type;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "Ticket " +
                "no. " + ticketId  +
                ", for event: " + event.getId() +
                "\nDate: " + event.getDateTime() +
                "\nDuration: " + event.getDuration() +
                "\nLocation: " + event.getLocation().getAddress() +
                "\nName: " + client.getName() + " " + client.getSurname() +
                "\nCategory: " + type + '\n' ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(ticketId, ticket.ticketId) && Objects.equals(event, ticket.event) && Objects.equals(client, ticket.client) && Objects.equals(type, ticket.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketId, event, client, type);
    }
}
