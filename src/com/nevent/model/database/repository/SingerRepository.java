package com.nevent.model.database.repository;


import com.nevent.model.database.config.DatabaseConfiguration;
import com.nevent.model.performer.Singer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SingerRepository {
    public Singer findById(String id){
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String find_singers = "SELECT * from singers where performerId = '" + id+ "'";
            String find_member_names = "SELECT * from member_names where singerId = '" + id+ "'";
            String find_best_known_songs = "SELECT * from best_known_songs where singerId = '" + id+ "'";
            Statement statement = connection.createStatement();
            Statement statement1 = connection.createStatement();
            Statement statement2 = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(find_singers);
            ResultSet resultSet1 = statement1.executeQuery(find_member_names);
            ResultSet resultSet2 = statement2.executeQuery(find_best_known_songs);
            List<String> member_names = new ArrayList<>();
            while(resultSet1.next()) {
                member_names.add(resultSet1.getString(2));
            }
            List<String> best_known_songs = new ArrayList<>();
            while(resultSet2.next()) {
                best_known_songs.add(resultSet2.getString(2));
            }
            resultSet.next();
            Singer singer = mapToSinger(resultSet, member_names, best_known_songs);
            resultSet.close();
            resultSet1.close();
            resultSet2.close();
            return singer;

        } catch (SQLException exception)
        {
            throw new RuntimeException("Something went wrong while trying to query singer with id = " + id);
        }
    }
    //create
    public Singer save(Singer singer) {
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "INSERT into singers(performerId, name, description, music_genre, is_group) VALUES(?,?,?,?,?)";


            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, singer.getPerformerId());
            preparedStatement.setString(2, singer.getName());
            preparedStatement.setString(3, singer.getDescription());
            preparedStatement.setString(4, singer.getMusicGenre());
            preparedStatement.setBoolean(5, singer.getGroup());
            preparedStatement.execute();
            List<String> member_names = singer.getMemberNames();

            for (String member_name:member_names) {
                String query_member_names = "INSERT INTO member_names(member_name, singerId) VALUES (?, ?)";
                PreparedStatement preparedStatement1 = connection.prepareStatement(query_member_names);
                preparedStatement1.setString(1, member_name);
                preparedStatement1.setString(2, singer.getPerformerId());
                preparedStatement1.execute();
            }
            List<String> best_known_songs = singer.getBestKnownSongs();

            for(String bks: best_known_songs) {
                String query_best_known_songs = "INSERT INTO best_known_songs(song_name, singerId) VALUES (?, ?)";
                PreparedStatement preparedStatement1 = connection.prepareStatement(query_best_known_songs);
                preparedStatement1.setString(1, bks);
                preparedStatement1.setString(2, singer.getPerformerId());
                preparedStatement1.execute();
            }
            return singer;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while saving the singer: " + singer);
        }
    }
    //read
    public List<Singer> findAll() {
        List<Singer> singers = new ArrayList<>();
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()){
            String query = "SELECT * FROM singers";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()) {
                String member_names_query = "SELECT * FROM member_names where singerId = " + resultSet.getString(1);
                Statement statement1 = connection.createStatement();
                ResultSet resultSet1 = statement1.executeQuery(member_names_query);
                List<String> mn= new ArrayList<>();
                while (resultSet1.next())
                {
                    mn.add(resultSet1.getString(2));
                }
                String bks_query = "SELECT * FROM best_known_songs where singerId = " + resultSet.getString(1);
                Statement statement2 = connection.createStatement();
                ResultSet resultSet2 = statement2.executeQuery(bks_query);
                List<String> bks = new ArrayList<>();
                while (resultSet2.next())
                {
                    bks.add(resultSet2.getString(2));
                }
                singers.add(mapToSinger(resultSet, mn, bks));
                resultSet1.close();
                resultSet2.close();
            }
            resultSet.close();
            return singers;
        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while trying to get all the singers\n");
        }
    }
    private Singer mapToSinger(ResultSet resultSet, List<String> member_names, List<String> best_known_songs) throws SQLException {
        return new Singer(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                resultSet.getString(4), resultSet.getBoolean(5), member_names,
                best_known_songs);
    }
    //update
    public void update(int id, String name, String description, String music_genre, Boolean is_group) {
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String update_query = "UPDATE singers SET name = ?, description = ?, music_genre=?, is_group = ? where performerId = " + id;
            PreparedStatement preparedStatement = connection.prepareStatement(update_query);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, description);
            preparedStatement.setString(4, music_genre);
            preparedStatement.setBoolean(5, is_group);
            preparedStatement.executeUpdate();
        } catch (SQLException exception)
        {
            throw new RuntimeException("Something went wrong while trying to update singer with id = " + id);
        }
    }
    //delete
    public void deleteById(String id){
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String delete_query = "DELETE FROM singers where performerId = " + id;
            String delete_mn = "DELETE FROM member_names where singerId = " + id;
            String delete_bks = "DELETE FROM best_known_songs where singerId = " + id;
            Statement statement = connection.createStatement();
            Statement statement1 = connection.createStatement();
            Statement statement2 = connection.createStatement();
            statement.executeUpdate(delete_query);
            statement1.executeUpdate(delete_mn);
            statement2.executeUpdate(delete_bks);
        } catch (SQLException exception)
        {
            throw new RuntimeException("Something went wrong while trying to delete singer with id = " + id);
        }
    }
}
