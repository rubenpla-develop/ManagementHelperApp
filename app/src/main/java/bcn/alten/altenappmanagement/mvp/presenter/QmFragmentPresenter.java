package bcn.alten.altenappmanagement.mvp.presenter;

import android.arch.lifecycle.LiveData;

import java.util.List;

import bcn.alten.altenappmanagement.database.AltenDatabase;
import bcn.alten.altenappmanagement.database.ops.qm.CreateNewQmWrapper;
import bcn.alten.altenappmanagement.database.ops.qm.DeleteQmWrapper;
import bcn.alten.altenappmanagement.database.ops.qm.EditQmWrapper;
import bcn.alten.altenappmanagement.mvp.model.QMItem;
import bcn.alten.altenappmanagement.mvp.view.IQmFragmentView;
import bcn.alten.altenappmanagement.pojo.WeekRange;
import bcn.alten.altenappmanagement.utils.JodaTimeConverter;

import static bcn.alten.altenappmanagement.utils.QMCalendarController.QMCalendarInstance;
import static bcn.alten.altenappmanagement.utils.QMDataFactory.QM_HEADER_ARROW_NEXT_WEEK_ACTION;
import static bcn.alten.altenappmanagement.utils.QMDataFactory.QM_HEADER_ARROW_PREVIOUS_WEEK_ACTION;

public class QmFragmentPresenter implements IQmFragmentPresenter{

    private final String TAG = IQmFragmentPresenter.class.getSimpleName();

    private IQmFragmentView view;

    public QmFragmentPresenter(IQmFragmentView view) {
        this.view = view;
    }

    @Override
    public void showQmList() {
        /*List<QMCategory> list = QMDataFactory.FactoryInstance()
                .getCurrentWeeks(QMDataFactory.createMockQMItemList());*/ //MOCKED CONTENT

        QMCalendarInstance().saveCurrentWeekAndYear(JodaTimeConverter.getInstance()
                        .getCurrentWeekOfYear(), JodaTimeConverter.getInstance().getCurrentYear());

        LiveData<List<QMItem>> qmList = AltenDatabase.getDatabase(view.getContext())
                .daoAccess()
                .fecthQMData();

        view.onLiveDataChanged(qmList);
    }

    @Override
    public void showQmListWithActionParam(int action) {
        int weekForAction = 0;

        switch (action) {
            case QM_HEADER_ARROW_NEXT_WEEK_ACTION:
                weekForAction = QMCalendarInstance().returnFollowingWeek(QMCalendarInstance().getWeekForReference(),
                        QMCalendarInstance().getYearForReference());
                break;
            case QM_HEADER_ARROW_PREVIOUS_WEEK_ACTION :
                weekForAction = QMCalendarInstance().returnPreviousWeek(QMCalendarInstance().getWeekForReference(),
                        QMCalendarInstance().getYearForReference());
                break;
            default :
                break;
        }

        LiveData<List<QMItem>> qmList = AltenDatabase.getDatabase(view.getContext())
                .daoAccess()
                .fecthQMData();

        WeekRange weekRange = new WeekRange(weekForAction, QMCalendarInstance().getYearForReference());

        view.onLiveDataGoToWeek(qmList, weekRange);
    }

    @Override
    public void goToWeek(String date) {
        LiveData<List<QMItem>> qmList = AltenDatabase.getDatabase(view.getContext())
                .daoAccess()
                .fecthQMData();

        WeekRange weekRange = new WeekRange(JodaTimeConverter.getInstance().getWeekOfYearFromDate(date),
                JodaTimeConverter.getInstance().getYearFromDate(date));

        view.onLiveDataGoToWeek(qmList, weekRange);
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
