package bcn.alten.altenappmanagement.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.mvp.presenter.FollowUpFragmentPresenter;
import bcn.alten.altenappmanagement.mvp.view.IFollowUpFragmentView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FollowUpFragment extends Fragment implements IFollowUpFragmentView {

    private final String TAG = FollowUpFragment.class.getSimpleName();

    @BindView(R.id.followup_recyclerView)
    RecyclerView expandableList;

    private FollowUpFragmentPresenter presenter;

    public static FollowUpFragment newInstance() {
            Bundle args = new Bundle();

            //arguments
            FollowUpFragment fragment = new FollowUpFragment();
            if (!args.isEmpty()) {
                fragment.setArguments(args);
            }

            return fragment;
    }

    public FollowUpFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_follow_up,
                container, false);

        ButterKnife.bind(this, view);

        presenter = new FollowUpFragmentPresenter(this);

        return view;
    }

    @Override
    public void ShowFollowUpList(List<Object> list) {

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
    public List<Object> addNewFollowUpCreated(Object model) {
        return null;
    }
}
