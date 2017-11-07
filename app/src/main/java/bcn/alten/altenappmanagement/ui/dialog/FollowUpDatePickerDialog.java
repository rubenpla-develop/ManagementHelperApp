package bcn.alten.altenappmanagement.ui.dialog;

import android.app.DatePickerDialog;
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

        datePickerFragment = new DatePickerDialog(context, onDateSetListener, year, month, day);
        datePickerFragment.show();
    }
}
