import com.nevent.model.client.Client;
import com.nevent.model.event.Event;

import java.util.Scanner;

public class ClientUtilitiesService {
   private final MainService service;

    public ClientUtilitiesService() {
        this.service = new MainService();
    }

    public void buyATicket(Event e, Client c){
        service.listAllEvents();
        e.buyATicketInterface(c);
    }
    public void bookATicket(Event e, Client c){
        service.listAllEvents();
        e.bookATicketInterface(c);
    }
    public void returnATicket(Event e, Client c){
        service.listAllEvents();
        e.returnATicket(c);
    }
    public void cancelBooking(Event e, Client c){
        e.cancelAReservation(c);
    }

    public void checkClientBalance(Client c){
        c.checkMyBalance();
    }
    public void addFunds(Client c, Double amount){
        c.loadMyAccount(amount);
    }
    public void seeMyTickets(Client c){
        c.displayTickets();
    }
    public void seeMyReservations(Client c){
        c.displayReservations();
    }
    public void giftAVoucher(Client sender, Client receiver) {
        Scanner reading = new Scanner(System.in);
        System.out.println("What amount do you want to gift this user? ");
        Double amount = reading.nextDouble();
        if (amount <= sender.getPaymentMethod().getLeftBalance()) {
            reading.nextLine();
            System.out.println("What is the reason for your gift? (use one word)");
            String reason = reading.nextLine();
            receiver.addVoucher(amount, reason);
            System.out.println("You have gifted " + receiver.getName() + " " + amount);
        } else {
            System.out.println("You don't have enough funds in your account!");
        }
    }
    public void transformBookingToTicket(Event e, Client c){
        e.transformBookingToTicket(c);
    }
}
