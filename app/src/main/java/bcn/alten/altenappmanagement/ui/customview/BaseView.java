package bcn.alten.altenappmanagement.ui.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import butterknife.ButterKnife;

public abstract class BaseView extends FrameLayout {

    protected int layoutId;

    public BaseView(Context context) {
        super(context);
        init(null);
    }

    public BaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    protected void init(AttributeSet attrs) {
        loadLayout();
        loadContent();
        setViews();
        loadResources(attrs);
    }

    protected abstract void loadLayout();
    protected abstract void setViews();

    protected void loadContent() {
        inflate(getContext(), layoutId, this);
        ButterKnife.bind(this);
    }

    protected void loadResources(AttributeSet attrs) {

    }
}
