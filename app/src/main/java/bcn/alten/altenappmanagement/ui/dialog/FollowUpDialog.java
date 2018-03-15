package bcn.alten.altenappmanagement.ui.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import bcn.alten.altenappmanagement.AltenApplication;
import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.model.FollowUp;
import bcn.alten.altenappmanagement.mvp.presenter.FollowUpFragmentPresenter;
import bcn.alten.altenappmanagement.ui.adapter.AutoCompleteViewAdapter;
import bcn.alten.altenappmanagement.ui.customview.ExtendedEditTextWithAutoComplete;
import bcn.alten.altenappmanagement.utils.JodaTimeConverter;
import bcn.alten.altenappmanagement.utils.controller.FollowUpErrorController;

import static android.app.DatePickerDialog.OnDateSetListener;
import static android.view.View.OnClickListener;

public class FollowUpDialog implements OnDateSetListener, OnClickListener,
        RadioGroup.OnCheckedChangeListener {

    public static final String ADD_FOLLOWUP_ACTION = "ADD_FOLLOWUP_ACTION";
    public static final String EDIT_FOLLOWUP_ACTION = "EDIT_FOLLOWUP_ACTION";

    private final int STATUS_SCHEDULED = 0;
    private final int STATUS_DONE = 1;
    private final int STATUS_CANCELLED = 2;

    public static final String NO_DATE = AltenApplication.getInstance()
            .getString(R.string.follow_up_dialog_no_date);

    private Context context;
    private View dialogView;
    private View dateViewClicked;
    private String actionMode;
    private FollowUp originalFollowUp;
    private FollowUp editedFollowUp;
    private String nextDateChosenStatus = "";

    private Resources res;

    private FollowUpFragmentPresenter followUpFragmentPresenter;

    // Recommended for Edit current FollowUp selected
    public FollowUpDialog(Context context, String actionMode, FollowUp followUpToEdit,
                          FollowUpFragmentPresenter presenter) {
        this.context = context;
        this.followUpFragmentPresenter = presenter;
        this.originalFollowUp = followUpToEdit;
        this.actionMode = actionMode;
        this.res = context.getResources();
    }

    // Recommended for Create New FollowUp
    public FollowUpDialog(Context context, String actionMode,
                          FollowUpFragmentPresenter presenter) {
        this.context = context;
        this.followUpFragmentPresenter = presenter;
        this.actionMode = actionMode;
        this.res = context.getResources();
    }

    @SuppressLint("InflateParams")
    public AlertDialog getDialog() {
        LayoutInflater inflater = LayoutInflater.from(context);
        dialogView = inflater.inflate(R.layout.dialog_followup_new_edit, null);
        final ExtendedEditTextWithAutoComplete consultantNameExtEditText = dialogView.
                findViewById(R.id.extended_edittext_consultor_name);
        final ExtendedEditTextWithAutoComplete clientNameExtEditText = dialogView.
                findViewById(R.id.extended_edittext_client_name);
        final CheckBox addNextFollowCheckbox = dialogView.findViewById(R.id.fup_dialog_checkbox_add_next_follow);
        final LinearLayout addNextFollowContainer = dialogView.findViewById(R.id.fup_dialog_container_next_follow);
        final TextView dateText = dialogView.findViewById(R.id.fup_dialog_date_edit);
        final TextView addNextFollowTextView = dialogView.findViewById(R.id.fup_dialog_next_date_edit);
        final RadioGroup statusGroup = dialogView.findViewById(R.id.fup_dialog_radio_group_status);

        AutoCompleteViewAdapter autoCompleteViewAdapter = new AutoCompleteViewAdapter(context, null );
        consultorNameExtEditText.setAdapter(autoCompleteViewAdapter);
        clientNameExtEditText.setAdapter(autoCompleteViewAdapter);
        statusGroup.setOnCheckedChangeListener(this);

        String formattedDate;

        if (EDIT_FOLLOWUP_ACTION.equals(actionMode)) {
            consultantNameExtEditText.setText(originalFollowUp.getConsultantName());
            clientNameExtEditText.setText(originalFollowUp.getCurrentClient());
            clientNameExtEditText.requestFocus();
            consultantNameExtEditText.requestFocus();

            if (originalFollowUp.getDateLastFollow() != null
                    && originalFollowUp.getDateLastFollow().length() > 0) {
                 formattedDate = JodaTimeConverter.getInstance()
                        .getDateInStringFormat(originalFollowUp.getDateLastFollow());
                 dateText.setText(formattedDate);
            }

            if (originalFollowUp.getDateNextFollow() != null
                    && originalFollowUp.getDateNextFollow().length() > 0) {
                addNextFollowCheckbox.setChecked(true);
                addNextFollowContainer.setVisibility(View.VISIBLE);
                formattedDate = JodaTimeConverter.getInstance()
                        .getDateInStringFormat(originalFollowUp.getDateNextFollow());
                addNextFollowTextView.setText(formattedDate);
            }

            if (res.getString(R.string.follow_up_dialog_radio_group_scheduled_value)
                    .equalsIgnoreCase(originalFollowUp.getStatus())) {
                statusGroup.check(R.id.fup_dialog_radio_scheduled);
            } else if (res.getString(R.string.follow_up_dialog_radio_group_done_value)
                    .equalsIgnoreCase(originalFollowUp.getStatus())) {
                statusGroup.check(R.id.fup_dialog_radio_done);
            } else if (res.getString(R.string.follow_up_dialog_radio_group_cancelled_value)
                    .equalsIgnoreCase(originalFollowUp.getStatus())) {
                statusGroup.check(R.id.fup_dialog_radio_cancelled);
            }
        }

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setPositiveButton(R.string.follow_up_dialog_positive_button, null)
                .setNegativeButton(R.string.follow_up_dialog_negative_button, (dialog, which) ->
                        dialog.dismiss());

        alertDialogBuilder.setView(dialogView);

        dateText.setOnClickListener(this);
        addNextFollowTextView.setOnClickListener(this);

        addNextFollowCheckbox.setOnClickListener(v -> {
            if (addNextFollowCheckbox.isChecked()) {
                addNextFollowContainer.setVisibility(View.VISIBLE);
            } else {
                addNextFollowContainer.setVisibility(View.INVISIBLE);
            }
        });

        final AlertDialog followUpDialog = alertDialogBuilder.create();
        followUpDialog.setOnShowListener(dialog -> {
            Button positiveButton = followUpDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(v -> {

                FollowUpErrorController errorController = new FollowUpErrorController(context);

                if (!errorController.isAnyFieldEmpty(dialogView)
                        && !errorController.isAnyErrorOnDateWithStates(dialogView)) {

                    String[] statusList = context.getResources().getStringArray(R.array.follow_up_status_values);
                    String formattedLastDate = JodaTimeConverter.getInstance()
                            .parseDateFromStringPatternToMillis(dateText.getText().toString());

                    String formattedNextDate = JodaTimeConverter.getInstance()
                            .parseDateFromStringPatternToMillis(addNextFollowTextView.getText().toString());

                    if (nextDateChosenStatus.equalsIgnoreCase(statusList[STATUS_DONE])) {
                        formattedLastDate = formattedNextDate;
                        formattedNextDate = "";
                    }

                    //TODO wrong implementation, check for foreign kesy clientId & consultantId
                    editedFollowUp = new FollowUp(originalFollowUp.getConsultantId(),
                            consultantNameExtEditText.getText().toString(),
                            originalFollowUp.getClientId(),
                            clientNameExtEditText.getText().toString(), formattedLastDate,
                            formattedNextDate, nextDateChosenStatus);

                    if (ADD_FOLLOWUP_ACTION.equals(actionMode)) {
                        followUpFragmentPresenter.createNewFollowUp(editedFollowUp);
                    } else if (EDIT_FOLLOWUP_ACTION.equals(actionMode)) {
                        editedFollowUp.setId(originalFollowUp.getId());
                        followUpFragmentPresenter.editFollowUp(editedFollowUp);
                    }

                    followUpDialog.dismiss();
                }
            });
        });

        return followUpDialog;
    }

    private void launchDatePickerDialog() {
        final AltenDatePickerDialog datePickerDialog= new AltenDatePickerDialog(context, this);
        datePickerDialog.showDatePicker();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        final String dateInmMillies = JodaTimeConverter.getInstance()
                .parseFromDatePicker(month,dayOfMonth, year);

        final String finalDateTime = JodaTimeConverter.getInstance()
                .getDateInStringFormat(dateInmMillies);

        FollowUpErrorController errorController = new FollowUpErrorController(context);

        TextView errorMessage = dialogView.findViewById(R.id.fup_dialog_error_message);
        boolean errorMessageIsShown = (errorMessage != null && errorMessage.getText().length() > 0);

        if (dateViewClicked.getId() == R.id.fup_dialog_next_date_edit && errorMessageIsShown) {
            errorMessage.setText("");
        }

        errorController.checkforFollowUpDates(dateViewClicked, dateInmMillies, finalDateTime);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fup_dialog_date_edit :
                dateViewClicked = dialogView.findViewById(R.id.fup_dialog_date_edit);
                launchDatePickerDialog();
                break;
            case R.id.fup_dialog_next_date_edit:
                dateViewClicked = dialogView.findViewById((R.id.fup_dialog_next_date_edit));
                launchDatePickerDialog();
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        String[] statusList = context.getResources().getStringArray(R.array.follow_up_status_values);
        switch (checkedId) {
            case R.id.fup_dialog_radio_scheduled:
                nextDateChosenStatus = statusList[STATUS_SCHEDULED];
                break;
            case R.id.fup_dialog_radio_done:
                nextDateChosenStatus = statusList[STATUS_DONE];
                break;
            case R.id.fup_dialog_radio_cancelled:
                nextDateChosenStatus = statusList[STATUS_CANCELLED];
                break;
            default:
                nextDateChosenStatus = "";
                break;
        }
    }
}