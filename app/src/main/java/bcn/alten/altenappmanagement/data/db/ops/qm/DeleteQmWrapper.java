package bcn.alten.altenappmanagement.data.db.ops.qm;

import bcn.alten.altenappmanagement.data.db.ops.followup.CreateNewFollowUpWrapper;
import bcn.alten.altenappmanagement.model.QMItem;

public class DeleteQmWrapper {

    private final String TAG = CreateNewFollowUpWrapper.class.getSimpleName();

    private QMItem qmItem;

    public DeleteQmWrapper(QMItem qmItem) {
        this.qmItem = qmItem;
    }

    public void performDeleteQmOperation() {
        DeleteQmAsyncTask asyncTask = new DeleteQmAsyncTask(qmItem);
        asyncTask.execute();
    }
}
