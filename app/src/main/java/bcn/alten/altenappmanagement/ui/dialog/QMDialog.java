package bcn.alten.altenappmanagement.ui.dialog;


import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.mvp.model.QMItem;
import bcn.alten.altenappmanagement.mvp.presenter.QmFragmentPresenter;
import bcn.alten.altenappmanagement.utils.JodaTimeConverter;
import bcn.alten.altenappmanagement.utils.QmErrorController;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;

import static android.app.DatePickerDialog.OnDateSetListener;
import static android.app.TimePickerDialog.OnTimeSetListener;
import static android.widget.RadioGroup.OnCheckedChangeListener;

public class QMDialog implements OnDateSetListener, OnTimeSetListener, OnCheckedChangeListener {

    public static final String ADD_QM_ACTION = "ADD_QM_ACTION";
    public static final String EDIT_QM_ACTION = "EDIT_QM_ACTION";

    private final int STATUS_SCHEDULED = 0;
    private final int STATUS_DONE = 1;
    private final int STATUS_ACCEPTED = 2;
    private final int STATUS_CANCELLED = 3;

    private Resources res;

    private Context context;
    private View dialogView;
    private QMItem qm;
    private String actionMode;
    private int weekOfYear;
    private String chosenStatus;

    private QmFragmentPresenter qmFragmentPresenter;

    public QMDialog(Context context, String actionMode, QMItem qmToEdit,
                          QmFragmentPresenter presenter) {
        this.context = context;
        this.qmFragmentPresenter = presenter;
        this.qm = qmToEdit;
        this.actionMode = actionMode;
        this.res = context.getResources();
    }

    public QMDialog(Context context, String actionMode,
                          QmFragmentPresenter presenter) {
        this.context = context;
        this.qmFragmentPresenter = presenter;
        this.actionMode = actionMode;
        this.res = context.getResources();
    }

    public AlertDialog getDialog() {
        LayoutInflater inflater = LayoutInflater.from(context);
        dialogView = inflater.inflate(R.layout.dialog_qm_new_edit, null);

        final ExtendedEditText clientNameExtEditText = dialogView.findViewById(R.id.qm_extended_edittext_client_name);
        final TextView clientPhoneEditText = dialogView.findViewById(R.id.qm_dialog_client_phone_edit);
        final ExtendedEditText candidateNameExtEditText = dialogView.findViewById(R.id.qm_extended_edittext_consultor_name);
        final TextView candidatePhoneEditText = dialogView.findViewById(R.id.qm_dialog_candidate_phone_edit);
        final TextView dateText = dialogView.findViewById(R.id.qm_dialog_date_edit);
        final TextView timeText = dialogView.findViewById(R.id.qm_dialog_time_edit);
        final RadioGroup statusGroup = dialogView.findViewById(R.id.qm_dialog_radio_group_status);
        
        if (EDIT_QM_ACTION.equals(actionMode)) {
            clientNameExtEditText.setText(qm.getClientName());
            clientPhoneEditText.setText(qm.getClientPhone());
            candidateNameExtEditText.setText(qm.getCandidateName());
            candidatePhoneEditText.setText(qm.getCandidatePhone());
            dateText.setText(JodaTimeConverter.getInstance().getDateInStringFormat(qm.getDate()));
            timeText.setText(qm.getTime());

            clientNameExtEditText.requestFocus();
            candidateNameExtEditText.requestFocus();

            if (res.getString(R.string.qm_dialog_radio_group_scheduled_value)
                    .equalsIgnoreCase(qm.getStatus())) {
                statusGroup.check(R.id.qm_dialog_radio_scheduled);
            } else if (res.getString(R.string.qm_dialog_radio_group_done_value)
                    .equalsIgnoreCase(qm.getStatus())) {
                statusGroup.check(R.id.qm_dialog_radio_done);
            } else if (res.getString(R.string.qm_dialog_radio_group_accepted_value)
                    .equalsIgnoreCase(qm.getStatus())) {
                statusGroup.check(R.id.qm_dialog_radio_accepted);
            } else if (res.getString(R.string.qm_dialog_radio_group_cancelled_value)
                    .equalsIgnoreCase(qm.getStatus())) {
                statusGroup.check(R.id.qm_dialog_radio_cancelled);
            }
        }

        statusGroup.setOnCheckedChangeListener(this);

        dateText.setOnClickListener(v -> {
            AltenDatePickerDialog datePickerDialog = new AltenDatePickerDialog(context, this);
            datePickerDialog.showDatePicker();
        });

        timeText.setOnClickListener(v -> {
            AltenTimePickerDialog timePickerDialog = new AltenTimePickerDialog(context, this);
            timePickerDialog.showTimePicker();
        });

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(dialogView);

        alertDialogBuilder.setPositiveButton(R.string.follow_up_dialog_positive_button, null)
                .setNegativeButton(R.string.follow_up_dialog_negative_button, (dialog, which) -> dialog.dismiss());

        AlertDialog qmDialog = alertDialogBuilder.create();

        qmDialog.setOnShowListener(dialog -> {
            Button positiveButton = qmDialog.getButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(v -> {

                //QmErrorController errorController = new QmErrorController(context);

                //if (!errorController.isAnyFieldEmpty(dialogView)
                  //      && !errorController.isAnyErrorOnDateWithStates(dialogView)) {

                    String formattedDate = JodaTimeConverter.getInstance()
                        .parseDateFromStringPatternToMillis(dateText.getText().toString());

                    weekOfYear = JodaTimeConverter.getInstance()
                            .getWeekOfYearFromDate(dateText.getText().toString());

                    QMItem editedQm = new QMItem(weekOfYear, clientNameExtEditText.getText().toString(),
                            clientPhoneEditText.getText().toString(),
                            candidateNameExtEditText.getText().toString(),
                            candidatePhoneEditText.getText().toString(),
                            formattedDate, timeText.getText().toString(),  chosenStatus);

                if (ADD_QM_ACTION.equals(actionMode)) {
                    qmFragmentPresenter.createNewQm(editedQm);
                } else if (EDIT_QM_ACTION.equals(actionMode)) {
                    editedQm.setId(qm.getId());
                    qmFragmentPresenter.editQm(editedQm);
                }

                    qmDialog.dismiss();
                //}
            });
        });

        return qmDialog;
    }
    
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        final String dateInmMillis = JodaTimeConverter.getInstance()
                .parseFromDatePicker(month,dayOfMonth, year);

        final String finalDateTime = JodaTimeConverter.getInstance()
                .getDateInStringFormat(dateInmMillis);
        
        weekOfYear = JodaTimeConverter.getInstance().getWeekOfYearFromDate(month, dayOfMonth, year);

        QmErrorController errorController = new QmErrorController(context);

        TextView dateView = dialogView.findViewById(R.id.qm_dialog_date_edit);

        errorController.checkForQmDate(dateView, dateInmMillis, finalDateTime);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String time = JodaTimeConverter.getInstance().parseFromTimePicker(hourOfDay, minute);

        TextView timeView = dialogView.findViewById(R.id.qm_dialog_time_edit);
        timeView.setText(time);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        String[] statusList = context.getResources().getStringArray(R.array.qm_status_values);
        switch (checkedId) {
            case R.id.qm_dialog_radio_scheduled:
                chosenStatus = statusList[STATUS_SCHEDULED];
                break;
            case R.id.qm_dialog_radio_done:
                chosenStatus = statusList[STATUS_DONE];
                break;
            case R.id.qm_dialog_radio_accepted:
                chosenStatus = statusList[STATUS_ACCEPTED];
                break;
            case R.id.qm_dialog_radio_cancelled:
                chosenStatus = statusList[STATUS_CANCELLED];
                break;
            default:
                chosenStatus = "";
                break;
        }
    }
}