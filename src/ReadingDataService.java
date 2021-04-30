import com.nevent.model.client.Client;
import com.nevent.model.event.Concert;
import com.nevent.model.event.Movie;
import com.nevent.model.event.StandUpShow;
import com.nevent.model.event.TheatrePlay;
import com.nevent.model.exceptions.LocationNotFound;
import com.nevent.model.exceptions.PerformerNotFound;
import com.nevent.model.location.Location;
import com.nevent.model.location.Seating;
import com.nevent.model.performer.Actor;
import com.nevent.model.performer.Comedian;
import com.nevent.model.performer.Performer;
import com.nevent.model.performer.Singer;
import com.sun.tools.javac.Main;


import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReadingDataService {
    private static ReadingDataService instance = null;
    private static MainService mainService = MainService.getInstance();
    public static ReadingDataService getInstance()
    {
        if(instance == null){
            instance = new ReadingDataService();
        }
        return instance; }
    private ReadingDataService(){}
    public List<Client> readClientCSV(){
        List<Client> clients = new ArrayList<Client>();
        try{
           BufferedReader csvReader = new BufferedReader(new FileReader("./resource/input/client.csv"));
           String row;
           while ((row = csvReader.readLine()) != null){
               String[] data = row.split(",");
               clients.add(new Client(data[0], data[1], Integer.parseInt(data[2])));
           }
           csvReader.close();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
        return clients;
    }

    public List<Location> readLocationCSV(){
        List<Location> locations = new ArrayList<Location>();
        try{
            BufferedReader csvReader = new BufferedReader(new FileReader("./resource/input/location.csv"));
            String row;
            while((row = csvReader.readLine()) != null){
                String[] data = row.split(",");
                Integer basic_tickets = Integer.parseInt(data[3]);
                Integer premium_tickets = Integer.parseInt(data[4]);
                Integer vip_tickets = Integer.parseInt(data[5]);
                Map<String, Integer> hartaLocuri = new HashMap<>();
                hartaLocuri.put("BASIC", basic_tickets);
                hartaLocuri.put("PREMIUM", premium_tickets);
                hartaLocuri.put("VIP", vip_tickets);
                Integer total = basic_tickets + premium_tickets + vip_tickets;
                Seating seating = new Seating(total, hartaLocuri);
                Location location = new Location(data[0], data[1], data[2], seating);
                locations.add(location);
            }
            csvReader.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return locations;
    }

    public List<Actor> readActorCSV(){
        List<Actor> actors = new ArrayList<Actor>();
        try{
            BufferedReader csvReader = new BufferedReader(new FileReader("./resource/input/actor.csv"));
            String row;
            while((row = csvReader.readLine()) != null){
                String[] data = row.split(",");
                Integer numberOfAwards = Integer.parseInt(data[2]);
                String award = data[3];
                List<String> awards = new ArrayList<>();
                if (numberOfAwards != 0) {
                    awards.add(award);
                }
                Integer numberOfMovies = Integer.parseInt(data[4]);
                String movie = data[5];
                List<String> productions = new ArrayList<String>();
                productions.add(movie);
                Actor actor = new Actor(data[0], data[1], awards, productions);
                actors.add(actor);
            }
            csvReader.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return actors;
    }

    public List<Comedian> readComedianCSV(){
        List<Comedian> comedians = new ArrayList<Comedian>();
        try{
            BufferedReader csvReader = new BufferedReader(new FileReader("./resource/input/comedian.csv"));
            String row;
            while((row = csvReader.readLine()) != null){
                String[] data = row.split(",");
                List<String> podcasts = new ArrayList<String>();
                String[] data2 = data[6].split(";");
                Collections.addAll(podcasts, data2);
                Comedian comedian = new Comedian(data[0], data[1], data[2], data[3], Integer.parseInt(data[4]), Integer.parseInt(data[5]), podcasts);
                comedians.add(comedian);
            }
            csvReader.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return comedians;
    }

    public List<Singer> readSingerCSV(){
        List<Singer> singers = new ArrayList<Singer>();
        try{
            BufferedReader csvReader = new BufferedReader(new FileReader("./resource/input/singer.csv"));
            String row;
            while((row = csvReader.readLine()) != null){
                String[] data = row.split(",");
                List<String> members = new ArrayList<String>();
                String[] data2 = data[4].split(";");
                Collections.addAll(members, data2);
                List<String> knownSongs = new ArrayList<String>();
                String[] data3 = data[5].split(";");
                Collections.addAll(knownSongs, data3);
                Singer singer = new Singer(data[0], data[1], data[2], Boolean.parseBoolean(data[3]), members, knownSongs);
                singers.add(singer);
            }
            csvReader.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return singers;
    }

    public List<Concert> readConcertCSV(){
        List<Concert> concerts = new ArrayList<Concert>();
        try{
            BufferedReader csvReader = new BufferedReader(new FileReader("./resource/input/concert.csv"));
            String row;
            while((row = csvReader.readLine()) != null){
                String[] data = row.split(",");
                String locationID = data[3];
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Double price_basic = Double.parseDouble(data[5]);
                Double price_premium = Double.parseDouble(data[6]);
                Double price_vip = Double.parseDouble(data[7]);
                HashMap<String, Double> prices = new HashMap<>();
                prices.put("BASIC", price_basic);
                prices.put("PREMIUM", price_premium);
                prices.put("VIP", price_vip);
                String openerID = data[8];
                String mainActID = data[9];
                Integer openerTime = Integer.parseInt(data[10]);
                Integer mainActTime = Integer.parseInt(data[11]);
                Concert concert = new Concert(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]), mainService.getLocationById(locationID), df.parse(data[4]),
                        prices, mainService.getPerformerById(openerID), mainService.getPerformerById(mainActID), openerTime, mainActTime);
                concerts.add(concert);
            }
            csvReader.close();
        }catch (IOException | ParseException | LocationNotFound | PerformerNotFound e){
            System.out.println(e.getMessage());
        }
        return concerts;
    }

    public List<Movie> readMovieCSV(){
        List<Movie> movies = new ArrayList<Movie>();
        try{
            BufferedReader csvReader = new BufferedReader(new FileReader("./resource/input/movie.csv"));
            String row;
            while((row = csvReader.readLine()) != null){
                String[] data = row.split(",");
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Double price_basic = Double.parseDouble(data[5]);
                Double price_premium = Double.parseDouble(data[6]);
                Double price_vip = Double.parseDouble(data[7]);
                HashMap<String, Double> prices = new HashMap<>();
                prices.put("BASIC", price_basic);
                prices.put("PREMIUM", price_premium);
                prices.put("VIP", price_vip);
                List<String> actorIDs = new ArrayList<String>();
                String[] data3 = data[11].split(";");
                Collections.addAll(actorIDs, data3);
                List<String> characters = new ArrayList<String>();
                String[] data4 = data[12].split(";");
                Collections.addAll(characters, data4);
                HashMap<Performer, String> cast = new HashMap<>();
                for (int i = 0; i < actorIDs.size(); i++){
                    Performer p = mainService.getPerformerById(actorIDs.get(i));
                    String name1 = characters.get(i);
                    cast.put(p, name1);
                }
                movies.add(new Movie(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]), mainService.getLocationById(data[3]),
                        df.parse(data[4]), prices, data[8], data[9], data[10], cast));
            }
            csvReader.close();
        }catch (IOException | ParseException | LocationNotFound | PerformerNotFound e){
            System.out.println(e.getMessage());
        }
        return movies;
    }

    public List<StandUpShow> readStandUpShowCSV(){
        List<StandUpShow> standUpShows = new ArrayList<StandUpShow>();
        try{
            BufferedReader csvReader = new BufferedReader(new FileReader("./resource/input/standupshow.csv"));
            String row;
            while((row = csvReader.readLine()) != null){
                String[] data = row.split(",");
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Double price_basic = Double.parseDouble(data[5]);
                Double price_premium = Double.parseDouble(data[6]);
                Double price_vip = Double.parseDouble(data[7]);
                HashMap<String, Double> prices = new HashMap<>();
                prices.put("BASIC", price_basic);
                prices.put("PREMIUM", price_premium);
                prices.put("VIP", price_vip);

                //TODO: bug comedianId = comedianName
                List<String> comedianNames = new ArrayList<String>();
                String[] data3 = data[8].split(";");
                Collections.addAll(comedianNames, data3);

                List<String> timePerComedian = new ArrayList<String>();
                String[] data4 = data[9].split(";");
                Collections.addAll(timePerComedian, data4);

                List<String> positionInShow = new ArrayList<String>();
                String[] data5 = data[10].split(";");
                Collections.addAll(positionInShow, data5);

                Set<Comedian> performers = new HashSet<>();
                Map<Comedian, Integer> schedule = new HashMap<>();
                Map<Comedian, String> positions = new HashMap<>();

                for(int i = 0; i < comedianNames.size(); i++){
                    Comedian p = mainService.getComedianByName(comedianNames.get(i));
                    performers.add(p);
                    schedule.put(p, Integer.parseInt(timePerComedian.get(i)));
                    positions.put(p, positionInShow.get(i));
                }

                standUpShows.add(new StandUpShow(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]), mainService.getLocationById(data[3]),
                        df.parse(data[4]), prices, performers, schedule, positions));
            }
            csvReader.close();
        }catch (IOException | ParseException | LocationNotFound | PerformerNotFound e){
            System.out.println(e.getMessage());
        }
        return standUpShows;
    }

    public List<TheatrePlay> readTheatrePlayCSV(){
        List<TheatrePlay> theatrePlays = new ArrayList<TheatrePlay>();
        try{
            BufferedReader csvReader = new BufferedReader(new FileReader("./resource/input/theatreplay.csv"));
            String row;
            while((row = csvReader.readLine()) != null){
                String[] data = row.split(",");
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Double price_basic = Double.parseDouble(data[5]);
                Double price_premium = Double.parseDouble(data[6]);
                Double price_vip = Double.parseDouble(data[7]);
                HashMap<String, Double> prices = new HashMap<>();
                prices.put("BASIC", price_basic);
                prices.put("PREMIUM", price_premium);
                prices.put("VIP", price_vip);
                List<String> performerIDs = new ArrayList<String>();
                String[] data3 = data[12].split(";");
                Collections.addAll(performerIDs, data3);
                List<String> roles = new ArrayList<String>();
                String[] data4 = data[13].split(";");
                Collections.addAll(roles, data4);
                HashMap<Performer, String> cast = new HashMap<>();
                for(int i = 0; i < performerIDs.size(); i++){
                    Performer p = mainService.getPerformerById(performerIDs.get(i));
                    cast.put(p, roles.get(i));
                }

                theatrePlays.add(new TheatrePlay(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]), mainService.getLocationById(data[3]),
                        df.parse(data[4]), prices, data[8], data[9], data[10], data[11], cast));
            }
            csvReader.close();
        }catch (IOException | ParseException | LocationNotFound | PerformerNotFound e){
            System.out.println(e.getMessage());
        }
        return theatrePlays;
    }

}
