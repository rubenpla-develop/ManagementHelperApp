package bcn.alten.altenappmanagement.mvp.presenter;

import java.util.List;

import bcn.alten.altenappmanagement.mvp.model.FollowUp;

public interface IFollowFragmentPresenter {

    void showFollowUpList();
    void editFollowUp(FollowUp followUp);
    List<Object> deleteFollowUp(int id);
    void createNewFollowUp(FollowUp followUp);

}
