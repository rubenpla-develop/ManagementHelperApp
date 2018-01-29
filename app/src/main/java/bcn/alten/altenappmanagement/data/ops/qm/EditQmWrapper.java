package bcn.alten.altenappmanagement.data.ops.qm;

import bcn.alten.altenappmanagement.data.ops.followup.CreateNewFollowUpWrapper;
import bcn.alten.altenappmanagement.mvp.model.QMItem;

public class EditQmWrapper {

    private final String TAG = CreateNewFollowUpWrapper.class.getSimpleName();

    private QMItem qmItem;

    public EditQmWrapper(QMItem qmItem) {
        this.qmItem = qmItem;
    }

    public void performEditQmOperation() {
        EditQmAsyncTask asyncTask = new EditQmAsyncTask(qmItem);
        asyncTask.execute();
    }
}
