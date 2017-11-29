package bcn.alten.altenappmanagement.database.ops.qm;

import android.os.AsyncTask;

import bcn.alten.altenappmanagement.application.AltenApplication;
import bcn.alten.altenappmanagement.database.AltenDatabase;
import bcn.alten.altenappmanagement.mvp.model.QMItem;

public class DeleteQmAsyncTask extends AsyncTask<QMItem, Void, Void> {

    private QMItem qmItem;

    public DeleteQmAsyncTask(QMItem qmItem) {
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
            .deleteQM(qmItem);

            return null;
    }
}