package bcn.alten.altenappmanagement.mvp.view;


import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;

import bcn.alten.altenappmanagement.expandable.groupmodel.QMCategory;
import bcn.alten.altenappmanagement.mvp.model.QMItem;

public interface IQmFragmentView {
    void showQmList(List<QMCategory> list);
    void onLiveDataChanged(LiveData<List<QMItem>> list);
    void onLiveDataGoToWeek(LiveData<List<QMItem>> list, int week);
    void editQm(QMItem qmToEdit);
    void deleteQm(QMItem qmToDelete);
    Context getContext();
}
