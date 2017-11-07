package bcn.alten.altenappmanagement.ui.fragment;

import android.animation.Animator;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import java.util.List;

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.adapter.ExpandableCategoryListAdapter;
import bcn.alten.altenappmanagement.expandable.groupmodel.Category;
import bcn.alten.altenappmanagement.mvp.model.FollowUp;
import bcn.alten.altenappmanagement.mvp.presenter.FollowUpFragmentPresenter;
import bcn.alten.altenappmanagement.mvp.view.IFollowUpFragmentView;
import bcn.alten.altenappmanagement.ui.dialog.FollowUpDeleteDialog;
import bcn.alten.altenappmanagement.ui.dialog.FollowUpDialog;
import bcn.alten.altenappmanagement.utils.CategoryDataFactory;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FollowUpFragment extends Fragment implements IFollowUpFragmentView {

    public static final String TAG = FollowUpFragment.class.getSimpleName();

    @BindView(R.id.followup_recyclerView)
    RecyclerView expandableList;

    @BindView(R.id.follow_up_fab)
    FloatingActionButton fab_add_people;

    private AlertDialog followUpDialog;

    private FollowUpFragmentPresenter presenter;
    private ExpandableCategoryListAdapter expandableRecyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private String actionMode;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_follow_up, container, false);

        ButterKnife.bind(this, view);

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        expandableList.setLayoutManager(layoutManager);
        expandableList.setHasFixedSize(true);

        fab_add_people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddFollowUpDialog();
            }
        });

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            final Interpolator interpolador = AnimationUtils.loadInterpolator(getActivity(),
                    android.R.interpolator.overshoot);

            fab_add_people.setScaleX(0);
            fab_add_people.setScaleY(0);

            fab_add_people.animate()
                    .scaleX(1)
                    .scaleY(1)
                    .setInterpolator(interpolador)
                    .setDuration(300)
                    .setStartDelay(1500)
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            /*fab_add_people.animate()
                                    .scaleY(0)
                                    .scaleX(0)
                                    .setInterpolator(interpolador)
                                    .setDuration(600)
                                    .start();*/
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
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
        expandableRecyclerViewAdapter = new ExpandableCategoryListAdapter(list, getActivity(),
                this);

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
    public void showFollowUpDialog(String mode, Object model) {

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
}
