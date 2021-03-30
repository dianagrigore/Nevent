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
        //mainService.listAllLocations();
        //mainService.addAnEventCLI();
        //mainService.seeEventsByCategory("Movie");

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
       // clientService.buyATicket(movie, client);
        //clientService.seeMyTickets(client);
        //clientService.returnATicket(movie, client);
        //clientService.seeMyTickets(client);

        //Booking tickets functionality tests
        //clientService.bookATicket(movie, client);
        //clientService.cancelBooking(movie, client);
        //clientService.giftAVoucher(client, client2);

        //Sorting methods test

        //Sort performers
        /*System.out.println("Before sorting the performers");
        mainService.listAllPerformers();
        mainService.performerNameSort();
        System.out.println("After sorting");
        mainService.listAllPerformers();*/

        //Sort events + findEventByCity and category

        /*System.out.println("Before sorting the events");
        mainService.listAllEvents();
        mainService.findEventByCity();
        mainService.eventDurationSort();
        System.out.println("After sorting the events");
        mainService.listAllEvents();*/
    }
}
