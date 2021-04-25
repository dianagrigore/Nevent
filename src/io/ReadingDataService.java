package io;

import com.nevent.model.client.Client;
import com.nevent.model.location.Location;
import com.nevent.model.location.Seating;
import com.nevent.model.performer.Actor;
import com.nevent.model.performer.Comedian;
import com.nevent.model.performer.Singer;

import java.io.*;
import java.util.*;

public class ReadingDataService {
    private static ReadingDataService instance = null;
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
           BufferedReader csvReader = new BufferedReader(new FileReader("./resource/client.csv"));
           String row;
           while ((row = csvReader.readLine()) != null){
               String[] data = row.split(",");
               String name = data[0];
               String surname = data[1];
               Integer age = Integer.parseInt(data[2]);
               Client c = new Client(name, surname, age);
               clients.add(c);
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
            BufferedReader csvReader = new BufferedReader(new FileReader("./resource/location.csv"));
            String row;
            while((row = csvReader.readLine()) != null){
                String[] data = row.split(",");
                String nameOfVenue = data[0];
                String address = data[1];
                String city = data[2];
                Integer basic_tickets = Integer.parseInt(data[3]);
                Integer premium_tickets = Integer.parseInt(data[4]);
                Integer vip_tickets = Integer.parseInt(data[5]);
                Map<String, Integer> hartaLocuri = new HashMap<>();
                hartaLocuri.put("BASIC", basic_tickets);
                hartaLocuri.put("PREMIUM", premium_tickets);
                hartaLocuri.put("VIP", vip_tickets);
                Integer total = basic_tickets + premium_tickets + vip_tickets;
                Seating seating = new Seating(total, hartaLocuri);
                Location location = new Location(nameOfVenue, address, city, seating);
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
            BufferedReader csvReader = new BufferedReader(new FileReader("./resource/actor.csv"));
            String row;
            while((row = csvReader.readLine()) != null){
                String[] data = row.split(",");
                String name = data[0];
                String description = data[1];
                Integer numberOfAwards = Integer.parseInt(data[2]);
                String award = data[3];
                List<String> awards = new ArrayList<>();
                if (numberOfAwards != 0) {
                    awards.add(award);
                }
                Integer numberOfMovies = Integer.parseInt(data[3]);
                String movie = data[4];
                List<String> productions = new ArrayList<String>();
                productions.add(movie);
                Actor actor = new Actor(name, description, awards, productions);
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
            BufferedReader csvReader = new BufferedReader(new FileReader("./resource/comedian.csv"));
            String row;
            while((row = csvReader.readLine()) != null){
                String[] data = row.split(",");
                String name = data[0];
                String description = data[1];
                String genreOfComedy = data[2];
                String positionInShow = data[3];
                Integer tenure = Integer.parseInt(data[4]);
                Integer timePerSet = Integer.parseInt(data[5]);
                List<String> podcasts = new ArrayList<String>();
                String[] data2 = data[6].split("|");
                Collections.addAll(podcasts, data2);
                Comedian comedian = new Comedian(name, description, genreOfComedy, positionInShow, tenure, timePerSet, podcasts);
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
            BufferedReader csvReader = new BufferedReader(new FileReader("./resource/singer.csv"));
            String row;
            while((row = csvReader.readLine()) != null){
                String[] data = row.split(",");
                String name = data[0];
                String description = data[1];
                String genre = data[2];
                Boolean isGroup = Boolean.parseBoolean(data[3]);
                List<String> members = new ArrayList<String>();
                String[] data2 = data[4].split("|");
                Collections.addAll(members, data2);
                List<String> knownSongs = new ArrayList<String>();
                String[] data3 = data[5].split("|");
                Collections.addAll(knownSongs, data3);
                Singer singer = new Singer(name, description, genre, isGroup, members, knownSongs);
                singers.add(singer);
            }
            csvReader.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return singers;
    }




}
