package bcn.alten.altenappmanagement.mvp.presenter;

import android.arch.lifecycle.LiveData;

import java.util.List;

import bcn.alten.altenappmanagement.database.AltenDatabase;
import bcn.alten.altenappmanagement.database.ops.qm.CreateNewQmWrapper;
import bcn.alten.altenappmanagement.database.ops.qm.DeleteQmWrapper;
import bcn.alten.altenappmanagement.database.ops.qm.EditQmWrapper;
import bcn.alten.altenappmanagement.mvp.model.QMItem;
import bcn.alten.altenappmanagement.mvp.view.IQmFragmentView;

public class QmFragmentPresenter implements IQmFragmentPresenter{

    private final String TAG = IQmFragmentPresenter.class.getSimpleName();

    private IQmFragmentView view;

    public QmFragmentPresenter(IQmFragmentView view) {
        this.view = view;
    }


    @Override
    public void showQmList() {
        /*List<QMCategory> list = QMDataFactory.getInstance()
                .getCurrentWeeks(QMDataFactory.createMockQMItemList());*/ //MOCKED CONTENT

        LiveData<List<QMItem>> qmList = AltenDatabase.getDatabase(view.getContext())
                .daoAccess()
                .fecthQMData();

        view.onLiveDataChanged(qmList);
    }

    @Override
    public void showQmListWithActionParam(int action) {

    }

    @Override
    public void goToWeek(String date) {
        LiveData<List<QMItem>> qmList = AltenDatabase.getDatabase(view.getContext())
                .daoAccess()
                .fecthQMData();

        view.onLiveDataGoToWeek(qmList, date);
    }

    @Override
    public void editQm(QMItem qmToEdit) {
        EditQmWrapper qmWrapper = new EditQmWrapper(qmToEdit);
        qmWrapper.performEditQmOperation();
    }

    @Override
    public void deleteQm(QMItem qmToDelete) {
        DeleteQmWrapper qmDeleteWrapper = new DeleteQmWrapper(qmToDelete);
        qmDeleteWrapper.performDeleteQmOperation();
    }

    @Override
    public void createNewQm(QMItem qm) {
        CreateNewQmWrapper createNewQmWrapper = new CreateNewQmWrapper(qm);
        createNewQmWrapper.performCreateNewQmOperation();
    }
}
