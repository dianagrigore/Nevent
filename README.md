# Nevent
E-ticketing platform
1) Object types - divided in categories

* com.nevent.model.client.payment
    * **Account**
    * **Voucher**
* com.nevent.model.client
    * **Client**
    * **Reservation**
    * **UnderageCriteria**
* com.nevent.model.comparators
    * **EventDurationSorter**
    * **LocationCitySorter**
    * **PerformerNameSorter**
* com.nevent.model.event
    * **Event**
    * **Concert** extends Event
    * **Movie** extends Event
    * **StandUpShow** extends Event
    * **TheatrePlay** extends Event
* com.nevent.model.location
    * **Location**
    * **Seating**
* com.nevent.model.performer
    * **Performer**
    * **Actor** extends Performer
    * **Comedian** extends Performer
    * **Singer** extends Performer
* com.nevent.model.ticket
    * **Ticket**
* **App**
* **ClientUtilitiesService**
* **MainService**
* **Filterable**

Exposed functionalities (only the public methods available through services, there are additional methods “called in the back” whose functionalities do not require additional attention)

* through client service
    * **buyATicket**(Event e, Client c) → allows client c to buy a ticket at event e
    * **returnATicket**(Event e, Client c) → allows client c to return a ticket and get refunded
    * **bookATicket**(Event e, Client c) → allows a client to make a ticket reservation
    * **cancelBook**(Event e, Client c)→ client can cancel a booking
    * **checkClientBalance**(Client c)→ allows a client to see his vouchers and current balance
    * **addFunds**(Client c) → allows a client to add to his personal account
    * **seeMyTickets**(Client c) → shows the client’s current tickets
    * **seeMyReservations**(Client c) → shows the current reservations that a client has made
    * **giftAVoucher(Client sender, Client receiver)** → sender can give a voucher of amount to receiver (amount is also drafted from sender’s account)
    * **transformBookingToTicket(Event e, Client c)** (in progress -> allows a user to transform one of his reservations into tickets)
* through the main service, grouped by “business utility“
    * **find events by criteria**
        * **seeEventsByCategory**(String type) → lists all the events of a certain type
        *  **seeEventByLocation()** -> user is prompted to enter a location, and then, if available, events are listed   
    * **add objects to collections** (lists) as it was a design choice not to let the randomly generated objects to be auto-added
        * addLocationToArray(Location l)
        * addClientToArray(Client c)
        * addEventToArray(Event e)
        * addPerformerToArray(Performer p)
    * add **new objects using custom trees** based on user’s input in the CLI
        * addANewLocationCLI()
        * addANewClientCLI()
        * addANewPerformerCLI()
        * addAnEventCLI()
    * some methods to **list the contents of the collections**
        *  listAllLocations()
        * listAllClients()
        * listAllPerformers()
            * also listAllActors, listAllComedians, listAllSingers
        * listAllEvents()
    * methods to **randomly generate objects**
        * clientGenerator()
        * locationGenerator()
        * performerGenerator(String type) → type is a string = name of the class
        * randomEvent(String type)
        * **createRandomDate**(int start, int end) -> creates a random date between 2 years
    * **sorting methods** - they sort the contents of the collections
        * eventDurationSort()
        * locationCitySort()
        * performerNameSort()

     

