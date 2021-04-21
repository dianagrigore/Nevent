import com.nevent.model.client.Client;
import com.nevent.model.client.Reservation;
import com.nevent.model.event.Event;
import com.nevent.model.ticket.Ticket;
import com.sun.tools.javac.Main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class EventManagementService {
    private final ClientUtilitiesService clientUtilitiesService;
    private final MainService mainService;

    public EventManagementService() {
        this.clientUtilitiesService = new ClientUtilitiesService();
        this.mainService = new MainService();
    }

    public void buyATicket(Event e, Client c){
        mainService.listAllEvents();
        buyATicketInterface(e, c);
    }
    public void bookATicket(Event e, Client c){
        mainService.listAllEvents();
        bookATicketInterface(e, c);
    }
    public void returnATicket(Event e, Client c){
        mainService.listAllEvents();
        returnATicketWorker(e, c);
    }

    public boolean canYouStillBuyATicket(Event e){
        Integer seats = e.getLocation().getSeating().getNumberOfSeats();
        return e.getReservations().size() + e.getSoldTickets().size() < seats;
    }
    public void buyATicketInterface(Event e, Client client){
        Scanner reading = new Scanner(System.in);  // Create a Scanner object
        if(clientUtilitiesService.canIBuyTicket(client, e)) {
            if(canYouStillBuyATicket(e)) {
                ChooseTicketType(e);
                String type = reading.nextLine();  // Read user input
                switch (type) {
                    case "BASIC":
                        buyATicket(e, client, "BASIC");
                        break;
                    case "PREMIUM":
                        buyATicket(e, client, "PREMIUM");
                        break;
                    case "VIP":
                        buyATicket(e, client, "VIP");
                        break;
                    default:
                        System.out.println("Invalid type, please try again");
                }
            }else{
                System.out.println("Sorry. No more seats left");
            }
        }
    }

    private void ChooseTicketType(Event e) {
        System.out.println("Choose the type of ticket, by introducing the name: ");
        for (String tip : e.getPricePerTicketType().keySet()) {
            Double price = e.getPricePerTicketType().get(tip);
            System.out.println(tip + " at price " + price);
        }
    }

    public void buyATicket(Event e, Client client, String type){
        Double price = e.getPricePerTicketType().get(type);
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
            Ticket ticket = new Ticket("Ticket" + (e.getSoldTickets().size() + 1), e, client, type);
            e.getSoldTickets().add(ticket);
            clientUtilitiesService.retrieveMoneyFromAccount(client, price);
            clientUtilitiesService.addANewTicket(client, ticket);
        }
        else {
            System.out.println("Not enough funds");
        }
    }

    private void returnATicketWorker(Event e, Client client){
        //Returning policy says you get all the amount back, vouchers are not reimbursable so that's kind of a way to make money
        //for now
        Ticket ticket = null;
        for(Ticket ticket1 : e.getSoldTickets()){
            if(ticket1.getClient().equals(client))
                ticket = ticket1;
        }
        if(ticket != null) {
            e.getSoldTickets().remove(ticket);
            clientUtilitiesService.reimburseAndDeleteTicket(client, ticket);
            System.out.println(ticket + " was removed from your collection.");
        }
        else {
            System.out.println("You don't own a ticket for this event");
        }
    }

    public void bookATicketInterface(Event e, Client client){
        Scanner reading = new Scanner(System.in);  // Create a Scanner object
        if(clientUtilitiesService.canIBuyTicket(client, e)) { //age check
            if(canYouStillBuyATicket(e)) {
                ChooseTicketType(e);
                String type = reading.nextLine();  // Read user input
                switch (type) {
                    case "BASIC":
                        bookATicket(e, client, "BASIC");
                        break;
                    case "PREMIUM":
                        bookATicket(e, client, "PREMIUM");
                        break;
                    case "VIP":
                        bookATicket(e, client, "VIP");
                        break;
                    default:
                        System.out.println("Invalid type, please try again");
                }
            } else {
                System.out.println("Sorry! No tickets left");
            }
        }

    }

    public void bookATicket(Event e, Client client, String type){
        int number = 0;
        if(!e.getReservations().isEmpty()){
            number = e.getReservations().size();
        }
        Ticket ticket = new Ticket("RES" + number + 1,e, client, type);
        List<Ticket> reservationTickets = new ArrayList<>();
        reservationTickets.add(ticket);
        Date date = new Date(); // This object contains the current date value
        Reservation reservation = new Reservation(date, reservationTickets);
        e.getReservations().add(reservation);
        clientUtilitiesService.addReservation(client, reservation);
        System.out.println("Congratulations! You have booked a ticket!");
    }

    public void cancelAReservation(Event e, Client client){
        Reservation reservation = null;

        for(Reservation reservation1 : client.getReservations()){
            if(reservation1.getTickets().get(0).getEvent().equals(e)){
                reservation = reservation1;
            }
        }
        if(reservation != null) {
            e.getReservations().remove(reservation);
            clientUtilitiesService.cancelReservation(client, reservation);
            System.out.println("You have canceled your reservation!");
        }
        else {
            System.out.println("You don't have a reservation for this event");
        }
    }

    public void transformBookingToTicket(Event e, Client c){
        int flag = 0;
        Reservation reservation = null;
        for(Reservation r : c.getReservations()){
            if(r.getTickets().get(r.getTickets().size() - 1).getEvent() == e){
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
            Double price = e.getPricePerTicketType().get(ticket.getType());
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
                e.getSoldTickets().add(ticket);
                clientUtilitiesService.retrieveMoneyFromAccount(c, price);
                clientUtilitiesService.addANewTicket(c, ticket);
                c.getReservations().remove(reservation);
            }
            else {
                System.out.println("Not enough funds");
            }
        }
    }


}
