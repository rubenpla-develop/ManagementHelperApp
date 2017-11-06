package bcn.alten.altenappmanagement.database.ops.followup;

import bcn.alten.altenappmanagement.mvp.model.FollowUp;
import bcn.alten.altenappmanagement.mvp.presenter.FollowUpFragmentPresenter;

public class DeleteFollowUpWrapper {
    private final String TAG = DeleteFollowUpWrapper.class.getSimpleName();

    private FollowUp followUp;
    private FollowUpFragmentPresenter presenter;

    //TODO not needed presenter???
    public DeleteFollowUpWrapper(FollowUp followUp, FollowUpFragmentPresenter presenter) {
        this.followUp = followUp;
        this.presenter = presenter;
    }

    public void performDeleteFollowUpOperation() {
        DeleteFollowUpAsyncTask asyncTask = new DeleteFollowUpAsyncTask(followUp);
        asyncTask.execute();
    }
}
