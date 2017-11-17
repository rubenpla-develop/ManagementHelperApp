package bcn.alten.altenappmanagement.mvp.presenter;

import android.arch.lifecycle.LiveData;

import java.util.List;

import bcn.alten.altenappmanagement.database.AltenDatabase;
import bcn.alten.altenappmanagement.database.ops.followup.CreateNewFollowUpWrapper;
import bcn.alten.altenappmanagement.database.ops.followup.DeleteFollowUpWrapper;
import bcn.alten.altenappmanagement.database.ops.followup.EditFollowUpWrapper;
import bcn.alten.altenappmanagement.mvp.model.FollowUp;
import bcn.alten.altenappmanagement.mvp.view.IFollowUpFragmentView;
import bcn.alten.altenappmanagement.utils.JodaTimeConverter;

public class FollowUpFragmentPresenter implements IFollowFragmentPresenter {

    private final String TAG = FollowUpFragmentPresenter.class.getSimpleName();

    private IFollowUpFragmentView view;

    public FollowUpFragmentPresenter(IFollowUpFragmentView view) {
        this.view = view;
    }

    @Override
    public void showFollowUpList() {
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
        EditFollowUpWrapper wrapper = new EditFollowUpWrapper(followUp);
        wrapper.performEditFollowUpOperation();
    }

    @Override
    public void deleteFollowUp(FollowUp followUp) {
        DeleteFollowUpWrapper wrapper = new DeleteFollowUpWrapper(followUp);
        wrapper.performDeleteFollowUpOperation();
    }

    @Override
    public void createNewFollowUp(FollowUp followUp) {
        CreateNewFollowUpWrapper wrapper = new CreateNewFollowUpWrapper(followUp);
        wrapper.performCreateNewFollowUpOperation();
    }

    @Override
    public void swipeFollowUp(FollowUp followUp, String status) {
        followUp.setFollowUpStatusToDone(JodaTimeConverter.getInstance().getCurrenDate(), status);
        editFollowUp(followUp);
    }
}