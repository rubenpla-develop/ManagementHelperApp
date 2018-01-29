package bcn.alten.altenappmanagement.ui.adapter.expandable.base;

import android.view.View;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

public abstract class BaseGroupHolder extends GroupViewHolder {
    public BaseGroupHolder(View itemView) {
        super(itemView);
        findViews();
    }

    protected abstract void findViews();

    public abstract void onBind(ExpandableGroup group);
}
