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
import bcn.alten.altenappmanagement.mvp.view.IQmFragmentView;

public class ExpandableQMListAdapter extends BaseExpandableListAdapter<QmGroupHolder, QmItemHolder> {
    private IQmFragmentView view;

    public ExpandableQMListAdapter(List<? extends ExpandableGroup> groups, Context context, IQmFragmentView view) {
        super(groups, context, view);
        this.view = view;
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

        ((QmItemHolder) holder).getView().setBackgroundColor(context.getResources()
                .getInteger(setRowColor(childIndex)));

        ((QmItemHolder) holder).getView().setOnClickListener(v -> {
            QMItem qmItem = new QMItem();
            qmItem.setId(model.getId());
            qmItem.setWeek(model.getWeek());
            qmItem.setClientName(model.getClientName());
            qmItem.setClientPhone(model.getClientPhone());
            qmItem.setCandidateName(model.getCandidateName());
            qmItem.setCandidatePhone(model.getCandidatePhone());
            qmItem.setDate(model.getDate());
            qmItem.setTime(model.getTime());
            qmItem.setStatus(model.getStatus());

             view.editQm(qmItem);
        });

        //TODO do stuff here (listeners, view logic...)
    }

    @Override
    public void onBindGroupViewHolder(GroupViewHolder holder, int flatPosition, ExpandableGroup group) {
        ((QmGroupHolder) holder).onBind(group);
    }
}
