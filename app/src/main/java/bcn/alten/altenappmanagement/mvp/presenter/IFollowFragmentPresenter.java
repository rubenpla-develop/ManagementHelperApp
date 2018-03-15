package bcn.alten.altenappmanagement.mvp.presenter;

import bcn.alten.altenappmanagement.model.FollowUp;

public interface IFollowFragmentPresenter {

    void showFollowUpList();
    void editFollowUp(FollowUp followUp);
    void deleteFollowUp(FollowUp followUp);
    void createNewFollowUp(FollowUp followUp);
    void showFollowUpDialog();
    void swipeFollowUp(FollowUp followUp, String status);
}
