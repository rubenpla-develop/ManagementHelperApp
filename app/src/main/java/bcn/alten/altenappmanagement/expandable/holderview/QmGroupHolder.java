package bcn.alten.altenappmanagement.expandable.holderview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.expandable.groupmodel.QMCategory;
import bcn.alten.altenappmanagement.utils.AnimationRenderer;

public class QmGroupHolder extends BaseGroupHolder {

    private TextView categoryName;
    private ImageView categoryIcon;
    private ImageView arrow;

    public QmGroupHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void findViews() {
        categoryName = itemView.findViewById(R.id.qm_group_holder_qm_category_title);
        categoryIcon = itemView.findViewById(R.id.qm_group_holder_category_icon);
        arrow = itemView.findViewById(R.id.qm_group_holder_list_item_genre_arrow);
    }

    @Override
    public void onBind(ExpandableGroup group) {
        categoryName.setText(group.getTitle());
        QMCategory category= (QMCategory) group;
        categoryIcon.setImageResource(category.getIconResId());
    }

    @Override
    public void expand() {
        AnimationRenderer animationRenderer = new AnimationRenderer();
        animationRenderer.expandGroupArrowRotation(arrow);
        super.expand();
    }

    @Override
    public void collapse() {
        AnimationRenderer animationRenderer = new AnimationRenderer();
        animationRenderer.collapseGroupArrowRotation(arrow);
        super.collapse();
    }
}
