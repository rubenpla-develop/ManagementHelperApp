package bcn.alten.altenappmanagement.ui.customview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

import bcn.alten.altenappmanagement.R;
import butterknife.BindView;

import static bcn.alten.altenappmanagement.ui.fragment.QMFragment.CLEAR_FILTER_OPTION;

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

    @BindView(R.id.qm_filter_group_clear_all_filters)
    QMFilterOptionButton allItemsButton;

    protected OnQmHeaderPanelClickListener qmHeaderPanelClickListener;

    @NonNull
    private ArrayList<Integer> qmFilterOptions;

    private String[] statusOptions;
    private int previousFilterOption;

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
        statusOptions = getResources().getStringArray(R.array.qm_status_values);

        stringList = res.getIntArray(R.array.qm_filter_options);
        qmFilterOptions = new ArrayList<>();

        for (int filterOption : stringList) {
            qmFilterOptions.add(filterOption);
        }

        previousFilterOption = CLEAR_FILTER_OPTION;
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
        allItemsButton.setOnClickListener(this);
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
                break;
            case (R.id.qm_filter_group_done_button) :
                qmHeaderPanelClickListener.onDoneButton();
                break;
            case (R.id.qm_filter_group_accepted_button) :
                qmHeaderPanelClickListener.onAcceptedButton();
                break;
            case (R.id.qm_filter_group_cancelled_button) :
                qmHeaderPanelClickListener.onCancelledButton();
                break;
            case (R.id.qm_filter_group_clear_all_filters) :
                qmHeaderPanelClickListener.onClearFilter();
                break;
            default:
                break;
        }
    }

    public void setPreviousFilterOption(int previousFilter) {
        previousFilterOption = previousFilter;
    }

    public int getPreviousFilterOption() {
        return previousFilterOption;
    }

    public void setOnQMHeaderPanelListener(OnQmHeaderPanelClickListener listener) {
        if (listener != null) {
            this.qmHeaderPanelClickListener = listener;
        }
    }

    public ArrayList<Integer> getFilterOptions() {
        return qmFilterOptions;
    }

    public String[] getStatusOptions() {
        return statusOptions;
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
