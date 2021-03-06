import com.nevent.model.commons.Filterable;
import com.nevent.model.client.Client;
import com.nevent.model.client.payment.CriteriaUnderage;
import com.nevent.model.comparators.EventDurationSorter;
import com.nevent.model.comparators.LocationCitySorter;
import com.nevent.model.comparators.PerformerNameSorter;
import com.nevent.model.database.repository.*;
import com.nevent.model.event.*;
import com.nevent.model.exceptions.ClientNotFound;
import com.nevent.model.exceptions.EventNotFound;
import com.nevent.model.exceptions.LocationNotFound;
import com.nevent.model.exceptions.PerformerNotFound;
import com.nevent.model.location.Location;
import com.nevent.model.location.Seating;
import com.nevent.model.performer.Actor;
import com.nevent.model.performer.Comedian;
import com.nevent.model.performer.Performer;
import com.nevent.model.performer.Singer;

import javax.sound.midi.spi.SoundbankReader;
import java.sql.SQLOutput;
import java.text.ParseException;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.*;


public class MainService
{
    private List<Location> Locations;
    private List<Performer> Performers;
    private List<Event> Events;
    private Set<Client> Clients;
    private Random random;

    private static MainService instance = null;
    public static MainService getInstance(){
        if(instance == null){
            instance = new MainService();
        }
        return instance;
    }
    private MainService() {
        locations = new ArrayList<>();
        performers = new ArrayList<>();
        events = new ArrayList<>();
        clients = new HashSet<>();
        random = new Random();
    }
    public void addLocationToArray(Location location){
        locations.add(location);
    }
    public void addClientToArray(Client client){
        clients.add(client);}
    public void addEventToArray(Event event){
        events.add(event); eventDurationSort();}
    public void addPerformerToArray(Performer performer){
        performers.add(performer); performerNameSort();}

    public void addANewLocationCLI(){
        Scanner reading = new Scanner(System.in);  // Create a Scanner object

        System.out.println("Please tell me the number of BASIC tickets");
        Integer numberBasic = reading.nextInt();

        System.out.println("Thanks, now the number of PREMIUM tickets");
        Integer numberPremium = reading.nextInt();

        System.out.println("Thanks, now the number of VIP tickets");
        Integer numberVIP = reading.nextInt();

        Integer noOfSeats = numberBasic + numberPremium + numberVIP;

        Map<String, Integer> seats = new HashMap<>();
        seats.put("BASIC", numberBasic);
        seats.put("PREMIUM", numberPremium);
        seats.put("VIP", numberVIP);
        Seating seating = new Seating(noOfSeats,seats);
        String s = reading.nextLine();
        System.out.println("Please tell me the name of your location");
        String locationName = reading.nextLine();

        System.out.println("Now the address");
        String address = reading.nextLine();

        System.out.println("Now the city of your location, please");
        String city = reading.nextLine();

        Location location = new Location(locationName, address, city, seating);
        locations.add(location);
        LocationRepository locationRepository = new LocationRepository();
        locationRepository.save(location);
        System.out.println("Congrats, you have added a new location\n\n");

    }
    public void addANewClientCLI(){
        Scanner reading = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Hi, tell me your first name");
        String name = reading.nextLine();

        System.out.print("Great! Now your last name\n");

        String surname = reading.nextLine();
        System.out.println("And wil you please give us your age?");
        Integer age = reading.nextInt();
        System.out.println("That's great!");
        Client client = new Client(name, surname, age);
        clients.add(client);
        ClientRepository clientRepository = new ClientRepository();
        clientRepository.save(client);
        System.out.println("Awesome! We created you an account and" +
                " you have no credit nor tickets.\n\n");
    }
    public void addANewPerformerCLI(){
        Scanner reading = new Scanner(System.in);
        System.out.println("Hi, can you tell me your name? ");
        String name = reading.nextLine();
        System.out.println("And maybe a short description of yourself");
        String description = reading.nextLine();
        System.out.println("Of the following, which one describes you best: 1. Actor\n2.Comedian\n3.Singer");
        Integer button = reading.nextInt();
        String v = reading.nextLine();
        switch(button){
            case 1:
                addActorCLI(reading, name, description);
                break;
            case 3:
                addSingerCLI(reading, name, description);
                break;
            case 2:
                addComedianCLI(reading, name, description);
                break;
            default:
                System.out.println("UhOh! That's not a job (at least according to us). Aborting..Try again");
        }

    }
    public void addAnEventCLI() throws ParseException {
        Scanner reading = new Scanner(System.in);
        System.out.println("Hi! Could you describe your event in a short sentance");
        String description = reading.nextLine();
        System.out.println("Is there any age restriction we should impose?");
        Integer ageRestriction = reading.nextInt();
        System.out.println("How long will your event last for?");
        Integer duration = reading.nextInt();
        System.out.println("Please pick one of the following locations by introducing the id (otherwise, it will be randomly generated)");
        listAllLocations();
        String locationId = reading.nextLine();
        Location  location = null;
        for(Location loc : locations){
            if(loc.getId().equals(locationId))
                location = loc;
        }
        locations.add(location);
        if(location == null)
            location = locationGenerator();
        String s = reading.nextLine();
        System.out.println("When will the event take place? (date as dd/mm/yyyy)");
        String data = reading.nextLine();
        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(data);
        System.out.println("How much will a BASIC ticket cost?");
        Double basicPrice = reading.nextDouble();
        System.out.println("How much will a PREMIUM ticket cost?");
        Double premiumPrice = reading.nextDouble();
        System.out.println("How much will a VIP ticket cost?");
        Double vipPrice = reading.nextDouble();
        HashMap<String, Double> pricePerTick = new HashMap<>();
        pricePerTick.put("BASIC", basicPrice);
        pricePerTick.put("PREMIUM", premiumPrice);
        pricePerTick.put("VIP", vipPrice);
        System.out.println("What type of event are you hosting? 1.Concert\n2.Movie\n" +
                "3.Stand-up show\n4.Play\n");
        Integer type = reading.nextInt();
        System.out.println("You can only use artists that are already registered in the following list. Remember to get them" +
                "by id");
        switch(type){
            case 1:
                addConcertCLI(reading, description, ageRestriction, duration, location, date1, pricePerTick);
                break;
            case 2:
                addMovieCLI(reading, description, ageRestriction, duration, location, date1, pricePerTick);
                break;
            case 3:
                addStandUpShowCLI(reading, description, ageRestriction, duration, location, date1, pricePerTick);
                break;
            case 4:
                addPlayCLI(reading, description, ageRestriction, duration, location, date1, pricePerTick);
                break;
            default:
                System.out.println("Unfortunately, we don't allow this type of event. Maybe try again?");
        }
        eventDurationSort();
    }

    private void addComedianCLI(Scanner reading, String name, String description) {
        System.out.println("So what comedy genre do you represent?");
        String comedyGenre = reading.nextLine();
        System.out.println("What position do you have in the show? opener/midler/headliner");
        String position = reading.nextLine();
        System.out.println("How many years have you been a comedian for?");
        Integer years = reading.nextInt();
        System.out.println("How long is your current set?");
        Integer setTime = reading.nextInt();
        String c = reading.nextLine();
        System.out.println("Any podcasts we know you from? (separate them by comma)");
        String[] podcasts = reading.nextLine().split(",");
        ArrayList<String> podcastNames = new ArrayList<>();
        Collections.addAll(podcastNames, podcasts);
        Comedian comedian = new Comedian(name, description, comedyGenre, position, years, setTime, podcastNames);
        performers.add(comedian);
        ComedianRepository comedianRepository = new ComedianRepository();
        comedianRepository.save(comedian);
        performerNameSort();
    }
    private void addSingerCLI(Scanner reading, String name, String description) {
        System.out.println("So what music genre do you represent?");
        String musicGenre = reading.nextLine();
        System.out.println("Are you part of a band? y/n");
        String isGroupString = reading.nextLine();
        Boolean isGroup = isGroupString.equals("y");
        System.out.println("What are your names (separate them by comma)");
        String[] names = reading.nextLine().split(",");
        ArrayList<String> nameMembers = new ArrayList<>();
        Collections.addAll(nameMembers, names);
        System.out.println("What are your best known songs (separate them by comma)");
        String[] songNames = reading.nextLine().split(",");
        ArrayList<String> nameSongs = new ArrayList<>();
        Collections.addAll(nameSongs, songNames);
        Singer singer = new Singer(name, description, musicGenre, isGroup, nameMembers, nameSongs);
        performers.add(singer);
        SingerRepository singerRepository = new SingerRepository();
        singerRepository.save(singer);
        performerNameSort();
    }
    private void addActorCLI(Scanner reading, String name, String description) {
        System.out.println("Have you won any awards? You can split them by comma");
        String[] awards1 = reading.nextLine().split(",");
        ArrayList<String> awardsFinal = new ArrayList<>();
        Collections.addAll(awardsFinal, awards1);
        System.out.println("Any past productions we should know you for?");
        String[] pastProductions = reading.nextLine().split(",");
        ArrayList<String> ppFinal = new ArrayList<>();
        Collections.addAll(ppFinal, pastProductions);
        System.out.println("See you later!");
        Actor actor = new Actor(name, description, awardsFinal, ppFinal);
        performers.add(actor);
        ActorRepository actorRepository = new ActorRepository();
        actorRepository.save(actor);
        performerNameSort();
    }



    private void addPlayCLI(Scanner reading, String description, Integer ageRestriction, Integer duration, Location location, Date date1, HashMap<String, Double> pricePerTick) {
        listAllActors();
        System.out.println("What genre does the play belong to?");
        String genreTheatre = reading.nextLine();
        System.out.println("What's the name of the play?");
        String playName = reading.nextLine();
        System.out.println("What's the director's name?");
        String directorName = reading.nextLine();
        System.out.println("What's the dresscode?");
        String dressCode = reading.nextLine();
        System.out.println("How many main characters are there in the play?");
        int noOfCastTheatre = reading.nextInt();
        Map<Performer, String> castTheatre = new HashMap<>();
        for(int i = 0; i < noOfCastTheatre; i++){
            System.out.println("Choose the id of the actor");
            String idActor = reading.nextLine();
            Performer actor = getPeformerById(idActor);
            System.out.println("Who is he playing in the play?");
            String nameCharacter = reading.nextLine();
            castTheatre.put(actor, nameCharacter);
        }
        TheatrePlay play = new TheatrePlay(description, ageRestriction, duration, location,
                date1, pricePerTick, genreTheatre, playName, directorName, dressCode, castTheatre);
        TheatrePlayRepository theatrePlayRepository = new TheatrePlayRepository();
        theatrePlayRepository.save(play);
        events.add(play);
    }
    private void addStandUpShowCLI(Scanner reading, String description, Integer ageRestriction, Integer duration, Location location, Date date1, HashMap<String, Double> pricePerTick) {
        listAllComedians();
        System.out.println("How many comedians are there in the show?");
        int noComedians = reading.nextInt();
        Set<Comedian> comedians = new HashSet<>();
        Map<Comedian, Integer> comedianSchedule = new HashMap<>();
        Map<Comedian, String> comedianRole = new HashMap<>();
        for(int i = 0; i < noComedians; i++){
            System.out.println("Choose the id of the comedian");
            String idComedian = reading.nextLine();
            Comedian comedian = (Comedian) getPeformerById(idComedian);
            comedians.add(comedian);
            comedianSchedule.put(comedian, comedian.getTimePerSet());
            comedianRole.put(comedian, comedian.getPositionInShow());
        }
        StandUpShow standUpShow = new StandUpShow(description, ageRestriction, duration, location,
                date1, pricePerTick, comedians, comedianSchedule, comedianRole);
        StandUpShowRepository standUpShowRepository = new StandUpShowRepository();
        standUpShowRepository.save(standUpShow);
        events.add(standUpShow);
    }
    private void addMovieCLI(Scanner reading, String description, Integer ageRestriction, Integer duration, Location location, Date date1, HashMap<String, Double> pricePerTick) {
        listAllActors();
        System.out.println("What genre does the movie belong to?");
        String genre = reading.nextLine();
        reading.nextLine();
        System.out.println("What's the movie called?");
        String name = reading.nextLine();
        System.out.println("Who is the director?");
        String director = reading.nextLine();
        System.out.println("How many main characters are there in the movie?");
        int noOfCast = reading.nextInt();
        Map<Performer, String> cast = new HashMap<>();
        for(int i = 0; i < noOfCast; i++){
            System.out.println("Choose the id of the actor");
            String idActor = reading.nextLine();
            reading.nextLine();
            Performer actor = getPeformerById(idActor);
            System.out.println("Who is he playing in the movie?");
            String nameCharacter = reading.nextLine();
            cast.put(actor, nameCharacter);
        }
        Movie movie= new Movie(description, ageRestriction, duration, location, date1,
                pricePerTick, genre, name, director, cast);
        MovieRepository movieRepository = new MovieRepository();
        movieRepository.save(movie);
        events.add(movie);
    }
    private void addConcertCLI(Scanner reading, String description, Integer ageRestriction, Integer duration, Location location, Date date1, HashMap<String, Double> pricePerTick) {
        listAllSingers();
        System.out.println("Who is opening your concert?");
        String idOpener = reading.nextLine();
        reading.nextLine();
        System.out.println("Who is the main act?");
        String idMainAct = reading.nextLine();
        System.out.println("How long will the opener perform?");
        Integer performanceOpener = reading.nextInt();
        Performer opener = getPeformerById(idOpener);
        Performer mainAct = getPeformerById(idMainAct);
        System.out.println("How long will the main act perform?");
        Integer mainActPerformance = reading.nextInt();
        Concert concert = new Concert(description, ageRestriction, duration,
                location, date1, pricePerTick, opener, mainAct, performanceOpener,
                mainActPerformance);
        ConcertRepository concertRepository = new ConcertRepository();
        concertRepository.save(concert);
        events.add(concert);
    }


    public Performer getPeformerById(String id){
        Performer performer = null;
        for(Performer perform : performers){
            if(perform.getPerformerId().equals(id)) {
                performer = perform;
                break;
            }
        }
        return performer;
    }

    public void listAllSingers(){
        performerNameSort();
        for (Performer perf : performers){
            if(perf instanceof Singer){
                perf.getPortrait();
            }
        }
    }
    public void listAllLocations(){
        locationCitySort();
        if(locations.isEmpty()){
            System.out.println("No locations to choose from, we're sorry for that");
        }
        for(Location location : locations){
            location.describeLocation();
        }
    }
    public void listAllClients(){
        for(Client client : clients){
            System.out.println(client.toString());
        }
    }
    public void listAllActors(){
        performerNameSort();
        for(Performer perf : performers){
            if(perf instanceof Actor){
                perf.getPortrait();
            }
        }
    }
    public void listAllComedians(){
        performerNameSort();
        for(Performer perf : performers){
            if(perf instanceof Comedian){
                perf.getPortrait();
            }
        }
}
    public void listAllPerformers(){
        performerNameSort();
        if(!performers.isEmpty()) {
            for (Performer performer : performers) {
                performer.getPortrait();
            }
        } else {
            System.out.println("Performers array is empty!");
        }
    }

    public Client clientGenerator(){
        String[] VECTOR_NAME = {"Jacque", "Rutigliano", "Gracia", "Hohl", "Jonas","Bermudes", "Linda","Unruh",
                "Lavoni","Gathers", "Lashell","Bolton", "Sadie","Nowlen",
                "Cheryl","Vicari", "Mitzi","Farrar", "Lanelle","Spoto",
                "Aleida","Linhart", "Kelsie","Turcotte", "Tameka","Giunta",
                "Alishia","Cudd", "Dyan","Ladue", "Darnell","Lema",
                "Natalie","Krawczyk", "Regenia","Gregory", "Renata","Aziz", "Sona","Rothfuss "};
        String name = VECTOR_NAME[random.nextInt(VECTOR_NAME.length)];
        String surname = VECTOR_NAME[random.nextInt(VECTOR_NAME.length)];
        Integer age = random.nextInt(99);
        return new Client(name, surname, age);
    }
    private Seating seatingGenerator(){
        Integer basic = random.nextInt(200);
        Integer premium = random.nextInt(200);
        Integer vip = random.nextInt(20);
        Map<String, Integer> hartaLocuri = new HashMap<>();
        hartaLocuri.put("BASIC", basic);
        hartaLocuri.put("PREMIUM", premium);
        hartaLocuri.put("VIP", vip);
        Integer total = basic + premium + vip;
        return new Seating(total, hartaLocuri);
    }
    public Location locationGenerator(){
        String[] CITIES = {"Istanbul", "Bucharest", "Moscow", "London", "Saint Petersburg",
        "Berlin", "Madrid", "Kiev", "Rome", "Paris", "Iasi", "Berlin", "Luxembourg",
        "Milan", "Budapest", "Warsaw", "Hamburg"};
        String[] PREPOSITIONS = {"Theatre", "Cinema", "Restaurant", "Garden", "Plaza"};
        String[] ADDR = {"Boulevard", "Street", "Str."};
        String nameOfVenue = CITIES[random.nextInt(CITIES.length)] + " " + PREPOSITIONS[random.nextInt(PREPOSITIONS.length)];
        String address = ADDR[random.nextInt(ADDR.length)] + " " +  CITIES[random.nextInt(CITIES.length)];
        String city = CITIES[random.nextInt(CITIES.length)];
        Seating seating = seatingGenerator();
        return new Location(nameOfVenue, address, city, seating);
    }
    public Performer performerGenerator(String type){
        String[] AWARDS = {"Oscar", "Emmy", "Golden Globe", "Golden bear", "Venice Festival Winner"};
        String[] VECTOR_NAME = {"Jacque", "Rutigliano", "Gracia", "Hohl", "Jonas","Bermudes", "Linda","Unruh",
                "Lavoni","Gathers", "Lashell","Bolton", "Sadie","Nowlen",
                "Cheryl","Vicari", "Mitzi","Farrar", "Lanelle","Spoto",
                "Aleida","Linhart", "Kelsie","Turcotte", "Tameka","Giunta",
                "Alishia","Cudd", "Dyan","Ladue", "Darnell","Lema",
                "Natalie","Krawczyk", "Regenia","Gregory", "Renata","Aziz", "Sona","Rothfuss "};
        String[] DESCRIPTION = {"it's awesome", "amazing", "showstopping", "never the same"};
        String name = VECTOR_NAME[random.nextInt(VECTOR_NAME.length)];
        String description = DESCRIPTION[random.nextInt(DESCRIPTION.length)];
        String[] MOVIES = {"Redemption", "Bitter Moon", "Control", "Titanic", "Everything", "Mary"};
        switch(type){
            case "Actor":
                Integer noOfAwards = random.nextInt(3);
                List<String> awards = new ArrayList<>();
                for(int i = 0; i < noOfAwards; i++) {
                    awards.add(AWARDS[random.nextInt(AWARDS.length)]);
                }

                Integer noOfPP = random.nextInt(3);
                List<String> pastP = new ArrayList<>();
                for (int i = 0; i < noOfPP; i++){
                    pastP.add(MOVIES[random.nextInt(MOVIES.length)]);
                }
                return new Actor(name, description, awards, pastP);
            case "Comedian":
                String[] COMEDY_GENRE = {"Alternative", "Character", "Music", "Improv", "Insult",
                "Observational", "Political satire", "Surreal"};
                String[] POSITIONS = {"opener", "headliner", "middler"};
                String[] PODCASTS = {"Dannish and O'Neill", "This is Important to Me", "The Comedy Snobs",
                "Comedy Fight Club", "Radio Nonsense"};
                String comedyGenre = COMEDY_GENRE[random.nextInt(COMEDY_GENRE.length)];
                String positionInShow = POSITIONS[random.nextInt(POSITIONS.length)];
                Integer tenure = random.nextInt(20);
                Integer timePerSet = random.nextInt(60);
                int noPodcasts = random.nextInt(3);
                List<String> podcasts = new ArrayList<>();
                for(int i = 0; i < noPodcasts; i++){
                    String pod = PODCASTS[random.nextInt(PODCASTS.length)];
                    podcasts.add(pod);
                }
                return new Comedian(name, description, comedyGenre, positionInShow, tenure, timePerSet, podcasts);
            case "Singer":
                String[] MUSIC_GENRES = {"Rock", "Pop", "EDM", "Hip-Hop", "Rap", "Jazz", "Classical"};
                String[] SONG_NAMES = {"Unfortunate", "Take it all", "Think about clouds", "Some Minutes", "Beloved Evening"};
                String musicGenre = MUSIC_GENRES[random.nextInt(MUSIC_GENRES.length)];
                Boolean isGroup = random.nextBoolean();
                List<String> memberNames = new ArrayList<>();
                if(!isGroup){
                    memberNames.add(VECTOR_NAME[random.nextInt(VECTOR_NAME.length)]);
                } else {
                    int maxMembers = random.nextInt(4);
                    for(int i = 0; i < maxMembers; i++){
                        memberNames.add(VECTOR_NAME[random.nextInt(VECTOR_NAME.length)]);
                    }
                }
                int noOfSongs = random.nextInt( 3);
                List<String> bestKnownSongs = new ArrayList<>();
                for(int i = 0; i < noOfSongs; i++){
                    bestKnownSongs.add(SONG_NAMES[random.nextInt(SONG_NAMES.length)]);
                }
                return new Singer(name, description, musicGenre, isGroup, memberNames, bestKnownSongs);
            default:
                System.out.println("You did not choose one of the viable options");
                return null;
        }

    }
    public Event randomEvent(String type) throws ParseException {
        String[] VECTOR_NAME = {"Jacque", "Rutigliano", "Gracia", "Hohl", "Jonas","Bermudes", "Linda","Unruh",
                "Lavoni","Gathers", "Lashell","Bolton", "Sadie","Nowlen",
                "Cheryl","Vicari", "Mitzi","Farrar", "Lanelle","Spoto",
                "Aleida","Linhart", "Kelsie","Turcotte", "Tameka","Giunta",
                "Alishia","Cudd", "Dyan","Ladue", "Darnell","Lema",
                "Natalie","Krawczyk", "Regenia","Gregory", "Renata","Aziz", "Sona","Rothfuss "};
        String[] DESCRIPTION = {"it's awesome", "amazing", "showstopping", "never the same"};
        String description = DESCRIPTION[random.nextInt(DESCRIPTION.length)];
        Integer ageRestriction = random.nextInt(18);
        Integer duration = random.nextInt(100);
        Location location;
        if(locations.isEmpty()){
            location = locationGenerator();
            locations.add(location);
        } else {
            location = locations.get(random.nextInt(locations.size()));
        }
        Date dateTime = createRandomDate(2021, 2030);
        Map<String, Double> priceTicket = new HashMap<>();
        Double start = 200.0;
        Double end = 490.0;
        Double priceBasic = start + (random.nextDouble() * (end - start));
        Double pricePremium = start + 300.0 +  (random.nextDouble() * (end - start));
        Double priceVIP = start + 900.0 + + (random.nextDouble() * (end - start));
        priceTicket.put("BASIC", Math.round(priceBasic*100.0)/100.0);
        priceTicket.put("PREMIUM", Math.round(pricePremium*100.0)/100.0);
        priceTicket.put("VIP", Math.round(priceVIP*100.0)/100.0);
        switch(type){
            case "Concert":
                Performer opener = performerGenerator("Singer");
                performers.add(opener);
                Performer main = performerGenerator("Singer");
                performers.add(main);
                Integer ptimeOpener = random.nextInt(120);
                Integer ptimeMain = random.nextInt(120);
                return new Concert(description, ageRestriction, duration, location, dateTime, priceTicket,
                        opener, main,ptimeOpener, ptimeMain);
            case "Movie":
                String[] MOVIE_GENRE = {"Horror", "Comedy", "Romance", "Romantic Comedy"};
                String[] MOVIES = {"Redemption", "Bitter Moon", "Control", "Titanic", "Everything", "Mary"};
                String genre = MOVIE_GENRE[random.nextInt(MOVIE_GENRE.length)];
                String movie = MOVIES[random.nextInt(MOVIES.length)];
                String director = VECTOR_NAME[random.nextInt(VECTOR_NAME.length)];
                Integer castMembers = random.nextInt(4);
                Map<Performer, String> cast = new HashMap<>();
                for(int i = 0; i < castMembers; i++){
                    Performer actor = performerGenerator("Actor");
                    performers.add(actor);
                    String plays = VECTOR_NAME[random.nextInt(VECTOR_NAME.length)];
                    cast.put(actor, plays);
                }
                return new Movie(description, ageRestriction, duration, location, dateTime, priceTicket,
                        genre, movie, director, cast);
            case "StandUpShow":
                Set<Comedian> comedians = new HashSet<>();
                Map<Comedian, Integer> schedule = new HashMap<>();
                Map<Comedian, String> roles = new HashMap<>();
                for(int i = 0; i < 3; i++){
                    Comedian comedian = (Comedian) performerGenerator("Comedian");
                    performers.add(comedian);
                    Integer howLong = comedian.getTimePerSet();
                    String role = comedian.getPositionInShow();
                    comedians.add(comedian);
                    schedule.put(comedian, howLong);
                    roles.put(comedian, role);
                }
                return new StandUpShow(description, ageRestriction, duration, location, dateTime, priceTicket,
                        comedians, schedule, roles);
            case "TheatrePlay":
                String[] PLAY_GENRE = {"Drama", "Comedy", "Romance", "Tragedy"};
                String[] DRESS_CODE = {"formal", "casual", "office"};
                String[] PLAYS = {"Redemption", "Bitter Moon", "Control", "Oedip", "Avar", "Everything", "Mary"};
                String genrePlay = PLAY_GENRE[random.nextInt(PLAY_GENRE.length)];
                String play = PLAYS[random.nextInt(PLAYS.length)];
                String directorPlay = VECTOR_NAME[random.nextInt(VECTOR_NAME.length)];
                Integer castMembersPlay = random.nextInt(4);
                Map<Performer, String> castPlay = new HashMap<>();
                for(int i = 0; i < castMembersPlay; i++){
                    Performer actor = performerGenerator("Actor");
                    performers.add(actor);
                    String plays = VECTOR_NAME[random.nextInt(VECTOR_NAME.length)];
                    castPlay.put(actor, plays);
                }
                String dressCode = DRESS_CODE[random.nextInt(DRESS_CODE.length)];
                return new TheatrePlay(description, ageRestriction, duration, location, dateTime, priceTicket,
                        genrePlay, play, directorPlay, dressCode, castPlay);
            default:
                return null;
        }
    }
    public static int createRandomIntBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }
    public static Date createRandomDate(int startYear, int endYear) throws ParseException {
        int day = createRandomIntBetween(1, 28);
        int month = createRandomIntBetween(1, 12);
        int year = createRandomIntBetween(startYear, endYear);
        String data = day + "/" + month + "/" + year;
        return new SimpleDateFormat("dd/MM/yyyy").parse(data);
    }

    public void listAllEvents(){
        eventDurationSort();
        for(Event event : events){
            event.getPresentation();
        }
    }
    public void seeEventsByCategory(String type){
        switch(type){
            case "Concert":
                int i = 0;
                for(Event e : events){
                    if (e instanceof Concert) {
                        if(i == 0){
                            System.out.println("Concerts:\n");
                            i++;
                        }
                        e.getPresentation();
                    }
                }
                break;
            case "Movie":
                int j = 0;
                for(Event e : events){
                    if (e instanceof Movie) {
                        if(j == 0){
                            System.out.println("Movies:\n");
                            j++;
                        }
                        e.getPresentation();
                    }
                }
                break;
            case "StandUpShow":
                int k = 0;
                for(Event e : events){
                    if (e instanceof StandUpShow) {
                        if(k == 0){
                            System.out.println("Stand-up shows:\n");
                            k++;
                        }
                        e.getPresentation();
                    }
                }
                break;
            case "TheatrePlay":
                int l = 0;
                for(Event e : events){
                    if (e instanceof TheatrePlay) {
                        if(l == 0){
                            System.out.println("Theatre plays:\n");
                            l++;
                        }
                        e.getPresentation();
                    }
                }
                break;
                default:
                System.out.println("no such type");
        }
    }
    public void displayEvent(Event event){
        event.getPresentation();
    }
    public void findEventByCity(){
        Scanner reading = new Scanner(System.in);
        System.out.println("Insert the city you want to querry events from, please.");
        String city = reading.nextLine();
        int flag = 0;
        for(Location location : locations){
            if (location.getCity().equals(city)) {
                flag = 1;
                break;
            }
        }
        if(flag == 1) {
            int found = 0;
            for (Event e : events) {
                if (e.getLocation().getCity().equals(city)) {
                    e.getPresentation();
                    found = 1;
                }
            }
            if (found == 0) {
                System.out.println("Sorry, no available events atm.");
            }
        }
        else {
            System.out.println("Sorry, you entered an invalid locations. Check the location list we provide and try again");
        }
    }

    public void eventDurationSort(){
        events.sort(new EventDurationSorter());
    }
    public void locationCitySort(){
        locations.sort(new LocationCitySorter());
    }
    public void performerNameSort(){
        performers.sort(new PerformerNameSorter());
    }

    public Set<Client> filterAllUnderage(int age_restriction){
        Filterable<Client, Integer> underage = new CriteriaUnderage();
        Set<Client> underageClient =  underage.filter(clients, age_restriction);
        for(Client client : underageClient){
            System.out.println(client.getName() + " " +  client.getSurname() + " - " + client.getAge());
        }
        if(underageClient.isEmpty())
        {
            System.out.println("There are no underage clients.");
        }
        return underageClient;
    }

    public List<Location> getLocations() {
        return locations;
    }
    public List<Client> getClients() {
        List<Client> clientList = new ArrayList<Client>();
        clientList.addAll(clients);
        return clientList;}
    public List<Event> getEvents(){
        return events;
    }
    public List<Performer> getPerformers(){
        return performers;
    }
    public Location getLocationById(String id) throws LocationNotFound {
        for (Location loc : locations){
            if(loc.getId().equals(id)){
                return loc;
            }
        }
        throw new LocationNotFound();
    }

    public Performer getPerformerById(String id) throws PerformerNotFound{
        for (Performer perf : performers){
            if(perf.getPerformerId().equals(id)){
                return perf;
            }
        }
        System.out.println(id);
        throw new PerformerNotFound();
    }

    public Client getClientById(String id) throws ClientNotFound{
        for (Client c : clients){
            if(c.getClientId().equals(id))
                return c;
        }
        System.out.println(id);
        throw new ClientNotFound();
    }

    public Event getEventById(String id) throws EventNotFound{
        for (Event e : events){
            if(e.getId().equals(id))
                return e;
        }
        System.out.println(id);
        throw new EventNotFound();
    }

    public Comedian getComedianByName(String name) throws PerformerNotFound{
        for (Performer perf : performers){
            if(perf instanceof Comedian && perf.getName().equals(name)){
                return (Comedian) perf;
            }
        }
        System.out.println(name);
        throw new PerformerNotFound();
    }

    public void showMenu(){
        System.out.println("------Add using the CLI-------");
        System.out.println("1. Add a new location using the CLI");
        System.out.println("2. Add a new client using the CLI");
        System.out.println("3. Add a new performer using the CLI");
        System.out.println("4. Add a new event using the CLI");
        System.out.println("------Add using randim generators-------");
        System.out.println("5. Generate a random location");
        System.out.println("6. Generate a random client");
        System.out.println("7. Generate a random performer");
        System.out.println("8. Generate a random event");
        System.out.println("------Sorting functionalities-------");
        System.out.println("9. Sort the events by duration");
        System.out.println("10. Sort the locations by city");
        System.out.println("11. Sort the performers by name");
        System.out.println("-------List all the contents of the collections-------");
        System.out.println("12. List all the locations");
        System.out.println("13. List all the clients");
        System.out.println("14. List all the events");
        System.out.println("15. List all the performers");
        System.out.println("30. List all singers");
        System.out.println("31. List all actors");
        System.out.println("32. List all comedians");
        System.out.println("------Find events by criteria-------");
        System.out.println("16. See events by category");
        System.out.println("17. See events by location");
        System.out.println("-------Client service functionalities---------");
        System.out.println("18. Buy a ticket");
        System.out.println("19. Return a ticket");
        System.out.println("20. Book a ticket");
        System.out.println("21. Cancel a booking");
        System.out.println("22. Check your account balance and vouchers");
        System.out.println("23. Add funds to your account");
        System.out.println("24. See your tickets");
        System.out.println("25. See your reservations");
        System.out.println("26. Gift a voucher");
        System.out.println("27. Transform a booking to a ticket");
        System.out.println("28. Filter all underage clients.");
        System.out.println("29. Display a certain event.");
        System.out.println("--------Updates--------");
        System.out.println("33. Update client details");
        System.out.println("34. Update location details");
        System.out.println("35. Update singer details");
        System.out.println("36. Update actor details");
        System.out.println("37. Update comedian details");
        System.out.println("--------Delete----------");
        System.out.println("38. Delete a client");
        System.out.println("39. Delete a location");
        System.out.println("40. Delete a singer");
        System.out.println("41. Delete an actor");
        System.out.println("42. Delete a comedian");
    }

}


