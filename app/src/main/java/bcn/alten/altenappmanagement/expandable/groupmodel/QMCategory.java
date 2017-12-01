package bcn.alten.altenappmanagement.expandable.groupmodel;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

import bcn.alten.altenappmanagement.mvp.model.QMItem;

public class QMCategory extends ExpandableGroup<QMItem> {

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
