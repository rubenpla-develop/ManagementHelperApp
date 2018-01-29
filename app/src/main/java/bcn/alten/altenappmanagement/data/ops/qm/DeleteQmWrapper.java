package bcn.alten.altenappmanagement.data.ops.qm;

import bcn.alten.altenappmanagement.data.ops.followup.CreateNewFollowUpWrapper;
import bcn.alten.altenappmanagement.mvp.model.QMItem;

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
