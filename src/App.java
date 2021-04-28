import com.nevent.model.client.Client;
import com.nevent.model.event.Concert;
import com.nevent.model.event.Movie;
import com.nevent.model.event.StandUpShow;
import com.nevent.model.event.TheatrePlay;
import com.nevent.model.location.Location;
import com.nevent.model.performer.Actor;
import com.nevent.model.performer.Comedian;
import com.nevent.model.performer.Singer;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class App {
    public static void main(String[] args) throws ParseException, IOException {
        //Instantiate services
        MainService mainService = MainService.getInstance();
        ClientUtilitiesService clientService = new ClientUtilitiesService();
        EventManagementService eventManagementService = new EventManagementService();
        AuditService auditService = new AuditService();
        WritingDataService writingDataService = WritingDataService.getInstance();
        ReadingDataService readingDataService = ReadingDataService.getInstance();

        List<Location> locations = readingDataService.readLocationCSV();
        for(Location location : locations)
            mainService.addLocationToArray(location);
        List<Actor> actors = readingDataService.readActorCSV();
        for(Actor actor : actors)
            mainService.addPerformerToArray(actor);
        List<Client> clients = readingDataService.readClientCSV();
        for(Client client:clients)
            mainService.addClientToArray(client);
        List<Comedian> comedians = readingDataService.readComedianCSV();
        for(Comedian comedian:comedians)
            mainService.addPerformerToArray(comedian);
        List<Singer> singers = readingDataService.readSingerCSV();
        for(Singer singer:singers)
            mainService.addPerformerToArray(singer);
        List<Concert> concerts = readingDataService.readConcertCSV();
        for(Concert concert : concerts)
            mainService.addEventToArray(concert);
        List<Movie> movies = readingDataService.readMovieCSV();
        for(Movie movie:movies)
            mainService.addEventToArray(movie);
        List<StandUpShow> standUpShows = readingDataService.readStandUpShowCSV();
        for(StandUpShow standUpShow : standUpShows)
            mainService.addEventToArray(standUpShow);
        List<TheatrePlay> theatrePlays = readingDataService.readTheatrePlayCSV();
        for(TheatrePlay theatrePlay : theatrePlays)
            mainService.addEventToArray(theatrePlay);

        //Add performers
        /*Actor actor = (Actor) mainService.performerGenerator("Actor");
        auditService.log("generated an actor");
        Comedian comedian = (Comedian) mainService.performerGenerator("Comedian");
        auditService.log("generated a comedian");
        Actor actor1 = (Actor) mainService.performerGenerator("Actor");
        auditService.log("generated an actor");
        Actor actor2 = (Actor) mainService.performerGenerator("Actor");
        auditService.log("generated an actor");
        Actor actor3 = (Actor) mainService.performerGenerator("Actor");
        auditService.log("generated an actor");
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
        mainService.listAllEvents();

        int AGE_RESTRICTION = 50;
            System.out.println("Clients under the age of " + AGE_RESTRICTION);
        mainService.filterAllUnderage(AGE_RESTRICTION);
        auditService.close();
        List<Client> clientList = readingDataService.readClientCSV();
        writingDataService.writeClientCSV(clientList);
        writingDataService.writeLocationCSV(mainService.getLocations());*/

    }
}
