package com.nevent.model.database.repository;


import com.nevent.model.database.config.DatabaseConfiguration;
import com.nevent.model.performer.Actor;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActorRepository {
    public Actor findById(String id){
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String find_actors = "SELECT * from actors where performerId = '" + id + "'";
            String find_awards = "SELECT * from awards where actorId = '" + id + "'";
            String find_past_productions = "SELECT * from past_productions where actorId = '" + id + "'";
            Statement statement = connection.createStatement();
            Statement statement1 = connection.createStatement();
            Statement statement2 = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(find_actors);
            ResultSet resultSet1 = statement1.executeQuery(find_awards);
            ResultSet resultSet2 = statement2.executeQuery(find_past_productions);

            List<String> awards = new ArrayList<>();
            while(resultSet1.next()) {
                awards.add(resultSet1.getString(2));
            }
            List<String> past_productions = new ArrayList<>();
            while(resultSet2.next()) {
                past_productions.add(resultSet2.getString(2));
            }
            Actor actor = null;
            if (resultSet.next()) {
            actor = mapToActor(resultSet, awards, past_productions);}
            resultSet.close();
            resultSet1.close();
            resultSet2.close();
            return actor;

        } catch (SQLException exception)
        {
            throw new RuntimeException("Something went wrong while trying to query actor with id = " + id);
        }
    }
    //create
    public Actor save(Actor actor) {
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "INSERT into actors(performerId, name, description) VALUES(?,?,?)";
            String query_awards = "INSERT INTO awards(award_name, actorId) VALUES (?, ?)";
            String query_past_productions = "INSERT INTO past_productions(past_production_name, actorId) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, actor.getPerformerId());
            preparedStatement.setString(2, actor.getName());
            preparedStatement.setString(3, actor.getDescription());
            preparedStatement.execute();
            List<String> awards = actor.getAwards();

            for (String award: awards) {
                PreparedStatement preparedStatement1 = connection.prepareStatement(query_awards);
                preparedStatement1.setString(1, award);
                preparedStatement1.setString(2, actor.getPerformerId());
                preparedStatement1.execute();
            }
            List<String> past_productions = actor.getPastProductions();

            for(String pp : past_productions) {
                PreparedStatement preparedStatement2 = connection.prepareStatement(query_past_productions);
                preparedStatement2.setString(1, pp);
                preparedStatement2.setString(2, actor.getPerformerId());
                preparedStatement2.execute();
            }
            return actor;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while saving the comedian: " + actor);
        }
    }
    //read
    public List<Actor> findAll() {
        List<Actor> actors = new ArrayList<>();
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()){
            String query = "SELECT * FROM actors";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()) {
                String award_query = "SELECT * FROM awards where actorId = '" + resultSet.getString(1) + "'";
                Statement statement1 = connection.createStatement();
                ResultSet resultSet1 = statement1.executeQuery(award_query);
                List<String> aw = new ArrayList<>();
                while (resultSet1.next())
                {
                    aw.add(resultSet1.getString(2));
                }
                String pp_query = "SELECT * FROM past_productions where actorId = '" + resultSet.getString(1) + "'";
                Statement statement2 = connection.createStatement();
                ResultSet resultSet2 = statement2.executeQuery(pp_query);
                List<String> pp = new ArrayList<>();
                while (resultSet2.next())
                {
                    pp.add(resultSet2.getString(2));
                }
                actors.add(mapToActor(resultSet, aw, pp));
                resultSet1.close();
                resultSet2.close();
            }
            resultSet.close();
            return actors;
        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while trying to get all the actors\n");
        }
    }
    private Actor mapToActor(ResultSet resultSet, List<String> awards, List<String> past_productions) throws SQLException {
        Actor actor =  new Actor(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), awards,
                past_productions
        );
        return actor;
    }
    //update
    public void update(String id, String name, String description) {
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String update_query = "UPDATE actors SET name = ?, description = ? where performerId = '" + id + "'";
            PreparedStatement preparedStatement = connection.prepareStatement(update_query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, description);
            preparedStatement.executeUpdate();
        } catch (SQLException exception)
        {
            throw new RuntimeException("Something went wrong while trying to update actor with id = " + id);
        }
    }
    //delete
    public void deleteById(String id){
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String delete_query = "DELETE FROM actors where performerId = '" + id + "'";
            String delete_awards = "DELETE FROM awards where actorId = '" + id + "'";
            String delete_past_productions = "DELETE FROM past_productions where actorId = '" + id + "'";
            String delete_movie_cast = "DELETE FROM movie_cast where performer_id = '" + id + "'";
            String delete_theatre_cast = "DELETE FROM theatre_cast where performer_id = '" + id + "'";
            Statement statement = connection.createStatement();
            Statement statement1 = connection.createStatement();
            Statement statement2 = connection.createStatement();
            Statement statement3 = connection.createStatement();
            Statement statement4 = connection.createStatement();
            statement4.executeUpdate(delete_theatre_cast);
            statement3.executeUpdate(delete_movie_cast);
            statement1.executeUpdate(delete_awards);
            statement2.executeUpdate(delete_past_productions);
            statement.executeUpdate(delete_query);

        } catch (SQLException exception)
        {
            exception.printStackTrace();
            throw new RuntimeException("Something went wrong while trying to delete actor with id = " + id);
        }
    }
}
