package com.nevent.model.database.repository;

import com.nevent.model.database.config.DatabaseConfiguration;
import com.nevent.model.event.Movie;
import com.nevent.model.event.TheatrePlay;
import com.nevent.model.location.Location;
import com.nevent.model.performer.Performer;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TheatrePlayRepository {
    public TheatrePlay findById(String id){
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String find_theatre_entry = "SELECT * from theatre_plays where id = '" + id + "'";
            String find_event_entry = "SELECT * from events where id = '" + id + "'";
            String find_theatre_cast = "SELECT * from theatre_cast where theatre_id = '" + id+ "'";
            String find_pricing_chart = "SELECT * from pricing_chart where id = '" + id+ "'";
            String find_event_location = "SELECT * from event_locations where id = '" + id+ "'";
            Statement statement = connection.createStatement();
            Statement statement1 = connection.createStatement();
            Statement statement2 = connection.createStatement();
            ResultSet theatre_entries = statement.executeQuery(find_theatre_entry);
            ResultSet event_entries = statement1.executeQuery(find_event_entry);
            ResultSet theatre_cast = statement2.executeQuery(find_theatre_cast);
            ResultSet pricing_chart = statement.executeQuery(find_pricing_chart);
            ResultSet event_location = statement.executeQuery(find_event_location);
            Map<String, Double> prices = new HashMap<>();
            while(pricing_chart.next()) {
                prices.put(pricing_chart.getString(2), pricing_chart.getDouble(3));
            }
            ActorRepository actorRepository = new ActorRepository();
            Map<Performer, String> cast = new HashMap<>();
            while(theatre_cast.next()) {
                Performer performer = actorRepository.findById(theatre_cast.getString(2));
                String role = theatre_cast.getString(3);
                cast.put(performer, role);
            }
            event_location.next();
            LocationRepository locationRepository = new LocationRepository();
            Location location = locationRepository.findById(event_location.getString(2));
            event_entries.next();
            theatre_entries.next();
            TheatrePlay theatrePlay = new TheatrePlay(event_entries.getString(1), event_entries.getString(2), event_entries.getInt(3),
                    event_entries.getInt(4), location, event_entries.getDate(5), prices, theatre_entries.getString(2),
                    theatre_entries.getString(3), theatre_entries.getString(4), theatre_entries.getString(5), cast);
            theatre_entries.close();
            event_entries.close();
            theatre_cast.close();
            pricing_chart.close();
            event_location.close();
            return theatrePlay;
        } catch (SQLException exception)
        {
            throw new RuntimeException("Something went wrong while trying to query theatre play with id = " + id);
        }
    }

    public TheatrePlay save(TheatrePlay theatrePlay) {
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String insert_theatre_entry = "INSERT INTO theatre_plays(id, genre, name, director_name, dress_code) values (?, ?, ?, ?, ?)";
            String insert_event_entry = "INSERT INTO events(id, description, age_restriction, duration, date) values (?, ?, ?, ?, ?)";
            String insert_theatre_cast = "INSERT INTO theatre_cast(performer_id, role, theatre_id) values (?, ?, ?)";
            String insert_pricing_chart = "INSERT INTO pricing_chart(type, price, id_event) values(?, ?, ?)";
            String insert_event_location = "INSERT INTO event_locations(id, locationId) values(?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insert_event_entry);
            preparedStatement.setString(1, theatrePlay.getId());
            preparedStatement.setString(2, theatrePlay.getDescription());
            preparedStatement.setInt(3, theatrePlay.getAgeRestriction());
            preparedStatement.setInt(4, theatrePlay.getDuration());
            preparedStatement.setObject(5, theatrePlay.getDateTime());
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(insert_theatre_entry);
            preparedStatement.setString(1, theatrePlay.getId());
            preparedStatement.setString(2, theatrePlay.getGenre());
            preparedStatement.setString(3, theatrePlay.getName());
            preparedStatement.setString(4, theatrePlay.getDirectorName());
            preparedStatement.setString(5, theatrePlay.getDressCode());
            preparedStatement.execute();
            Map<Performer, String> cast = theatrePlay.getCast();
            for(Map.Entry<Performer, String> member : cast.entrySet()){
                preparedStatement = connection.prepareStatement(insert_theatre_cast);
                preparedStatement.setString(1, member.getKey().getPerformerId());
                preparedStatement.setString(2, member.getValue());
                preparedStatement.setString(3, theatrePlay.getId());
                preparedStatement.execute();
            }
            Map<String, Double> prices = theatrePlay.getPricePerTicketType();
            for(Map.Entry<String, Double> price : prices.entrySet()) {
                preparedStatement = connection.prepareStatement(insert_pricing_chart);
                preparedStatement.setString(1, price.getKey());
                preparedStatement.setDouble(2, price.getValue());
                preparedStatement.setString(3, theatrePlay.getId());
                preparedStatement.execute();
            }
            preparedStatement = connection.prepareStatement(insert_event_location);
            preparedStatement.setString(1, theatrePlay.getId());
            preparedStatement.setString(2, theatrePlay.getLocation().getId());
            preparedStatement.execute();
            return theatrePlay;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while saving the theatre play: " + theatrePlay);
        }
    }
    public List<TheatrePlay> findAll() {
        List<TheatrePlay> theatrePlays = new ArrayList<>();
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()){
            String find_plays = "SELECT * from theatre_plays";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(find_plays);
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

                String find_theatre_casts = "SELECT * from theatre_cast where theatre_id = " + resultSet.getString(1);
                Statement theatre_statement = connection.createStatement();
                ResultSet theatre_res = theatre_statement.executeQuery(find_theatre_casts);
                Map<Performer, String> cast = new HashMap<>();
                ActorRepository actorRepository = new ActorRepository();
                while(theatre_res.next()){
                    cast.put(actorRepository.findById(theatre_res.getString(2)), theatre_res.getString(3));
                }

                String find_events = "SELECT * from events where id = " + resultSet.getString(1);
                Statement event_statement = connection.createStatement();
                ResultSet event_res = event_statement.executeQuery(find_events);
                TheatrePlay theatrePlay = new TheatrePlay(resultSet.getString(1), event_res.getString(2), event_res.getInt(3),
                        event_res.getInt(4), location, event_res.getDate(5), prices, resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getString(5),  cast);
                theatrePlays.add(theatrePlay);
            }
            return theatrePlays;
        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while trying to get all the theatre plays\n");
        }
    }

    public void update(int id, String genre, String name, String director, String dress_code){
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String update_query = "UPDATE theatre_plays SET genre = ?, name = ?, director_name = ?, dress_code = ? where id = " + id;
            PreparedStatement preparedStatement = connection.prepareStatement(update_query);
            preparedStatement.setString(2, genre);
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, director);
            preparedStatement.setString(5, dress_code);
        } catch (SQLException exception)
        {
            throw new RuntimeException("Something went wrong while trying to update theatre play with id = " + id);
        }
    }

    public void deleteById(String id){
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String delete_theatre_entry = "DELETE from theatre_plays where id = " + id;
            String delete_event_entry = "DELETE from events where id = " + id;
            String delete_theatre_cast = "DELETE from theatre_cast where theatre_id = " + id;
            String delete_pricing_chart = "DELETE from pricing_chart where id_event = " + id;
            String delete_event_location = "DELETE from event_locations where id = " + id;
            Statement delete_theatre_statement = connection.createStatement();
            delete_theatre_statement.executeUpdate(delete_theatre_entry);
            delete_theatre_statement.executeUpdate(delete_event_entry);
            delete_theatre_statement.executeUpdate(delete_theatre_cast);
            delete_theatre_statement.executeUpdate(delete_pricing_chart);
            delete_theatre_statement.executeUpdate(delete_event_location);

        }catch (SQLException exception)
        {
            throw new RuntimeException("Something went wrong while trying to delete theatre play with id = " + id);
        }
    }
}
