import com.nevent.model.client.Client;
import com.nevent.model.database.config.SetupData;
import com.nevent.model.database.repository.*;
import com.nevent.model.event.*;
import com.nevent.model.exceptions.ClientNotFound;
import com.nevent.model.exceptions.EventNotFound;
import com.nevent.model.location.Location;
import com.nevent.model.performer.Actor;
import com.nevent.model.performer.Comedian;
import com.nevent.model.performer.Performer;
import com.nevent.model.performer.Singer;

import java.io.IOException;
import java.sql.SQLOutput;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws ParseException, IOException, ClientNotFound, EventNotFound {
        //Instantiate services
        SetupData setupData = new SetupData();
        setupData.setup();

        MainService mainService = MainService.getInstance();
        ClientUtilitiesService clientService = new ClientUtilitiesService();
        EventManagementService eventManagementService = new EventManagementService();
        AuditService auditService = new AuditService();
        WritingDataService writingDataService = WritingDataService.getInstance();
        ReadingDataService readingDataService = ReadingDataService.getInstance();
        Scanner scanner = new Scanner(System.in);

        ClientRepository clientRepository = new ClientRepository();
        ComedianRepository comedianRepository = new ComedianRepository();
        ActorRepository actorRepository = new ActorRepository();
        SingerRepository singerRepository = new SingerRepository();
        LocationRepository locationRepository = new LocationRepository();
        MovieRepository movieRepository = new MovieRepository();
        StandUpShowRepository standUpShowRepository = new StandUpShowRepository();
        TheatrePlayRepository theatrePlayRepository = new TheatrePlayRepository();
        ConcertRepository concertRepository = new ConcertRepository();

        List<Location> locations = readingDataService.readLocationCSV();
        for(Location location : locations) {
            mainService.addLocationToArray(location);
            locationRepository.save(location);
        }
        auditService.log("added locations from CSV");
        List<Actor> actors = readingDataService.readActorCSV();
        for(Actor actor : actors) {
            mainService.addPerformerToArray(actor);
            actorRepository.save(actor);
        }
        auditService.log("added actors from CSV");
        List<Client> clients = readingDataService.readClientCSV();
        for(Client client:clients) {
            mainService.addClientToArray(client);
            clientRepository.save(client);
        }
        auditService.log("added clients from CSV");
        List<Comedian> comedians = readingDataService.readComedianCSV();
        for(Comedian comedian:comedians) {
            mainService.addPerformerToArray(comedian);
            comedianRepository.save(comedian);
        }
        auditService.log("added comedians from CSV");
        List<Singer> singers = readingDataService.readSingerCSV();
        for(Singer singer:singers) {
            mainService.addPerformerToArray(singer);
            singerRepository.save(singer);
        }
        auditService.log("added singers from CSV");
        List<Concert> concerts = readingDataService.readConcertCSV();
        for(Concert concert : concerts) {
            mainService.addEventToArray(concert);
            concertRepository.save(concert);
        }
        auditService.log("added concerts from CSV");
        List<Movie> movies = readingDataService.readMovieCSV();
        for(Movie movie:movies) {
            mainService.addEventToArray(movie);
            movieRepository.save(movie);
        }
        auditService.log("added movies from CSV");
        List<StandUpShow> standUpShows = readingDataService.readStandUpShowCSV();
        for(StandUpShow standUpShow : standUpShows) {
            mainService.addEventToArray(standUpShow);
            standUpShowRepository.save(standUpShow);
        }
        auditService.log("added stand-up from CSV");
        List<TheatrePlay> theatrePlays = readingDataService.readTheatrePlayCSV();
        for(TheatrePlay theatrePlay : theatrePlays) {
            mainService.addEventToArray(theatrePlay);
            theatrePlayRepository.save(theatrePlay);
        }
        auditService.log("added theatre plays from CSV");

        System.out.println("What is your client id?\n");
        String clientId = scanner.next();
        Client user = clientRepository.findById(clientId);


        int action = 1;
        while(action != 0){
            mainService.showMenu();
            action = scanner.nextInt();
            switch(action) {
                case 1:
                    mainService.addANewLocationCLI();
                    auditService.log("added a new location using the CLI");
                    break;
                case 2:
                    mainService.addANewClientCLI();
                    auditService.log("added a new client using the CLI");
                    break;
                case 3:
                    mainService.addANewPerformerCLI();
                    auditService.log("added a new performer using the CLI");
                    break;
                case 4:
                    mainService.addAnEventCLI();
                    auditService.log("added a new event using the CLI");
                    break;
                case 5:
                    Location loc = mainService.locationGenerator();
                    mainService.addLocationToArray(loc);
                    locationRepository.save(loc);
                    auditService.log("generated a new location");
                    break;
                case 6:
                    Client c = mainService.clientGenerator();
                    mainService.addClientToArray(c);
                    clientRepository.save(c);
                    auditService.log("generated a new client");
                    break;
                case 7:
                    System.out.println("What kind of performer?\n");
                    String type = scanner.next();
                    Performer p = mainService.performerGenerator(type);
                    if (p instanceof Actor)
                        actorRepository.save((Actor) p);
                    if(p instanceof Comedian)
                        comedianRepository.save((Comedian) p);
                    if(p instanceof Singer)
                        singerRepository.save((Singer) p);
                    mainService.addPerformerToArray(p);
                    auditService.log("generated a new performer");
                    break;
                case 8:
                    System.out.println("What kind of event?\n");
                    String type2 = scanner.next();
                    Event e = mainService.randomEvent(type2);
                    if (e instanceof Concert)
                        concertRepository.save((Concert) e);
                    if(e instanceof Movie)
                        movieRepository.save((Movie) e);
                    if(e instanceof StandUpShow)
                        standUpShowRepository.save((StandUpShow) e);
                    if(e instanceof TheatrePlay)
                        theatrePlayRepository.save((TheatrePlay) e);
                    mainService.addEventToArray(e);
                    auditService.log("generated a new event");
                    break;
                case 9:
                    mainService.eventDurationSort();
                    auditService.log("sorted events by duration");
                    break;
                case 10:
                    mainService.locationCitySort();
                    auditService.log("sorted locations by city name");
                    break;
                case 11:
                    mainService.performerNameSort();
                    auditService.log("sorted performers by name");
                    break;
                case 12:
                    mainService.listAllLocations();
                    auditService.log("listed all locations");
                    break;
                case 13:
                    mainService.listAllClients();
                    auditService.log("listed all clients");
                    break;
                case 14:
                    mainService.listAllEvents();
                    auditService.log("listed all events");
                    break;
                case 15:
                    mainService.listAllPerformers();
                    auditService.log("listed all performers");
                    break;
                case 16:
                    System.out.println("What category of events do you want to view (ex. Concert)?\n");
                    String category = scanner.next();
                    mainService.seeEventsByCategory(category);
                    auditService.log("see events by category");
                    break;
                case 17:
                    mainService.findEventByCity();
                    auditService.log("see events by category");
                    break;
                case 18:
                    System.out.println("What event are you interested in? (I need the ID)\n");
                    String eventId = scanner.next();
                    Event en = mainService.getEventById(eventId);
                    eventManagementService.buyATicket(en,user);
                    auditService.log("bought a ticket");
                    break;
                case 19:
                    System.out.println("What event are you interested in? (I need the ID)\n");
                    String eventId2 = scanner.next();
                    Event e2 = mainService.getEventById(eventId2);
                    eventManagementService.returnATicket(e2, user);
                    auditService.log("returned a ticket");
                    break;
                case 20:
                    System.out.println("What event are you interested in? (I need the ID)\n");
                    String eventId3 = scanner.next();
                    Event e3 = mainService.getEventById(eventId3);
                    eventManagementService.bookATicket(e3, user);
                    auditService.log("bought a ticket");
                    break;
                case 21:
                    System.out.println("What event are you interested in? (I need the ID)\n");
                    String eventId4 = scanner.next();
                    Event e4 = mainService.getEventById(eventId4);
                    eventManagementService.cancelAReservation(e4, user);
                    auditService.log("cancelled a reservation");
                    break;
                case 22:
                    clientService.checkClientBalance(user);
                    auditService.log("client checked balance");
                    break;
                case 23:
                    System.out.println("What amount do you want to deposit?\n");
                    Double amount = scanner.nextDouble();
                    clientService.addFunds(user, amount);
                    auditService.log("client added funds to account");
                    break;
                case 24:
                    clientService.seeMyTickets(user);
                    auditService.log("client viewed his tickets");
                    break;
                case 25:
                    clientService.seeMyReservations(user);
                    auditService.log("client viewed his reservations");
                    break;
                case 26:
                    System.out.println("Who do you want to gift a voucher to?\n");
                    String receiverId = scanner.next();
                    Client receiver = mainService.getClientById(receiverId);
                    clientService.giftAVoucher(user, receiver);
                    auditService.log("A voucher was gifted");
                    break;
                case 27:
                    System.out.println("What event are you interested in? (I need the ID)\n");
                    String eventId5= scanner.next();
                    Event e5 = mainService.getEventById(eventId5);
                    eventManagementService.transformBookingToTicket(e5, user);
                    auditService.log("A booking was transformed to a ticket");
                    break;
                case 28:
                    System.out.println("What is the age restriction?\n");
                    int age_res = scanner.nextInt();
                    mainService.filterAllUnderage(age_res);
                    auditService.log("filtered all underage clients");
                    break;
                case 29:
                    System.out.println("What event are you interested in viewing (EVnr)\n");
                    String evid = scanner.next();
                    Event ev = mainService.getEventById(evid);
                    mainService.displayEvent(ev);
                    break;
                case 30:
                    List<Singer> singerList = singerRepository.findAll();
                    for(Singer s : singerList)
                        System.out.println(s);
                    break;
                case 31:
                    List<Actor> actorList = actorRepository.findAll();
                    for(Actor a : actorList)
                        System.out.println(a);
                    break;
                case 32:
                    List<Comedian> comedianList = comedianRepository.findAll();
                    for(Comedian co : comedianList)
                        System.out.println(co);
                    break;
                case 33:
                    System.out.println("What is the client's id?");
                    String cl_id = scanner.next();
                    System.out.println("What is the new name?");
                    String nn = scanner.next();
                    System.out.println("What is the new surname?");
                    String ns = scanner.next();
                    System.out.println("What is the new age?");
                    Integer na = scanner.nextInt();
                    clientRepository.update(cl_id, nn, ns, na);
                    System.out.println("Done!");
                    break;
                case 34:
                    // singer, actor, comedian
                    System.out.println("What is the location id?");
                    String lid = scanner.next();
                    System.out.println("What is the new venue name?");
                    String vn = scanner.next();
                    System.out.println("What is the new address?");
                    String nad = scanner.next();
                    System.out.println("What is the new city name?");
                    String cit = scanner.next();
                    Integer smth = scanner.nextInt();
                    locationRepository.update(lid, vn, nad, cit);
                    System.out.println("Done!");
                    break;
                case 35:
                    System.out.println("What is the singer id?");
                    String sid = scanner.next();
                    System.out.println("What is the new singer name?");
                    String sn = scanner.next();
                    System.out.println("What is the new singer description?");
                    String nsd = scanner.next();
                    System.out.println("What is the new music_genre?");
                    String mg = scanner.next();
                    singerRepository.update(sid, sn, nsd, mg, true);
                    break;
                case 36:
                    System.out.println("What is the actor id?");
                    String aid = scanner.next();
                    System.out.println("What is the new name?");
                    String new_actor_name = scanner.next();
                    System.out.println("What is the new description?");
                    String new_actor_description = scanner.next();
                    actorRepository.update(aid, new_actor_name, new_actor_description);
                    break;
                case 37:
                    System.out.println("What is the comedian id?");
                    String comedian_new_id = scanner.next();
                    System.out.println("What is the new name?");
                    String comedian_new_name = scanner.next();
                    System.out.println("What is the new description");
                    String comedian_new_description = scanner.next();
                    System.out.println("What is the new position in show?");
                    String comedian_new_position = scanner.next();
                    System.out.println("What is the new tenure?");
                    Integer new_tenure = scanner.nextInt();
                    System.out.println("What is the new time per set?");
                    Integer timePerSet = scanner.nextInt();
                    break;
                case 38:
                    System.out.println("What is the client id you want to delete?");
                    String cl_id_del = scanner.next();
                    clientRepository.deleteById(cl_id_del);
                    System.out.println("Deleted");
                    break;
                case 39:
                    System.out.println("What is the location id you want to delete?");
                    String loc_id_del = scanner.next();
                    locationRepository.deleteById(loc_id_del);
                    System.out.println("Deleted");
                    break;
                case 40:
                    System.out.println("What is the singer id you want to delete?");
                    String sing_id_del = scanner.next();
                    singerRepository.deleteById(sing_id_del);
                    System.out.println("Deleted");
                    break;
                case 41:
                    System.out.println("What is the actor id you want to delete");
                    String actor_id_del = scanner.next();
                    actorRepository.deleteById(actor_id_del);
                    System.out.println("Deleted");
                    break;
                case 42:
                    System.out.println("What is the comedian id you want to delete");
                    String com_id_del = scanner.next();
                    comedianRepository.deleteById(com_id_del);
                    System.out.println("Deleted");
                    break;
            }
        }
        writingDataService.writeClientCSV(mainService.getClients());
        auditService.log("Wrote clients output");
        writingDataService.writeLocationCSV(mainService.getLocations());
        auditService.log("Wrote locations output");
        writingDataService.writeActorCSV(mainService.getPerformers().stream().filter(item -> item instanceof Actor)
                .map(item -> (Actor) item).collect(Collectors.toList()));
        auditService.log("Wrote actors output");
        writingDataService.writeComedianCSV(mainService.getPerformers().stream().filter(item -> item instanceof Comedian)
                .map(item -> (Comedian) item).collect(Collectors.toList()));
        auditService.log("Wrote comedians output");
        writingDataService.writeSingerCSV(mainService.getPerformers().stream().filter(item -> item instanceof Singer)
                .map(item -> (Singer) item).collect(Collectors.toList()));
        auditService.log("Wrote singers output");
        writingDataService.writeConcertCSV(mainService.getEvents().stream().filter(item -> item instanceof Concert)
                .map(item -> (Concert) item).collect(Collectors.toList()));
        auditService.log("Wrote concerts output");
        writingDataService.writeMovieCSV(mainService.getEvents().stream().filter(item -> item instanceof Movie)
                .map(item -> (Movie) item).collect(Collectors.toList()));
        auditService.log("Wrote movies output");
        writingDataService.writeTheatrePlayCSV(mainService.getEvents().stream().filter(item -> item instanceof TheatrePlay)
                .map(item -> (TheatrePlay) item).collect(Collectors.toList()));
        auditService.log("Wrote theatre plays output");
        writingDataService.writeStandUpShowCSV(mainService.getEvents().stream().filter(item -> item instanceof StandUpShow)
                .map(item -> (StandUpShow) item).collect(Collectors.toList()));
        auditService.log("Wrote stand-up shows output");
        auditService.close();

    }
}
