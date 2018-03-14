package bcn.alten.altenappmanagement.mvp.presenter.base;

import android.arch.lifecycle.LiveData;

import java.util.List;

import bcn.alten.altenappmanagement.model.Client;
import bcn.alten.altenappmanagement.mvp.view.base.IBaseView;

import static bcn.alten.altenappmanagement.data.db.AltenDatabase.getDatabase;

public class BasePresenter  {
    protected IBaseView view;
    private LiveData<List<Client>> clientsList;
    private LiveData<List<Client>> consultantsList;


    public BasePresenter(IBaseView view) {
        this.view = view;
    }

    public void getClients() {
        clientsList = getDatabase(view.getContext()).daoAccess().fetchClientData();
    }

    public void getConsultants() {
        consultantsList = getDatabase(view.getContext()).daoAccess().fetchConsultantData();
    }

    protected LiveData<List<Client>> getClientsList() {
        return clientsList;
    }

    protected LiveData<List<Client>> getConsultantsList() {
        return consultantsList;
    }
}
