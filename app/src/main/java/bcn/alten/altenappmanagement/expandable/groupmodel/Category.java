package bcn.alten.altenappmanagement.expandable.groupmodel;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

import bcn.alten.altenappmanagement.mvp.model.FollowUpModel;

public class Category extends ExpandableGroup<FollowUpModel> {

    public Category(String title, List items) {
        super(title, items);
    }
}
