package bcn.alten.altenappmanagement.utils.factory;

import java.util.Arrays;
import java.util.List;

import bcn.alten.altenappmanagement.model.Client;
import bcn.alten.altenappmanagement.model.Consultant;

/**
 * Created by alten on 13/3/18.
 */

public class BaseFactory {

    private final String TAG = FollowUpFactory.class.getSimpleName();

    private static BaseFactory instance;

    public static BaseFactory getInstance() {
        if (instance == null) {
            instance = new BaseFactory();
        }

        return instance;
    }

    public List<Client> createMockClientList() {
        Client client1 = new Client("HP", "693948564"); 
        Client client2 = new Client("La Caixa", "693948564");
        Client client3 = new Client("OpenTrends", "693948564");
        Client client4 = new Client("Seat", "693948564");
        Client client5 = new Client("DXC", "693948564");

        List<Client> clientList = Arrays.asList(client1, client2, client3, client4, client5);

        return clientList;
    }

    public List<Consultant>createMockConsultantList() {
        Consultant consultant1 = new Consultant("Ruben Pla Ferrero", "693948564");
        Consultant consultant2 = new Consultant("Raul Huete", "693948564");
        Consultant consultant3 = new Consultant("JOrdi Gras", "693948564");
        Consultant consultant4 = new Consultant("Javier Roldan", "693948564");
        Consultant consultant5 = new Consultant("John Carpentez", "693948564");

        List<Consultant> consultantList = Arrays.asList(consultant1, consultant2, consultant3, consultant4, consultant5);

        return consultantList;
    }
}
