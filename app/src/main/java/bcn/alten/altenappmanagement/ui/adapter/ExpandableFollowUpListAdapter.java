package bcn.alten.altenappmanagement.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.ui.adapter.expandable.groupmodel.FollowUpCategory;
import bcn.alten.altenappmanagement.ui.adapter.holder.FollowUpGroupHolder;
import bcn.alten.altenappmanagement.ui.adapter.holder.FollowUpItemHolder;
import bcn.alten.altenappmanagement.model.FollowUp;
import bcn.alten.altenappmanagement.mvp.view.IFollowUpFragmentView;

public class ExpandableFollowUpListAdapter extends ExpandableRecyclerViewAdapter<FollowUpGroupHolder,
        FollowUpItemHolder> {

    private Context context;
    private LayoutInflater inflater;
    private IFollowUpFragmentView followUpFragmentView;

    public ExpandableFollowUpListAdapter(List<? extends ExpandableGroup> groups,
                                         Context context, IFollowUpFragmentView view) {
        super(groups);
        this.context = context;
        this.followUpFragmentView = view;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public FollowUpGroupHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.followup_item_parent, parent, false);

        return new FollowUpGroupHolder(view);
    }

    @Override
    public FollowUpItemHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.swipeable_child_item, parent, false);

        return new FollowUpItemHolder(view);
    }

    @Override
    public void onBindChildViewHolder(final FollowUpItemHolder holder, int flatPosition,
                                      final ExpandableGroup group, final int childIndex) {
        final FollowUp model = (FollowUp) group.getItems().get(childIndex);
        holder.onBind(model);

        holder.getView().setBackgroundColor(context.getResources()
                .getInteger(setRowColor(childIndex)));

        holder.getView().setOnClickListener(v -> {

            String consultantId = model.getConsultantId();
            String consultantName =  model.getConsultantName();
            String clientId = model.getClientId();
            String clientName =   model.getCurrentClient();
            String lastDateFollowUp =   model.getDateLastFollow();
            String nextFollowUp = model.getDateNextFollow();
            String status = model.getStatus();
            FollowUp followUp  = new FollowUp(consultantId, consultantName, clientId, clientName,
                    lastDateFollowUp, nextFollowUp, status);

            followUp.setId(model.getId());

            followUpFragmentView.editFollowUp(followUp);
        });

        ((FollowUpItemHolder)holder).getView().setOnLongClickListener(v -> {
            String consultantId = model.getConsultantId();
            String consultantName =  model.getConsultantName();
            String clientId = model.getClientId();
            String clientName =   model.getCurrentClient();
            String lastDateFollowUp =   model.getDateLastFollow();
            String nextFollowUp = model.getDateNextFollow();
            String status = model.getStatus();
            FollowUp followUp  = new FollowUp(consultantId, consultantName, clientId, clientName,
                    lastDateFollowUp, nextFollowUp, status);

            followUp.setId(model.getId());

            followUpFragmentView.deleteFollowUp(followUp);

            return false;
        });
    }

    @Override
    public void onBindGroupViewHolder(FollowUpGroupHolder holder, int flatPosition,
                                      ExpandableGroup group) {
        holder.onBind(group);
    }

    protected int setRowColor(int childIndex) {
        int row_color = (((childIndex % 2) == 0 ? R.color.pair_list_row_color :
                R.color.odd_list_row_color));

        return row_color;
    }

    public int getGroupsCount() {
        return getGroups().size();
    }

    public FollowUp getItem(final int flatPosition) {
        final int GROUP_HEADER_INDEX = 1;
        int parentGroup = 0;
        int indexPosition = flatPosition;
        boolean isNextCategory = true;

        List<FollowUpCategory> grouplist = ((List<FollowUpCategory>) getGroups());

        for (FollowUpCategory followUpCategory : grouplist) {
            if (isGroupExpanded(followUpCategory)) {
                if (indexPosition > followUpCategory.getItems().size()) {
                    indexPosition -= followUpCategory.getItems().size() + GROUP_HEADER_INDEX;
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