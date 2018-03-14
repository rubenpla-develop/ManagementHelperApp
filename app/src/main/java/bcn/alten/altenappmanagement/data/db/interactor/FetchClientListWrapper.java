package bcn.alten.altenappmanagement.data.db.interactor;

/**
 * Created by alten on 13/3/18.
 */

public class FetchClientListWrapper {

    public void performCreateNewFollowUpOperation() {
        FetchClientListAsyncTask asyncTask = new FetchClientListAsyncTask();
        asyncTask.execute();
    }
}
