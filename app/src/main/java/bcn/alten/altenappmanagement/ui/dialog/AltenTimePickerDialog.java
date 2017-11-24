package bcn.alten.altenappmanagement.ui.dialog;

import android.app.TimePickerDialog;
import android.content.Context;

import org.joda.time.DateTime;

import java.util.Calendar;

public class AltenTimePickerDialog {

    private android.app.TimePickerDialog datePickerFragment;
    private Context context;
    private android.app.TimePickerDialog.OnTimeSetListener onTimeSetListener;

    public AltenTimePickerDialog(Context context, android.app.TimePickerDialog.OnTimeSetListener onTImeSetListener)
    {
        this.context = context;
        this.onTimeSetListener = onTimeSetListener;
    }

    public void showTimePicker() {
        final Calendar c = Calendar.getInstance();

        DateTime dt = new DateTime().now();

        int hour = dt.getHourOfDay();
        int minutes = dt.getMinuteOfHour();


        datePickerFragment = new TimePickerDialog(context, onTimeSetListener, hour, minutes, true);
        datePickerFragment.show();
    }
}
