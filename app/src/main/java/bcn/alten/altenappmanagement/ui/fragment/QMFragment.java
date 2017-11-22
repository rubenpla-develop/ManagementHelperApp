package bcn.alten.altenappmanagement.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.adapter.ExpandableQMListAdapter;
import bcn.alten.altenappmanagement.expandable.groupmodel.QMCategory;
import bcn.alten.altenappmanagement.utils.QMDataFactory;
import butterknife.BindView;
import butterknife.ButterKnife;

public class QMFragment extends Fragment {

    private final String TAG = QMFragment.class.getSimpleName();

    @BindView(R.id.qm_recyclerView)
    RecyclerView expandableRecyclerView;

    private ExpandableQMListAdapter expandableRecyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public QMFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qm, container, false);
        ButterKnife.bind(this, view);

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,
                false);
        expandableRecyclerView.setLayoutManager(layoutManager);
        expandableRecyclerView.setHasFixedSize(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //TODO START - not correct, only for beginning, use custom constructor instead of this one-
        List<QMCategory> list = QMDataFactory.getInstance().getCurrentWeeks(QMDataFactory.createMockQMItemList());
        expandableRecyclerViewAdapter = new ExpandableQMListAdapter(list, getActivity(), null);
        //TODO END - DI THIS  WITH MVP

        expandableRecyclerView.setAdapter(expandableRecyclerViewAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(expandableRecyclerView.getContext(),
                OrientationHelper.HORIZONTAL);
        expandableRecyclerView.addItemDecoration(dividerItemDecoration);
    }
}
