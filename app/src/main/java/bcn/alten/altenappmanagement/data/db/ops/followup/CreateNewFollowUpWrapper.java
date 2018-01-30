package bcn.alten.altenappmanagement.data.db.ops.followup;

import bcn.alten.altenappmanagement.model.FollowUp;

public class CreateNewFollowUpWrapper {

    private final String TAG = CreateNewFollowUpWrapper.class.getSimpleName();

    private FollowUp followUp;

    public CreateNewFollowUpWrapper(FollowUp followUp) {
        this.followUp = followUp;
    }

    public void performCreateNewFollowUpOperation() {
        CreateNewFollowUpAsyncTask asyncTask = new CreateNewFollowUpAsyncTask(followUp);
        asyncTask.execute();
    }
}
