package bcn.alten.altenappmanagement.mvp.presenter;

import android.arch.lifecycle.LiveData;

import bcn.alten.altenappmanagement.mvp.presenter.base.BasePresenter;
import bcn.alten.altenappmanagement.mvp.view.IQMCreateEditActivityView;

import static bcn.alten.altenappmanagement.data.db.AltenDatabase.getDatabase;

public class QMCreateEditActivityPresenter extends BasePresenter implements iQMCreateEditActivityPresenter {

    public QMCreateEditActivityPresenter(IQMCreateEditActivityView view) {
        super(view);
    }

    @Override
    public void getLiveDataClients() {
        super.getLiveDataClients();

        ((IQMCreateEditActivityView) view).setClientAutoCompleteAdapter(getClientsList());
    }

    @Override
    public void getLiveDataConsultants() {
        super.getLiveDataConsultants();

        ((IQMCreateEditActivityView) view).setConsultantAutoCompleteAdapter(getConsultantsList());
    }

    @Override
    public LiveData<String> getConsultantById(int id) {
        LiveData<String> consultant = getDatabase(view.getContext()).daoAccess()
                .getConsultantIdByTheirId(id);

        //TODO check if exists, if not any non-null result, we have to save it to Db

        return consultant;
    }

    @Override
    public LiveData<String> getClientById(int id) {
        LiveData<String> consultant = getDatabase(view.getContext()).daoAccess()
                .getClientIdByTheirId(id);

        //TODO check if exists, if not any non-null result, we have to save it to Db

        return consultant;
    }

    @Override
    public LiveData<String> getConsultantByName(String name) {
        return null;
    }

    @Override
    public LiveData<String> getClientByName(String name) {
        return null;
    }
}
