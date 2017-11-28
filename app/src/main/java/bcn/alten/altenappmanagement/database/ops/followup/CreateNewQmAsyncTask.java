package bcn.alten.altenappmanagement.database.ops.followup;

import android.os.AsyncTask;

import bcn.alten.altenappmanagement.application.AltenApplication;
import bcn.alten.altenappmanagement.database.AltenDatabase;
import bcn.alten.altenappmanagement.mvp.model.QMItem;

public class CreateNewQmAsyncTask extends AsyncTask<QMItem, Void , Void> {
    private QMItem qmItem;

    public CreateNewQmAsyncTask(QMItem qmItem) {
        super();
        this.qmItem = qmItem;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(QMItem... params) {
        AltenDatabase.getDatabase(AltenApplication.getInstance()
                .getApplicationContext())
                .daoAccess()
                .createNewQM(qmItem);

        return null;
    }
}
