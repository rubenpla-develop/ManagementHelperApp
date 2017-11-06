package bcn.alten.altenappmanagement.mvp.presenter;

import bcn.alten.altenappmanagement.mvp.model.FollowUp;

public interface IFollowFragmentPresenter {

    void showFollowUpList();
    void editFollowUp(FollowUp followUp);
    void deleteFollowUp(FollowUp followUp);
    void createNewFollowUp(FollowUp followUp);

}
