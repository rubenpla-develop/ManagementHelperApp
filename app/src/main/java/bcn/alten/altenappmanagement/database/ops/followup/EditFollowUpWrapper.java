package bcn.alten.altenappmanagement.database.ops.followup;

import bcn.alten.altenappmanagement.mvp.model.FollowUp;

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
