package bcn.alten.altenappmanagement.ui.adapter.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.util.List;

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.model.QMItem;
import bcn.alten.altenappmanagement.mvp.view.IQmFragmentView;

public class BaseExpandableListAdapter<Q extends GroupViewHolder, Q1 extends ChildViewHolder>
        extends ExpandableRecyclerViewAdapter {

    protected Context context;
    protected LayoutInflater inflater;

    public BaseExpandableListAdapter(List<? extends ExpandableGroup> groups, Context context,
                                     IQmFragmentView view) {
        super(groups);
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public GroupViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public ChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        return null;
    }


    @Override
    public void onBindChildViewHolder(ChildViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {

    }

    @Override
    public void onBindGroupViewHolder(GroupViewHolder holder, int flatPosition, ExpandableGroup group) {

    }

    protected int setRowColor(int childIndex) {
        int row_color = (((childIndex % 2) == 0 ? R.color.pair_list_row_color :
                R.color.odd_list_row_color));

        return row_color;
    }

    public int getGroupsCount() {
        return getGroups().size();
    }

    public QMItem getItem(final int flatPosition) {
       return null;
    }
}
