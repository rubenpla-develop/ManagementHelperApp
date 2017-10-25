package bcn.alten.altenappmanagement.expandable.holderview;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.mvp.model.FollowUpModel;

public class FollowUpHolder extends ChildViewHolder {

    private RelativeLayout followUpContainer;
    private TextView consultorName;
    private TextView clientName;
    private TextView date;
    private TextView status;

    public FollowUpHolder(View itemView) {
        super(itemView);
        findViews();
    }

    private void findViews() {
        followUpContainer = itemView.findViewById(R.id.followup_item_container);
        consultorName = itemView.findViewById(R.id.consulter_name);
        clientName = itemView.findViewById(R.id.client_name);
        date = itemView.findViewById(R.id.date_value);
        status = itemView.findViewById(R.id.status_value);
    }

    public void onBind(FollowUpModel followUpModel) {
        consultorName.setText(followUpModel.getConsultorName());
        clientName.setText(followUpModel.getCurrentClient());
        //TODO make conversion from long to Date format (joda time)
        date.setText(String.valueOf(followUpModel.getDateLastFollow()));
        status.setText(followUpModel.getStatus());
    }

    public RelativeLayout getView() {
        return followUpContainer;
    }
}
