package com.nevent.model.database.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SetupData {
    public void setup(){
        createTable();
        //createFunction();
    }
    private void createTable() {
        String client = "CREATE TABLE IF NOT EXISTS clients (\n" +
                "    clientId VARCHAR(255) PRIMARY KEY,\n" +
                "    name VARCHAR(255),\n" +
                "    surname VARCHAR(255),\n" +
                "    age INT" +
                ")";

        String account = "CREATE TABLE IF NOT EXISTS accounts (\n" +
                "   clientId VARCHAR(255) primary key,\n"+
                "   leftBalance DOUBLE,\n " +
                "   FOREIGN KEY (clientId) REFERENCES clients(clientId) ON DELETE CASCADE" +
                 ")";
        String comedian = "CREATE TABLE IF NOT EXISTS comedians(\n" +
                "  performerId VARCHAR(255) primary key,\n" +
                "  name VARCHAR(255), \n" +
                "  description VARCHAR(255), \n" +
                "  genreOfComedy VARCHAR(255), \n" +
                "  positionInShow VARCHAR(255), \n" +
                "  tenure INT, \n" +
                "  timePerSet INT" +
                ")";
        String podcasts = "CREATE TABLE IF NOT EXISTS podcasts (\n" +
                "id INT primary key AUTO_INCREMENT," +
                "name VARCHAR(255), \n" +
                "comedianId VARCHAR(255), \n" +
                "FOREIGN KEY (comedianId) REFERENCES comedians(performerId) ON DELETE CASCADE" +
                ")";
        String actor = "CREATE TABLE IF NOT EXISTS actors(\n" +
                "  performerId VARCHAR(255) primary key,\n" +
                "  name VARCHAR(255), \n" +
                "  description VARCHAR(255)" +
                ")";
        String awards = "CREATE TABLE IF NOT EXISTS awards(\n" +
                " id INT primary key AUTO_INCREMENT, \n" +
                " award_name VARCHAR(255), \n" +
                " actorId VARCHAR(255),\n" +
                " FOREIGN KEY (actorId) REFERENCES actors(performerId) ON DELETE CASCADE)";
        String past_productions = "CREATE TABLE IF NOT EXISTS past_productions(\n" +
                " id INT primary key AUTO_INCREMENT, \n" +
                " past_production_name VARCHAR(255), \n" +
                " actorId VARCHAR(255),\n" +
                " FOREIGN KEY (actorId) REFERENCES actors(performerId) ON DELETE CASCADE)";
        String singer = "CREATE TABLE IF NOT EXISTS singers(\n" +
                "  performerId VARCHAR(255) primary key,\n" +
                "  name VARCHAR(255), \n" +
                "  description VARCHAR(255), \n" +
                "  music_genre VARCHAR(255), \n" +
                "  is_group BOOLEAN" +
                ")";
        String member_names = "CREATE TABLE IF NOT EXISTS member_names(\n" +
                " id INT primary key AUTO_INCREMENT, \n" +
                " member_name VARCHAR(255), \n" +
                " singerId VARCHAR(255),\n" +
                " FOREIGN KEY (singerId) REFERENCES singers(performerId) ON DELETE CASCADE)";
        String best_known_songs = "CREATE TABLE IF NOT EXISTS best_known_songs(\n" +
                " id INT primary key AUTO_INCREMENT, \n" +
                " song_name VARCHAR(255), \n" +
                " singerId VARCHAR(255),\n" +
                " FOREIGN KEY (singerId) REFERENCES singers(performerId) ON DELETE CASCADE)";
        String locations = "CREATE TABLE IF NOT EXISTS locations(\n" +
                "id VARCHAR(255) primary key, \n" +
                "nameOfVenue VARCHAR(255), \n" +
                "address VARCHAR(255), \n" +
                "city VARCHAR(255))";
        String seating_chart = "CREATE TABLE IF NOT EXISTS seating(\n" +
                "id INT primary key AUTO_INCREMENT, \n" +
                "type VARCHAR(255), \n" +
                "number_of_tickets INT," +
                "locationId VARCHAR(255), \n" +
                "FOREIGN KEY (locationId) REFERENCES locations(id) ON DELETE CASCADE)";
        String event = "CREATE TABLE IF NOT EXISTS events(\n" +
                " id VARCHAR(255) primary key, \n" +
                " description VARCHAR(255), \n" +
                " age_restriction INT, \n" +
                " duration INT, \n" +
                " date DATETIME) \n";
        String event_locations = "CREATE TABLE IF NOT EXISTS event_locations(\n" +
                "id VARCHAR(255) primary key, \n" +
                "locationId VARCHAR(255)," +
                "FOREIGN KEY (locationId) REFERENCES locations(id) ON DELETE CASCADE, \n" +
                "FOREIGN KEY (id) REFERENCES events(id) ON DELETE CASCADE)";
        String pricing_chart = "CREATE TABLE IF NOT EXISTS pricing_chart(\n" +
                "id INT primary key AUTO_INCREMENT, \n" +
                "type VARCHAR(255), \n" +
                "price DOUBLE, \n" +
                "id_event VARCHAR(255), \n" +
                "FOREIGN KEY (id_event) REFERENCES events(id) ON DELETE CASCADE)";

        String cast = "CREATE TABLE IF NOT EXISTS movie_cast(\n" +
                "id INTEGER primary key AUTO_INCREMENT, \n" +
                "performer_id VARCHAR(255),\n" +
                "role VARCHAR(255), \n" +
                "movie_id VARCHAR(255), \n" +
                "FOREIGN KEY (movie_id) REFERENCES movies(id) ON DELETE CASCADE," +
                "FOREIGN KEY (performer_id) REFERENCES actors(performerId) ON DELETE CASCADE)";

        String movie = "CREATE TABLE IF NOT EXISTS movies(\n" +
                " id VARCHAR(255) primary key, \n" +
                " genre VARCHAR(255),\n" +
                " NAME VARCHAR(255), \n" +
                " DIRECTOR VARCHAR(255), \n" +
                "FOREIGN KEY (id) REFERENCES events(id) ON DELETE CASCADE)\n";
        String stand_up_show = "CREATE TABLE IF NOT EXISTS stand_up_shows(\n" +
                " id VARCHAR(255) primary key, \n" +
                "FOREIGN KEY (id) REFERENCES events(id) ON DELETE CASCADE)\n";
        String standup_comedians = "CREATE TABLE IF NOT EXISTS standup_comedians(\n" +
                " ID INT AUTO_INCREMENT PRIMARY KEY,\n" +
                " comedianId VARCHAR(255), \n" +
                " schedule_time INTEGER, \n" +
                " role_in_show VARCHAR(255), \n" +
                " showId VARCHAR(255), \n" +
                " FOREIGN KEY (comedianId) references comedians(performerId) ON DELETE CASCADE, \n" +
                " FOREIGN KEY (showId) REFERENCES stand_up_shows(id) ON DELETE CASCADE)";
        String theatrePlay = "CREATE TABLE IF NOT EXISTS theatre_plays(\n" +
                "id VARCHAR(255) primary key, \n" +
                "genre VARCHAR(255), \n" +
                "name VARCHAR(255), \n" +
                "director_name VARCHAR(255), \n" +
                "dress_code VARCHAR(255), \n" +
                "FOREIGN KEY (id) references events(id) ON DELETE CASCADE)";
        String theatre_cast = "CREATE TABLE IF NOT EXISTS theatre_cast(\n" +
                "id INTEGER primary key AUTO_INCREMENT, \n" +
                "performer_id VARCHAR(255),\n" +
                "role VARCHAR(255), \n" +
                "theatre_id VARCHAR(255), \n" +
                "FOREIGN KEY (theatre_id) REFERENCES theatre_plays(id) ON DELETE CASCADE,\n" +
                "FOREIGN KEY (performer_id) REFERENCES actors(performerId) ON DELETE CASCADE)";
        String concert = "CREATE TABLE IF NOT EXISTS concerts(\n" +
                "id VARCHAR(255) primary key, \n" +
                "performanceTimeOpener INT, \n" +
                "performanceTimeMainAct INT, \n" +
                "FOREIGN KEY (id) references events(id) ON DELETE CASCADE)";
        String concert_performers = "CREATE TABLE IF NOT EXISTS concert_performers(\n" +
                "id INTEGER primary key AUTO_INCREMENT, \n" +
                "concertId VARCHAR(255), \n" +
                "performerId VARCHAR(255), \n" +
                "type VARCHAR(255), \n" +
                "FOREIGN KEY (concertId) REFERENCES concerts(id) ON DELETE CASCADE, \n" +
                "FOREIGN KEY (performerId) REFERENCES singers(performerId) ON DELETE CASCADE)";

        String audit = "CREATE TABLE IF NOT EXISTS audit(\n" +
                "id INTEGER primary key AUTO_INCREMENT, \n" +
                "nume_actiune VARCHAR(255), \n" +
                "timestamp DATETIME)";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            Statement statement = connection.createStatement();
            String[] queries = {client, account, comedian, podcasts, actor, awards, past_productions,
            singer, member_names, best_known_songs, locations, event,  seating_chart, event_locations,
            pricing_chart, movie,  cast, stand_up_show, standup_comedians, theatrePlay, theatre_cast,
            concert, concert_performers, audit};
            for (String query : queries)
                statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //TODO: fix this to fit client
    //TODO: drop function if exists
    private void createFunction() {
        /*String query = "CREATE FUNCTION nevent.get_age(required_id int) RETURNS int(11)\n" +
                "BEGIN\n" +
                "update nevent.clients\n" +
                "set is_borrowed = 1\n" +
                "where id = required_id;\n" +
                "RETURN row_count();\n" +
                "END";*/
        String query = "";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
