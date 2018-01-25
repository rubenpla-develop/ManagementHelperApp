package bcn.alten.altenappmanagement.expandable.holderview;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.mvp.model.QMItem;
import bcn.alten.altenappmanagement.utils.JodaTimeConverter;

import static bcn.alten.altenappmanagement.utils.QMUtils.codifyStatusText;

public class QmItemHolder extends BaseChildHolder {

    private RelativeLayout containerView;
    private TextView clientName;
    private TextView candidateName;
    private TextView date;
    private TextView time;
    private TextView status;

    public QmItemHolder(View itemView) {
        super(itemView);
    }
    
    @Override
    protected void findViews() {
        containerView = itemView.findViewById(R.id.qm_item_child);
        clientName = itemView.findViewById(R.id.qm_client_name);
        candidateName = itemView.findViewById(R.id.qm_candidate_name);
        date = itemView.findViewById(R.id.qm_date_value);
        time = itemView.findViewById(R.id.qm_time_value);
        status = itemView.findViewById(R.id.qm_status_value);
    }

    @Override
    public void onBind(Object object) {
        clientName.setText(((QMItem) object).getClientName());
        candidateName.setText(((QMItem) object).getCandidateName());
        date.setText(JodaTimeConverter.getInstance()
                .getDateInStringFormat(((QMItem) object).getDate()));
        time.setText(((QMItem) object).getTime());
        status.setText(codifyStatusText(((QMItem) object).getStatus()));
    }

    public RelativeLayout getView() {
        return containerView;
    }

    public TextView getClientName() {
        return clientName;
    }

    public TextView getCandidateName() {
        return candidateName;
    }
}
