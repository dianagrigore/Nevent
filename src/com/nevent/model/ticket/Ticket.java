package com.nevent.model.ticket;

import com.nevent.model.client.Client;
import com.nevent.model.event.Event;

public class Ticket {
    private String ticketId;
    private Event event;
    private Client client;
    private String type;

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
}
