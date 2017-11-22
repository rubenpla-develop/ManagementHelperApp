package bcn.alten.altenappmanagement.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.util.List;

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.expandable.holderview.QmGroupHolder;
import bcn.alten.altenappmanagement.expandable.holderview.QmItemHolder;
import bcn.alten.altenappmanagement.mvp.model.QMItem;

public class ExpandableQMListAdapter extends BaseExpandableListAdapter<QmGroupHolder, QmItemHolder> {

    //TODO IqmFragmentView

    public ExpandableQMListAdapter(List<? extends ExpandableGroup> groups, Context context, Object view) {
        super(groups, context, view);
    }

    @Override
    public QmGroupHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.qm_item_parent, parent, false);

        return new QmGroupHolder(view);
    }

    @Override
    public QmItemHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.qm_item_child, parent, false);

        return new QmItemHolder(view);
    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final QMItem model = (QMItem) group.getItems().get(childIndex);
        ((QmItemHolder)holder).onBind(model);

        //TODO do stuff here (listeners, view logic...)
    }

    @Override
    public void onBindGroupViewHolder(GroupViewHolder holder, int flatPosition, ExpandableGroup group) {
        ((QmGroupHolder) holder).onBind(group);
    }
}
