import com.nevent.model.client.Client;

import java.text.ParseException;

public class App {
    public static void main(String[] args) throws ParseException {
        MainService mainService = new MainService();
        mainService.addAnEvent();
        mainService.showAllEvents();

        ClientUtilitiesService clientService = new ClientUtilitiesService();

    }
}
