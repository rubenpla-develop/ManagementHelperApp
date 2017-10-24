package bcn.alten.altenappmanagement.ui.fragment;

import android.support.v4.app.Fragment;

import java.util.List;

import bcn.alten.altenappmanagement.mvp.view.IFollowUpFragmentView;

public class FollowUpFragment extends Fragment implements IFollowUpFragmentView {

    public FollowUpFragment(){};

    @Override
    public void ShowFollowUpList(List<Object> list) {

    }

    @Override
    public boolean editFollowUp(Object model) {
        return false;
    }

    @Override
    public boolean deleteFollowUp(List<Object> list) {
        return false;
    }

    @Override
    public void showFollowUpDialog(String mode, Object model) {

    }

    @Override
    public List<Object> addNewFollowUpCreated(Object model) {
        return null;
    }
}
