package bcn.alten.altenappmanagement.expandable.holderview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.expandable.groupmodel.Category;

public class GroupHolder extends GroupViewHolder {

    private TextView categoryName;
    private ImageView categoryIcon;
    private View badgeView;
    private TextView badgeNumber;

    public GroupHolder(View itemView) {
        super(itemView);
        findViews();
    }

    private void findViews() {
        categoryName = itemView.findViewById(R.id.category_title);
        categoryIcon = itemView.findViewById(R.id.category_icon);
        badgeNumber = itemView.findViewById(R.id.badge_view_number);
        badgeView = itemView.findViewById(R.id.badge_view);
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
}
