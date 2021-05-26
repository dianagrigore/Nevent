# Nevent
E-ticketing command line service that allows event and client efficient management.

The class structure is, as follows:

* com.nevent.model.client.payment
    * **Account**
    * **Voucher**
* com.nevent.model.client
    * **Client**
    * **Reservation**
    * **UnderageCriteria**
* com.nevent.model.commons
    * **Filterable**
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
* com.nevent.model.exceptions
    * **ClientNotFound**
    * **EventNotFound**
    * **LocationNotFound**
    * **PerformerNotFound**
* **App**
* **AuditService** -> service to audit every action, logs available in resources/output/audit_logs.csv
* **ClientUtilitiesService**
* **EventManagementService**
* **MainService**
* **ReadingDataService** -> CSV read for all types of resources
* **WritingDataService** -> CSV write for all types of resources

Exposed functionalities (only the public methods available through services, there are additional methods “called in the back” whose functionalities do not require additional attention). These are the options available in the command line menu.

----Add using the CLI-------

1. Add a new location using the CLI
2. Add a new client using the CLI
3. Add a new performer using the CLI
4. Add a new event using the CLI

------Add using randim generators-------

1. Generate a random location
2. Generate a random client
3. Generate a random performer
4. Generate a random event

------Sorting functionalities-------

1. Sort the events by duration
2. Sort the locations by city
3. Sort the performers by name

-------List all the contents of the collections-------

1. List all the locations
2. List all the clients
3. List all the events
4. List all the performers
5. List all singers
6. List all actors
7. List all comedians

------Find events by criteria-------

1. See events by category
2. See events by location

-------Client service functionalities---------

1. Buy a ticket
2. Return a ticket
3. Book a ticket
4. Cancel a booking
5. Check your account balance and vouchers
6. Add funds to your account
7. See your tickets
8. See your reservations
9. Gift a voucher
10. Transform a booking to a ticket
11. Filter all underage clients.
12. Display a certain event.

--------Updates--------

1. Update client details
2. Update location details
3. Update singer details
4. Update actor details
5. Update comedian details

--------Delete----------

1. Delete a client
2. Delete a location
3. Delete a singer
4. Delete an actor
5. Delete a comedian



     

