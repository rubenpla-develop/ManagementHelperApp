package bcn.alten.altenappmanagement.ui.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.HashMap;

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.ui.customview.callback.RadioCheckable;
import bcn.alten.altenappmanagement.utils.ViewUtils;

/**
 * Created by alten on 16/1/18.
 */

public class QMFilterGroup extends LinearLayout {

    // Attribute Variables
    private int checkedId = View.NO_ID;
    private boolean protectFromCheckedChange = false;

    // Variables
    private OnCheckedChangeListener onCheckedChangeListener;
    private HashMap<Integer, View> childViewsMap = new HashMap<>();
    private PassThroughHierarchyChangeListener passThroughListener;
    private RadioCheckable.OnCheckedChangeListener childOnCheckedChangeListener;

    public QMFilterGroup(Context context) {
        super(context);
        setupView();
    }

    public QMFilterGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(attrs);
        setupView();
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public QMFilterGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(attrs);
        setupView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public QMFilterGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        parseAttributes(attrs);
        setupView();
    }

    private void parseAttributes(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs,
                R.styleable.QMFilterGroup, 0, 0);
        try {
            checkedId = a.getResourceId(R.styleable.QMFilterGroup_presetRadioCheckedId, View.NO_ID);
        } finally {
            a.recycle();
        }
    }

    // Template method
    private void setupView() {
        childOnCheckedChangeListener = new CheckedStateTracker();
        passThroughListener = new PassThroughHierarchyChangeListener();
        super.setOnHierarchyChangeListener(passThroughListener);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (child instanceof RadioCheckable) {
            final RadioCheckable button = (RadioCheckable) child;
            if (button.isChecked()) {
                protectFromCheckedChange = true;
                if (checkedId != View.NO_ID) {
                    setCheckedStateForView(checkedId, false);
                }
                protectFromCheckedChange = false;
                setCheckedId(child.getId(), true);
            }
        }

        super.addView(child, index, params);
    }

    @Override
    public void setOnHierarchyChangeListener(OnHierarchyChangeListener listener) {
        // the user listener is delegated to our pass-through listener
        passThroughListener.mOnHierarchyChangeListener = listener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // checks the appropriate radio button as requested in the XML file
        if (checkedId != View.NO_ID) {
            protectFromCheckedChange = true;
            setCheckedStateForView(checkedId, true);
            protectFromCheckedChange = false;
            setCheckedId(checkedId, true);
        }
    }

    private void setCheckedId(@IdRes int id, boolean isChecked) {
        checkedId = id;
        if (onCheckedChangeListener != null) {
            onCheckedChangeListener.onCheckedChanged(this, childViewsMap.get(id), isChecked, checkedId);
        }
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    public void clearCheck() {
        check(View.NO_ID);
    }

    public void check(@IdRes int id) {
        // don't even bother
        if (id != View.NO_ID && (id == checkedId)) {
            return;
        }

        if (checkedId != View.NO_ID) {
            setCheckedStateForView(checkedId, false);
        }

        if (id != View.NO_ID) {
            setCheckedStateForView(id, true);
        }

        setCheckedId(id, true);
    }

    private void setCheckedStateForView(int viewId, boolean checked) {
        View checkedView;
        checkedView = childViewsMap.get(viewId);
        if (checkedView == null) {
            checkedView = findViewById(viewId);
            if (checkedView != null) {
                childViewsMap.put(viewId, checkedView);
            }
        }
        if (checkedView != null && checkedView instanceof RadioCheckable) {
            ((RadioCheckable) checkedView).setChecked(checked);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }
    //================================================================================
    // Public methods
    //================================================================================


    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    public OnCheckedChangeListener getOnCheckedChangeListener() {
        return onCheckedChangeListener;
    }


    //================================================================================
    // Nested classes
    //================================================================================
    public static interface OnCheckedChangeListener {
        void onCheckedChanged(View radioGroup, View radioButton, boolean isChecked, int checkedId);
    }

    public static class LayoutParams extends LinearLayout.LayoutParams {
        /**
         * {@inheritDoc}
         */
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        /**
         * {@inheritDoc}
         */
        public LayoutParams(int w, int h) {
            super(w, h);
        }

        /**
         * {@inheritDoc}
         */
        public LayoutParams(int w, int h, float initWeight) {
            super(w, h, initWeight);
        }

        /**
         * {@inheritDoc}
         */
        public LayoutParams(ViewGroup.LayoutParams p) {
            super(p);
        }

        /**
         * {@inheritDoc}
         */
        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        /**
         * <p>Fixes the child's width to
         * {@link android.view.ViewGroup.LayoutParams#WRAP_CONTENT} and the child's
         * height to  {@link android.view.ViewGroup.LayoutParams#WRAP_CONTENT}
         * when not specified in the XML file.</p>
         *
         * @param a          the styled attributes set
         * @param widthAttr  the width attribute to fetch
         * @param heightAttr the height attribute to fetch
         */
        @Override
        protected void setBaseAttributes(TypedArray a,
                                         int widthAttr, int heightAttr) {

            if (a.hasValue(widthAttr)) {
                width = a.getLayoutDimension(widthAttr, "layout_width");
            } else {
                width = WRAP_CONTENT;
            }

            if (a.hasValue(heightAttr)) {
                height = a.getLayoutDimension(heightAttr, "layout_height");
            } else {
                height = WRAP_CONTENT;
            }
        }
    }

    //================================================================================
    // Inner classes
    //================================================================================
    private class CheckedStateTracker implements RadioCheckable.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(View buttonView, boolean isChecked) {
            // prevents from infinite recursion
            if (protectFromCheckedChange) {
                return;
            }

            protectFromCheckedChange = true;
            if (checkedId != View.NO_ID) {
                setCheckedStateForView(checkedId, false);
            }
            protectFromCheckedChange = false;

            int id = buttonView.getId();
            setCheckedId(id, true);
        }
    }

    private class PassThroughHierarchyChangeListener implements
            ViewGroup.OnHierarchyChangeListener {
        private ViewGroup.OnHierarchyChangeListener mOnHierarchyChangeListener;

        /**
         * {@inheritDoc}
         */
        public void onChildViewAdded(View parent, View child) {
            if (parent == QMFilterGroup.this && child instanceof RadioCheckable) {
                int id = child.getId();
                // generates an id if it's missing
                if (id == View.NO_ID) {
                    id = ViewUtils.generateViewId();
                    child.setId(id);
                }
                ((RadioCheckable) child).addOnCheckChangeListener(
                        childOnCheckedChangeListener);
                childViewsMap.put(id, child);
            }

            if (mOnHierarchyChangeListener != null) {
                mOnHierarchyChangeListener.onChildViewAdded(parent, child);
            }
        }

        /**
         * {@inheritDoc}
         */
        public void onChildViewRemoved(View parent, View child) {
            if (parent == QMFilterGroup.this && child instanceof RadioCheckable) {
                ((RadioCheckable) child).removeOnCheckChangeListener(childOnCheckedChangeListener);
            }
            childViewsMap.remove(child.getId());
            if (mOnHierarchyChangeListener != null) {
                mOnHierarchyChangeListener.onChildViewRemoved(parent, child);
            }
        }
    }
}
