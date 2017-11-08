package bcn.alten.altenappmanagement.expandable.holderview;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.expandable.groupmodel.Category;

import static android.R.attr.animationDuration;

public class GroupHolder extends GroupViewHolder {

    private RelativeLayout container;
    private TextView categoryName;
    private ImageView categoryIcon;
    private View badgeView;
    private TextView badgeNumber;
    private ImageView arrow;

    public GroupHolder(View itemView) {
        super(itemView);
        findViews();
       // setListeners();
    }

    private void findViews() {
        container = itemView.findViewById(R.id.group_container);
        categoryName = itemView.findViewById(R.id.category_title);
        categoryIcon = itemView.findViewById(R.id.category_icon);
        badgeNumber = itemView.findViewById(R.id.badge_view_number);
        badgeView = itemView.findViewById(R.id.badge_view);
        arrow = itemView.findViewById(R.id.list_item_genre_arrow);
    }

    public void onBind(ExpandableGroup group) {
        categoryName.setText(group.getTitle());
        Category category= (Category) group;
        categoryIcon.setImageResource(category.getIconResId());

        if (group.getItemCount() >= 1) {
            badgeView.setVisibility(View.VISIBLE);
            badgeNumber.setText(String.valueOf(group.getItemCount()));
        }
    }

    private void setListeners() {
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RotateAnimation rotateAnimation = new RotateAnimation(180.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setInterpolator(new DecelerateInterpolator());
                rotateAnimation.setRepeatCount(0);
                rotateAnimation.setDuration(animationDuration);
                rotateAnimation.setFillAfter(true);
                arrow.startAnimation(rotateAnimation);
            }
        });
    }
}
