package bcn.alten.altenappmanagement.mvp.presenter;

import java.util.ArrayList;
import java.util.List;

import bcn.alten.altenappmanagement.expandable.groupmodel.Category;
import bcn.alten.altenappmanagement.mvp.view.IFollowUpFragmentView;
import bcn.alten.altenappmanagement.utils.CategoryDataFactory;

public class FollowUpFragmentPresenter implements IFollowFragmentPresenter {

    private final String TAG = FollowUpFragmentPresenter.class.getSimpleName();

    private IFollowUpFragmentView view;
    private ArrayList<Object> list;

    public FollowUpFragmentPresenter(IFollowUpFragmentView view) {
        this.view = view;
        list = new ArrayList<>();
    }

    @Override
    public void showFollowUpList() {
        //TODO get items from DB

        //TODO mocking content
        List<Category> categoryList = CategoryDataFactory.makeGenres();
        view.ShowFollowUpList(categoryList);
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
