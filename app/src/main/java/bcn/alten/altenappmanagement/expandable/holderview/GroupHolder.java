package bcn.alten.altenappmanagement.expandable.holderview;

import android.view.View;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import bcn.alten.altenappmanagement.R;

public class GroupHolder extends GroupViewHolder {

    private TextView categoryName;
    public GroupHolder(View itemView) {
        super(itemView);
        findViews();
    }

    private void findViews() {
        categoryName = itemView.findViewById(R.id.category_title);
    }

    public void setCategoryTitle(ExpandableGroup group) {
        categoryName.setText(group.getTitle());
    }
}
