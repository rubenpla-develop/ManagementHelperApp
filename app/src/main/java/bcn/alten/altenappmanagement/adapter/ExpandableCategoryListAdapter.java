package bcn.alten.altenappmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.expandable.holderview.FollowUpHolder;
import bcn.alten.altenappmanagement.expandable.holderview.GroupHolder;
import bcn.alten.altenappmanagement.mvp.model.FollowUpModel;
import bcn.alten.altenappmanagement.mvp.view.IMainActivityView;

public class ExpandableCategoryListAdapter  extends ExpandableRecyclerViewAdapter<GroupHolder,
        FollowUpHolder>  {

    private Context context;
    private LayoutInflater inflater;

    public ExpandableCategoryListAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    public ExpandableCategoryListAdapter(Context context, List<? extends ExpandableGroup> groups) {
        super(groups);
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public GroupHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.followup_item_parent, parent, false);

        return new GroupHolder(view);
    }

    @Override
    public FollowUpHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.followup_item_child, parent, false);

        return new FollowUpHolder(view);
    }

    @Override
    public void onBindChildViewHolder(final FollowUpHolder holder, int flatPosition,
                                      final ExpandableGroup group, final int childIndex) {
        final FollowUpModel model = (FollowUpModel) group.getItems().get(childIndex);
        holder.onBind(model);

        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO TEMP, only for testing logic
                ((IMainActivityView) context).showMessage("Categoria : " + group.getTitle()
                        + ", Consultor: " +
                        ((FollowUpModel) group.getItems().get(childIndex)).getConsultorName()
                        + "\nCliente : " +  ((FollowUpModel) group.getItems().get(childIndex))
                        .getCurrentClient()
                        + ", Posicion: " + (childIndex + 1));
            }
        });
    }

    @Override
    public void onBindGroupViewHolder(GroupHolder holder, int flatPosition,
                                      ExpandableGroup group) {
        holder.onBind(group);
    }
}
