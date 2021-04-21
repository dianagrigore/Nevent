import com.nevent.model.client.Client;
import com.nevent.model.event.Concert;
import com.nevent.model.event.Movie;
import com.nevent.model.event.TheatrePlay;
import com.nevent.model.location.Location;
import com.nevent.model.performer.Actor;
import com.nevent.model.performer.Comedian;
import com.nevent.model.performer.Singer;

import java.text.ParseException;

public class App {
    public static void main(String[] args) throws ParseException {
        //Instantiate services
        MainService mainService = new MainService();
        ClientUtilitiesService clientService = new ClientUtilitiesService();
        EventManagementService eventManagementService = new EventManagementService();

        //Add performers
        Actor actor = (Actor) mainService.performerGenerator("Actor");
        Comedian comedian = (Comedian) mainService.performerGenerator("Comedian");
        Actor actor1 = (Actor) mainService.performerGenerator("Actor");
        Actor actor2 = (Actor) mainService.performerGenerator("Actor");
        Actor actor3 = (Actor) mainService.performerGenerator("Actor");
        mainService.addPerformerToArray(actor1);
        mainService.addPerformerToArray(actor2);
        mainService.addPerformerToArray(actor3);
        Comedian comedian3 = (Comedian) mainService.performerGenerator("Comedian");
        Actor actor4 = (Actor) mainService.performerGenerator("Actor");
        Actor actor5 = (Actor) mainService.performerGenerator("Actor");
        Actor actor6 = (Actor) mainService.performerGenerator("Actor");
        //Actor actor13 = (Actor)mainService.performerGenerator("Something");

        Movie movie = (Movie) mainService.randomEvent("Movie");
        mainService.addEventToArray(movie);
        TheatrePlay theatrePlay = (TheatrePlay) mainService.randomEvent("TheatrePlay");
        mainService.addEventToArray(theatrePlay); //must be added because random generation does not do this

        //Location and events functionalities
        mainService.listAllLocations();
        mainService.seeEventsByCategory("Movie");

        //add 2 clients
        Client client = mainService.clientGenerator();
        mainService.addClientToArray(client);
        Client client2 = mainService.clientGenerator();
        mainService.addClientToArray(client2);

        //Accoutn functionalities
        clientService.checkClientBalance(client);
        clientService.addFunds(client, 2000.0);
        clientService.checkClientBalance(client);

        //Check reservations and tickets
        clientService.seeMyReservations(client);
        clientService.seeMyTickets(client);

        //Buy a ticket functionality tests
        eventManagementService.buyATicket(movie, client);
        clientService.seeMyTickets(client);
        eventManagementService.returnATicket(movie, client);
        clientService.seeMyTickets(client);

        //Booking tickets functionality tests
        eventManagementService.bookATicket(movie, client);
        clientService.seeMyReservations(client);
        clientService.seeMyTickets(client);
        eventManagementService.transformBookingToTicket(movie, client);
        clientService.seeMyReservations(client);
        clientService.seeMyTickets(client);
        eventManagementService.bookATicket(movie, client);
        clientService.seeMyReservations(client);
        eventManagementService.cancelAReservation(movie, client);
        clientService.giftAVoucher(client, client2);
        clientService.seeMyReservations(client);

        //Sort performers
        System.out.println("Before sorting the performers");
        mainService.listAllPerformers();
        mainService.performerNameSort();
        System.out.println("After sorting");
        mainService.listAllPerformers();

        //Sort events + findEventByCity and category -> nu stiu daca cerinta cerea sa avem colectii mereu sortate, asa
        //ca pe langa acestea, am adaugat sortari la adaugarea de elemente astfel incat colectiile sunt mereu sortate
        //si functionalitatea testelor de mai jos dispare
    /*
        System.out.println("Before sorting the events");
        mainService.listAllEvents();
        mainService.findEventByCity();
        mainService.eventDurationSort();
        System.out.println("After sorting the events");
        mainService.listAllEvents();*/

        int AGE_RESTRICTION = 50;
            System.out.println("Clients under the age of " + AGE_RESTRICTION);
        mainService.filterAllUnderage(AGE_RESTRICTION);
    }
}
