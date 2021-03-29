import com.nevent.model.client.Client;
import com.nevent.model.event.Event;

public class ClientUtilitiesService {
    MainService service;

    public ClientUtilitiesService() {
        this.service = new MainService();
    }

    public void buyATicket(Event e, Client c){
        service.showAllEvents();
        e.buyATicketInterface(c);
    }
    public void bookATicket(Event e, Client c){
        service.showAllEvents();
        e.bookATicketInterface(c);
    }
    public void returnATicket(Event e, Client c){
        service.showAllEvents();
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
}
