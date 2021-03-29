
import com.nevent.model.ticket.Ticket;

import java.text.ParseException;

public class App {
    public static void main(String[] args) throws ParseException {
        Service mainService = new Service();
        mainService.addAnEvent();
        mainService.showAllEvents();
    }
}
