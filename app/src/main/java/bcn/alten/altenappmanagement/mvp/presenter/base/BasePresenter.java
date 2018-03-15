package bcn.alten.altenappmanagement.mvp.presenter.base;

import android.arch.lifecycle.LiveData;

import java.util.List;

import bcn.alten.altenappmanagement.model.Client;
import bcn.alten.altenappmanagement.model.pojo.AutoCompleteLists;
import bcn.alten.altenappmanagement.mvp.view.base.IBaseView;

import static bcn.alten.altenappmanagement.data.db.AltenDatabase.getDatabase;

public class BasePresenter  {
    protected IBaseView view;
    private LiveData<List<Client>> clientsList;
    private LiveData<List<Client>> consultantsList;
    private AutoCompleteLists autoCompleteLists;


    public BasePresenter(IBaseView view) {
        this.view = view;
        autoCompleteLists = new AutoCompleteLists();
    }

    public void getLiveDataClients() {
        clientsList = getDatabase(view.getContext()).daoAccess().fetchClientData();
    }

    public void getLiveDataConsultants() {
        consultantsList = getDatabase(view.getContext()).daoAccess().fetchConsultantData();
    }

    public void getAllLiveDataLists() {
        getLiveDataClients();
        getLiveDataConsultants();
    }

    public LiveData<List<Client>> getClientsList() {
        return clientsList;
    }

    public LiveData<List<Client>> getConsultantsList() {
        return consultantsList;
    }

    public AutoCompleteLists getAutoCompleteLists() {
        return autoCompleteLists;
    }

    public void setAutoCompleteLists(AutoCompleteLists autoCompleteLists) {
        this.autoCompleteLists = autoCompleteLists;
    }

    public IBaseView getView() {
        return view;
    }
}
