package bcn.alten.altenappmanagement.mvp.presenter;

import android.arch.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import bcn.alten.altenappmanagement.database.AltenDatabase;
import bcn.alten.altenappmanagement.database.ops.CreateNewFollowUpWrapper;
import bcn.alten.altenappmanagement.mvp.model.FollowUp;
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
    public void showFollowUpList() {
        //getting items from DB
        LiveData<List<FollowUp>> categoryList = AltenDatabase.getDatabase(view.getContext())
                .daoAccess()
                .fecthFollowUpData();

        view.onLiveDataChanged(categoryList);

        //TODO mocking content
        /*List<Category> categoryList = CategoryDataFactory.createMockFilteredCategories();
        view.ShowFollowUpList(categoryList);*/
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
    public void createNewFollowUp(FollowUp followUp) {
        CreateNewFollowUpWrapper wrapper = new CreateNewFollowUpWrapper(followUp, this);
        wrapper.performCreateNewFollowUpOperation();
    }
}
