package bcn.alten.altenappmanagement.data.db.interactor.qm;

import bcn.alten.altenappmanagement.data.db.interactor.followup.CreateNewFollowUpWrapper;
import bcn.alten.altenappmanagement.model.QMItem;

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
