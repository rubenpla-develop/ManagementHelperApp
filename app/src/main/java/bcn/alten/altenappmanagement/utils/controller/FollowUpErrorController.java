package bcn.alten.altenappmanagement.utils.controller;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.mvp.view.IMainActivityView;
import bcn.alten.altenappmanagement.utils.JodaTimeConverter;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class FollowUpErrorController {

    private final String TAG = FollowUpErrorController.class.getSimpleName();

    private Context context;
    private Resources res;

    public FollowUpErrorController(Context context) {
        this.context = context;
        res = context.getResources();
    }

    public boolean isAnyFieldEmpty(View dialogView) {
        boolean isAnyError = false;
        
        final TextFieldBoxes consultorNameBox = dialogView.findViewById(R.id.textfieldbox_consultor_name);
        final TextFieldBoxes clientNameBox = dialogView.findViewById(R.id.textfieldbox_client_name);
        final ExtendedEditText consultorNameExtEditText = dialogView.findViewById(R.id.extended_edittext_consultor_name);
        final ExtendedEditText clientNameExtEditText = dialogView.findViewById(R.id.extended_edittext_client_name);
        
        if (consultorNameExtEditText.getText().toString().isEmpty()) {
            consultorNameBox.setError(context.getString(R.string.follow_up_error_controller_empty_field),
                    false);
            consultorNameBox.setErrorColor(R.color.background_soft_orange);
            isAnyError = true;
        }

        if (clientNameExtEditText.getText().toString().isEmpty()) {
            clientNameBox.setError(context.getString(R.string.follow_up_error_controller_empty_field),
                    false);
            clientNameBox.setErrorColor(R.color.background_soft_orange);
            isAnyError = true;
        }
        
        return isAnyError;
    }

    public boolean isAnyErrorOnDateWithStates(View dialogView) {
        boolean isAnyError = false;

        TextView nextDateTextView = dialogView.findViewById(R.id.fup_dialog_next_date_edit);
        RadioGroup stateRadioGroup = dialogView.findViewById(R.id.fup_dialog_radio_group_status);
        TextView errorMessageTextView = dialogView.findViewById(R.id.fup_dialog_error_message);

        int checkedStatus = stateRadioGroup.getCheckedRadioButtonId();
        final String date = nextDateTextView.getText().toString();
        final Boolean isValidDate = JodaTimeConverter.getInstance().isAValidDate(date);

        if (!isStatusSelectionOk(checkedStatus, isValidDate, date)) {
            isAnyError = true;
            errorMessageTextView.setText(context.getResources()
                    .getString(R.string.follow_up_error_controller_state_with_no_date));

            return isAnyError;
        }

        return isAnyError;
    }

    public boolean isStatusSelectionOk(int checkedStatus, boolean isValidDate, String date) {
        boolean isStatusOk = true;
        final int NO_STATUS_SELECTED = -1;

        final boolean isAnyStatusSelected = (checkedStatus != NO_STATUS_SELECTED);
        if (isValidDate) {
            if (isAnyStatusSelected) {
                switch (checkedStatus) {
                    case R.id.fup_dialog_radio_scheduled:

                        break;
                    case R.id.fup_dialog_radio_done:
                        String dateInMillis = JodaTimeConverter.getInstance()
                                .parseDateFromStringPatternToMillis(date);
                        int comparedDates = JodaTimeConverter.getInstance().compareDates(dateInMillis);

                        if (comparedDates == JodaTimeConverter.NEWER_DATE) {
                            isStatusOk = false;
                        }
                        break;
                    case R.id.fup_dialog_radio_cancelled:

                        break;
                    default:
                        break;
                }
            }
        }

        if(!isValidDate && isAnyStatusSelected) {
            isStatusOk = false;
        }

        return isStatusOk;
    }

    public void checkforFollowUpDates(View dateViewClicked, String dateInMillis,
                                      String finalDateTime) {
        int comparedDates = JodaTimeConverter.getInstance().compareDates(dateInMillis);

        //We compare date set in DatePicker dialog versus current date today, every date view
        // (last followUp, next FollowUp) is comprared to check if its previous or newer date,
        // for last follow, date must be previous than current date; For next followUp, exactly
        // inverse, date MUST be newer than current date.
        switch (comparedDates) {
            case JodaTimeConverter.CURRENT_DATE:
                if (dateViewClicked.getId() == R.id.fup_dialog_next_date_edit) {
                    ((TextView) dateViewClicked).setText(finalDateTime);
                } else {
                    ((IMainActivityView) context).showMessage(res.getString(R.string.follow_up_error_controller_newer_date));
                }

                break;

            case JodaTimeConverter.NEWER_DATE :
                if (dateViewClicked.getId() == R.id.fup_dialog_date_edit) {
                    ((IMainActivityView) context).showMessage(res.getString(R.string.follow_up_error_controller_newer_date));
                } else {
                    ((TextView) dateViewClicked).setText(finalDateTime);
                }

                break;

            case JodaTimeConverter.PREVIOUS_DATE:
                if (dateViewClicked.getId() == R.id.fup_dialog_next_date_edit) {
                    ((IMainActivityView) context).showMessage(res.getString(R.string.follow_up_error_controller_previous_date));
                } else {
                    ((TextView) dateViewClicked).setText(finalDateTime);
                }
                break;

            default:
                break;
        }
    }
}
