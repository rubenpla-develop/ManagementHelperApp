package bcn.alten.altenappmanagement.data.db.ops.qm;

import bcn.alten.altenappmanagement.data.db.ops.followup.CreateNewFollowUpWrapper;
import bcn.alten.altenappmanagement.model.QMItem;

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
