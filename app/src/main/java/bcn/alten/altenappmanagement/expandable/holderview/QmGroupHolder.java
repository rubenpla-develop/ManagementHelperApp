package bcn.alten.altenappmanagement.expandable.holderview;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.expandable.groupmodel.LegendBadgeCounter;
import bcn.alten.altenappmanagement.expandable.groupmodel.QMCategory;
import bcn.alten.altenappmanagement.utils.AnimationRenderer;

public class QmGroupHolder extends BaseGroupHolder {

    private TextView categoryName;
    private ImageView categoryIcon;
    private ImageView arrow;
    private LinearLayout badgeScheduledContainer;
    private LinearLayout badgeDoneContainer;
    private LinearLayout badgeAcceptedContainer;
    private LinearLayout badgeCancelledContainer;
    private TextView scheduleBadge;
    private TextView doneBadge;
    private TextView acceptedBadge;
    private TextView cancelledBadge;

    public QmGroupHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void findViews() {
        categoryName = itemView.findViewById(R.id.qm_group_holder_qm_category_title);
        categoryIcon = itemView.findViewById(R.id.qm_group_holder_category_icon);
        arrow = itemView.findViewById(R.id.qm_group_holder_list_item_genre_arrow);
        badgeScheduledContainer = itemView.findViewById(R.id.qm_group_holder_schedule_legend_container);
        badgeDoneContainer = itemView.findViewById(R.id.qm_group_holder_done_legend_container);
        badgeAcceptedContainer = itemView.findViewById(R.id.qm_group_holder_accepted_legend_container);
        badgeCancelledContainer = itemView.findViewById(R.id.qm_group_holder_cancelled_legend_container);
        scheduleBadge = itemView.findViewById(R.id.qm_group_holder_schedule_legend_badge);
        doneBadge = itemView.findViewById(R.id.qm_group_holder_done_legend_badge);
        acceptedBadge = itemView.findViewById(R.id.qm_group_holder_accepted_legend_badge);
        cancelledBadge = itemView.findViewById(R.id.qm_group_holder_cancelled_legend_badge);
    }

    @Override
    public void onBind(ExpandableGroup group) {
        categoryName.setText(group.getTitle());
        QMCategory category= (QMCategory) group;
        categoryIcon.setImageResource(category.getIconResId());

        if (group.getItemCount() >= 1) {
            arrow.setVisibility(View.VISIBLE);
        }
    }

    public void onBindLegendBadges(LegendBadgeCounter counter) {
        scheduleBadge.setText(String.valueOf(counter.scheduleBadgeCount));
        doneBadge.setText(String.valueOf(counter.doneBadgeCount));
        acceptedBadge.setText(String.valueOf(counter.acceptedBadgeCount));
        cancelledBadge.setText(String.valueOf(counter.cancelledBadgeCount));
    }

    @Override
    public void expand() {
        AnimationRenderer animationRenderer = new AnimationRenderer();
        animationRenderer.expandGroupArrowRotation(arrow);
        super.expand();
    }

    @Override
    public void collapse() {
        AnimationRenderer animationRenderer = new AnimationRenderer();
        animationRenderer.collapseGroupArrowRotation(arrow);
        super.collapse();
    }
}
