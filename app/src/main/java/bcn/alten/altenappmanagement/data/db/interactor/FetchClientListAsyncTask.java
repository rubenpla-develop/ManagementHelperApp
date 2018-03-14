package bcn.alten.altenappmanagement.data.db.interactor;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import bcn.alten.altenappmanagement.AltenApplication;
import bcn.alten.altenappmanagement.data.db.AltenDatabase;
import bcn.alten.altenappmanagement.model.Client;

public class FetchClientListAsyncTask extends AsyncTask<Void, Void, List<Client>> {

    List<Client> clientList = new ArrayList<>();

    public FetchClientListAsyncTask() {
        super();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Client> doInBackground(Void... params) {
        clientList = AltenDatabase.getDatabase(AltenApplication.getInstance()
                .getApplicationContext())
                .daoAccess()
                .fetchClientData()
                .getValue();

        return clientList;
    }

    @Override
    protected void onPostExecute(List<Client> clients) {
        super.onPostExecute(clients);
    }
}
