package bcn.alten.altenappmanagement.data.db.interactor.client;

import bcn.alten.altenappmanagement.model.Client;

/**
 * Created by alten on 13/3/18.
 */

public class CreateClientWrapper {

    private final String TAG = CreateClientWrapper.class.getSimpleName();

    private Client client;

    public CreateClientWrapper(Client client) {
        this.client = client;
    }

    public void performCreateNewClientOperation() {
        CreateClientAsyncTask asyncTask = new CreateClientAsyncTask(client);
        asyncTask.execute();
    }
}
