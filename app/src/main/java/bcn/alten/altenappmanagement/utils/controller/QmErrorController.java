package bcn.alten.altenappmanagement.utils.controller;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.ui.customview.ExtendedEditTextWithAutoComplete;
import bcn.alten.altenappmanagement.ui.customview.TextFieldBoxesWithAutoComplete;
import bcn.alten.altenappmanagement.utils.JodaTimeConverter;


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

        final TextFieldBoxesWithAutoComplete candidateNameBox = activityView
                .findViewById(R.id.qm_activity_textfieldbox_candidate_name);
        final TextFieldBoxesWithAutoComplete clientNameBox = activityView
                .findViewById(R.id.qm_activity_textfieldbox_client_name);
        final ExtendedEditTextWithAutoComplete candidateNameExtEditText = activityView
                .findViewById(R.id.qm_activity_extended_edittext_candidate_name);
        final ExtendedEditTextWithAutoComplete clientNameExtEditText = activityView
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
        boolean isAnyError = true;

        TextView dateTextView = activityView.findViewById(R.id.qm_activity_date_edit);
        RadioGroup stateRadioGroup = activityView.findViewById(R.id.qm_activity_radio_group_status);

        int checkedStatus = stateRadioGroup.getCheckedRadioButtonId();
        final String date = dateTextView.getText().toString();
        final Boolean isValidDate = JodaTimeConverter.getInstance().isAValidDate(date);

        if (isValidDate && !isStatusWrong(checkedStatus, isValidDate, date)) {
                isAnyError = false;
        }

        printErrors(isValidDate, isStatusWrong(checkedStatus, isValidDate, date));

        return isAnyError;
    }

    public boolean isStatusWrong(int checkedStatus,boolean isValidDate, String date) {
        boolean isAnyError = false;
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
                        isAnyError = true;
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
            isAnyError = true;
        }

        return isAnyError;
    }

    public void printErrors(boolean isValidDate, boolean isDateWithStatusWrong) {
        if (!isValidDate) {
            TextView dateErrorMessage = activityView.findViewById(R.id.qm_activity_date_error_message);
            dateErrorMessage.setVisibility(View.VISIBLE);
        }

        if (isDateWithStatusWrong) {
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
