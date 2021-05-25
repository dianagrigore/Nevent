package com.nevent.model.database.repository;

import com.nevent.model.database.config.DatabaseConfiguration;
import com.nevent.model.event.Concert;
import com.nevent.model.event.Movie;
import com.nevent.model.location.Location;
import com.nevent.model.performer.Performer;
import com.nevent.model.performer.Singer;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConcertRepository {
    public Concert findById(String id) {
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String find_concert_entry = "SELECT * from concerts where id = " + id;
            String find_event_entry = "SELECT * from events where id = " + id;
            String find_concert_performers = "SELECT * from concert_performers where concertId = " + id;
            String find_pricing_chart = "SELECT * from pricing_chart where id = " + id;
            String find_event_location = "SELECT * from event_locations where id = " + id;
            Statement statement = connection.createStatement();
            Statement statement1 = connection.createStatement();
            Statement statement2 = connection.createStatement();
            ResultSet concert_entries = statement.executeQuery(find_concert_entry);
            ResultSet event_entries = statement1.executeQuery(find_event_entry);
            ResultSet concert_performers = statement2.executeQuery(find_concert_performers);
            ResultSet pricing_chart = statement.executeQuery(find_pricing_chart);
            ResultSet event_location = statement.executeQuery(find_event_location);
            Map<String, Double> prices = new HashMap<>();
            while (pricing_chart.next()) {
                prices.put(pricing_chart.getString(2), pricing_chart.getDouble(3));
            }
            SingerRepository singerRepository = new SingerRepository();
            Singer opener = singerRepository.findById(concert_performers.getString(3));
            concert_performers.next();
            Singer mainAct = singerRepository.findById(concert_performers.getString(3));
            //todo: location
            LocationRepository locationRepository = new LocationRepository();
            Location location = locationRepository.findById(event_location.getString(2));
            Concert concert = new Concert(event_entries.getString(1), event_entries.getString(2),
                    event_entries.getInt(3), event_entries.getInt(4), location, event_entries.getDate(5),
                    prices, opener, mainAct, concert_entries.getInt(2), concert_entries.getInt(3));
            concert_entries.close();
            event_entries.close();
            concert_performers.close();
            pricing_chart.close();
            event_location.close();
            return concert;
        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while trying to query concert with id = " + id);
        }
    }
     public Concert save(Concert concert){
         try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
             String insert_concert_entry = "INSERT INTO concerts(id, performanceTimeOpener, performanceTimeMainAct) values (?, ?, ?)";
             String insert_event_entry = "INSERT INTO events(id, description, age_restriction, duration, date) values (?, ?, ?, ?, ?)";
             String insert_concert_performers = "INSERT INTO concert_performers(concertId, performerId, type) values (?, ?, ?)";
             String insert_pricing_chart = "INSERT INTO pricing_chart(type, price, id_event) values(?, ?, ?)";
             String insert_event_location = "INSERT INTO event_locations(id, locationId) values(?, ?)";

             PreparedStatement preparedStatement = connection.prepareStatement(insert_event_entry);
             preparedStatement.setString(1, concert.getId());
             preparedStatement.setString(2, concert.getDescription());
             preparedStatement.setInt(3, concert.getAgeRestriction());
             preparedStatement.setInt(4, concert.getDuration());
             preparedStatement.setObject(5, concert.getDateTime());
             preparedStatement.execute();

             preparedStatement = connection.prepareStatement(insert_concert_entry);
             preparedStatement.setString(1, concert.getId());
             preparedStatement.setInt(2, concert.getPerformanceTimeOpener());
             preparedStatement.setInt(3, concert.getPerformanceTimeMainAct());
             preparedStatement.execute();

             preparedStatement = connection.prepareStatement(insert_concert_performers);
             preparedStatement.setString(1, concert.getId());
             preparedStatement.setString(2, concert.getOpener().getPerformerId());
             preparedStatement.setString(3, "opener");
             preparedStatement.execute();

             preparedStatement = connection.prepareStatement(insert_concert_performers);
             preparedStatement.setString(1, concert.getId());
             preparedStatement.setString(2, concert.getMainAct().getPerformerId());
             preparedStatement.setString(3, "main");
             preparedStatement.execute();

             Map<String, Double> prices = concert.getPricePerTicketType();
             for(Map.Entry<String, Double> price : prices.entrySet()) {
                 preparedStatement = connection.prepareStatement(insert_pricing_chart);
                 preparedStatement.setString(1, price.getKey());
                 preparedStatement.setDouble(2, price.getValue());
                 preparedStatement.setString(3, concert.getId());
                 preparedStatement.execute();
             }
             preparedStatement = connection.prepareStatement(insert_event_location);
             preparedStatement.setString(1, concert.getId());
             preparedStatement.setString(2, concert.getLocation().getId());
             preparedStatement.execute();
             return concert;

         } catch (SQLException exception) {
             throw new RuntimeException("Something went wrong while saving the concert: " + concert);
         }
     }
    public List<Concert> findAll() {
        List<Concert> concerts = new ArrayList<>();
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()){
            String find_concerts = "SELECT * from concerts";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(find_concerts);
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

                String find_concert_performers = "SELECT * from concert_performers where concertId = " + resultSet.getString(1);
                Statement concert_statement = connection.createStatement();
                ResultSet concert_res = concert_statement.executeQuery(find_concert_performers);
                SingerRepository singerRepository = new SingerRepository();
                Singer opener = singerRepository.findById(concert_res.getString(3));
                concert_res.next();
                Singer main = singerRepository.findById(concert_res.getString(3));


                String find_events = "SELECT * from events where id = " + resultSet.getString(1);
                Statement event_statement = connection.createStatement();
                ResultSet event_res = event_statement.executeQuery(find_events);
                Concert concert = new Concert(event_res.getString(1), event_res.getString(2),
                        event_res.getInt(3), event_res.getInt(4), location, event_res.getDate(5),
                        prices, opener, main, concert_res.getInt(2), concert_res.getInt(3));
                concerts.add(concert);
            }
            return concerts;
        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while trying to get all the concerts\n");
        }
    }

    public void update(int id, Integer performanceTimeOpener, Integer performanceTimeMainAct){
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String update_query = "UPDATE concerts SET performanceTimeOpener = ?, performanceTimeMainAct = ? where id = " + id;
            PreparedStatement preparedStatement = connection.prepareStatement(update_query);
            preparedStatement.setInt(1, performanceTimeOpener);
            preparedStatement.setInt(2, performanceTimeMainAct);
        } catch (SQLException exception)
        {
            throw new RuntimeException("Something went wrong while trying to update concert with id = " + id);
        }
    }

    public void deleteById(String id){
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String delete_concert_entry = "DELETE from concerts where id = " + id;
            String delete_event_entry = "DELETE from events where id = " + id;
            String delete_concert_performers = "DELETE from concert_performers where concertId = " + id;
            String delete_pricing_chart = "DELETE from pricing_chart where id_event = " + id;
            String delete_event_location = "DELETE from event_locations where id = " + id;
            Statement delete_movie_statement = connection.createStatement();
            delete_movie_statement.executeUpdate(delete_concert_entry);
            delete_movie_statement.executeUpdate(delete_event_entry);
            delete_movie_statement.executeUpdate(delete_concert_performers);
            delete_movie_statement.executeUpdate(delete_pricing_chart);
            delete_movie_statement.executeUpdate(delete_event_location);

        }catch (SQLException exception)
        {
            throw new RuntimeException("Something went wrong while trying to delete concert with id = " + id);
        }
    }

}
