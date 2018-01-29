package bcn.alten.altenappmanagement.mvp.view;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;

import bcn.alten.altenappmanagement.ui.adapter.expandable.groupmodel.FollowUpCategory;
import bcn.alten.altenappmanagement.mvp.model.FollowUp;

public interface IFollowUpFragmentView {

    void ShowFollowUpList(List<FollowUpCategory> list);
    void onLiveDataChanged(LiveData<List<FollowUp>> list);
    void editFollowUp(FollowUp followUp);
    void deleteFollowUp(FollowUp followUp);
    void showAddFollowUpDialog();
    Context getContext();
}
