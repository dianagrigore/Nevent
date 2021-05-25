package com.nevent.model.database.repository;

import com.nevent.model.client.Client;
import com.nevent.model.client.payment.Account;
import com.nevent.model.database.config.DatabaseConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//TODO: add account support
public class ClientRepository {
    public Client findById(String id){
   try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
       String find = "SELECT * from clients where clientId = '" + id + "'";
       String find1 = "SELECT * from accounts where clientId = '" + id + "'";
       Statement statement = connection.createStatement();
       Statement statement1 = connection.createStatement();
       ResultSet resultSet = statement.executeQuery(find);
       ResultSet resultSet1 = statement1.executeQuery(find1);
       Client client = null;
       if (resultSet.next() && resultSet1.next()) {
       client = mapToClient(resultSet, resultSet1);}
       resultSet.close();
       resultSet1.close();
       return client;

    } catch (SQLException exception)
    {
        throw new RuntimeException("Something went wrong while trying to query client with id = " + id);
    }
    }
    //create
    //TODO: ADD voucher support
    public Client save(Client client) {
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "INSERT into clients(clientId, name, surname, age) VALUES(?,?,?,?)";
            String query_account = "INSERT INTO accounts(clientId, leftBalance) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, client.getClientId());
            preparedStatement.setString(2, client.getName());
            preparedStatement.setString(3, client.getSurname());
            preparedStatement.setInt(4, client.getAge());
            preparedStatement.execute();
            PreparedStatement preparedStatement1 = connection.prepareStatement(query_account);
            preparedStatement1.setString(1, client.getClientId());
            preparedStatement1.setDouble(2, client.getPaymentMethod().getLeftBalance());
            preparedStatement1.execute();
            return client;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while saving the client: " + client);
        }
    }
    //read
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()){
            String query = "SELECT * FROM clients";
            String account_query = "SELECT * FROM accounts";
            Statement statement = connection.createStatement();
            Statement statement1 = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ResultSet resultSet1 = statement1.executeQuery(account_query);
            while(resultSet.next() && resultSet1.next()) {
                clients.add(mapToClient(resultSet, resultSet1));
            }
            resultSet.close();
            resultSet1.close();
            return clients;
        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while trying to get all the clients\n");
        }
    }
    private Client mapToClient(ResultSet resultSet, ResultSet resultSet1) throws SQLException {
        Account account = new Account(resultSet1.getString(1), resultSet1.getDouble(2));
        return new Client(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4),
                account);
    }
    //update
    public void update(int id, String name, String surname, Integer age) {
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String update_query = "UPDATE clients SET name = ?, surname = ?, age = ? where id = '" + id + "'";
            PreparedStatement preparedStatement = connection.prepareStatement(update_query);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, surname);
            preparedStatement.setInt(4, age);
            preparedStatement.executeUpdate();
        } catch (SQLException exception)
        {
            throw new RuntimeException("Something went wrong while trying to update client with id = " + id);
        }
    }
    //delete
    public void deleteById(String id){
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String delete_query = "DELETE FROM clients where clientId = '" + id + "'";
            String delete_account = "DELETE FROM accounts where clientId = '" + id + "'";
            Statement statement = connection.createStatement();
            Statement statement1 = connection.createStatement();
            statement.executeUpdate(delete_query);
            statement1.executeUpdate(delete_account);
        } catch (SQLException exception)
        {
            throw new RuntimeException("Something went wrong while trying to delete client with id = " + id);
        }
    }
}
