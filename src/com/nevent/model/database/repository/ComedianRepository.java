package com.nevent.model.database.repository;


import com.nevent.model.database.config.DatabaseConfiguration;
import com.nevent.model.performer.Comedian;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComedianRepository {
    public Comedian findById(String id){
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String find_comedians = "SELECT * from comedians where performerId = '" + id + "'";
            String find_podcasts = "SELECT * from podcasts where comedianId = '" + id + "'";
            Statement statement = connection.createStatement();
            Statement statement1 = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(find_comedians);
            ResultSet resultSet1 = statement1.executeQuery(find_podcasts);
            List<String> podcasts = new ArrayList<>();
            while(resultSet1.next()) {
                podcasts.add(resultSet1.getString(2));
            }
            Comedian comedian = null;
            if(resultSet.next()) {
                comedian = mapToComedian(resultSet, podcasts);
            }
            resultSet.close();
            resultSet1.close();
            return comedian;

        } catch (SQLException exception)
        {
            throw new RuntimeException("Something went wrong while trying to query comedian with id = " + id);
        }
    }
    //create
    public Comedian save(Comedian comedian) {
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "INSERT into comedians(performerId, name, description, genreOfComedy, positionInShow, tenure, timePerSet) VALUES(?,?,?,?,?,?,?)";
            String query_podcasts = "INSERT INTO podcasts(name, comedianId) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, comedian.getPerformerId());
            preparedStatement.setString(2, comedian.getName());
            preparedStatement.setString(3, comedian.getDescription());
            preparedStatement.setString(4, comedian.getGenreOfComedy());
            preparedStatement.setString(5, comedian.getPositionInShow());
            preparedStatement.setInt(6, comedian.getTenure());
            preparedStatement.setInt(7, comedian.getTimePerSet());
            preparedStatement.execute();
            List<String> podcasts = comedian.getPodcasts();

            for (String podcast : podcasts) {
                PreparedStatement preparedStatement1 = connection.prepareStatement(query_podcasts);
                preparedStatement1.setString(1, podcast);
                preparedStatement1.setString(2, comedian.getPerformerId());
                preparedStatement1.execute();
            }
            return comedian;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while saving the comedian: " + comedian);
        }
    }
    //read
    public List<Comedian> findAll() {
        List<Comedian> comedians = new ArrayList<>();
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()){
            String query = "SELECT * FROM comedians";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()) {
                String podcast_query = "SELECT * FROM podcasts where comedianId = " + resultSet.getString(1);
                Statement statement1 = connection.createStatement();
                ResultSet resultSet1 = statement1.executeQuery(podcast_query);
                List<String> pc = new ArrayList<>();
                while (resultSet1.next())
                {
                    pc.add(resultSet1.getString(2));
                }
                comedians.add(mapToComedian(resultSet, pc));
                resultSet1.close();
            }
            resultSet.close();
            return comedians;
        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while trying to get all the comedians\n");
        }
    }
    private Comedian mapToComedian(ResultSet resultSet, List<String> podcasts) throws SQLException {
        return new Comedian(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),
                resultSet.getString(5), resultSet.getInt(6), resultSet.getInt(7), podcasts
           );
    }
    //update
    public void update(int id, String name, String description, String positionInShow, Integer tenure, Integer timePerSet) {
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String update_query = "UPDATE comedians SET name = ?, description = ?, positionInShow = ?, tenure = ?, timePerSet = ? where performerId = " + id;
            PreparedStatement preparedStatement = connection.prepareStatement(update_query);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, description);
            preparedStatement.setString(4, positionInShow);
            preparedStatement.setInt(5, tenure);
            preparedStatement.setInt(6, timePerSet);
            preparedStatement.executeUpdate();
        } catch (SQLException exception)
        {
            throw new RuntimeException("Something went wrong while trying to update comedian with id = " + id);
        }
    }
    //delete
    public void deleteById(String id){
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String delete_query = "DELETE FROM comedians where performerId = " + id;
            String delete_podcasts = "DELETE FROM podcasts where  comedianId = " + id;
            Statement statement = connection.createStatement();
            Statement statement1 = connection.createStatement();
            statement.executeUpdate(delete_query);
            statement1.executeUpdate(delete_podcasts);
        } catch (SQLException exception)
        {
            throw new RuntimeException("Something went wrong while trying to delete comedian with id = " + id);
        }
    }
}
