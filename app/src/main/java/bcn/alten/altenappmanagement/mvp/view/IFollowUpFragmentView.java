package bcn.alten.altenappmanagement.mvp.view;

import android.arch.lifecycle.LiveData;

import java.util.List;

import bcn.alten.altenappmanagement.model.FollowUp;
import bcn.alten.altenappmanagement.mvp.view.base.IBaseView;
import bcn.alten.altenappmanagement.ui.adapter.expandable.groupmodel.FollowUpCategory;

public interface IFollowUpFragmentView extends IBaseView {

    void ShowFollowUpList(List<FollowUpCategory> list);
    void onLiveDataChanged(LiveData<List<FollowUp>> list);
    void editFollowUp(FollowUp followUp);
    void deleteFollowUp(FollowUp followUp);
    void showAddFollowUpDialog();
}
