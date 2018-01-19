package bcn.alten.altenappmanagement.utils;

import android.content.Context;

import bcn.alten.altenappmanagement.R;

import static bcn.alten.altenappmanagement.QmCreateEditActivity.STATUS_ACCEPTED;
import static bcn.alten.altenappmanagement.QmCreateEditActivity.STATUS_CANCELLED;
import static bcn.alten.altenappmanagement.QmCreateEditActivity.STATUS_DONE;
import static bcn.alten.altenappmanagement.QmCreateEditActivity.STATUS_SCHEDULED;
import static bcn.alten.altenappmanagement.application.AltenApplication.getInstance;

/**
 * Created by alten on 19/1/18.
 */

public class QMUtils {

    public static String codifyStatusText(String status) {
        Context context = getInstance().getApplicationContext();
        String[] statusList = context.getResources()
                .getStringArray(R.array.qm_status_values);

        String formattedStatus = "";

        if (status.equalsIgnoreCase(statusList[STATUS_SCHEDULED])) {
            formattedStatus = context.getString(R.string.qm_dialog_radio_group_scheduled_value);
        } else if (status.equalsIgnoreCase(statusList[STATUS_DONE])) {
            formattedStatus = context.getString(R.string.qm_dialog_radio_group_done_value);
        } else if (status.equalsIgnoreCase(statusList[STATUS_ACCEPTED])) {
            formattedStatus = context.getString(R.string.qm_dialog_radio_group_accepted_value);
        } else if (status.equalsIgnoreCase(statusList[STATUS_CANCELLED])) {
            formattedStatus = context.getString(R.string.qm_dialog_radio_group_cancelled_value);
        }

        return formattedStatus;
    }
}
