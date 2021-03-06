package bcn.alten.altenappmanagement.ui.fragment;

import android.app.DatePickerDialog;
import android.arch.lifecycle.LiveData;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.util.List;

import bcn.alten.altenappmanagement.ui.activity.QmCreateEditActivity;
import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.ui.adapter.ExpandableQMListAdapter;
import bcn.alten.altenappmanagement.ui.adapter.expandable.groupmodel.QMCategory;
import bcn.alten.altenappmanagement.model.QMItem;
import bcn.alten.altenappmanagement.mvp.presenter.QmFragmentPresenter;
import bcn.alten.altenappmanagement.mvp.view.IQmFragmentView;
import bcn.alten.altenappmanagement.model.pojo.WeekRange;
import bcn.alten.altenappmanagement.ui.customview.QMFilterGroup;
import bcn.alten.altenappmanagement.ui.customview.QmHeaderPanel;
import bcn.alten.altenappmanagement.ui.dialog.AltenDatePickerDialog;
import bcn.alten.altenappmanagement.ui.dialog.QMDeleteDialog;
import bcn.alten.altenappmanagement.utils.JodaTimeConverter;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.kobakei.materialfabspeeddial.FabSpeedDial;

import static bcn.alten.altenappmanagement.utils.factory.QMDataFactory.FactoryInstance;
import static bcn.alten.altenappmanagement.utils.factory.QMDataFactory.QM_HEADER_ARROW_NEXT_WEEK_ACTION;
import static bcn.alten.altenappmanagement.utils.factory.QMDataFactory.QM_HEADER_ARROW_PREVIOUS_WEEK_ACTION;

public class QMFragment extends Fragment implements IQmFragmentView, DatePickerDialog.OnDateSetListener,
        QmHeaderPanel.OnQmHeaderPanelClickListener, QMFilterGroup.OnCheckedChangeListener{

    private final String TAG = QMFragment.class.getSimpleName();

    public static final String ADD_QM_ACTION = "ADD_QM_ACTION";
    public static final String EDIT_QM_ACTION = "EDIT_QM_ACTION";

    public static final String QM_ITEM_PARAM = "QM_ITEM_PARAM";
    public static final String QM_ACTIONMODE_PARAM = "QM_ACTIONMODE_PARAM";
    
    public static final int SCHEDULED_FILTER_OPTION = 0;
    public static final int DONE_FILTER_OPTION = 1;
    public static final int ACCEPTED_FILTER_OPTION = 2;
    public static final int CANCELLED_FILTER_OPTION = 3;
    public static final int CLEAR_FILTER_OPTION = 4;

    @BindView(R.id.qm_recyclerView)
    RecyclerView expandableRecyclerView;

    @BindView(R.id.qm_fab)
    FabSpeedDial qmFabSpeedDialButton;

    @BindView(R.id.qm_header_panel)
    QmHeaderPanel  qmHeaderPanel;

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
        qmHeaderPanel.setOnQMHeaderPanelListener(this);

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
        //TODO default adapter creation/update content
        expandableRecyclerViewAdapter = new ExpandableQMListAdapter(list, getActivity(), this);
        expandableRecyclerView.setAdapter(expandableRecyclerViewAdapter);
        
        //  TODO adapter init & update content separated (UNSTABLE)
        /*if (expandableRecyclerViewAdapter == null) {
            expandableRecyclerViewAdapter = new ExpandableQMListAdapter(list, getActivity(), this);
            expandableRecyclerView.setAdapter(expandableRecyclerViewAdapter);
        } else {
            expandableRecyclerViewAdapter.updateAdapter(list);
        }*/
    }

    @Override
    public void onLiveDataChanged(LiveData<List<QMItem>> list) {
        list.observe(this, qmItems -> {
            List<QMCategory> categoryList = FactoryInstance().getCurrentWeeks(list.getValue());
            presenter.saveBackupList(qmItems);

            showQmList(categoryList);
        });
    }

    @Override
    public void onLiveDataGoToWeek(LiveData<List<QMItem>> list, WeekRange weekRange) {
        list.observe(this, (List<QMItem> qmItems) -> {
            List<QMCategory> categoryList = FactoryInstance()
                    .getSelectedWeek(list.getValue(), weekRange);

            presenter.saveBackupList(qmItems);

            if (qmHeaderPanel.getPreviousFilterOption() == CLEAR_FILTER_OPTION) {
                showQmList(categoryList);
            } else {
                presenter.filterByStatus(qmHeaderPanel.getStatusOptions(),
                        qmHeaderPanel.getPreviousFilterOption());
            }
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
    public void onClickArrowUp() {
        presenter.showQmListWithActionParam(QM_HEADER_ARROW_NEXT_WEEK_ACTION);
    }

    @Override
    public void onClickArrowDown() {
        presenter.showQmListWithActionParam(QM_HEADER_ARROW_PREVIOUS_WEEK_ACTION);
    }

    @Override
    public void onScheduledButton() {
        int filterOption = qmHeaderPanel.getFilterOptions().get(SCHEDULED_FILTER_OPTION);
        filterByStatusBy(filterOption);
    }

    @Override
    public void onDoneButton() {
        int filterOption = qmHeaderPanel.getFilterOptions().get(DONE_FILTER_OPTION);
        filterByStatusBy(filterOption);
    }

    @Override
    public void onAcceptedButton() {
        int filterOption = qmHeaderPanel.getFilterOptions().get(ACCEPTED_FILTER_OPTION);
        filterByStatusBy(filterOption);
    }

    @Override
    public void onCancelledButton() {
        int filterOption = qmHeaderPanel.getFilterOptions().get(CANCELLED_FILTER_OPTION);
        filterByStatusBy(filterOption);
    }

    @Override
    public void onClearFilter() {
        int filterOption = qmHeaderPanel.getFilterOptions().get(CLEAR_FILTER_OPTION);
        filterByStatusBy(filterOption);
    }

    private void filterByStatusBy(int filterOption) {
        qmHeaderPanel.setPreviousFilterOption(filterOption);
        presenter.filterByStatus(qmHeaderPanel.getStatusOptions(), filterOption);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        final String dateInmMillis = JodaTimeConverter.getInstance()
                .parseFromDatePicker(month,dayOfMonth, year);
        final String date = JodaTimeConverter.getInstance().getDateInStringFormat(dateInmMillis);

        presenter.goToWeek(date);
    }

    @Override
    public void onCheckedChanged(View radioGroup, View radioButton, boolean isChecked, int checkedId) {
        Log.i(TAG, "RadioGroup: " + radioGroup + "\nRadioButton : " + radioButton
                + "\n isChecked : " + isChecked + "\nchekedId : "
                + getResources().getResourceTypeName(checkedId));
    }
}