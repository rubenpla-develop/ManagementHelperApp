package bcn.alten.altenappmanagement.mvp.view;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;

import bcn.alten.altenappmanagement.expandable.groupmodel.Category;
import bcn.alten.altenappmanagement.mvp.model.FollowUp;

public interface IFollowUpFragmentView {

    void ShowFollowUpList(List<Category> list);
    void onLiveDataChanged(LiveData<List<FollowUp>> list);
    boolean editFollowUp(Object model);
    boolean deleteFollowUp(List<Object> list);
    void showFollowUpDialog(String mode, Object model);
    List<Object> addNewFollowUpCreated(Object model);
    void showAddFollowUpDialog();
    Context getContext();
}
