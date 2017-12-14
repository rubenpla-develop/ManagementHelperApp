package bcn.alten.altenappmanagement.mvp.presenter;

import android.arch.lifecycle.LiveData;

import java.util.List;

import bcn.alten.altenappmanagement.database.AltenDatabase;
import bcn.alten.altenappmanagement.database.ops.qm.CreateNewQmWrapper;
import bcn.alten.altenappmanagement.database.ops.qm.DeleteQmWrapper;
import bcn.alten.altenappmanagement.database.ops.qm.EditQmWrapper;
import bcn.alten.altenappmanagement.mvp.model.QMItem;
import bcn.alten.altenappmanagement.mvp.view.IQmFragmentView;
import bcn.alten.altenappmanagement.utils.JodaTimeConverter;
import bcn.alten.altenappmanagement.utils.QMDataFactory;

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
        int weekForAction = 0;

        if (action == QMDataFactory.getInstance().QM_HEADER_ARROW_UP_ACTION) {
            weekForAction = QMDataFactory.getInstance().getCurrentWeek() + 1;
        } else if (action == QMDataFactory.getInstance().QM_HEADER_ARROW_DOWN_ACTION) {
            weekForAction = QMDataFactory.getInstance().getCurrentWeek() - 1;
        }

        LiveData<List<QMItem>> qmList = AltenDatabase.getDatabase(view.getContext())
                .daoAccess()
                .fecthQMData();

        view.onLiveDataGoToWeek(qmList, weekForAction);
    }

    @Override
    public void goToWeek(String date) {
        LiveData<List<QMItem>> qmList = AltenDatabase.getDatabase(view.getContext())
                .daoAccess()
                .fecthQMData();

        int week = JodaTimeConverter.getInstance().getWeekOfYearFromDate(date);

        view.onLiveDataGoToWeek(qmList, week);
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
