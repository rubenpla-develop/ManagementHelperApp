package bcn.alten.altenappmanagement.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.mvp.model.FollowUp;
import bcn.alten.altenappmanagement.mvp.presenter.FollowUpFragmentPresenter;
import bcn.alten.altenappmanagement.mvp.view.IMainActivityView;
import bcn.alten.altenappmanagement.utils.FollowUpErrorController;
import bcn.alten.altenappmanagement.utils.JodaTimeConverter;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

import static android.app.DatePickerDialog.OnDateSetListener;
import static android.view.View.OnClickListener;

public class FollowUpDialog implements OnDateSetListener, OnClickListener {

    public static final String ADD_FOLLOWUP_ACTION = "ADD_FOLLOWUP_ACTION";
    public static final String EDIT_FOLLOWUP_ACTION = "EDIT_FOLLOWUP_ACTION";

    public static final String NO_DATE = "NO_DATE";

    private Context context;
    private View dialogView;
    private View dateViewClicked;
    private String actionMode;
    private FollowUp followUp;
    private FollowUp editedFollowUp;

    private FollowUpFragmentPresenter followUpFragmentPresenter;

    public FollowUpDialog(Context context) {
        this.context = context;
    }

    public FollowUpDialog(Context context, FollowUpFragmentPresenter presenter) {
        this.context = context;
        this.followUpFragmentPresenter = presenter;
    }

    public FollowUpDialog(Context context, String actionMode, FollowUp followUpToEdit,
                          FollowUpFragmentPresenter presenter) {
        this.context = context;
        this.followUpFragmentPresenter = presenter;
        this.followUp = followUpToEdit;
        this.actionMode = actionMode;
    }

    public FollowUpDialog(Context context, String actionMode,
                          FollowUpFragmentPresenter presenter) {
        this.context = context;
        this.followUpFragmentPresenter = presenter;
        this.actionMode = actionMode;
    }

    public AlertDialog getDialog() {
        LayoutInflater inflater = LayoutInflater.from(context);
        dialogView = inflater.inflate(R.layout.dialog_followup_new_edit, null);
        final TextFieldBoxes consultorNameBox = dialogView.findViewById(R.id.textfieldbox_consultor_name);
        final TextFieldBoxes clientNameBox = dialogView.findViewById(R.id.textfieldbox_client_name);
        final ExtendedEditText consultorNameExtEditText = dialogView.findViewById(R.id.extended_edittext_consultor_name);
        final ExtendedEditText clientNameExtEditText = dialogView.findViewById(R.id.extended_edittext_client_name);
        final CheckBox addNextFollowCheckbox = dialogView.findViewById(R.id.fup_dialog_checkbox_add_next_follow);
        final LinearLayout addNextFollowContainer = dialogView.findViewById(R.id.fup_dialog_container_next_follow);
        final TextView dateText = dialogView.findViewById(R.id.fup_dialog_date_edit);
        final TextView addNextFollowTextView = dialogView.findViewById(R.id.fup_dialog_next_date_edit);

        String formattedDate;

        if (EDIT_FOLLOWUP_ACTION.equals(actionMode)) {
            consultorNameExtEditText.setText(followUp.getConsultorName());
            clientNameExtEditText.setText(followUp.getCurrentClient());
            clientNameExtEditText.requestFocus();
            consultorNameExtEditText.requestFocus();

            if (followUp.getDateLastFollow() != null
                    && followUp.getDateLastFollow().length() > 0) {
                 formattedDate = JodaTimeConverter.getInstance()
                        .getDateInStringFormat(followUp.getDateLastFollow());
                 dateText.setText(formattedDate);
            }

            if (followUp.getDateNextFollow() != null
                    && followUp.getDateNextFollow().length() > 0) {
                addNextFollowCheckbox.setChecked(true);
                addNextFollowContainer.setVisibility(View.VISIBLE);
                formattedDate = JodaTimeConverter.getInstance()
                        .getDateInStringFormat(followUp.getDateNextFollow());
                addNextFollowTextView.setText(formattedDate);
            }
        }

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setPositiveButton(R.string.follow_up_dialog_positive_button,
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String formattedLastDate = JodaTimeConverter.getInstance()
                .parseDateFromStringPatternToMillis(dateText.getText().toString());

                String formattedNextDate = JodaTimeConverter.getInstance()
                        .parseDateFromStringPatternToMillis(addNextFollowTextView.getText().toString());

                editedFollowUp = new FollowUp(consultorNameExtEditText.getText().toString(),
                        clientNameExtEditText.getText().toString(), formattedLastDate,
                        formattedNextDate);

                if (ADD_FOLLOWUP_ACTION.equals(actionMode)) {
                    followUpFragmentPresenter.createNewFollowUp(editedFollowUp);
                } else if (EDIT_FOLLOWUP_ACTION.equals(actionMode)) {
                    editedFollowUp.setId(followUp.getId());
                    followUpFragmentPresenter.editFollowUp(editedFollowUp);
                }

                ((IMainActivityView) context).showMessage("FollowUp Dialog OK!");
                dialog.dismiss();
            }
        }).setNegativeButton(R.string.follow_up_dialog_negative_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialogBuilder.setView(dialogView);

        dateText.setOnClickListener(this);
        addNextFollowTextView.setOnClickListener(this);

        addNextFollowCheckbox.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addNextFollowCheckbox.isChecked()) {
                    addNextFollowContainer.setVisibility(View.VISIBLE);
                } else {
                    addNextFollowContainer.setVisibility(View.INVISIBLE);
                }
            }
        });

        AlertDialog dialog = alertDialogBuilder.create();

        return dialog;
    }

    private void launchDatePickerDialog() {
        final FollowUpDatePickerDialog datePickerDialog= new FollowUpDatePickerDialog(context, this);
        datePickerDialog.showDatePicker();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        final String dateInmMillies = JodaTimeConverter.getInstance()
                .parsefromDatePicker(month,dayOfMonth, year);

        final String finalDateTime = JodaTimeConverter.getInstance()
                .getDateInStringFormat(dateInmMillies);

        FollowUpErrorController errorController = new FollowUpErrorController(context,
                followUpFragmentPresenter);

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
}