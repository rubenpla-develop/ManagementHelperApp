package bcn.alten.altenappmanagement.expandable.groupmodel;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

import bcn.alten.altenappmanagement.mvp.model.FollowUp;

/**
 * Created by alten on 22/11/17.
 */

public class QMCategory extends ExpandableGroup<FollowUp> {

    private int iconResId;

    public QMCategory(String title, List items) {
        super(title, items);
    }

    public QMCategory(String title, List items, int resId) {
        super(title, items);
        this.iconResId = resId;
    }

    public int getIconResId() {
        return iconResId;
    }
}
