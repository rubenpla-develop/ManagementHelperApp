package bcn.alten.altenappmanagement.mvp.view;

import java.util.List;

public interface IFollowUpFragmentView {

    void ShowFollowUpList(List<Object> list);
    boolean editFollowUp(Object model);
    boolean deleteFollowUp(List<Object> list);
    void showFollowUpDialog(String mode, Object model);
    List<Object> addNewFollowUpCreated(Object model);
}
