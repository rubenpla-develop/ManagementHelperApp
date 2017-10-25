package bcn.alten.altenappmanagement.mvp.view;

import java.util.List;

import bcn.alten.altenappmanagement.expandable.groupmodel.Category;

public interface IFollowUpFragmentView {

    void ShowFollowUpList(List<Category> list);
    boolean editFollowUp(Object model);
    boolean deleteFollowUp(List<Object> list);
    void showFollowUpDialog(String mode, Object model);
    List<Object> addNewFollowUpCreated(Object model);
}
