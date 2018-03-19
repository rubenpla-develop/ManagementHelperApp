package bcn.alten.altenappmanagement.mvp.presenter;

import android.arch.lifecycle.LiveData;

import java.util.List;

import bcn.alten.altenappmanagement.data.db.interactor.followup.CreateNewFollowUpWrapper;
import bcn.alten.altenappmanagement.data.db.interactor.followup.DeleteFollowUpWrapper;
import bcn.alten.altenappmanagement.data.db.interactor.followup.EditFollowUpWrapper;
import bcn.alten.altenappmanagement.model.Client;
import bcn.alten.altenappmanagement.model.FollowUp;
import bcn.alten.altenappmanagement.mvp.presenter.base.BasePresenter;
import bcn.alten.altenappmanagement.mvp.view.IFollowUpFragmentView;
import bcn.alten.altenappmanagement.mvp.view.base.IBaseView;
import bcn.alten.altenappmanagement.utils.JodaTimeConverter;

import static bcn.alten.altenappmanagement.data.db.AltenDatabase.getDatabase;

public class FollowUpFragmentPresenter extends BasePresenter implements
        IFollowFragmentPresenter {

    private final String TAG = FollowUpFragmentPresenter.class.getSimpleName();

    public FollowUpFragmentPresenter(IBaseView view) {
        super(view);
    }

    @Override
    public void showFollowUpList() {
        LiveData<List<FollowUp>> categoryList = getDatabase(view.getContext())
                .daoAccess()
                .fecthFollowUpData();

        ((IFollowUpFragmentView) view).onLiveDataChanged(categoryList);

        //TODO mocking content
        /*List<FollowUpCategory> categoryList = CategoryDataFactory.createMockFilteredCategories();
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
    public void showFollowUpDialog() {
        getAllLiveDataLists();

        ((IFollowUpFragmentView) view).showAddFollowUpDialog();
    }

    @Override
    public void swipeFollowUp(FollowUp followUp, String status) {
        followUp.setFollowUpStatusToDone(JodaTimeConverter.getInstance().getCurrenDate(), status);
        editFollowUp(followUp);
    }
}