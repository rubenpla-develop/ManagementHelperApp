package bcn.alten.altenappmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.expandable.groupmodel.Category;
import bcn.alten.altenappmanagement.expandable.holderview.FollowUpHolder;
import bcn.alten.altenappmanagement.expandable.holderview.GroupHolder;
import bcn.alten.altenappmanagement.mvp.model.FollowUp;
import bcn.alten.altenappmanagement.mvp.view.IFollowUpFragmentView;

public class ExpandableCategoryListAdapter  extends ExpandableRecyclerViewAdapter<GroupHolder,
        FollowUpHolder>  {

    private Context context;
    private LayoutInflater inflater;
    private IFollowUpFragmentView followUpFragmentView;

    public ExpandableCategoryListAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    public ExpandableCategoryListAdapter(Context context, List<? extends ExpandableGroup> groups) {
        super(groups);
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    public ExpandableCategoryListAdapter(List<? extends ExpandableGroup> groups,
                                         Context context, IFollowUpFragmentView view) {
        super(groups);
        this.context = context;
        this.followUpFragmentView = view;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public GroupHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.followup_item_parent, parent, false);

        return new GroupHolder(view);
    }

    @Override
    public FollowUpHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.swipeable_child_item, parent, false);

        return new FollowUpHolder(view);
    }

    @Override
    public void onBindChildViewHolder(final FollowUpHolder holder, int flatPosition,
                                      final ExpandableGroup group, final int childIndex) {
        final FollowUp model = (FollowUp) group.getItems().get(childIndex);
        holder.onBind(model);

        holder.getView().setBackgroundColor(context.getResources()
                .getInteger(setRowColor(childIndex)));

        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String consultorName =  model.getConsultorName();
                String clientName =   model.getCurrentClient();
                String lastDateFollowUp =   model.getDateLastFollow();
                String nextFollowUp = model.getDateNextFollow();
                String status = model.getStatus();
                FollowUp followUp  = new FollowUp(consultorName, clientName, lastDateFollowUp,
                        nextFollowUp, status, "");

                followUp.setId(model.getId());

                followUpFragmentView.editFollowUp(followUp);
            }
        });

        holder.getView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String consultorName =  model.getConsultorName();
                String clientName =   model.getCurrentClient();
                String lastDateFollowUp =   model.getDateLastFollow();
                String nextFollowUp = model.getDateNextFollow();
                String status = model.getStatus();
                FollowUp followUp  = new FollowUp(consultorName, clientName, lastDateFollowUp,
                        nextFollowUp, status, "");

                followUp.setId(model.getId());

                followUpFragmentView.deleteFollowUp(followUp);

                return false;
            }
        });
    }

    private int setRowColor(int childIndex) {
        int row_color = (((childIndex % 2) == 0 ? R.color.pair_list_row_color :
                        R.color.odd_list_row_color));

        return row_color;
    }

    @Override
    public void onBindGroupViewHolder(GroupHolder holder, int flatPosition,
                                      ExpandableGroup group) {
        holder.onBind(group);
    }

    public int getGroupsCount() {
        return getGroups().size();
    }

    public FollowUp getItem(final int flatPosition) {
        final int GROUP_HEADER_INDEX = 1;
        int parentGroup = 0;
        int indexPosition = flatPosition;
        boolean isNextCategory = true;

        List<Category> grouplist = ((List<Category>) getGroups());

        for (Category category : grouplist) {
            if (isGroupExpanded(category)) {
                if (indexPosition > category.getItems().size()) {
                    indexPosition -= category.getItems().size() + GROUP_HEADER_INDEX;
                } else {
                    isNextCategory = false;
                }
            } else {
                indexPosition -= GROUP_HEADER_INDEX;
            }

            if (!isNextCategory) {
                break;
            }

            parentGroup += 1;
        }

        return ((FollowUp) getGroups().get(parentGroup).getItems().get(indexPosition -1));
    }
}