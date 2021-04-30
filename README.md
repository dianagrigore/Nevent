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

       ------Add using the CLI-------
       1. Add a new location using the CLI
       2. Add a new client using the CLI
       3. Add a new performer using the CLI
       4. Add a new event using the CLI
       ------Add using randim generators-------
       5. Generate a random location
       6. Generate a random client
       7. Generate a random performer
       8. Generate a random event
       ------Sorting functionalities-------
       9. Sort the events by duration
       10. Sort the locations by city
       11. Sort the performers by name
       -------List all the contents of the collections-------
       12. List all the locations
       13. List all the clients
       14. List all the events
       15. List all the performers
       ------Find events by criteria-------
       16. See events by category
       17. See events by location
       -------Client service functionalities---------
       18. Buy a ticket
       19. Return a ticket
       20. Book a ticket
       21. Cancel a booking;
       22. Check your account balance and vouchers
       23. Add funds to your account
       24. See your tickets
       25. See your reservations
       26. Gift a voucher
       27. Transform a booking to a ticket
       28. Filter all underage clients.
       29. Display a certain event.

     

