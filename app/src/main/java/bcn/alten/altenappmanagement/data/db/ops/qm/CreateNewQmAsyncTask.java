package bcn.alten.altenappmanagement.data.db.ops.qm;

import android.os.AsyncTask;

import bcn.alten.altenappmanagement.AltenApplication;
import bcn.alten.altenappmanagement.data.db.AltenDatabase;
import bcn.alten.altenappmanagement.model.QMItem;

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
