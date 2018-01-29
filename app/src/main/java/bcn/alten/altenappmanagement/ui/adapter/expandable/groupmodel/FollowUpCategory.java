package bcn.alten.altenappmanagement.ui.adapter.expandable.groupmodel;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

import bcn.alten.altenappmanagement.mvp.model.FollowUp;

public class FollowUpCategory extends ExpandableGroup<FollowUp> {

    private int iconResId;

    public FollowUpCategory(String title, List items) {
        super(title, items);
    }

    public FollowUpCategory(String title, List items, int resId) {
        super(title, items);
        this.iconResId = resId;
    }

    public int getIconResId() {
        return iconResId;
    }
}
