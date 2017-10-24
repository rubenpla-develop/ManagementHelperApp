package bcn.alten.altenappmanagement.mvp.presenter;

import java.util.ArrayList;
import java.util.List;

import bcn.alten.altenappmanagement.mvp.view.IFollowUpFragmentView;

public class FollowUpFragmentPresenter implements IFollowFragmentPresenter {

    private final String TAG = FollowUpFragmentPresenter.class.getSimpleName();

    private IFollowUpFragmentView view;
    private ArrayList<Object> list;

    public FollowUpFragmentPresenter(IFollowUpFragmentView view) {
        this.view = view;
        list = new ArrayList<>();
    }

    @Override
    public List<Object> showFollowUpList() {
        //TODO get content from database about FollowUp items

        return list;
    }

    @Override
    public Object editFollowUp(int id) {
        //TODO get item from id, modify, and store on DB, once at all, update recyclerview data
        return null;
    }

    @Override
    public List<Object> deleteFollowUp(int id) {
        //TODO get item from id, modify, delete from DB, once at all, update recyclerview data

        return list;
    }

    @Override
    public Object createNewFollowUp() {
        //TODO create new element, store on DB and finally update list on recyclerview

        return null;
    }
}
