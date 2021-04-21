import com.nevent.model.client.Client;
import com.nevent.model.client.Reservation;
import com.nevent.model.client.payment.Account;
import com.nevent.model.client.payment.Voucher;
import com.nevent.model.event.Event;
import com.nevent.model.ticket.Ticket;

import java.util.Scanner;

public class ClientUtilitiesService {
   private final MainService service;
    public ClientUtilitiesService() {
        this.service = new MainService();
    }




    public void addVoucher(Client c, Double amount, String reason){
        Voucher voucher = new Voucher(reason, amount);
        c.getPaymentMethod().addAVoucer(voucher);
    }

    public boolean canIBuyTicket(Client c, Event certainEvent){
        Integer requiredMinimumAge = certainEvent.getAgeRestriction();
        Integer myAge = c.getAge();
        if (myAge > requiredMinimumAge){
            System.out.println("You can buy/book a ticket for " + certainEvent.getId());
            return true;
        }
        else {
            System.out.println("You don't have the right age to buy a ticket for this event.");
            return false;
        }
    }

    public void retrieveMoneyFromAccount(Client c, Double amount){
        c.getPaymentMethod().retrieveFromThisAccount(amount);
    }

    public void addANewTicket(Client c, Ticket ticket){
        c.getTickets().add(ticket);
    }

    public void reimburseAndDeleteTicket(Client c, Ticket ticket){
        c.getTickets().remove(ticket); //remove the ticket from the list
        String type = ticket.getType();
        Event event = ticket.getEvent();
        Double ticketValue = event.getPricePerTicketType().get(type);
        c.getPaymentMethod().addToThisAccount(ticketValue); //add the amount back in account
    }

    public void addReservation(Client c, Reservation reservation){
        c.getReservations().add(reservation);
    }

    public void cancelReservation(Client c, Reservation reservation){
        c.getReservations().remove(reservation);
    }
    public void checkClientBalance(Client c){
        Account myAccount = c.getPaymentMethod();
        Double moneyLeft = myAccount.getLeftBalance();
        System.out.println("You have " + moneyLeft.toString() + " lei left in your account");
        myAccount.seeMyVouchers();
    }
    public void addFunds(Client c, Double amount){
        c.getPaymentMethod().addToThisAccount(amount);
    }
    public void seeMyTickets(Client c){
        if(c.getTickets().isEmpty()) {
            System.out.println("No tickets bought\n");
        } else {
            for (Ticket t : c.getTickets()) {
                System.out.println(t.toString());
            }
        }
    }
    public void seeMyReservations(Client c){
        if(c.getReservations().isEmpty()){
            System.out.println("No reservations\n");
        } else {
            for (Reservation r : c.getReservations()) {
                System.out.println(r.toString());
            }
        }
    }
    public void giftAVoucher(Client sender, Client receiver) {
        Scanner reading = new Scanner(System.in);
        System.out.println("What amount do you want to gift this user? ");
        while (!reading.hasNextDouble()) {
            System.out.print("You must enter a valid number! Try again: ");
            reading.next();
        }// end edit
        Double amount = reading.nextDouble();

        if (amount <= sender.getPaymentMethod().getLeftBalance()) {
            reading.nextLine();
            System.out.println("What is the reason for your gift? (use one word)");
            String reason = reading.nextLine();
            addVoucher(receiver, amount, reason);
            System.out.println("You have gifted " + receiver.getName() + " " + amount);
        } else {
            System.out.println("You don't have enough funds in your account!");
        }
    }

}
