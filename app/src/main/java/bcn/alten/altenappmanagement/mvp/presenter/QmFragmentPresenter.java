package bcn.alten.altenappmanagement.mvp.presenter;

import android.arch.lifecycle.LiveData;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.List;

import bcn.alten.altenappmanagement.data.db.AltenDatabase;
import bcn.alten.altenappmanagement.data.db.interactor.qm.CreateNewQmWrapper;
import bcn.alten.altenappmanagement.data.db.interactor.qm.DeleteQmWrapper;
import bcn.alten.altenappmanagement.data.db.interactor.qm.EditQmWrapper;
import bcn.alten.altenappmanagement.ui.adapter.expandable.groupmodel.QMCategory;
import bcn.alten.altenappmanagement.model.QMItem;
import bcn.alten.altenappmanagement.mvp.view.IQmFragmentView;
import bcn.alten.altenappmanagement.model.pojo.WeekRange;
import bcn.alten.altenappmanagement.utils.JodaTimeConverter;

import static bcn.alten.altenappmanagement.ui.activity.QmCreateEditActivity.STATUS_ACCEPTED;
import static bcn.alten.altenappmanagement.ui.activity.QmCreateEditActivity.STATUS_CANCELLED;
import static bcn.alten.altenappmanagement.ui.activity.QmCreateEditActivity.STATUS_DONE;
import static bcn.alten.altenappmanagement.ui.activity.QmCreateEditActivity.STATUS_SCHEDULED;
import static bcn.alten.altenappmanagement.ui.fragment.QMFragment.ACCEPTED_FILTER_OPTION;
import static bcn.alten.altenappmanagement.ui.fragment.QMFragment.CANCELLED_FILTER_OPTION;
import static bcn.alten.altenappmanagement.ui.fragment.QMFragment.CLEAR_FILTER_OPTION;
import static bcn.alten.altenappmanagement.ui.fragment.QMFragment.DONE_FILTER_OPTION;
import static bcn.alten.altenappmanagement.ui.fragment.QMFragment.SCHEDULED_FILTER_OPTION;
import static bcn.alten.altenappmanagement.utils.controller.QMCalendarController.QMCalendarInstance;
import static bcn.alten.altenappmanagement.utils.factory.QMDataFactory.FactoryInstance;
import static bcn.alten.altenappmanagement.utils.factory.QMDataFactory.QM_HEADER_ARROW_NEXT_WEEK_ACTION;
import static bcn.alten.altenappmanagement.utils.factory.QMDataFactory.QM_HEADER_ARROW_PREVIOUS_WEEK_ACTION;

public class QmFragmentPresenter implements IQmFragmentPresenter{

    private final String TAG = IQmFragmentPresenter.class.getSimpleName();

    private IQmFragmentView view;

    private List<QMItem> backupList;

    public QmFragmentPresenter(IQmFragmentView view) {
        this.view = view;
    }

    @Override
    public void showQmList() {
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
                weekForAction = QMCalendarInstance().returnFollowingWeek(QMCalendarInstance()
                                .getWeekForReference(), QMCalendarInstance().getYearForReference());

                break;
            case QM_HEADER_ARROW_PREVIOUS_WEEK_ACTION :
                weekForAction = QMCalendarInstance().returnPreviousWeek(QMCalendarInstance()
                                .getWeekForReference(), QMCalendarInstance().getYearForReference());

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

    @Override
    public void saveBackupList(List<QMItem> listToBackup) {
        this.backupList = listToBackup;
    }

    @Override
    public void filterByStatus(String[] statusOptions, int filterAction) {
        WeekRange weekRange = new WeekRange(QMCalendarInstance().getWeekForReference(),
                QMCalendarInstance().getYearForReference());

        List<? extends ExpandableGroup> groupList = FactoryInstance()
                .getSelectedWeek(backupList, weekRange);

        List<QMItem> filteredList = new ArrayList<>();
        QMItem qmItem;

        final boolean IS_SCHEDULED_FILTERED =  filterAction == SCHEDULED_FILTER_OPTION;
        final boolean IS_DONE_FILTERED = filterAction == DONE_FILTER_OPTION;
        final boolean IS_ACCEPTED_FILTERED_ = filterAction == ACCEPTED_FILTER_OPTION;
        final boolean IS_CANCELLED_FILTERED = filterAction == CANCELLED_FILTER_OPTION;
        final boolean IS_FILTER_CLEAR = filterAction == CLEAR_FILTER_OPTION;

        if (!IS_FILTER_CLEAR) {

            //We are iterating all expandable groups composing QM list
            for (int i = 0; i < groupList.size(); i++) {
                //Iterating all elements of each expandable group of QM list
                for (Object item : groupList.get(i).getItems()) {
                    qmItem = (QMItem) item;

                    if (qmItem.getStatus().equalsIgnoreCase(statusOptions[STATUS_SCHEDULED])
                            && IS_SCHEDULED_FILTERED) {
                        filteredList.add(qmItem);
                        
                    } else if (qmItem.getStatus().equalsIgnoreCase(statusOptions[STATUS_DONE])
                            && IS_DONE_FILTERED) {
                        filteredList.add(qmItem);
                        
                    } else if (qmItem.getStatus().equalsIgnoreCase(statusOptions[STATUS_ACCEPTED])
                            && IS_ACCEPTED_FILTERED_) {
                        filteredList.add(qmItem);
                        
                    } else if (qmItem.getStatus().equalsIgnoreCase(statusOptions[STATUS_CANCELLED])
                            && IS_CANCELLED_FILTERED) {
                        filteredList.add(qmItem);
                    }
                }

                groupList.get(i).getItems().clear();
                groupList.get(i).getItems().addAll(filteredList);
                filteredList.clear();
            }
        }

        view.showQmList((List<QMCategory>) groupList);
    }

    public List<QMItem> getBackupList() {
        return backupList;
    }
}
