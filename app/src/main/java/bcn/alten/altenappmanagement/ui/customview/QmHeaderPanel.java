package bcn.alten.altenappmanagement.ui.customview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

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

    @BindView(R.id.qm_filter_group_radio_group_container)
    QMFilterGroup filterGroupContainer;

    @BindView(R.id.qm_filter_group_scheduled_button)
    QMFilterOptionButton scheduledButton;

    @BindView(R.id.qm_filter_group_done_button)
    QMFilterOptionButton doneButton;

    @BindView(R.id.qm_filter_group_accepted_button)
    QMFilterOptionButton acceptedButton;

    @BindView(R.id.qm_filter_group_cancelled_button)
    QMFilterOptionButton cancelledButton;

    @BindView(R.id.qm_filter_group_all_items_button)
    QMFilterOptionButton allItemsButton;

    protected OnQmHeaderPanelClickListener qmHeaderPanelClickListener;

    @NonNull
    private ArrayList qmFilterOptions;

    private int[] filterOptions;

    public QmHeaderPanel(Context context) {
        super(context);
        setFilterOptions();
    }

    public QmHeaderPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFilterOptions();
    }

    public QmHeaderPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
        setFilterOptions();
    }

    private void setFilterOptions() {
        int[] stringList;

        stringList = res.getIntArray(R.array.qm_filter_options);
        qmFilterOptions = new ArrayList<>();

        for (int filterOption : stringList) {
            qmFilterOptions.add(filterOption);
        }
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
            case (R.id.qm_filter_group_scheduled_button) :
                qmHeaderPanelClickListener.onScheduledButton();
                Toast.makeText(getContext(), getResources().getResourceEntryName(R.id.qm_filter_group_scheduled_button),
                        Toast.LENGTH_LONG).show();
                break;
            case (R.id.qm_filter_group_done_button) :
                qmHeaderPanelClickListener.onDoneButton();
                Toast.makeText(getContext(), getResources().getResourceEntryName(R.id.qm_filter_group_done_button),
                        Toast.LENGTH_LONG).show();
                break;
            case (R.id.qm_filter_group_accepted_button) :
                qmHeaderPanelClickListener.onAcceptedButton();
                Toast.makeText(getContext(), getResources().getResourceEntryName(R.id.qm_filter_group_accepted_button),
                        Toast.LENGTH_LONG).show();
                break;
            case (R.id.qm_filter_group_cancelled_button) :
                qmHeaderPanelClickListener.onCancelledButton();
                Toast.makeText(getContext(), getResources().getResourceEntryName(R.id.qm_filter_group_cancelled_button),
                        Toast.LENGTH_LONG).show();
                break;
            case (R.id.qm_filter_group_all_items_button) :
                qmHeaderPanelClickListener.onClearFilter();
                Toast.makeText(getContext(), getResources().getResourceEntryName(R.id.qm_filter_group_all_items_button),
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

    public ArrayList<Integer> getFilterOptionsArray() {
        return qmFilterOptions;
    }

    public interface OnQmHeaderPanelClickListener {
        void onClickArrowUp();
        void onClickArrowDown();
        void onScheduledButton();
        void onDoneButton();
        void onAcceptedButton();
        void onCancelledButton();
        void onClearFilter();
    }
}
