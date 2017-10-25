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

    public GroupHolder(View itemView) {
        super(itemView);
        findViews();
    }

    private void findViews() {
        categoryName = itemView.findViewById(R.id.category_title);
        categoryIcon = itemView.findViewById(R.id.category_icon);
    }

    public void onBind(ExpandableGroup group) {
        categoryName.setText(group.getTitle());
        Category category= (Category) group;
        categoryIcon.setImageResource(category.getIconResId());
    }
}
