package bcn.alten.altenappmanagement.data.db.interactor.client;

import android.os.AsyncTask;

import bcn.alten.altenappmanagement.AltenApplication;
import bcn.alten.altenappmanagement.data.db.AltenDatabase;
import bcn.alten.altenappmanagement.model.Client;

public class CreateClientAsyncTask extends AsyncTask<Client, Void, Void> {

    private Client client;

    public CreateClientAsyncTask(Client client) {
        super();
        this.client = client;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Client... params) {
        AltenDatabase.getDatabase(AltenApplication.getInstance()
                .getApplicationContext())
                .daoAccess()
                .insertClient(client);

        return null;
    }
}
