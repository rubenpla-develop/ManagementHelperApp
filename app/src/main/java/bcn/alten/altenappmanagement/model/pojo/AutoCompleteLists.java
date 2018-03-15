package bcn.alten.altenappmanagement.model.pojo;

import java.util.List;

import bcn.alten.altenappmanagement.model.Client;

public class AutoCompleteLists {

    private List<Client> consultantsList;
    private List<Client> clientsList;

    public AutoCompleteLists() {}

    public AutoCompleteLists(List<Client> consultantsList, List<Client> clientsList) {
        this.consultantsList = consultantsList;
        this.clientsList = clientsList;
    }

    public List<Client> getConsultantsList() {
        return consultantsList;
    }

    public void setConsultantsList(List<Client> consultantsList) {
        this.consultantsList = consultantsList;
    }

    public List<Client> getClientsList() {
        return clientsList;
    }

    public void setClientsList(List<Client> clientsList) {
        this.clientsList = clientsList;
    }
}
