package bcn.alten.altenappmanagement.mvp.presenter;

import java.util.List;

import bcn.alten.altenappmanagement.mvp.view.IFollowUpFragmentView;

public class FollowUpFragmentPresenter implements IFollowFragmentPresenter {

    private final String TAG = FollowUpFragmentPresenter.class.getSimpleName();

    private IFollowUpFragmentView view;

    public FollowUpFragmentPresenter(IFollowUpFragmentView view) {
        this.view = view;
    }

    @Override
    public List<Object> showFollowUpList() {
        return null;
    }

    @Override
    public Object editFollowUp(int id) {
        return null;
    }

    @Override
    public List<Object> deleteFollowUp(int id) {
        return null;
    }

    @Override
    public Object createNewFollowUp() {
        return null;
    }
}
