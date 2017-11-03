package bcn.alten.altenappmanagement.ui.fragment.dialog;

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
import bcn.alten.altenappmanagement.utils.JodaTimeConverter;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;

import static android.app.DatePickerDialog.OnDateSetListener;
import static android.view.View.OnClickListener;

public class FollowUpDialog implements OnDateSetListener, OnClickListener {

    public static final String ADD_FOLLOWUP_ACTION = "ADD_FOLLOWUP_ACTION";
    public static final String EDIT_FOLLOWUP_ACTION = "EDIT_FOLLOWUP_ACTION";

    private Context context;
    private OnDateSetListener onDateSetListener = null;
    private OnClickListener onClickListener = null;
    private View dialogView;
    private View dateViewClicked;

    private FollowUpFragmentPresenter followUpFragmentPresenter;

    public FollowUpDialog(Context context) {
        this.context = context;
    }

    public FollowUpDialog(Context context, FollowUpFragmentPresenter presenter) {
        this.context = context;
        this.followUpFragmentPresenter = presenter;
    }

    public FollowUpDialog(Context context, OnDateSetListener onDateListener) {
        this.context = context;
        this.onDateSetListener = onDateListener;
    }

    public FollowUpDialog(Context context, OnDateSetListener onDateListener,
                          OnClickListener onClickListener) {
        this.context = context;
        this.onDateSetListener = onDateListener;
        this.onClickListener = onClickListener;
    }

    public AlertDialog getDialog() {
        LayoutInflater inflater = LayoutInflater.from(context);

        dialogView = inflater.inflate(R.layout.dialog_followup_new_edit, null);
        final ExtendedEditText consultorNameExtEditText = dialogView.findViewById(R.id.extended_edittext_consultor_name);
        final ExtendedEditText clientNameExtEditText = dialogView.findViewById(R.id.extended_edittext_client_name);
        final CheckBox addNextFollowCheckbox = dialogView.findViewById(R.id.fup_dialog_checkbox_add_next_follow);
        final LinearLayout addNextFollowContainer = dialogView.findViewById(R.id.fup_dialog_container_next_follow);
        final TextView dateText = dialogView.findViewById(R.id.fup_dialog_date_edit);
        final TextView addNextFollowTextView = dialogView.findViewById(R.id.fup_dialog_next_date_edit);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setPositiveButton(R.string.follow_up_dialog_positive_button,
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String formattedLastDate = JodaTimeConverter.getInstance()
                .parseDateFromStringPatternToMillis(dateText.getText().toString());

                String formattedNextDate = JodaTimeConverter.getInstance()
                        .parseDateFromStringPatternToMillis(addNextFollowTextView.getText().toString());

                FollowUp followUp = new FollowUp(consultorNameExtEditText.getText().toString(),
                        clientNameExtEditText.getText().toString(), formattedLastDate,
                        formattedNextDate);

                followUpFragmentPresenter.createNewFollowUp(followUp);
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
                .getDateInStringFormat(Long.valueOf(dateInmMillies));

        ((TextView) dateViewClicked).setText(finalDateTime);
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
                launchDatePickerDialog();
                break;
        }
    }
}



