package bcn.alten.altenappmanagement.data.ops.followup;

import bcn.alten.altenappmanagement.mvp.model.FollowUp;

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
