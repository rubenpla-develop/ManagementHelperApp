package bcn.alten.altenappmanagement.database.ops.followup;

import bcn.alten.altenappmanagement.mvp.model.QMItem;

public class CreateNewQmWrapper {

    private final String TAG = CreateNewFollowUpWrapper.class.getSimpleName();

    private QMItem qmItem;

    public CreateNewQmWrapper(QMItem qmItem) {
        this.qmItem = qmItem;
    }

    public void performCreateNewQmOperation() {
        CreateNewQmAsyncTask asyncTask = new CreateNewQmAsyncTask(qmItem);
        asyncTask.execute();
    }
}
