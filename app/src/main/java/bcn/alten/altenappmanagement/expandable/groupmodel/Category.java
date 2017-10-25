package bcn.alten.altenappmanagement.expandable.groupmodel;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

import bcn.alten.altenappmanagement.mvp.model.FollowUpModel;

public class Category extends ExpandableGroup<FollowUpModel> {

    private int iconResId;

    public Category(String title, List items) {
        super(title, items);
    }

    public Category(String title, List items, int resId) {
        super(title, items);
        this.iconResId = resId;
    }

    public int getIconResId() {
        return iconResId;
    }
}
