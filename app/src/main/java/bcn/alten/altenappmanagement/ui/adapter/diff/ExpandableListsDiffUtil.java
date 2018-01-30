package bcn.alten.altenappmanagement.ui.adapter.diff;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import bcn.alten.altenappmanagement.model.QMItem;

/**
 * Created by alten on 18/1/18.
 */

public class ExpandableListsDiffUtil extends DiffUtil.Callback {

    private ExpandableGroup oldList;
    private ExpandableGroup newList;

    public ExpandableListsDiffUtil(ExpandableGroup oldList,
                                   ExpandableGroup newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList != null ? oldList.getItems().size() : 0;
    }

    @Override
    public int getNewListSize() {
        return newList != null ? newList.getItems().size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {

        QMItem newQMItem = (QMItem) newList.getItems().get(newItemPosition);
        QMItem oldQMItem = (QMItem) oldList.getItems().get(oldItemPosition);

        return newQMItem.getId() == oldQMItem.getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

        QMItem newQMItem = (QMItem) newList.getItems().get(newItemPosition);
        QMItem oldQMItem = (QMItem) oldList.getItems().get(oldItemPosition);

        return newQMItem.equals(oldQMItem);
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
