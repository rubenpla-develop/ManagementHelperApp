package bcn.alten.altenappmanagement.ui.fragment;

import android.app.DatePickerDialog;
import android.arch.lifecycle.LiveData;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import java.util.List;

import bcn.alten.altenappmanagement.QmCreateEditActivity;
import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.adapter.ExpandableQMListAdapter;
import bcn.alten.altenappmanagement.expandable.groupmodel.QMCategory;
import bcn.alten.altenappmanagement.mvp.model.QMItem;
import bcn.alten.altenappmanagement.mvp.presenter.QmFragmentPresenter;
import bcn.alten.altenappmanagement.mvp.view.IQmFragmentView;
import bcn.alten.altenappmanagement.ui.dialog.AltenDatePickerDialog;
import bcn.alten.altenappmanagement.ui.dialog.QMDeleteDialog;
import bcn.alten.altenappmanagement.utils.JodaTimeConverter;
import bcn.alten.altenappmanagement.utils.QMDataFactory;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.kobakei.materialfabspeeddial.FabSpeedDial;

public class QMFragment extends Fragment implements IQmFragmentView, DatePickerDialog.OnDateSetListener {

    private final String TAG = QMFragment.class.getSimpleName();
    public static final String ADD_QM_ACTION = "ADD_QM_ACTION";
    public static final String EDIT_QM_ACTION = "EDIT_QM_ACTION";

    public static final String QM_ITEM_PARAM = "QM_ITEM_PARAM";
    public static final String QM_ACTIONMODE_PARAM = "QM_ACTIONMODE_PARAM";

    @BindView(R.id.qm_recyclerView)
    RecyclerView expandableRecyclerView;

    @BindView(R.id.qm_fab)
    FabSpeedDial qmFabSpeedDialButton;

    @BindView(R.id.qm_header_container)
    LinearLayout headerContainer;

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

        qmFabSpeedDialButton.addOnMenuItemClickListener((miniFab, label, itemId) -> {

            switch (itemId) {
                case R.id.go_to_week_dial :
                    AltenDatePickerDialog datePickerDialog = new AltenDatePickerDialog(getActivity(),
                            this);
                    datePickerDialog.showDatePicker();
                    break;
                case R.id.add_qm_dial :
                    launchQmActivityForResult(ADD_QM_ACTION, null);
                    break;

                default:
                    break;
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == QmCreateEditActivity.QM_ACTIVITY_LAUNCH) {
            switch (resultCode) {
                case QmCreateEditActivity.RESULT_ADD_OK :
                    if (data.getExtras().containsKey(QM_ITEM_PARAM)) {
                        QMItem qmItem = data.getParcelableExtra(QM_ITEM_PARAM);
                        presenter.createNewQm(qmItem);
                    }
                    break;
                case QmCreateEditActivity.RESULT_EDIT_OK:
                    if (data.getExtras().containsKey(QM_ITEM_PARAM)) {
                        QMItem qmItem = data.getParcelableExtra(QM_ITEM_PARAM);
                        presenter.editQm(qmItem);
                    }
                    break;
                default:
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void launchQmActivityForResult(String actionMode, @Nullable QMItem qmToEdit) {
        if (ADD_QM_ACTION.equals(actionMode)) {
            Intent i = new Intent(getActivity(), QmCreateEditActivity.class);
            i.putExtra(QM_ACTIONMODE_PARAM, ADD_QM_ACTION);
            startActivityForResult(i, QmCreateEditActivity.QM_ACTIVITY_LAUNCH);

        } else if (EDIT_QM_ACTION.equals(actionMode)) {
            Intent i = new Intent(getActivity(), QmCreateEditActivity.class);
            i.putExtra(QM_ACTIONMODE_PARAM, EDIT_QM_ACTION);
            i.putExtra(QM_ITEM_PARAM, qmToEdit);
            startActivityForResult(i, QmCreateEditActivity.QM_ACTIVITY_LAUNCH);
        }

    }

    @Override
    public void showQmList(List<QMCategory> list) {
        expandableRecyclerViewAdapter = new ExpandableQMListAdapter(list, getActivity(), this);
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
    public void onLiveDataGoToWeek(LiveData<List<QMItem>> list, String date) {
        list.observe(this, qmItems -> {
            List<QMCategory> categoryList = QMDataFactory.getInstance()
                    .getSelectedWeek(list.getValue(), date);

            showQmList(categoryList);
        });
    }

    @Override
    public void editQm(QMItem qmToEdit) {
        launchQmActivityForResult(EDIT_QM_ACTION, qmToEdit);
    }

    @Override
    public void deleteQm(QMItem qmToDelete) {
        QMDeleteDialog qmDeleteDialog = new QMDeleteDialog(getActivity(),  qmToDelete, presenter);
        AlertDialog alertDialog = qmDeleteDialog.getDialog();
        alertDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        final String dateInmMillis = JodaTimeConverter.getInstance()
                .parseFromDatePicker(month,dayOfMonth, year);
        final String date = JodaTimeConverter.getInstance().getDateInStringFormat(dateInmMillis);

        presenter.goToWeek(date);
    }
}