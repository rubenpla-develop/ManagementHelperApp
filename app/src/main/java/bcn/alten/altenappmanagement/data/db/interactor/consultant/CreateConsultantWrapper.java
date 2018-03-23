package bcn.alten.altenappmanagement.data.db.interactor.consultant;

import bcn.alten.altenappmanagement.model.Consultant;

public class CreateConsultantWrapper {

    private final String TAG = CreateConsultantWrapper.class.getSimpleName();

    private Consultant consultant;

    public CreateConsultantWrapper(Consultant consultant) {
        this.consultant = consultant;
    }

    public void performCreateNewConsultantOperation() {
        CreateConsultantAsyncTask asyncTask = new CreateConsultantAsyncTask(consultant);
        asyncTask.execute();
    }
}
