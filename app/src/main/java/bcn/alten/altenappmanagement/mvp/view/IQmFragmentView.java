package bcn.alten.altenappmanagement.mvp.view;


import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;

import bcn.alten.altenappmanagement.ui.adapter.expandable.groupmodel.QMCategory;
import bcn.alten.altenappmanagement.mvp.model.QMItem;
import bcn.alten.altenappmanagement.mvp.model.WeekRange;

public interface IQmFragmentView {
    void showQmList(List<QMCategory> list);
    void onLiveDataChanged(LiveData<List<QMItem>> list);
    void onLiveDataGoToWeek(LiveData<List<QMItem>> list, WeekRange weekRange);
    void editQm(QMItem qmToEdit);
    void deleteQm(QMItem qmToDelete);
    Context getContext();
}
