package bcn.alten.altenappmanagement.ui.fragment.dialog;

import android.content.Context;

import java.util.Calendar;

public class FollowUpDatePickerDialog {

    private android.app.DatePickerDialog datePickerFragment;
    private Context context;
    private android.app.DatePickerDialog.OnDateSetListener onDateSetListener;

    public FollowUpDatePickerDialog(Context context, android.app.DatePickerDialog.OnDateSetListener onDateSetListener)
    {
        this.context = context;
        this.onDateSetListener = onDateSetListener;
    }

    public void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        datePickerFragment = new android.app.DatePickerDialog(context, onDateSetListener, year, month, day);
        datePickerFragment.show();
    }

    /*@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), , year, month, day);
    }*/
}
