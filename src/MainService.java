import com.nevent.model.client.Client;
import com.nevent.model.comparators.EventDurationSorter;
import com.nevent.model.comparators.LocationCitySorter;
import com.nevent.model.comparators.PerformerNameSorter;
import com.nevent.model.event.*;
import com.nevent.model.location.Location;
import com.nevent.model.location.Seating;
import com.nevent.model.performer.Actor;
import com.nevent.model.performer.Comedian;
import com.nevent.model.performer.Performer;
import com.nevent.model.performer.Singer;

import java.text.ParseException;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.*;


public class MainService
{
    private final List<Location> Locations;
    private final List<Performer> Performers;
    private final List<Event> Events;
    private final Set<Client> Clients;
    private final Random random;

    public MainService() {
        Locations = new ArrayList<>();
        Performers = new ArrayList<>();
        Events = new ArrayList<>();
        Clients = new HashSet<>();
        random = new Random();
    }
    public void addLocationToArray(Location location){
        Locations.add(location);
    }
    public void addClientToArray(Client client){Clients.add(client);}
    public void addEventToArray(Event event){Events.add(event);}
    public void addPerformerToArray(Performer performer){Performers.add(performer);}

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
        Locations.add(location);
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
        Clients.add(client);
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
        System.out.println("Please pick one of the following locations by introducing the id");
        listAllLocations();
        String locationId = reading.nextLine();
        Location location = null;
        for(Location loc : Locations){
            if(loc.getId().equals(locationId))
                location = loc;
        }
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
        Performers.add(comedian);
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
        Performers.add(singer);
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
        Performers.add(actor);
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
        Events.add(play);
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
        Events.add(standUpShow);
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
        Events.add(movie);
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
        Events.add(concert);
    }


    public Performer getPeformerById(String id){
        Performer performer = null;
        for(Performer perform : Performers){
            if(perform.getPerformerId().equals(id)) {
                performer = perform;
                break;
            }
        }
        return performer;
    }

    public void listAllSingers(){
        for (Performer perf : Performers){
            if(perf instanceof Singer){
                perf.getPortrait();
            }
        }
    }
    public void listAllLocations(){
        if(Locations.isEmpty()){
            System.out.println("No locations to choose from, we're sorry for that");
        }
        for(Location location : Locations){
            location.describeLocation();
        }
    }
    public void listAllClients(){
        for(Client client : Clients){
            System.out.println(client.toString());
        }
    }
    public void listAllActors(){
        for(Performer perf : Performers){
            if(perf instanceof Actor){
                perf.getPortrait();
            }
        }
    }
    public void listAllComedians(){
        for(Performer perf : Performers){
            if(perf instanceof Comedian){
                perf.getPortrait();
            }
        }
}
    public void listAllPerformers(){
        if(!Performers.isEmpty()) {
            for (Performer performer : Performers) {
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
                String positionInShow = COMEDY_GENRE[random.nextInt(POSITIONS.length)];
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
                if(isGroup){
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
        if(Locations.isEmpty()){
            location = locationGenerator();
            Locations.add(location);
        } else {
            location = Locations.get(random.nextInt(Locations.size()));
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
                Performers.add(opener);
                Performer main = performerGenerator("Singer");
                Performers.add(main);
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
                    Performers.add(actor);
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
                    Performers.add(comedian);
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
                    Performers.add(actor);
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
        for(Event event : Events){
            event.getPresentation();
        }
    }
    public void seeEventsByCategory(String type){
        switch(type){
            case "Concert":
                int i = 0;
                for(Event e : Events){
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
                for(Event e : Events){
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
                for(Event e : Events){
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
                for(Event e : Events){
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
        for(Location location : Locations){
            if (location.getCity().equals(city)) {
                flag = 1;
                break;
            }
        }
        if(flag == 1) {
            int found = 0;
            for (Event e : Events) {
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
        Events.sort(new EventDurationSorter());
    }
    public void locationCitySort(){
        Locations.sort(new LocationCitySorter());
    }
    public void performerNameSort(){
        Performers.sort(new PerformerNameSorter());
    }
}


