package bcn.alten.altenappmanagement.mvp.presenter;

import android.arch.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import bcn.alten.altenappmanagement.database.AltenDatabase;
import bcn.alten.altenappmanagement.database.ops.followup.CreateNewFollowUpWrapper;
import bcn.alten.altenappmanagement.database.ops.followup.DeleteFollowUpWrapper;
import bcn.alten.altenappmanagement.database.ops.followup.EditFollowUpWrapper;
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
    public void editFollowUp(FollowUp followUp) {
        EditFollowUpWrapper wrapper = new EditFollowUpWrapper(followUp, this);
        wrapper.performEditFollowUpOperation();
    }

    @Override
    public void deleteFollowUp(FollowUp followUp) {
        DeleteFollowUpWrapper wrapper = new DeleteFollowUpWrapper(followUp, this);
        wrapper.performDeleteFollowUpOperation();
    }

    @Override
    public void createNewFollowUp(FollowUp followUp) {
        CreateNewFollowUpWrapper wrapper = new CreateNewFollowUpWrapper(followUp, this);
        wrapper.performCreateNewFollowUpOperation();
    }
}
