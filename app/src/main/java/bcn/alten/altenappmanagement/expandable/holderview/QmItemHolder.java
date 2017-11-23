package bcn.alten.altenappmanagement.expandable.holderview;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.mvp.model.QMItem;

public class QmItemHolder extends BaseChildHolder {

    private LinearLayout containerView;
    private TextView client;
    private TextView consultor;
    private TextView status;

    public QmItemHolder(View itemView) {
        super(itemView);
    }
    
    @Override
    protected void findViews() {
        containerView = itemView.findViewById(R.id.qm_item_child_container);
        client = itemView.findViewById(R.id.qm_client);
        consultor = itemView.findViewById(R.id.qm_consultor);
        status = itemView.findViewById(R.id.qm_status);
    }

    @Override
    public void onBind(Object object) {
        client.setText(((QMItem) object).getClientName());
        consultor.setText(((QMItem) object).getCandidateName());
        status.setText(((QMItem) object).getStatus());
    }

    public LinearLayout getView() {
        return containerView;
    }
}
