package bcn.alten.altenappmanagement.data.db.interactor.consultant;

import android.os.AsyncTask;

import bcn.alten.altenappmanagement.AltenApplication;
import bcn.alten.altenappmanagement.data.db.AltenDatabase;
import bcn.alten.altenappmanagement.model.Consultant;

public class CreateConsultantAsyncTask extends AsyncTask<Consultant, Void, Void> {

    private Consultant consultant;

    public CreateConsultantAsyncTask(Consultant consultant) {
        super();
        this.consultant = consultant;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Consultant... params) {
        AltenDatabase.getDatabase(AltenApplication.getInstance()
                .getApplicationContext())
                .daoAccess()
                .insertConsultant(consultant);

        return null;
    }
}
