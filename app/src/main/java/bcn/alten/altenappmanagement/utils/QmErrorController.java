package bcn.alten.altenappmanagement.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import bcn.alten.altenappmanagement.R;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;


public class QmErrorController {

    private final String TAG = FollowUpErrorController.class.getSimpleName();

    private Context context;
    private Resources res;
    private View activityView;

    public QmErrorController(Context context) {
        this.context = context;
        res = context.getResources();
    }

    public QmErrorController(Context context, @NonNull View activityView) {
        this.context = context;
        res = context.getResources();
        this.activityView = activityView;
    }

    public boolean isAnyFieldEmpty() {
        boolean isAnyError = false;

        final TextFieldBoxes candidateNameBox = activityView
                .findViewById(R.id.qm_activity_textfieldbox_candidate_name);
        final TextFieldBoxes clientNameBox = activityView
                .findViewById(R.id.qm_activity_textfieldbox_client_name);
        final ExtendedEditText candidateNameExtEditText = activityView
                .findViewById(R.id.qm_activity_extended_edittext_candidate_name);
        final ExtendedEditText clientNameExtEditText = activityView
                .findViewById(R.id.qm_activity_extended_edittext_client_name);

        if (candidateNameExtEditText.getText().toString().isEmpty()) {
            candidateNameBox.setError(context.getString(R.string.follow_up_error_controller_empty_field),
                    false);
            candidateNameBox.setErrorColor(R.color.background_soft_orange);
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

    public boolean isAnyErrorOnDateWithStates() {
        boolean isDateWithStatesOk = false;

        TextView dateTextView = activityView.findViewById(R.id.qm_activity_date_edit);
        RadioGroup stateRadioGroup = activityView.findViewById(R.id.qm_activity_radio_group_status);

        int checkedStatus = stateRadioGroup.getCheckedRadioButtonId();
        final String date = dateTextView.getText().toString();
        final Boolean isValidDate = JodaTimeConverter.getInstance().isAValidDate(date);

        if (isValidDate/* && isStatusSelectionOk(checkedStatus, isValidDate, date)*/) {
                isDateWithStatesOk = true;
        }

        printErrors(isValidDate, isDateWithStatesOk);

        return isDateWithStatesOk;
    }

    public boolean isStatusSelectionOk(int checkedStatus,boolean isValidDate, String date) {
        boolean isStatusOk = true;
        final int NO_STATUS_SELECTED = -1;

        final boolean isAnyStatusSelected = (checkedStatus != NO_STATUS_SELECTED);
        if (isAnyStatusSelected) {
            switch (checkedStatus) {
                case R.id.qm_activity_radio_scheduled:

                    break;
                case R.id.qm_activity_radio_done:
                    String dateInMillis = JodaTimeConverter.getInstance()
                            .parseDateFromStringPatternToMillis(date);
                    int comparedDates = JodaTimeConverter.getInstance().compareDates(dateInMillis);

                    if (comparedDates == JodaTimeConverter.NEWER_DATE) {
                        isStatusOk = false;
                    }
                    break;
                case R.id.qm_activity_radio_cancelled:

                    break;
                case R.id.qm_activity_radio_accepted:

                    break;
                default:
                    break;
            }
        }

        if (isValidDate && !isAnyStatusSelected) {
            isStatusOk = false;
        }

        return isStatusOk;
    }

    public void printErrors(boolean isValidDate, boolean isDateWithStatusOk) {
        if (!isValidDate) {
            TextView dateErrorMessage = activityView.findViewById(R.id.qm_activity_date_error_message);
            dateErrorMessage.setVisibility(View.VISIBLE);
        }

        if (!isDateWithStatusOk) {
            TextView statusErrorMessage = activityView.findViewById(R.id.qm_activity_status_error_message);
            statusErrorMessage.setVisibility(View.VISIBLE);
        }
    }

    public void checkForQmDate(View dateView, String dateInMillis, String finalDateTime) {
        int comparedDates = JodaTimeConverter.getInstance().compareDates(dateInMillis);

        if (comparedDates == JodaTimeConverter.PREVIOUS_DATE) {
            ((TextView) dateView).setText(finalDateTime);
        } else {
            ((TextView) dateView).setText(finalDateTime);
        }
    }
}
