
import com.nevent.model.ticket.Ticket;

public class App {
    public static void main(String[] args){
        Service mainService = new Service();
        mainService.addANewLocation();
        mainService.listAllLocations();
    }
}
