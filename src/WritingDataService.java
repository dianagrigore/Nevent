import com.nevent.model.client.Client;
import com.nevent.model.event.Concert;
import com.nevent.model.event.Movie;
import com.nevent.model.event.StandUpShow;
import com.nevent.model.event.TheatrePlay;
import com.nevent.model.location.Location;
import com.nevent.model.location.Seating;
import com.nevent.model.performer.Comedian;
import com.nevent.model.performer.Actor;
import com.nevent.model.performer.Performer;
import com.nevent.model.performer.Singer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class WritingDataService{
    private static WritingDataService instance = null;
    private WritingDataService() {}
    public static WritingDataService getInstance()
    {
        if(instance == null){
            instance = new WritingDataService();
        }
        return instance;
    }
    public void writeLocationCSV(List<Location> locations){
        try{
            File file = new File("./resource/output/location.csv");
            file.createNewFile();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        try{
            FileWriter writer = new FileWriter("./resource/output/location.csv");
            writer.write("");
            for (Location location : locations){
                writer.write(location.getNameOfVenue() + ", " + location.getAddress() + ", " + location.getCity());
                Seating seating = location.getSeating();
                for (Map.Entry<String,Integer> entry : seating.getTicketsOfEachType().entrySet()){
                    writer.write(", " + entry.getValue());
                }
                writer.write('\n');
            }
            writer.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void writeClientCSV(List<Client> clients){
        try{
            File file = new File("./resource/output/client.csv");
            file.createNewFile();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        try{
            FileWriter writer = new FileWriter("./resource/output/client.csv");
            writer.write("");
            for (Client client:clients){
                writer.write(client.getName() + ',' + client.getSurname() + ',' + client.getAge());
                writer.write('\n');
            }
            writer.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void writeActorCSV(List<Actor> actors){
        try{
            File file = new File("./resource/output/actor.csv");
            file.createNewFile();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        try{
            FileWriter writer = new FileWriter("./resource/output/actor.csv");
            writer.write("");
            for (Actor actor: actors){
                writer.write(actor.getName() + ',' +actor.getDescription() + ',' + actor.getAwards() + ',' + actor.getPastProductions());
                writer.write('\n');
            }
            writer.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void writeComedianCSV(List<Comedian> comedians){
        try{
            File file = new File("./resource/output/comedian.csv");
            file.createNewFile();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        try{
            FileWriter writer = new FileWriter("./resource/output/comedian.csv");
            writer.write("");
            for (Comedian comedian:comedians){
                writer.write(comedian.getName() + ',' + comedian.getDescription() + ',' + comedian.getGenreOfComedy() + ','
                + comedian.getPositionInShow()+ ',' + comedian.getTenure() + ',' + comedian.getTimePerSet() + ',' + comedian.getPodcasts());
                writer.write('\n');
            }
            writer.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void writeSingerCSV(List<Singer> singers){
        try{
            File file = new File("./resource/output/singer.csv");
            file.createNewFile();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        try{
            FileWriter writer = new FileWriter("./resource/output/singer.csv");
            writer.write("");
            for (Singer singer:singers){
                writer.write(singer.getName() + ',' + singer.getDescription() + ',' + singer.getMusicGenre() + ',' + singer.getGroup() + ',' );
                for (String singer2 : singer.getMemberNames()){
                    writer.write(singer2 + '|');
                }
                writer.write(',');
                for(String song : singer.getBestKnownSongs())
                    writer.write(song + '|');
                writer.write('\n');
            }
            writer.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void writeConcertCSV(List<Concert> concerts){
        try{
            File file = new File("./resource/output/concert.csv");
            file.createNewFile();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        try{
            FileWriter writer = new FileWriter("./resource/output/concert.csv");
            writer.write("");
            for (Concert concert:concerts){
                writer.write(concert.getDescription() + ',' + concert.getAgeRestriction() + ',' + concert.getDuration()
                + ',' + concert.getLocation().getId() + ',' + concert.getDateTime() + ',');
                for(Double d: concert.getPricePerTicketType().values()){
                    writer.write(d.toString() + ',');
                }
                writer.write(concert.getOpener().getPerformerId() + ',' + concert.getMainAct().getPerformerId() + ',' +
                        concert.getPerformanceTimeOpener() + ',' + concert.getPerformanceTimeMainAct());
                writer.write('\n');
            }
            writer.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void writeMovieCSV(List<Movie> movies){
        try{
            File file = new File("./resource/output/movie.csv");
            file.createNewFile();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        try{
            FileWriter writer = new FileWriter("./resource/output/movie.csv");
            writer.write("");
            for (Movie movie : movies){
                writer.write(movie.getDescription() + ',' + movie.getAgeRestriction() + ',' + movie.getDuration()
                        + ',' + movie.getLocation().getId() + ',' + movie.getDateTime() + ',');
                for(Double d: movie.getPricePerTicketType().values()){
                    writer.write(d.toString() + ',');
                }
                writer.write(movie.getGenre() + ',' + movie.getName() + ',' + movie.getDirector() + ',');
                for(Performer p: movie.getCast().keySet()){
                    writer.write(p.getPerformerId() + '|');
                }
                writer.write(',');
                for(String c : movie.getCast().values()){
                    writer.write(c + '|');
                }
                writer.write('\n');
            }
            writer.write('\n');
            writer.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void writeStandUpShowCSV(List<StandUpShow> standUpShows){
        try{
            File file = new File("./resource/output/standupshow.csv");
            file.createNewFile();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        try{
            FileWriter writer = new FileWriter("./resource/output/standupshow.csv");
            writer.write("");
            for (StandUpShow standUpShow:standUpShows){
                writer.write(standUpShow.getDescription() + ',' + standUpShow.getAgeRestriction() + ',' + standUpShow.getDuration()
                        + ',' + standUpShow.getLocation().getId() + ','+  standUpShow.getDateTime() + ',');
                for(Double d: standUpShow.getPricePerTicketType().values()){
                    writer.write(d.toString() + ',');
                }
                for (Comedian comedian: standUpShow.getComedians()){
                    writer.write(comedian.getPerformerId() + '|');
                }
                writer.write(',');
                for(Integer h : standUpShow.getSchedule().values()){
                    writer.write(h.toString() + '|');
                }
                writer.write(',');
                for(String role : standUpShow.getRolesInShow().values()){
                    writer.write(role + '|');
                }
                writer.write('\n');
            }
            writer.write('\n');
            writer.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void writeTheatrePlayCSV(List<TheatrePlay> theatrePlays){
        try{
            File file = new File("./resource/output/theatreplay.csv");
            file.createNewFile();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        try{
            FileWriter writer = new FileWriter("./resource/output/theatreplay.csv");
            writer.write("");
            for (TheatrePlay theatrePlay:theatrePlays){
                writer.write(theatrePlay.getDescription() + ',' + theatrePlay.getAgeRestriction() + ',' + theatrePlay.getDuration()
                        + ',' + theatrePlay.getLocation().getId() + ',' +  theatrePlay.getDateTime() + ',');
                for(Double d: theatrePlay.getPricePerTicketType().values()){
                    writer.write(d.toString() + ',');
                }
               writer.write(theatrePlay.getGenre() + ',' + theatrePlay.getName() + ',' + theatrePlay.getDirectorName()
               + ',' + theatrePlay.getDressCode() + ',');
                for(Performer p: theatrePlay.getCast().keySet()){
                    writer.write(p.getPerformerId() + '|');
                }
                writer.write(',');
                for(String name : theatrePlay.getCast().values()){
                    writer.write(name + '|');
                }
                writer.write('\n');
            }
            writer.write('\n');
            writer.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
