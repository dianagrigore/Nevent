package com.nevent.model.client;

import com.nevent.model.client.payment.Account;
import com.nevent.model.event.Event;
import com.nevent.model.ticket.Ticket;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
/*Client = user that can buy and book tickets
* clientId is auto-generated based on the number of clients in the app
* When a user is created, a null Account is associated to him
* User can check his balance
* User can add/retrieve funds from the account
* User can have his age checked
* User can display his tickets and vouchers
* User can buy, book, cancel, return reservations and tickets
* By design, a user cannot buy a ticket if he doesn't have enough funds, even if he can use vouchers
* Also, when canceling -> vouchers are not returned
* Most of the functionalities are present in the ClientUtilitiesService
* */
public class Client {
    private String clientId;
    private String name;
    private String surname;
    private Integer age;
    private Account paymentMethod;
    private static Integer numberOfClients = 0;
    private ArrayList<Ticket> tickets;
    private Set<Reservation> reservations;

    public Client(String name,
                  String surname,
                  Integer age) {
        this.clientId = "CLIENT" + (++numberOfClients).toString();
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.paymentMethod = new Account(clientId);
        this.tickets = new ArrayList<Ticket>();
        this.reservations = new HashSet<Reservation>();
    }

    public static Integer getNumberOfClients() {
        return numberOfClients;
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

    public void checkMyBalance(){
        Account myAccount = this.getPaymentMethod();
        Double moneyLeft = myAccount.getLeftBalance();
        System.out.println("You have " + moneyLeft.toString() + " lei left in your account");
        myAccount.seeMyVouchers();
    }

    public void displayTickets(){
        if(tickets.isEmpty()) {
            System.out.println("No tickets bought\n");
        } else {
            for (Ticket t : tickets) {
                System.out.println(t.toString());
            }
        }
    }

    public void displayReservations(){
        if(reservations.isEmpty()){
            System.out.println("No reservations\n");
        } else {
            for (Reservation r : reservations) {
                System.out.println(r.toString());
            }
        }
    }

    public boolean canIBuyTicket(Event certainEvent){
        Integer requiredMinimumAge = certainEvent.getAgeRestriction();
        Integer myAge = this.getAge();
        if (myAge > requiredMinimumAge){
            System.out.println("You can buy a ticket for " + certainEvent.getId());
            return true;
        }
        else {
            System.out.println("You don't have the right age to buy a ticket for this event.");
            return false;
        }
    }

    public void retrieveMoneyFromAccount(Double amount){
        this.paymentMethod.retrieveFromThisAccount(amount);
    }

    public void addANewTicket(Ticket ticket){
        this.tickets.add(ticket);
    }

    public void reimburseAndDeleteTicket(Ticket ticket){
        this.tickets.remove(ticket); //remove the ticket from the list
        String type = ticket.getType();
        Event event = ticket.getEvent();
        Double ticketValue = event.getPricePerTicketType().get(type);
        this.paymentMethod.addToThisAccount(ticketValue); //add the amount back in account
    }

    public void loadMyAccount(Double amount){
            this.paymentMethod.addToThisAccount(amount);
    }

    public void addReservation(Reservation reservation){
        this.reservations.add(reservation);
    }

    public void cancelReservation(Reservation reservation){
        this.reservations.remove(reservation);
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
