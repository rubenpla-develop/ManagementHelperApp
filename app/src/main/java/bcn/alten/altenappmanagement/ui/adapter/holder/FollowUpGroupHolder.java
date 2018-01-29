package bcn.alten.altenappmanagement.ui.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.ui.adapter.expandable.base.BaseGroupHolder;
import bcn.alten.altenappmanagement.ui.adapter.expandable.groupmodel.FollowUpCategory;
import bcn.alten.altenappmanagement.utils.AnimationRenderer;

public class FollowUpGroupHolder extends BaseGroupHolder {

    private TextView categoryName;
    private ImageView categoryIcon;
    private View badgeView;
    private TextView badgeNumber;
    private ImageView arrow;

    public FollowUpGroupHolder(View itemView) {
        super(itemView);
        findViews();
    }

    @Override
    protected void findViews() {
        categoryName = itemView.findViewById(R.id.category_title);
        categoryIcon = itemView.findViewById(R.id.category_icon);
        badgeNumber = itemView.findViewById(R.id.badge_view_number);
        badgeView = itemView.findViewById(R.id.badge_view);
        arrow = itemView.findViewById(R.id.list_item_genre_arrow);
    }

    @Override
    public void onBind(ExpandableGroup group) {
        categoryName.setText(group.getTitle());
        FollowUpCategory followUpCategory = (FollowUpCategory) group;
        categoryIcon.setImageResource(followUpCategory.getIconResId());

        if (group.getItemCount() >= 1) {
            badgeView.setVisibility(View.VISIBLE);
            badgeNumber.setText(String.valueOf(group.getItemCount()));
            arrow.setVisibility(View.VISIBLE);
        } else {
            if (badgeView.getVisibility() == View.VISIBLE) {
                badgeView.setVisibility(View.INVISIBLE);
            }
        }
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
