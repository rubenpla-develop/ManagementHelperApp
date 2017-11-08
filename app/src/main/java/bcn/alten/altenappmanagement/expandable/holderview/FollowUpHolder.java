package bcn.alten.altenappmanagement.expandable.holderview;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.mvp.model.FollowUp;
import bcn.alten.altenappmanagement.utils.JodaTimeConverter;

public class FollowUpHolder extends ChildViewHolder {

    private FrameLayout followUpContainer;
    public RelativeLayout viewBackground;
    public RelativeLayout viewForeground;
    private TextView consultorName;
    private TextView clientName;
    private TextView date;
   // private TextView status;

    public FollowUpHolder(View itemView) {
        super(itemView);
        findViews();
    }

    private void findViews() {
        followUpContainer = itemView.findViewById(R.id.swipe_container);
        consultorName = itemView.findViewById(R.id.consulter_name);
        clientName = itemView.findViewById(R.id.client_name);
        date = itemView.findViewById(R.id.date_value);
       // status = itemView.findViewById(R.id.status_value);
        viewBackground = itemView.findViewById(R.id.view_background);
        viewForeground = itemView.findViewById(R.id.view_foreground);
    }

    public void onBind(FollowUp followUp) {
        consultorName.setText(followUp.getConsultorName());
        clientName.setText(followUp.getCurrentClient());

        String realDateFormat = JodaTimeConverter.getInstance()
                .getDateInStringFormat(followUp.getDateLastFollow());
        date.setText(String.valueOf(realDateFormat));
    }

    public FrameLayout getView() {
        return followUpContainer;
    }
}
