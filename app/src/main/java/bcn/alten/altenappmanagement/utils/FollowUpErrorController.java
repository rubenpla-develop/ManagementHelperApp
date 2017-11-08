package bcn.alten.altenappmanagement.utils;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.mvp.presenter.FollowUpFragmentPresenter;
import bcn.alten.altenappmanagement.mvp.view.IMainActivityView;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class FollowUpErrorController {

    private final String TAG = FollowUpErrorController.class.getSimpleName();

    private FollowUpFragmentPresenter presenter;
    private Context context;
    private Resources res;

    public FollowUpErrorController(Context context, FollowUpFragmentPresenter presenter) {
        this.presenter = presenter;
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
            consultorNameBox.setError("Campo Vacio", false);
            consultorNameBox.setErrorColor(R.color.background_soft_orange);
            isAnyError = true;
        }

        if (clientNameExtEditText.getText().toString().isEmpty()) {
            clientNameBox.setError("Campo Vacio", false);
            clientNameBox.setErrorColor(R.color.background_soft_orange);
            isAnyError = true;
        }
        
        return isAnyError;
    }

    public void checkforFollowUpDates(View dateViewClicked, String dateInmMillies, 
                                      String finalDateTime) {
        int comparedDates = JodaTimeConverter.getInstance().compareDates(dateInmMillies);

        //We compare date set in DatePicker dialog versus current date today, every date view
        // (last followUp, next FollowUp) is comprared to check if its previous or newer date,
        // for last follow, date must be previous than current date; For next followUp, exactly
        // inverse, date MUST be newer than current date.
        switch (comparedDates) {
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
