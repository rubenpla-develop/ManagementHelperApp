package bcn.alten.altenappmanagement.ui.fragment;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import java.util.List;

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.adapter.ExpandableFollowUpListAdapter;
import bcn.alten.altenappmanagement.expandable.groupmodel.Category;
import bcn.alten.altenappmanagement.expandable.holderview.FollowUpItemHolder;
import bcn.alten.altenappmanagement.mvp.model.FollowUp;
import bcn.alten.altenappmanagement.mvp.presenter.FollowUpFragmentPresenter;
import bcn.alten.altenappmanagement.mvp.view.IFollowUpFragmentView;
import bcn.alten.altenappmanagement.ui.customview.RecyclerItemTouchHelper;
import bcn.alten.altenappmanagement.ui.dialog.FollowUpDeleteDialog;
import bcn.alten.altenappmanagement.ui.dialog.FollowUpDialog;
import bcn.alten.altenappmanagement.utils.CategoryDataFactory;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FollowUpFragment extends Fragment implements IFollowUpFragmentView,
        RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    public static final String TAG = FollowUpFragment.class.getSimpleName();

    @BindView(R.id.followup_recyclerView)
    RecyclerView expandableRecyclerView;

    @BindView(R.id.follow_up_fab)
    FloatingActionButton fab_add_people;

    private AlertDialog followUpDialog;

    private FollowUpFragmentPresenter presenter;
    private ExpandableFollowUpListAdapter expandableRecyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public static FollowUpFragment newInstance() {
            Bundle args = new Bundle();

            FollowUpFragment fragment = new FollowUpFragment();
            if (!args.isEmpty()) {
                fragment.setArguments(args);
            }

            return fragment;
    }

    public FollowUpFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_follow_up, container, false);

        ButterKnife.bind(this, view);

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        expandableRecyclerView.setLayoutManager(layoutManager);
        expandableRecyclerView.setHasFixedSize(true);

        fab_add_people.setOnClickListener(v -> showAddFollowUpDialog());

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            final Interpolator interpolador = AnimationUtils.loadInterpolator(getActivity(),
                    android.R.interpolator.overshoot);

            fab_add_people.setScaleX(0);
            fab_add_people.setScaleY(0);

            fab_add_people.animate()
                    .scaleX(1)
                    .scaleY(1)
                    .setInterpolator(interpolador)
                    .setDuration(400)
                    .setStartDelay(2000);
        }

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
        expandableRecyclerViewAdapter = new ExpandableFollowUpListAdapter(list, getActivity(),
                this);

        expandableRecyclerView.setAdapter(expandableRecyclerViewAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(expandableRecyclerView.getContext(),
                OrientationHelper.HORIZONTAL);
        expandableRecyclerView.addItemDecoration(dividerItemDecoration);
        ItemTouchHelper.SimpleCallback recycleritemTouchHelper =
                new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);

        new ItemTouchHelper(recycleritemTouchHelper).attachToRecyclerView(expandableRecyclerView);
    }

    @Override
    public void onLiveDataChanged(final LiveData< List<FollowUp>> list) {
        list.observe(this, followUpList -> {
            List<Category> categoryList = CategoryDataFactory.getInstance()
                    .getDataFilteredByCategory(list.getValue());

            ShowFollowUpList(categoryList);
        });
    }

    @Override
    public void editFollowUp(FollowUp followUp) {
        FollowUpDialog followupEditDialog = new FollowUpDialog(getContext(), FollowUpDialog.EDIT_FOLLOWUP_ACTION,
                followUp, presenter);

        followUpDialog = followupEditDialog.getDialog();
        followUpDialog.show();
    }

    @Override
    public void deleteFollowUp(FollowUp followUp) {
        FollowUpDeleteDialog followUpDeleteDialog = new FollowUpDeleteDialog(getContext(), followUp,
                presenter);

        followUpDialog = followUpDeleteDialog.getDialog();
        followUpDialog.show();
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    public void showAddFollowUpDialog() {
        FollowUpDialog followupAddDialog = new FollowUpDialog(getActivity(),
                FollowUpDialog.ADD_FOLLOWUP_ACTION ,presenter);

        followUpDialog = followupAddDialog.getDialog();
        followUpDialog.show();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof FollowUpItemHolder) {
            FollowUp followUp = expandableRecyclerViewAdapter.getItem(position);

            presenter.swipeFollowUp(followUp, getString(R.string.follow_up_dialog_radio_group_done_value));
        }
    }
}
