package bcn.alten.altenappmanagement.expandable.holderview;

import android.view.View;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public abstract class BaseChildHolder extends ChildViewHolder {
    public BaseChildHolder(View itemView) {
        super(itemView);
        findViews();
    }

    protected abstract void findViews();

    public abstract void onBind(Object object);
}
