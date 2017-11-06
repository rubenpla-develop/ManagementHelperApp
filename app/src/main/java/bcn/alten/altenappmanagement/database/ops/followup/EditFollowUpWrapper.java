package bcn.alten.altenappmanagement.database.ops.followup;

import bcn.alten.altenappmanagement.mvp.model.FollowUp;
import bcn.alten.altenappmanagement.mvp.presenter.FollowUpFragmentPresenter;

public class EditFollowUpWrapper {
    private final String TAG = CreateNewFollowUpWrapper.class.getSimpleName();

    private FollowUp followUp;
    private FollowUpFragmentPresenter presenter;

    //TODO not needed presenter???
    public EditFollowUpWrapper(FollowUp followUp, FollowUpFragmentPresenter presenter) {
        this.followUp = followUp;
        this.presenter = presenter;
    }

    public void performEditFollowUpOperation() {
        EditFollowUpAsyncTask asyncTask = new EditFollowUpAsyncTask(followUp);
        asyncTask.execute();
    }
}
