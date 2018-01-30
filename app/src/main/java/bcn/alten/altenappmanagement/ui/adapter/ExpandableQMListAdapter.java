package bcn.alten.altenappmanagement.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.util.List;

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.ui.adapter.base.BaseExpandableListAdapter;
import bcn.alten.altenappmanagement.utils.LegendBadgeCounter;
import bcn.alten.altenappmanagement.ui.adapter.holder.QmGroupHolder;
import bcn.alten.altenappmanagement.ui.adapter.holder.QmItemHolder;
import bcn.alten.altenappmanagement.model.QMItem;
import bcn.alten.altenappmanagement.mvp.view.IQmFragmentView;

import static bcn.alten.altenappmanagement.utils.QMUtils.codifyStatusText;

public class ExpandableQMListAdapter extends BaseExpandableListAdapter<QmGroupHolder, QmItemHolder> {
    private IQmFragmentView view;
    private QMItem qmItem;
    protected LegendBadgeCounter badgeCounter;

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
            qmItem = new QMItem();

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

        ((QmItemHolder) holder).getView().setOnLongClickListener(v -> {
            qmItem = new QMItem();
            qmItem.setId(model.getId());
            qmItem.setWeek(model.getWeek());
            qmItem.setClientName(model.getClientName());
            qmItem.setClientPhone(model.getClientPhone());
            qmItem.setCandidateName(model.getCandidateName());
            qmItem.setCandidatePhone(model.getCandidatePhone());
            qmItem.setDate(model.getDate());
            qmItem.setTime(model.getTime());
            qmItem.setStatus(model.getStatus());

            view.deleteQm(qmItem);
            return false;
        });

        ((QmItemHolder) holder).getCandidateName().setOnClickListener(v -> {
            if (!context.getResources().getString(R.string.qm_dialog_no_phone)
                    .equalsIgnoreCase(model.getCandidatePhone())) {
                String phone = model.getCandidatePhone();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                context.startActivity(intent);
            }
        });

        ((QmItemHolder) holder).getClientName().setOnClickListener(v -> {
            if (!context.getResources().getString(R.string.qm_dialog_no_phone)
                    .equalsIgnoreCase(model.getCandidatePhone())) {
                String phone = model.getClientPhone();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public synchronized void onBindGroupViewHolder(GroupViewHolder holder, int flatPosition, ExpandableGroup group) {
        ((QmGroupHolder) holder).onBind(group);
        LegendBadgeSorter badgeSorter = new LegendBadgeSorter(group);
        badgeCounter = new LegendBadgeCounter();
        badgeSorter.filterGroupByBadges();
        ((QmGroupHolder)holder).onBindLegendBadges(badgeCounter);
    }

    private class LegendBadgeSorter {
        private ExpandableGroup group;

        public LegendBadgeSorter(ExpandableGroup group) {
            this.group = group;
        }

        public void filterGroupByBadges() {
            List<QMItem> listToFilterBadges = group.getItems();

            for (QMItem qmItem : listToFilterBadges) {
                if (context.getString(R.string.qm_dialog_radio_group_scheduled_value)
                        .equals(codifyStatusText(qmItem.getStatus()))) {
                    incrementPlusOneScheduleBadge(badgeCounter);
                } else if (context.getString(R.string.qm_dialog_radio_group_done_value)
                        .equals(codifyStatusText(qmItem.getStatus()))) {
                    incrementPlusOneDoneBadge(badgeCounter);
                } else if (context.getString(R.string.qm_dialog_radio_group_accepted_value)
                        .equals(codifyStatusText(qmItem.getStatus()))) {
                    incrementPlusOneAcceptedBadge(badgeCounter);
                } else if (context.getString(R.string.qm_dialog_radio_group_cancelled_value)
                        .equals(codifyStatusText(qmItem.getStatus()))) {
                    incrementPlusOneCancelledBadge(badgeCounter);
                }
            }
        }

        private void incrementPlusOneScheduleBadge(LegendBadgeCounter counter) {
            counter.scheduleBadgeCount += 1;
        }

        private void incrementPlusOneDoneBadge(LegendBadgeCounter counter) {
            counter.doneBadgeCount += 1;
        }

        private void incrementPlusOneAcceptedBadge(LegendBadgeCounter counter) {
            counter.acceptedBadgeCount += 1;
        }

        private void incrementPlusOneCancelledBadge(LegendBadgeCounter counter) {
            counter.cancelledBadgeCount += 1;
        }
    }
}
