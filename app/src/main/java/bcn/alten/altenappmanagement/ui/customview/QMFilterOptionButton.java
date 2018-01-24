package bcn.alten.altenappmanagement.ui.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.ui.customview.callback.RadioCheckable;
import butterknife.BindView;

/**
 * Created by alten on 16/1/18.
 */

public class QMFilterOptionButton extends BaseView implements RadioCheckable {

    @BindView(R.id.filter_option_button_container)
    FrameLayout qmFilterOptionContainer;

    @BindView(R.id.filter_option_button_icon)
    ImageView qmFilterOptionsIcon;

    private int defaultColor;
    private int pressedStateColor;
    private int iconbackGround;

    // Variables
    private Drawable mInitialBackgroundDrawable;
    private OnClickListener mOnClickListener;
    private OnTouchListener mOnTouchListener;
    private boolean mChecked;
    private ArrayList<OnCheckedChangeListener> mOnCheckedChangeListeners = new ArrayList<>();


    //================================================================================
    // Constructors
    //================================================================================


    public QMFilterOptionButton(Context context) {
        super(context);
        setupView();
    }

    public QMFilterOptionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView();
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public QMFilterOptionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupView();
    }

    @Override
    protected void loadLayout() {
        layoutId = R.layout.qm_filter_option_button_layout;
    }

    @Override
    protected void setViews() {
        mInitialBackgroundDrawable = getBackground();
        if (iconbackGround != 0)
        {
            qmFilterOptionsIcon.setBackgroundResource(iconbackGround);
        }
    }

    //================================================================================
    // Init & inflate methods
    //================================================================================


    @Override
    protected void loadResources(AttributeSet attrs) {
        super.loadResources(attrs);
        TypedArray a = getContext().obtainStyledAttributes(attrs,
                R.styleable.QMFilterOptionButton, 0, 0);

        try {
            defaultColor = a.getResourceId(R.styleable.QMFilterOptionButton_defaultColor,
                    0);
            pressedStateColor = a.getResourceId(R.styleable.QMFilterOptionButton_pressedButtonColor,
                    0);
            iconbackGround = a.getResourceId((R.styleable.QMFilterOptionButton_iconBackground),
                    0);

        } finally {
            a.recycle();
        }
    }

    // Template method
    private void setupView() {
        setCustomTouchListener();
    }

    //================================================================================
    // Overriding default behavior
    //================================================================================

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        mOnClickListener = l;
    }

    private void setCustomTouchListener() {
        super.setOnTouchListener(new TouchListener());
    }

    @Override
    public void setOnTouchListener(OnTouchListener onTouchListener) {
        mOnTouchListener = onTouchListener;
    }

    public OnTouchListener getOnTouchListener() {
        return mOnTouchListener;
    }

    private void onTouchDown(MotionEvent motionEvent) {
        setChecked(true);
    }

    private void onTouchUp(MotionEvent motionEvent) {
        // Handle user defined click listeners
        if (mOnClickListener != null) {
            mOnClickListener.onClick(this);
        }
    }
    //================================================================================
    // Public methods
    //================================================================================

    public void setCheckedState() {
        qmFilterOptionContainer.setBackgroundColor(pressedStateColor);
        //ViewCompat.setBackgroundTintList(qmFilterOptionContainer, ColorStateList.valueOf(pressedStateColor));
    }

    public void setNormalState() {
        //ViewCompat.setBackgroundTintList(qmFilterOptionContainer, ColorStateList.valueOf(pressedStateColor));
        qmFilterOptionContainer.setBackgroundDrawable(mInitialBackgroundDrawable);
    }

    //================================================================================
    // Checkable implementation
    //================================================================================

    @Override
    public void setChecked(boolean checked) {
        if (mChecked != checked) {
            mChecked = checked;
            if (!mOnCheckedChangeListeners.isEmpty()) {
                for (int i = 0; i < mOnCheckedChangeListeners.size(); i++) {
                    mOnCheckedChangeListeners.get(i).onCheckedChanged(this, mChecked);
                }
            }
            if (mChecked) {
                setCheckedState();
            } else {
                setNormalState();
            }
        }
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }


    @Override
    public void addOnCheckChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        mOnCheckedChangeListeners.add(onCheckedChangeListener);

    }

    @Override
    public void removeOnCheckChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        mOnCheckedChangeListeners.remove(onCheckedChangeListener);
    }

    //================================================================================
    // Inner classes
    //================================================================================
    private final class TouchListener implements OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    onTouchDown(event);
                    break;
                case MotionEvent.ACTION_UP:
                    onTouchUp(event);
                    break;
            }
            if (mOnTouchListener != null) {
                mOnTouchListener.onTouch(v, event);
            }
            return true;
        }
    }
}

