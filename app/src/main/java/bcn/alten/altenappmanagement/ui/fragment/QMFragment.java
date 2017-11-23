package bcn.alten.altenappmanagement.ui.fragment;

import android.arch.lifecycle.LiveData;
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
import bcn.alten.altenappmanagement.mvp.model.QMItem;
import bcn.alten.altenappmanagement.mvp.presenter.QmFragmentPresenter;
import bcn.alten.altenappmanagement.mvp.view.IQmFragmentView;
import bcn.alten.altenappmanagement.utils.QMDataFactory;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.kobakei.materialfabspeeddial.FabSpeedDial;

public class QMFragment extends Fragment implements IQmFragmentView {

    private final String TAG = QMFragment.class.getSimpleName();

    @BindView(R.id.qm_recyclerView)
    RecyclerView expandableRecyclerView;

    @BindView(R.id.qm_fab_speed_dial)
    FabSpeedDial qmFabSpeedDialButton;

    private ExpandableQMListAdapter expandableRecyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private QmFragmentPresenter presenter;

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

        qmFabSpeedDialButton = new FabSpeedDial(getActivity());
        qmFabSpeedDialButton.addOnMenuItemClickListener((fab, textView, itemId) -> {
            // do stuff
        });

        qmFabSpeedDialButton.addOnStateChangeListener(new FabSpeedDial.OnStateChangeListener() {
            @Override
            public void onStateChange(boolean open) {
                //do stuff
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new QmFragmentPresenter(this);
        presenter.showQmList();
    }


    @Override
    public void showQmList(List<QMCategory> list) {
        expandableRecyclerViewAdapter = new ExpandableQMListAdapter(list, getActivity(), null);
        expandableRecyclerView.setAdapter(expandableRecyclerViewAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(expandableRecyclerView.getContext(),
                OrientationHelper.HORIZONTAL);
        expandableRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onLiveDataChanged(LiveData<List<QMItem>> list) {
        /*List<QMCategory> list = QMDataFactory.getInstance()
                .getCurrentWeeks(QMDataFactory.createMockQMItemList());*/ //MOCKED CONTENT

        list.observe(this, qmItems -> {
            List<QMCategory> categoryList = QMDataFactory.getInstance().getCurrentWeeks(list.getValue());

            showQmList(categoryList);
        });
    }

    @Override
    public void editQm(QMItem qmToEdit) {

    }

    @Override
    public void deleteQm(QMItem qmToDelete) {

    }

    @Override
    public void showAddQmDialog() {

    }
}
