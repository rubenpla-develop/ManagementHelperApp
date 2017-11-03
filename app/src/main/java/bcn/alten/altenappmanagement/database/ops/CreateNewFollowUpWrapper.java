package bcn.alten.altenappmanagement.database.ops;

import bcn.alten.altenappmanagement.mvp.model.FollowUp;
import bcn.alten.altenappmanagement.mvp.presenter.FollowUpFragmentPresenter;

public class CreateNewFollowUpWrapper {

    private final String TAG = CreateNewFollowUpWrapper.class.getSimpleName();

    private FollowUp followUp;
    private FollowUpFragmentPresenter presenter;

    public CreateNewFollowUpWrapper(FollowUp followUp, FollowUpFragmentPresenter presenter) {
        this.followUp = followUp;
        this.presenter = presenter;
    }

    public void performCreateNewFollowUpOperation() {
        CreateNewFollowUpAsyncTask asyncTask = new CreateNewFollowUpAsyncTask(followUp);
        asyncTask.execute();
    }
}
