import com.nevent.model.client.Client;
import com.nevent.model.event.Concert;
import com.nevent.model.event.Event;
import com.nevent.model.event.Movie;
import com.nevent.model.event.TheatrePlay;
import com.nevent.model.location.Location;
import com.nevent.model.location.Seating;
import com.nevent.model.performer.Actor;
import com.nevent.model.performer.Comedian;
import com.nevent.model.performer.Performer;
import com.nevent.model.performer.Singer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
//TODO: refactor the massive function please
public class Service
{
    ArrayList<Location> Locations;
    ArrayList<Performer> Performers;
    ArrayList<Event> Events;
    ArrayList<Client> Clients;

    public void addANewLocation(){
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
        System.out.println("Please tell me the name of your location");
        String locationName = reading.nextLine();
        System.out.println("Now the address");
        String address = reading.nextLine();
        System.out.println("Now the city of your location, please");
        String city = reading.nextLine();
        Location location = new Location(locationName, address, city, seating);
        Locations.add(location);
        System.out.println("Congrats, you have added a new location");
        reading.close();
    }

    public void addANewClient(){
        Scanner reading = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Hi, tell me your first name");
        String name = reading.nextLine();
        System.out.print("Great! Now your last name");
        String surname = reading.nextLine();
        System.out.println("And wil you please give us your age?");
        Integer age = reading.nextInt();
        System.out.println("That's great!");
        Client client = new Client(name, surname, age);
        Clients.add(client);
        System.out.println("Awesome! We created you an account and" +
                " you have no credit nor tickets so maybe add some :)");
        reading.close();
    }

    public void addANewPerformer(){
        Scanner reading = new Scanner(System.in);
        System.out.println("Hi, can you tell me your name? ");
        String name = reading.nextLine();
        System.out.println("And maybe a short description of yourself");
        String description = reading.nextLine();
        System.out.println("Of the following, which one describes you best: 1. Actor\n2.Comedian\n3.Singer");
        Integer button = reading.nextInt();
        switch(button){
            case 1:
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
                break;
            case 3:
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
                break;
            case 2:
                System.out.println("So what comedy genre do you represent?");
                String comedyGenre = reading.nextLine();
                System.out.println("What position do you have in the show? opener/midler/headliner");
                String position = reading.nextLine();
                System.out.println("How many years have you been a comedian for?");
                Integer years = reading.nextInt();
                System.out.println("How long is your current set?");
                Integer setTime = reading.nextInt();
                System.out.println("Any podcasts we know you from? (separate them by comma)");
                String[] podcasts = reading.nextLine().split(",");
                ArrayList<String> podcastNames = new ArrayList<>();
                Collections.addAll(podcastNames, podcasts);
                Comedian comedian = new Comedian(name, description, comedyGenre, position, years, setTime, podcastNames);
                Performers.add(comedian);
                break;
            default:
                System.out.println("UhOh! That's not a job (at least according to us). Aborting..Try again");
        }
        reading.close();
    }
    public void addAnEvent() throws ParseException {
        Scanner reading = new Scanner(System.in);
        System.out.println("Hi! Could you describe your event in a short sentance");
        String description = reading.nextLine();
        System.out.println("Is there any age restriction we should impose?");
        Integer ageRestriction = reading.nextInt();
        System.out.println("How long will your event last for?");
        Integer duration = reading.nextInt();
        System.out.println("Please pick one of the following locations by introducing the id");
        for(Location location : Locations){
            System.out.println("id: " + location.getId() + ". " + location.getAddress() + " " + location.getAddress());
        }
        String locationId = reading.nextLine();
        Location location = null;
        for(Location loc : Locations){
            if(loc.getId().equals(locationId))
                location = loc;
        }
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
                listAllSingers();
                System.out.println("Who is opening your concert?");
                String idOpener = reading.nextLine();
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
            case 2:
                listAllActors();
                System.out.println("What genre does the movie belong to?");
                String genre = reading.nextLine();
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
                    Performer actor = getPeformerById(idActor);
                    System.out.println("Who is he playing in the movie?");
                    String nameCharacter = reading.nextLine();
                    cast.put(actor, nameCharacter);
                }
                Movie movie= new Movie(description, ageRestriction, duration, location, date1,
                        pricePerTick, genre, name, director, cast);
                Events.add(movie);
            case 3:
                listAllComedians();
                System.out.println("How many comedians are there in the show?");
                int noComedians = reading.nextInt();
                Set<Performer> comedians = new HashSet<>();
                Map<Performer, Integer> comedianSchedule = new HashMap<>();
                Map<Performer, String> comedianRole = new HashMap<>();
                for(int i = 0; i < noComedians; i++){
                    System.out.println("Choose the id of the comedian");
                    String idComedian = reading.nextLine();
                    //Performer comedian = new Comedian(); //TODO fix downcasting issue;
                }
            case 4:
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
            default:
                System.out.println("Unfortunarely, we don't allow this type of event. Maybe try again?");
        }
    }
//TODO: fix horrible code duplication
    public void listAllSingers(){
        for (Performer perf : Performers){
            if(perf instanceof Singer){
                perf.getPortrait();
            }
        }
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
}


