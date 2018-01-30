package bcn.alten.altenappmanagement.mvp.presenter;

import java.util.List;

import bcn.alten.altenappmanagement.model.QMItem;

public interface IQmFragmentPresenter {
    void showQmList();
    void showQmListWithActionParam(int action);
    void goToWeek(String date);
    void editQm(QMItem qmToEdit);
    void deleteQm(QMItem qmToDelete);
    void createNewQm(QMItem qm);
    void saveBackupList(List<QMItem> listToBackup);
    void filterByStatus(String[] statusOptions, int status);
}

