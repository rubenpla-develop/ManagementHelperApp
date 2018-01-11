package bcn.alten.altenappmanagement.ui.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import bcn.alten.altenappmanagement.R;
import butterknife.BindView;

/**
 * Created by alten on 11/1/18.
 */

public class QmHeaderPanel extends BaseView implements View.OnClickListener{

    @BindView(R.id.qm_header_arrow_down)
    ImageButton arrowDown;

    @BindView(R.id.qm_header_arrow_up)
    ImageButton arrowUp;

    @BindView(R.id.qm_header_scheduled_button)
    ImageButton scheduledButton;

    @BindView(R.id.qm_header_done_button)
    ImageButton doneButton;

    @BindView(R.id.qm_header_accepted_button)
    ImageButton acceptedButton;

    @BindView(R.id.qm_header_cancelled_button)
    ImageButton cancelledButton;

    protected OnQmHeaderPanelClickListener qmHeaderPanelClickListener;

    public QmHeaderPanel(Context context) {
        super(context);
    }

    public QmHeaderPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public QmHeaderPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
    }

    @Override
    protected void loadLayout() {
        layoutId = R.layout.qm_header_layout;
    }

    @Override
    protected void setViews() {
        arrowDown.setOnClickListener(this);
        arrowUp.setOnClickListener(this);
        scheduledButton.setOnClickListener(this);
        doneButton.setOnClickListener(this);
        acceptedButton.setOnClickListener(this);
        cancelledButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.qm_header_arrow_up) :
                qmHeaderPanelClickListener.onClickArrowUp();
                break;
            case (R.id.qm_header_arrow_down) :
                qmHeaderPanelClickListener.onClickArrowDown();
                break;
            case (R.id.qm_header_scheduled_button) :
                Toast.makeText(getContext(), getResources().getResourceEntryName(R.id.qm_header_scheduled_button),
                        Toast.LENGTH_LONG).show();
                break;
            case (R.id.qm_header_done_button) :
                Toast.makeText(getContext(), getResources().getResourceEntryName(R.id.qm_header_done_button),
                        Toast.LENGTH_LONG).show();
                break;
            case (R.id.qm_header_accepted_button) :
                Toast.makeText(getContext(), getResources().getResourceEntryName(R.id.qm_header_accepted_button),
                        Toast.LENGTH_LONG).show();
                break;
            case (R.id.qm_header_cancelled_button) :
                Toast.makeText(getContext(), getResources().getResourceEntryName(R.id.qm_header_cancelled_button),
                        Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }

    public void setOnQMHeaderPanelListener(OnQmHeaderPanelClickListener listener) {
        if (listener != null) {
            this.qmHeaderPanelClickListener = listener;
        }
    }

    public interface OnQmHeaderPanelClickListener {
        void onClickArrowUp();
        void onClickArrowDown();
    }
}
