package com.nevent.model.database.repository;


import com.nevent.model.database.config.DatabaseConfiguration;
import com.nevent.model.location.Location;
import com.nevent.model.location.Seating;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationRepository {
    public Location findById(String id){
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String find = "SELECT * from locations where id = '" + id + "'";
            String find1 = "SELECT * from seating where locationId = '" + id+ "'";
            Statement statement = connection.createStatement();
            Statement statement1 = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(find);
            ResultSet resultSet1 = statement1.executeQuery(find1);
            Map<String, Integer> ticketsOfType = new HashMap<>();
            int count = 0;
            while(resultSet1.next())
            {
                ticketsOfType.put(resultSet1.getString(2), resultSet1.getInt(3));
                count += resultSet1.getInt(3);
            }
            Seating s = new Seating(count, ticketsOfType);
            Location location = null;
            if(resultSet.next()) {
                location = mapToLocation(resultSet, s);
            }
            resultSet.close();
            resultSet1.close();
            return location;

        } catch (SQLException exception)
        {
            throw new RuntimeException("Something went wrong while trying to query location with id = " + id);
        }
    }
    //create
    public Location save(Location location) {
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "INSERT into locations(id, nameOfVenue, address, city) VALUES(?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, location.getId());
            preparedStatement.setString(2, location.getNameOfVenue());
            preparedStatement.setString(3, location.getAddress());
            preparedStatement.setString(4, location.getCity());
            preparedStatement.execute();

            Seating seating = location.getSeating();
            Map<String, Integer> ticketspertype  = seating.getTicketsOfEachType();

                for (Map.Entry<String, Integer> kv : ticketspertype.entrySet()) {
                    String query_seating = "INSERT INTO seating(type, number_of_tickets, locationId) VALUES (?, ?, ?)";
                    PreparedStatement preparedStatement1 = connection.prepareStatement(query_seating);
                    preparedStatement1.setString(1, kv.getKey());
                    preparedStatement1.setInt(2, kv.getValue());
                    preparedStatement1.setString(3, location.getId());
                    preparedStatement1.execute();
                }

            return location;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while saving the location: " + location);
        }
    }
    //read
    public List<Location> findAll() {
        List<Location> locations = new ArrayList<>();
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()){
            String query = "SELECT * FROM locations";
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()) {
                String seating_query = "SELECT * FROM seating where locationId = '" + resultSet.getString(1) + "'";
                Statement statement1 = connection.createStatement();
                ResultSet resultSet1 = statement1.executeQuery(seating_query);
                Map<String, Integer> tickets = new HashMap<>();
                int count = 0;
                while(resultSet1.next()) {
                    String k = resultSet1.getString(2);
                    int val = resultSet1.getInt(3);
                    count += val;
                    tickets.put(k, val);
                }
                Seating s = new Seating(count, tickets);
                locations.add(mapToLocation(resultSet, s));
                resultSet1.close();
            }
            resultSet.close();

            return locations;
        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while trying to get all the locations\n");
        }
    }
    private Location mapToLocation(ResultSet resultSet, Seating seating) throws SQLException {
        return new Location(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),
                seating);
    }
    //update
    public void update(String id, String nameOfVenue, String address, String city) {
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String update_query = "UPDATE locations SET nameOfVenue = ?, address = ?, city = ? where id = '" + id + "'";
            PreparedStatement preparedStatement = connection.prepareStatement(update_query);
            preparedStatement.setString(1, nameOfVenue);
            preparedStatement.setString(2, address);
            preparedStatement.setString(3, city);
            preparedStatement.executeUpdate();
        } catch (SQLException exception)
        {
            exception.printStackTrace();
            throw new RuntimeException("Something went wrong while trying to update location with id = " + id);
        }
    }
    //delete
    public void deleteById(String id){
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String delete_query = "DELETE FROM locations where id = '" + id + "'";
            String delete_seating = "DELETE FROM seating where locationId = '" + id + "'";
            Statement statement = connection.createStatement();
            Statement statement1 = connection.createStatement();
            statement.executeUpdate(delete_query);
            statement1.executeUpdate(delete_seating);
        } catch (SQLException exception)
        {
            throw new RuntimeException("Something went wrong while trying to delete location with id = " + id);
        }
    }
}
