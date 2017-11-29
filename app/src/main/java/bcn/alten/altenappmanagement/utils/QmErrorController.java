package bcn.alten.altenappmanagement.utils;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;


public class QmErrorController {

    private final String TAG = FollowUpErrorController.class.getSimpleName();

    private Context context;
    private Resources res;

    public QmErrorController(Context context) {
        this.context = context;
        res = context.getResources();
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
