package bcn.alten.altenappmanagement.data.db.ops.followup;

import bcn.alten.altenappmanagement.model.FollowUp;

public class DeleteFollowUpWrapper {
    private final String TAG = DeleteFollowUpWrapper.class.getSimpleName();

    private FollowUp followUp;

    public DeleteFollowUpWrapper(FollowUp followUp) {
        this.followUp = followUp;
    }

    public void performDeleteFollowUpOperation() {
        DeleteFollowUpAsyncTask asyncTask = new DeleteFollowUpAsyncTask(followUp);
        asyncTask.execute();
    }
}
