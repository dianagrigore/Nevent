package com.nevent.model.client;

import com.nevent.model.client.payment.Account;
import com.nevent.model.ticket.Ticket;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

public class Client {
    private String clientId;
    private String name;
    private String surname;
    private Integer age;
    private Account paymentMethod;
    private static Integer numberOfClients;
    private ArrayList<Ticket> tickets;
    private Set<Reservation> reservations;

    public Client(String clientId,
                  String name,
                  String surname,
                  Integer age,
                  Account paymentMethod,
                  ArrayList<Ticket> tickets,
                  Set<Reservation> reservations) {
        this.clientId = clientId;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.paymentMethod = paymentMethod;
        this.tickets = tickets;
        this.reservations = reservations;
    }

    public static Integer getNumberOfClients() {
        return numberOfClients;
    }

    public static void setNumberOfClients(Integer numberOfClients) {
        Client.numberOfClients = numberOfClients;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Account getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Account paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(ArrayList<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }


    @Override
    public String toString() {
        return "Client{" +
                "clientId='" + clientId + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", paymentMethod=" + paymentMethod +
                ", tickets=" + tickets +
                ", reservations=" + reservations +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(clientId, client.clientId) && Objects.equals(name, client.name) && Objects.equals(surname, client.surname) && Objects.equals(age, client.age) && Objects.equals(paymentMethod, client.paymentMethod) && Objects.equals(tickets, client.tickets) && Objects.equals(reservations, client.reservations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, name, surname, age, paymentMethod, tickets, reservations);
    }
}
