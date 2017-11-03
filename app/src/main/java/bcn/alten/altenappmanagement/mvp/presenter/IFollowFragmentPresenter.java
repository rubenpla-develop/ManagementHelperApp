package bcn.alten.altenappmanagement.mvp.presenter;

import java.util.List;

import bcn.alten.altenappmanagement.mvp.model.FollowUp;

public interface IFollowFragmentPresenter {

    void showFollowUpList();
    Object editFollowUp(int id);
    List<Object> deleteFollowUp(int id);
    void createNewFollowUp(FollowUp followUp);

}
