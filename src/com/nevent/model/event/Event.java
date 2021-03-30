package com.nevent.model.event;

import com.nevent.model.client.Client;
import com.nevent.model.client.Reservation;
import com.nevent.model.location.Location;
import com.nevent.model.ticket.Ticket;

import java.util.*;
/*
*Class event, holds the interfaces for book and buy + return and cancel ticket + booking
* */
public abstract class Event {
    private String id;
    private String description;
    private Integer ageRestriction;
    private Integer duration;
    private Location location;
    private static Integer numberOfEvents = 0;
    private List<Ticket> soldTickets;
    private ArrayList<Reservation> reservations;
    private Date dateTime;
    private Map<String, Double> pricePerTicketType;

    public Event(String description,
                 Integer ageRestriction,
                 Integer duration,
                 Location location,
                 Date dateTime,
                 Map<String, Double> pricePerTicketType) {
        this.id = "EV" + (++numberOfEvents).toString();
        this.description = description;
        this.ageRestriction = ageRestriction;
        this.duration = duration;
        this.location = location;
        this.soldTickets = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.dateTime = dateTime;
        this.pricePerTicketType = pricePerTicketType;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(Integer ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Map<String, Double> getPricePerTicketType() {
        return pricePerTicketType;
    }

    public void setPricePerTicketType(Map<String, Double> pricePerTicketType) {
        this.pricePerTicketType = pricePerTicketType;
    }

    public String getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Ticket> getSoldTickets() {
        return soldTickets;
    }

    public void setSoldTickets(List<Ticket> soldTickets) {
        this.soldTickets = soldTickets;
    }

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(ArrayList<Reservation> reservations) {
        this.reservations = reservations;
    }

    public boolean canYouStillBuyATicket(){
        Integer seats = this.getLocation().getSeating().getNumberOfSeats();
        return this.reservations.size() + this.soldTickets.size() < seats;
    }
    public void buyATicketInterface(Client client){
        int i = 1;
        Scanner reading = new Scanner(System.in);  // Create a Scanner object
        if(client.canIBuyTicket(this)) {
            if(canYouStillBuyATicket()) {
                ChooseTicketType();
                String type = reading.nextLine();  // Read user input
                switch (type) {
                    case "BASIC":
                        buyATicket(client, "BASIC");
                        break;
                    case "PREMIUM":
                        buyATicket(client, "PREMIUM");
                        break;
                    case "VIP":
                        buyATicket(client, "VIP");
                        break;
                    default:
                        System.out.println("Invalid type, please try again");
                }
                }else{
                System.out.println("Sorry. No more seats left");
            }
        }
    }

    private void ChooseTicketType() {
        System.out.println("Choose the type of ticket, by introducing the name: ");
        for (String tip : this.pricePerTicketType.keySet()) {
            Double price = this.pricePerTicketType.get(tip);
            System.out.println(tip + " at price " + price);
        }
    }

    public void buyATicket(Client client, String type){
        Double price = this.pricePerTicketType.get(type);
        System.out.println("Would you like to use any vouchers? y/n");
        Scanner reading = new Scanner(System.in);  // Create a Scanner object
        String answer = reading.nextLine();
        String reason = null;
        switch(answer){
            case "y":
                client.getPaymentMethod().seeMyVouchers();
                System.out.println("Please enter the reason of your voucher");
                reason = reading.nextLine();
                break;
            case "n":
                System.out.println("That's alright");
                break;
            default:
                System.out.println("Not a valid option");
        }
        Double voucher_value = 0.0;
        if(reason != null){
            voucher_value = client.getPaymentMethod().getTheValueOfThisVoucher(reason);
        }

        if(client.getPaymentMethod().getLeftBalance() + voucher_value >= price){
            client.getPaymentMethod().useTheVoucher(reason);
            Ticket ticket = new Ticket("Ticket" + (soldTickets.size() + 1),this, client, type);
            this.soldTickets.add(ticket);
            client.retrieveMoneyFromAccount(price);
            client.addANewTicket(ticket);
         }
        else {
            System.out.println("Not enough funds");
        }
    }

    public void returnATicket(Client client){
        //Returning policy says you get all the amount back, vouchers are not reimbursable so that's kind of a way to make money
        //for now
        Ticket ticket = null;
        for(Ticket ticket1 : soldTickets){
            if(ticket1.getClient().equals(client))
                ticket = ticket1;
        }
        if(ticket != null) {
            soldTickets.remove(ticket);
            client.reimburseAndDeleteTicket(ticket);
            System.out.println(ticket + " was removed from your collection.");
        }
        else {
            System.out.println("You don't own a ticket for this event");
        }
    }

    public void bookATicketInterface(Client client){
        int i = 1;
        Scanner reading = new Scanner(System.in);  // Create a Scanner object
        if(client.canIBuyTicket(this)) { //age check
            if(canYouStillBuyATicket()) {
                ChooseTicketType();
                String type = reading.nextLine();  // Read user input
                switch (type) {
                    case "BASIC":
                        bookATicket(client, "BASIC");
                        break;
                    case "PREMIUM":
                        bookATicket(client, "PREMIUM");
                        break;
                    case "VIP":
                        bookATicket(client, "VIP");
                        break;
                    default:
                        System.out.println("Invalid type, please try again");
                }
            } else {
                System.out.println("Sorry! No tickets left");
            }
        }

    }

    public void bookATicket(Client client, String type){
        Integer number = 0;
        if(!reservations.isEmpty()){
            number = reservations.size();
        }
        Ticket ticket = new Ticket("RES" + number + 1,this, client, type);
        List<Ticket> reservationTickets = new ArrayList<>();
        reservationTickets.add(ticket);
        Date date = new Date(); // This object contains the current date value
        Reservation reservation = new Reservation(date, reservationTickets);
        this.reservations.add(reservation);
        client.addReservation(reservation);
        System.out.println("Congratulations! You have booked a ticket!");
    }

    public void cancelAReservation(Client client){
        Reservation reservation = null;

        for(Reservation reservation1 : client.getReservations()){
            if(reservation1.getTickets().get(0).getEvent().equals(this)){
                reservation = reservation1;
            }
        }
        if(reservation != null) {
            reservations.remove(reservation);
            client.cancelReservation(reservation);
            System.out.println("You have canceled your reservation!");
        }
        else {
            System.out.println("You don't have a reservation for this event");
        }
    }

    public void transformBookingToTicket(Client c){
        int flag = 0;
        Reservation reservation = null;
        for(Reservation r : c.getReservations()){
            if(r.getTickets().get(r.getTickets().size() - 1).getEvent() == this){
                {
                    reservation = r;
                    flag = 1;
                    break;
                }
            }
        }
        if(flag == 0){
            System.out.println("You don't have a reservation for this event!");
        }
        else {
                Ticket ticket = reservation.getTickets().get(0);
                Double price = this.pricePerTicketType.get(ticket.getType());
                System.out.println("Would you like to use any vouchers? y/n");
                Scanner reading = new Scanner(System.in);  // Create a Scanner object
                String answer = reading.nextLine();
                String reason = null;
                switch(answer){
                    case "y":
                        c.getPaymentMethod().seeMyVouchers();
                        System.out.println("Please enter the reason of your voucher");
                        reason = reading.nextLine();
                        break;
                    case "n":
                        System.out.println("That's alright");
                        break;
                    default:
                        System.out.println("Not a valid option");
                }
                Double voucher_value = 0.0;
                if(reason != null){
                    voucher_value = c.getPaymentMethod().getTheValueOfThisVoucher(reason);
                }

                if(c.getPaymentMethod().getLeftBalance() + voucher_value >= price){
                    c.getPaymentMethod().useTheVoucher(reason);
                    this.soldTickets.add(ticket);
                    c.retrieveMoneyFromAccount(price);
                    c.addANewTicket(ticket);
                    c.getReservations().remove(reservation);
                }
                else {
                    System.out.println("Not enough funds");
                }
            }
        }

    public void getPresentation(){
        System.out.println("Event no: " + this.getId() + "\nDescription: " + this.getDescription()
        + "\nAge restriction: " + this.ageRestriction + "\nDuration: "+ this.duration);
        this.getLocation().describeLocation();
        System.out.println("Price per ticket type chart:\n");
        for(String key : pricePerTicketType.keySet()){
            System.out.println(key + " costs " + pricePerTicketType.get(key));
        }
        System.out.println("\n");
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", ageRestriction=" + ageRestriction +
                ", duration=" + duration +
                ", location=" + location +
                ", soldTickets=" + soldTickets +
                ", reservations=" + reservations +
                ", dateTime=" + dateTime +
                ", pricePerTicketType=" + pricePerTicketType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id) && Objects.equals(description, event.description) && Objects.equals(ageRestriction, event.ageRestriction) && Objects.equals(duration, event.duration) && Objects.equals(location, event.location) && Objects.equals(soldTickets, event.soldTickets) && Objects.equals(reservations, event.reservations) && Objects.equals(dateTime, event.dateTime) && Objects.equals(pricePerTicketType, event.pricePerTicketType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, ageRestriction, duration, location, dateTime, pricePerTicketType);
    }
}
