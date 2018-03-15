package bcn.alten.altenappmanagement.model.pojo;

import java.util.List;

import bcn.alten.altenappmanagement.model.BaseItem;

public class AutoCompleteLists {

    private List<? extends BaseItem> consultantsList;
    private List<? extends BaseItem> clientsList;

    public AutoCompleteLists() {}

    public AutoCompleteLists(List<? extends BaseItem> consultantsList, List<? extends BaseItem> clientsList) {
        this.consultantsList = consultantsList;
        this.clientsList = clientsList;
    }

    public List<? extends BaseItem> getConsultantsList() {
        return consultantsList;
    }

    public void setConsultantsList(List<? extends BaseItem> consultantsList) {
        this.consultantsList = consultantsList;
    }

    public List<? extends BaseItem> getClientsList() {
        return clientsList;
    }

    public void setClientsList(List<? extends BaseItem> clientsList) {
        this.clientsList = clientsList;
    }
}
