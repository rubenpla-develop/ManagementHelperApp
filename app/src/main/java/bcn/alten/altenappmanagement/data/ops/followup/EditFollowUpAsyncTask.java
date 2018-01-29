package bcn.alten.altenappmanagement.data.ops.followup;

import android.os.AsyncTask;

import bcn.alten.altenappmanagement.AltenApplication;
import bcn.alten.altenappmanagement.data.db.AltenDatabase;
import bcn.alten.altenappmanagement.mvp.model.FollowUp;

public class EditFollowUpAsyncTask extends AsyncTask<FollowUp, Void, Void> {
    private FollowUp followUp;

    public EditFollowUpAsyncTask(FollowUp followUp) {
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
                .updateFollowUp(followUp);

        return null;
    }
}
