package bcn.alten.altenappmanagement.mvp.presenter;

import bcn.alten.altenappmanagement.mvp.model.QMItem;

public interface IQmFragmentPresenter {
    void showQmList();
    void showQmListWithActionParam(int action);
    void goToWeek(String date);
    void editQm(QMItem qmToEdit);
    void deleteQm(QMItem qmToDelete);
    void createNewQm(QMItem qm);
    void filterByStatus(int status);
}

