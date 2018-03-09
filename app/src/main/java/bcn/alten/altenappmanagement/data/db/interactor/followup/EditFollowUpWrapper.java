package bcn.alten.altenappmanagement.data.db.interactor.followup;

import bcn.alten.altenappmanagement.model.FollowUp;

public class EditFollowUpWrapper {
    private final String TAG = EditFollowUpWrapper.class.getSimpleName();

    private FollowUp followUp;

    public EditFollowUpWrapper(FollowUp followUp) {
        this.followUp = followUp;
    }

    public void performEditFollowUpOperation() {
        EditFollowUpAsyncTask asyncTask = new EditFollowUpAsyncTask(followUp);
        asyncTask.execute();
    }
}
