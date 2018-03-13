package bcn.alten.altenappmanagement.mvp.presenter.base;

import java.util.List;

import bcn.alten.altenappmanagement.data.db.AltenDatabase;
import bcn.alten.altenappmanagement.model.Client;
import bcn.alten.altenappmanagement.model.Consultant;
import bcn.alten.altenappmanagement.mvp.view.base.IBaseView;

public class BasePresenter  {
    public IBaseView view;

    public BasePresenter(IBaseView view) {
        this.view = view;
    }

    public List<Client> getClients() {
        List<Client> clientsList;
        clientsList = AltenDatabase.getDatabase(view.getContext()).daoAccess().fecthClientData();

        return clientsList;
    }


    public List<Consultant> getConsultants() {
        List<Consultant> consultantsList;
        consultantsList = AltenDatabase.getDatabase(view.getContext()).daoAccess()
                .fecthConsultantData();

        return consultantsList;
    }
}
