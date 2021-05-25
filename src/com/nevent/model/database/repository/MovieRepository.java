package com.nevent.model.database.repository;

import com.nevent.model.database.config.DatabaseConfiguration;
import com.nevent.model.event.Movie;
import com.nevent.model.location.Location;
import com.nevent.model.performer.Performer;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieRepository {
    public Movie findById(String id){
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String find_movie_entry = "SELECT * from movies where id = " + id;
            String find_event_entry = "SELECT * from events where id = " + id;
            String find_movie_cast = "SELECT * from movie_cast where movie_id = " + id;
            String find_pricing_chart = "SELECT * from pricing_chart where id = " + id;
            String find_event_location = "SELECT * from event_locations where id = " + id;
            Statement statement = connection.createStatement();
            Statement statement1 = connection.createStatement();
            Statement statement2 = connection.createStatement();
            ResultSet movie_entries = statement.executeQuery(find_movie_entry);
            ResultSet event_entries = statement1.executeQuery(find_event_entry);
            ResultSet movie_cast = statement2.executeQuery(find_movie_cast);
            ResultSet pricing_chart = statement.executeQuery(find_pricing_chart);
            ResultSet event_location = statement.executeQuery(find_event_location);
            Map<String, Double> prices = new HashMap<>();
            while(pricing_chart.next()) {
                prices.put(pricing_chart.getString(2), pricing_chart.getDouble(3));
            }
            ActorRepository actorRepository = new ActorRepository();
            Map<Performer, String> cast = new HashMap<>();
            while(movie_cast.next()) {
                Performer performer = actorRepository.findById(movie_cast.getString(2));
                String role = movie_cast.getString(3);
                cast.put(performer, role);
            }
            //todo: location
            LocationRepository locationRepository = new LocationRepository();
            Location location = locationRepository.findById(event_location.getString(2));
            Movie movie = new Movie(event_entries.getString(1), event_entries.getString(2), event_entries.getInt(3),
                    event_entries.getInt(4), location, event_entries.getDate(5), prices, movie_entries.getString(2),
                    movie_entries.getString(3), movie_entries.getString(4), cast);
            movie_entries.close();
            event_entries.close();
            movie_cast.close();
            pricing_chart.close();
            event_location.close();
            return movie;
        } catch (SQLException exception)
        {
            throw new RuntimeException("Something went wrong while trying to query movie with id = " + id);
        }
        }

    public Movie save(Movie movie) {
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String insert_movie_entry = "INSERT INTO movies(id, genre, NAME, DIRECTOR) values (?, ?, ?, ?)";
            String insert_event_entry = "INSERT INTO events(id, description, age_restriction, duration, date) values (?, ?, ?, ?, ?)";
            String insert_movie_cast = "INSERT INTO movie_cast(performer_id, role, movie_id) values (?, ?, ?)";
            String insert_pricing_chart = "INSERT INTO pricing_chart(type, price, id_event) values(?, ?, ?)";
            String insert_event_location = "INSERT INTO event_locations(id, locationId) values(?, ?)";

            PreparedStatement preparedStatement =  connection.prepareStatement(insert_event_entry);
            preparedStatement.setString(1, movie.getId());
            preparedStatement.setString(2, movie.getDescription());
            preparedStatement.setInt(3, movie.getAgeRestriction());
            preparedStatement.setInt(4, movie.getDuration());
            preparedStatement.setObject(5, movie.getDateTime());
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(insert_movie_entry);
            preparedStatement.setString(1, movie.getId());
            preparedStatement.setString(2, movie.getGenre());
            preparedStatement.setString(3, movie.getName());
            preparedStatement.setString(4, movie.getDirector());
            preparedStatement.execute();

            Map<Performer, String> cast = movie.getCast();
            for(Map.Entry<Performer, String> member : cast.entrySet()){
                preparedStatement = connection.prepareStatement(insert_movie_cast);
                preparedStatement.setString(1, member.getKey().getPerformerId());
                preparedStatement.setString(2, member.getValue());
                preparedStatement.setString(3, movie.getId());
                preparedStatement.execute();
            }
            Map<String, Double> prices = movie.getPricePerTicketType();
            for(Map.Entry<String, Double> price : prices.entrySet()) {
                preparedStatement = connection.prepareStatement(insert_pricing_chart);
                preparedStatement.setString(1, price.getKey());
                preparedStatement.setDouble(2, price.getValue());
                preparedStatement.setString(3, movie.getId());
                preparedStatement.execute();
            }
            preparedStatement = connection.prepareStatement(insert_event_location);
            preparedStatement.setString(1, movie.getId());
            preparedStatement.setString(2, movie.getLocation().getId());
            preparedStatement.execute();
            return movie;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while saving the movie: " + movie);
        }
        }
        public List<Movie> findAll() {
        List<Movie> movies = new ArrayList<>();
            try (Connection connection = DatabaseConfiguration.getDatabaseConnection()){
                String find_movies = "SELECT * from movies";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(find_movies);
                while(resultSet.next())
                 {
                    String find_event_locations = "SELECT * from event_locations where id = " + resultSet.getString(1);
                    Statement location_statement = connection.createStatement();
                    ResultSet location_res = location_statement.executeQuery(find_event_locations);
                    LocationRepository locationRepository = new LocationRepository();
                    Location location = locationRepository.findById(location_res.getString(2));

                     String find_pricing_charts = "SELECT * from pricing_chart where id = " + resultSet.getString(1);
                     Statement chart = connection.createStatement();
                     ResultSet pricing_res = chart.executeQuery(find_pricing_charts);
                     Map<String, Double> prices = new HashMap<>();
                     while(pricing_res.next()){
                         prices.put(pricing_res.getString(2), pricing_res.getDouble(3));
                     }

                     String find_movie_casts = "SELECT * from movie_cast where movie_id = " + resultSet.getString(1);
                     Statement movie_statement = connection.createStatement();
                     ResultSet movie_res = movie_statement.executeQuery(find_movie_casts);
                     Map<Performer, String> cast = new HashMap<>();
                     ActorRepository actorRepository = new ActorRepository();
                     while(movie_res.next()){
                         cast.put(actorRepository.findById(movie_res.getString(2)), movie_res.getString(3));
                     }

                     String find_events = "SELECT * from events where id = " + resultSet.getString(1);
                     Statement event_statement = connection.createStatement();
                     ResultSet event_res = event_statement.executeQuery(find_events);
                     Movie movie = new Movie(resultSet.getString(1), event_res.getString(2), event_res.getInt(3),
                             event_res.getInt(4), location, event_res.getDate(5), prices, resultSet.getString(2),
                             resultSet.getString(3), resultSet.getString(4), cast);
                     movies.add(movie);
                 }
                return movies;
               } catch (SQLException exception) {
        throw new RuntimeException("Something went wrong while trying to get all the movies\n");
    }
        }

        public void update(int id, String genre, String name, String director){
            try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
                String update_query = "UPDATE movies SET genre = ?, name = ?, director = ? where id = " + id;
                PreparedStatement preparedStatement = connection.prepareStatement(update_query);
                preparedStatement.setString(2, genre);
                preparedStatement.setString(3, name);
                preparedStatement.setString(4, director);
            } catch (SQLException exception)
            {
                throw new RuntimeException("Something went wrong while trying to update movie with id = " + id);
            }
            }

       public void deleteById(String id){
           try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
               String delete_movie_entry = "DELETE from movies where id = " + id;
               String delete_event_entry = "DELETE from events where id = " + id;
               String delete_movie_cast = "DELETE from movie_cast where movie_id = " + id;
               String delete_pricing_chart = "DELETE from pricing_chart where id_event = " + id;
               String delete_event_location = "DELETE from event_locations where id = " + id;
               Statement delete_movie_statement = connection.createStatement();
               delete_movie_statement.executeUpdate(delete_movie_entry);
               delete_movie_statement.executeUpdate(delete_event_entry);
               delete_movie_statement.executeUpdate(delete_movie_cast);
               delete_movie_statement.executeUpdate(delete_pricing_chart);
               delete_movie_statement.executeUpdate(delete_event_location);

           }catch (SQLException exception)
           {
               throw new RuntimeException("Something went wrong while trying to delete movie with id = " + id);
           }
        }
}
