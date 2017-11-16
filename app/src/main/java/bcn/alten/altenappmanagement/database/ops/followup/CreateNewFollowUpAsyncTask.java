package bcn.alten.altenappmanagement.database.ops.followup;

import android.os.AsyncTask;

import bcn.alten.altenappmanagement.application.AltenApplication;
import bcn.alten.altenappmanagement.database.AltenDatabase;
import bcn.alten.altenappmanagement.mvp.model.FollowUp;

public class CreateNewFollowUpAsyncTask extends AsyncTask<FollowUp, Void, Void> {
    private FollowUp followUp;

    public CreateNewFollowUpAsyncTask(FollowUp followUp) {
        super();
        this.followUp = followUp;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(FollowUp... params) {
        AltenDatabase.getDatabase(AltenApplication.getInstance()
                .getApplicationContext())
                .daoAccess()
                .createNewFollowUp(followUp);

        return null;
    }
}
