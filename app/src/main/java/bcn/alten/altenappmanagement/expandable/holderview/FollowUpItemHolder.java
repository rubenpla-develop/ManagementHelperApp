package bcn.alten.altenappmanagement.expandable.holderview;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.mvp.model.FollowUp;
import bcn.alten.altenappmanagement.utils.JodaTimeConverter;

public class FollowUpItemHolder extends BaseChildHolder {
    private LinearLayout lastDateContainer;
    private LinearLayout nextDateContainer;
    public RelativeLayout viewBackground;
    public RelativeLayout viewForeground;
    private TextView consultorName;
    private TextView clientName;
    private TextView lastDate;
    private TextView nextDate;
    private TextView status;

    public FollowUpItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void findViews() {
        lastDateContainer = itemView.findViewById(R.id.last_date_container);
        nextDateContainer = itemView.findViewById(R.id.next_date_container);
        consultorName = itemView.findViewById(R.id.consulter_name);
        clientName = itemView.findViewById(R.id.client_name);
        lastDate = itemView.findViewById(R.id.last_date_value);
        nextDate = itemView.findViewById(R.id.next_date_value);
        status = itemView.findViewById(R.id.status_value);
        viewBackground = itemView.findViewById(R.id.view_background);
        viewForeground = itemView.findViewById(R.id.view_foreground);
    }

    @Override
    public void onBind(Object object) {
        consultorName.setText(((FollowUp) object).getConsultorName());
        clientName.setText(((FollowUp) object).getCurrentClient());

        final String NO_DATE = "";

        String realDateFormat = JodaTimeConverter.getInstance()
                .getDateInStringFormat(((FollowUp) object).getDateLastFollow());

        if (realDateFormat.equals(NO_DATE)) {
            lastDateContainer.setVisibility(View.INVISIBLE);
        } else {
            lastDate.setText(realDateFormat);
        }

        realDateFormat = JodaTimeConverter.getInstance()
                .getDateInStringFormat(((FollowUp) object).getDateNextFollow());

        if (realDateFormat.equals(NO_DATE)) {
            nextDateContainer.setVisibility(View.INVISIBLE);
        } else {
            nextDate.setText(realDateFormat);
        }

        if (((FollowUp) object).getStatus().equalsIgnoreCase(NO_DATE)) {
            status.setText("Sin estado");
        } else {
            status.setText(((FollowUp) object).getStatus());
        }
    }

    public RelativeLayout getView() {
        return viewForeground;
    }

    public TextView getConsultorName() {
        return consultorName;
    }

    public TextView getClientName() {
        return clientName;
    }

    public TextView getLastDate() {
        return lastDate;
    }

    public TextView getNextDate() {
        return nextDate;
    }

    public TextView getStatus() {
        return status;
    }
}
