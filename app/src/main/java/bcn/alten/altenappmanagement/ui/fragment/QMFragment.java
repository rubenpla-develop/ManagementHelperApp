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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
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
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.kobakei.materialfabspeeddial.FabSpeedDial;

import static android.view.View.OnClickListener;
import static bcn.alten.altenappmanagement.utils.QMDataFactory.FactoryInstance;
import static bcn.alten.altenappmanagement.utils.QMDataFactory.QM_HEADER_ARROW_NEXT_WEEK_ACTION;
import static bcn.alten.altenappmanagement.utils.QMDataFactory.QM_HEADER_ARROW_PREVIOUS_WEEK_ACTION;

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

    @BindView(R.id.qm_header_arrow_up)
    ImageButton qmHeaderArrowUp;

    @BindView(R.id.qm_header_arrow_down)
    ImageButton qmHeaderArrowDown;

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

        OnClickListener qmHeaderArrowsListener = v -> {
          switch (v.getId()) {
              case R.id.qm_header_arrow_up:
                  presenter.showQmListWithActionParam(QM_HEADER_ARROW_NEXT_WEEK_ACTION);
                  break;
              case R.id.qm_header_arrow_down:
                  presenter.showQmListWithActionParam(QM_HEADER_ARROW_PREVIOUS_WEEK_ACTION);
                  break;
              default :
                  break;
          }
        };

        qmHeaderArrowUp.setOnClickListener(qmHeaderArrowsListener);
        qmHeaderArrowDown.setOnClickListener(qmHeaderArrowsListener);

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
    }

    @Override
    public void onLiveDataChanged(LiveData<List<QMItem>> list) {
        /*List<QMCategory> list = QMDataFactory.FactoryInstance()
                .getCurrentWeeks(QMDataFactory.createMockQMItemList());*/ //MOCKED CONTENT

        list.observe(this, qmItems -> {
            List<QMCategory> categoryList = FactoryInstance().getCurrentWeeks(list.getValue());

            showQmList(categoryList);
        });
    }

    @Override
    public void onLiveDataGoToWeek(LiveData<List<QMItem>> list, int week, int year) {
        list.observe(this, qmItems -> {
            List<QMCategory> categoryList = FactoryInstance()
                    .getSelectedWeek(list.getValue(), week);

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