package bcn.alten.altenappmanagement.data.ops.followup;

import bcn.alten.altenappmanagement.mvp.model.FollowUp;

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
