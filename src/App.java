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
        //Services
        MainService mainService = new MainService();
        ClientUtilitiesService clientService = new ClientUtilitiesService();

        //Add events, locations, performers
        Actor actor = (Actor) mainService.performerGenerator("Actor");
        Comedian comedian = (Comedian) mainService.performerGenerator("Comedian");
        Actor actor1 = (Actor) mainService.performerGenerator("Actor");
        Actor actor2 = (Actor) mainService.performerGenerator("Actor");
        Actor actor3 = (Actor) mainService.performerGenerator("Actor");
        //Actor actor1 = (Actor)mainService.performerGenerator("Something"); // TODO: maybe an error should be thrown
        mainService.addPerformerToArray(actor1);
        mainService.addPerformerToArray(actor2);
        mainService.addPerformerToArray(actor3);
        Movie movie = (Movie) mainService.randomEvent("Movie");
        mainService.addEventToArray(movie);
        Comedian comedian3 = (Comedian) mainService.performerGenerator("Comedian");
        Actor actor4 = (Actor) mainService.performerGenerator("Actor");
        Actor actor5 = (Actor) mainService.performerGenerator("Actor");
        Actor actor6 = (Actor) mainService.performerGenerator("Actor");
        //Actor actor13 = (Actor)mainService.performerGenerator("Something"); // TODO: maybe an error should be thrown
        TheatrePlay theatrePlay = (TheatrePlay) mainService.randomEvent("TheatrePlay");
        mainService.addEventToArray(theatrePlay);
        //mainService.listAllLocations();
        //mainService.addAnEventCLI();
        mainService.seeEventsByCategory("Movie");

        //add a client and try clientService methods
        Client client = mainService.clientGenerator();
        mainService.addClientToArray(client);
        Client client2 = mainService.clientGenerator();
        mainService.addClientToArray(client2);
        clientService.checkClientBalance(client);
        clientService.addFunds(client, 2000.0);
        clientService.checkClientBalance(client);
        clientService.seeMyReservations(client);
        clientService.seeMyTickets(client);
       // clientService.buyATicket(movie, client);
        //clientService.seeMyTickets(client);
        //clientService.returnATicket(movie, client);
        //clientService.seeMyTickets(client);
        //clientService.bookATicket(movie, client);
        //clientService.cancelBooking(movie, client);
        clientService.giftAVoucher(client, client2);
    }
}
