package bcn.alten.altenappmanagement.mvp.presenter;

import java.util.List;

public interface IFollowFragmentPresenter {

    //TODO all 'Object' ---> change by its own model
    void showFollowUpList();
    Object editFollowUp(int id);
    List<Object> deleteFollowUp(int id);
    Object createNewFollowUp();

}
