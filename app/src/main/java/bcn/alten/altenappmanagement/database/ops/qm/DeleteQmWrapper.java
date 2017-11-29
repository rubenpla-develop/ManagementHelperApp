package bcn.alten.altenappmanagement.database.ops.qm;

import bcn.alten.altenappmanagement.database.ops.followup.CreateNewFollowUpWrapper;
import bcn.alten.altenappmanagement.mvp.model.QMItem;

public class DeleteQmWrapper {

    private final String TAG = CreateNewFollowUpWrapper.class.getSimpleName();

    private QMItem qmItem;

    public DeleteQmWrapper(QMItem qmItem) {
        this.qmItem = qmItem;
    }

    public void performDeleteQmOperation() {
        EditQmAsyncTask asyncTask = new EditQmAsyncTask(qmItem);
        asyncTask.execute();
    }
}
