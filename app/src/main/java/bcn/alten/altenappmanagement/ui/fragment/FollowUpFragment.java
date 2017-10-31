package bcn.alten.altenappmanagement.ui.fragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.adapter.ExpandableCategoryListAdapter;
import bcn.alten.altenappmanagement.expandable.groupmodel.Category;
import bcn.alten.altenappmanagement.mvp.model.FollowUp;
import bcn.alten.altenappmanagement.mvp.presenter.FollowUpFragmentPresenter;
import bcn.alten.altenappmanagement.mvp.view.IFollowUpFragmentView;
import bcn.alten.altenappmanagement.utils.CategoryDataFactory;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FollowUpFragment extends Fragment implements IFollowUpFragmentView {

    private final String TAG = FollowUpFragment.class.getSimpleName();

    @BindView(R.id.followup_recyclerView)
    RecyclerView expandableList;

    private FollowUpFragmentPresenter presenter;
    private ExpandableCategoryListAdapter expandableRecyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public static FollowUpFragment newInstance() {
            Bundle args = new Bundle();

            //arguments
            FollowUpFragment fragment = new FollowUpFragment();
            if (!args.isEmpty()) {
                fragment.setArguments(args);
            }

            return fragment;
    }

    public FollowUpFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_follow_up,
                container, false);

        ButterKnife.bind(this, view);

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        expandableList.setLayoutManager(layoutManager);
        expandableList.setHasFixedSize(true);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new FollowUpFragmentPresenter(this);
        presenter.showFollowUpList();
    }

    @Override
    public void ShowFollowUpList(List<Category> list) {
        expandableRecyclerViewAdapter = new ExpandableCategoryListAdapter(getActivity(),
                list);

        expandableList.setAdapter(expandableRecyclerViewAdapter);
    }

    @Override
    public void onLiveDataChanged(final LiveData< List<FollowUp>> list) {
        list.observe(this, new Observer<List<FollowUp>>() {
            @Override
            public void onChanged(@Nullable List<FollowUp> followUpList) {
                List<Category> categoryList = CategoryDataFactory.getInstance()
                        .getDataFilteredByCategory(list.getValue());

                ShowFollowUpList(categoryList);
            }
        });
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
    public Context getContext() {
        return super.getContext();
    }

    @Override
    public List<Object> addNewFollowUpCreated(Object model) {
        return null;
    }
}
