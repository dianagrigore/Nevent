package com.nevent.model.database.repository;

import com.nevent.model.database.config.DatabaseConfiguration;
import com.nevent.model.event.Movie;
import com.nevent.model.event.StandUpShow;
import com.nevent.model.location.Location;
import com.nevent.model.performer.Comedian;
import com.nevent.model.performer.Performer;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class StandUpShowRepository {
    public StandUpShow findById(String id){
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String find_standup_entry = "SELECT * from stand_up_shows where id = '" + id + "'";
            String find_event_entry = "SELECT * from events where id = '" + id+ "'";
            String find_standup_cast = "SELECT * from standup_comedians where showId = '" + id+ "'";
            String find_pricing_chart = "SELECT * from pricing_chart where id = '" + id+ "'";
            String find_event_location = "SELECT * from event_locations where id = '" + id+ "'";
            Statement statement = connection.createStatement();
            Statement statement1 = connection.createStatement();
            Statement statement2 = connection.createStatement();
            Statement statement3= connection.createStatement();
            Statement statement4 = connection.createStatement();
            ResultSet standup_entries = statement.executeQuery(find_standup_entry);
            ResultSet event_entries = statement1.executeQuery(find_event_entry);
            ResultSet standup_cast = statement2.executeQuery(find_standup_cast);
            ResultSet pricing_chart = statement3.executeQuery(find_pricing_chart);
            ResultSet event_location = statement4.executeQuery(find_event_location);
            Map<String, Double> prices = new HashMap<>();
            while(pricing_chart.next()) {
                prices.put(pricing_chart.getString(2), pricing_chart.getDouble(3));
            }
            ComedianRepository comedianRepository = new ComedianRepository();
            int i = 0;
            Comedian c1 = null, c2 = null, c3 = null;
            Integer t1 = null, t2 = null, t3 = null;
            String r1 = null, r2 = null, r3 = null;
            while(standup_cast.next()) {
                Comedian comedian = comedianRepository.findById(standup_cast.getString(2));
                if(i == 0){
                    c1 = comedian;
                    t1 = standup_cast.getInt(3);
                    r1 = standup_cast.getString(4);
                } else if(i == 1){
                    c2 = comedian;
                    t2 = standup_cast.getInt(3);
                    r2 = standup_cast.getString(4);
                } else {
                    c3 = comedian;
                    t3 = standup_cast.getInt(3);
                    r3 = standup_cast.getString(4);
                }
                i++;
            }
            Set<Comedian> comedianSet = new HashSet<Comedian>();
            comedianSet.add(c1);
            comedianSet.add(c2);
            comedianSet.add(c3);

            HashMap<Comedian, Integer> schedule = new HashMap<>();
            schedule.put(c1, t1);
            schedule.put(c2, t2);
            schedule.put(c3, t3);

            HashMap<Comedian, String> roles = new HashMap<>();
            roles.put(c1, r1);
            roles.put(c2, r2);
            roles.put(c3, r3);

            LocationRepository locationRepository = new LocationRepository();
            event_location.next();
            Location location = locationRepository.findById(event_location.getString(2));
            event_entries.next();
            standup_entries.next();
            StandUpShow standUpShow = new StandUpShow(event_entries.getString(1), event_entries.getString(2), event_entries.getInt(3),
                    event_entries.getInt(4), location, event_entries.getDate(5), prices, comedianSet, schedule, roles);
            standup_entries.close();
            event_entries.close();
            standup_cast.close();
            pricing_chart.close();
            event_location.close();
            return standUpShow;
        } catch (SQLException exception)
        {
            throw new RuntimeException("Something went wrong while trying to query stand-up show with id = " + id);
        }
    }

    public StandUpShow save(StandUpShow standUpShow) {
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String insert_standup_entry = "INSERT INTO stand_up_shows(id) values (?)";
            String insert_event_entry = "INSERT INTO events(id, description, age_restriction, duration, date) values (?, ?, ?, ?, ?)";
            String insert_standup_cast = "INSERT INTO standup_comedians(comedianId, schedule_time, role_in_show, showId) values (?, ?, ?, ?)";
            String insert_pricing_chart = "INSERT INTO pricing_chart(type, price, id_event) values(?, ?, ?)";
            String insert_event_location = "INSERT INTO event_locations(id, locationId) values(?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insert_event_entry);
            preparedStatement.setString(1, standUpShow.getId());
            preparedStatement.setString(2, standUpShow.getDescription());
            preparedStatement.setInt(3, standUpShow.getAgeRestriction());
            preparedStatement.setInt(4, standUpShow.getDuration());
            preparedStatement.setObject(5, standUpShow.getDateTime());
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(insert_standup_entry);
            preparedStatement.setString(1, standUpShow.getId());
            preparedStatement.execute();

            Set<Comedian> comedians = standUpShow.getComedians();
            for(Comedian comedian : comedians){
                preparedStatement = connection.prepareStatement(insert_standup_cast);
                preparedStatement.setString(1, comedian.getPerformerId());
                preparedStatement.setInt(2, standUpShow.getSchedule().get(comedian));
                preparedStatement.setString(3, standUpShow.getRolesInShow().get(comedian));
                preparedStatement.setString(4, standUpShow.getId());
                preparedStatement.execute();
            }
            Map<String, Double> prices = standUpShow.getPricePerTicketType();
            for(Map.Entry<String, Double> price : prices.entrySet()) {
                preparedStatement = connection.prepareStatement(insert_pricing_chart);
                preparedStatement.setString(1, price.getKey());
                preparedStatement.setDouble(2, price.getValue());
                preparedStatement.setString(3, standUpShow.getId());
                preparedStatement.execute();
            }
            preparedStatement = connection.prepareStatement(insert_event_location);
            preparedStatement.setString(1, standUpShow.getId());
            preparedStatement.setString(2, standUpShow.getLocation().getId());
            preparedStatement.execute();
            return standUpShow;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while saving the stand-up show: " + standUpShow);
        }
    }
    public List<StandUpShow> findAll() {
        List<StandUpShow> standUpShows = new ArrayList<>();
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()){
            String find_standup_shows = "SELECT * from stand_up_shows";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(find_standup_shows);
            while(resultSet.next())
            {
                String find_event_locations = "SELECT * from event_locations where id = '" + resultSet.getString(1)+ "'";
                Statement location_statement = connection.createStatement();
                ResultSet location_res = location_statement.executeQuery(find_event_locations);
                LocationRepository locationRepository = new LocationRepository();
                Location location = locationRepository.findById(location_res.getString(2));

                String find_pricing_charts = "SELECT * from pricing_chart where id = '" + resultSet.getString(1)+ "'";
                Statement chart = connection.createStatement();
                ResultSet pricing_res = chart.executeQuery(find_pricing_charts);
                Map<String, Double> prices = new HashMap<>();
                while(pricing_res.next()){
                    prices.put(pricing_res.getString(2), pricing_res.getDouble(3));
                }

                String find_standup_comedians = "SELECT * from standup_comedians where showId = '" + resultSet.getString(1) + "'";
                Statement s_statement = connection.createStatement();
                ResultSet s_res = s_statement.executeQuery(find_standup_comedians);
                Set<Comedian> comedians = new HashSet<>();
                Map<Comedian, Integer> schedule = new HashMap<>();
                Map<Comedian, String> roles = new HashMap<>();
                ComedianRepository comedianRepository = new ComedianRepository();
                while(s_res.next()){
                    Comedian comedian = comedianRepository.findById(s_res.getString(2));
                    comedians.add(comedian);
                    schedule.put(comedian, s_res.getInt(3));
                    roles.put(comedian, s_res.getString(4));
                }
                resultSet.next();
                String find_events = "SELECT * from events where id = '" + resultSet.getString(1) + "'";
                Statement event_statement = connection.createStatement();
                ResultSet event_res = event_statement.executeQuery(find_events);
                event_res.next();
                StandUpShow standUpShow = new StandUpShow(resultSet.getString(1), event_res.getString(2), event_res.getInt(3),
                        event_res.getInt(4), location, event_res.getDate(5), prices, comedians,
                        schedule, roles);
                standUpShows.add(standUpShow);
            }
            return standUpShows;
        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while trying to get all the standup-shows\n");
        }
    }

    public void deleteById(String id){
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String delete_standup_entry = "DELETE from stand_up_shows where id = '" + id + "'";
            String delete_event_entry = "DELETE from events where id = '" + id + "'";
            String delete_standup_comedians = "DELETE from standup_comedians where showId = '" + id + "'";
            String delete_pricing_chart = "DELETE from pricing_chart where id_event = '" + id + "'";
            String delete_event_location = "DELETE from event_locations where id = '" + id + "'";
            Statement delete_movie_statement = connection.createStatement();
            delete_movie_statement.executeUpdate(delete_standup_entry);
            Statement delete_er_statement = connection.createStatement();
            delete_er_statement.executeUpdate(delete_event_entry);
            Statement delete_sc_statement = connection.createStatement();
            delete_sc_statement.executeUpdate(delete_standup_comedians);
            Statement delete_pc_statement = connection.createStatement();
            delete_pc_statement.executeUpdate(delete_pricing_chart);
            Statement delete_el_statement = connection.createStatement();
            delete_el_statement.executeUpdate(delete_event_location);

        }catch (SQLException exception)
        {
            throw new RuntimeException("Something went wrong while trying to delete standup show with id = " + id);
        }
    }
}
