# Nevent
E-ticketing platform
1) Object types - divided in categories

* com.nevent.model.client.payment
    * *Account*
        * clientId, leftBalance, vouchers
    * *Voucher*
        * reason, value
* com.nevent.model.client
    * *Client*
        * clientId, name, surname, age, paymentMethod, static numberOfClients, tickets, reservations
    * *Reservation*
        * client, dateOfReservation, tickets
* com.nevent.model.event
    * *Event*
        * id, description, ageRestriction, duration, location, static numberOfEvents, soldTickets, reservations, date, pricePerTicket
    * *Concert* extends Event
        * opener, mainAct, performanceTimeOpener, performanceTimeMainact
    * *Movie* extends Event
        * genre, name, director, cast
    * *StandUpShow* extends Event
        * comedians, schedule, rolesInShow
    * *TheatrePlay* extends Event
        * genre, name.directorName, dressCode, cast
* com.nevent.model.location
    * *Location*
        * id, nameOfVenue, address, city, Seating, static numberOfLocations
    * *Seating*
        * numberOfSeats, ticketsOfEachType
* com.nevent.model.performer
    * *Performer*
        * performerId, name, static numberOfPerformers, description, currentEvents
    * *Actor* extends Performer
        * awards, pastProductions
    * *Comedian* extends Performer
        * genre, position, tenure, timePerSet, podcasts
    * *Singer* extends Performer
        * musicGenre, isGroup, memberNames, bestKnownSongs
* com.nevent.model.ticket
    * *Ticket*
        * ticketId, event, client, type
* *App*
* *ClientUtilitiesService*
* *MainService*

