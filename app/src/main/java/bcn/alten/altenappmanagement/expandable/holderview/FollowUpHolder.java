package bcn.alten.altenappmanagement.expandable.holderview;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.mvp.model.FollowUp;
import bcn.alten.altenappmanagement.utils.JodaTimeConverter;

public class FollowUpHolder extends ChildViewHolder {

    private RelativeLayout followUpContainer;
    private LinearLayout lastDateContainer;
    private LinearLayout nextDateContainer;
    private TextView consultorName;
    private TextView clientName;
    private TextView lastDate;
    private TextView nextDate;
    private TextView status;

    public FollowUpHolder(View itemView) {
        super(itemView);
        findViews();
    }

    private void findViews() {
        followUpContainer = itemView.findViewById(R.id.followup_item_container);
        lastDateContainer = itemView.findViewById(R.id.last_date_container);
        nextDateContainer = itemView.findViewById(R.id.next_date_container);
        consultorName = itemView.findViewById(R.id.consulter_name);
        clientName = itemView.findViewById(R.id.client_name);
        lastDate = itemView.findViewById(R.id.last_date_value);
        nextDate = itemView.findViewById(R.id.next_date_value);
        status = itemView.findViewById(R.id.status_value);
    }

    public void onBind(FollowUp followUp) {
        consultorName.setText(followUp.getConsultorName());
        clientName.setText(followUp.getCurrentClient());

        final String NO_DATE = "";

        String realDateFormat = JodaTimeConverter.getInstance()
                .getDateInStringFormat(followUp.getDateLastFollow());

        if (realDateFormat.equals(NO_DATE)) {
            lastDateContainer.setVisibility(View.INVISIBLE);
        } else {
            lastDate.setText(realDateFormat);
        }

        realDateFormat = JodaTimeConverter.getInstance()
                .getDateInStringFormat(followUp.getDateNextFollow());

        if (realDateFormat.equals(NO_DATE)) {
            nextDateContainer.setVisibility(View.INVISIBLE);
        } else {
            nextDate.setText(realDateFormat);
        }

        status.setText(followUp.getStatus());
    }

    public RelativeLayout getView() {
        return followUpContainer;
    }
}
