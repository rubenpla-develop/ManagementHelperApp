package bcn.alten.altenappmanagement.ui.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

import bcn.alten.altenappmanagement.ui.customview.base.AutoCompleteTextViewBasic;

public class ExtendedEditTextWithAutoComplete extends AutoCompleteTextViewBasic {

    private final int DEFAULT_AUTO_COMPLETE_DELAY = 750;
    private final int HANDLER_MESSAGE_CHANGED = 100;

    public int DEFAULT_TEXT_COLOR;
    protected String prefix;
    protected String suffix;
    protected int prefixTextColor;
    protected int suffixTextColor;

    private int autoCompleteDelay;
    private ProgressBar progressBar = null;

    private static Handler autoCompleteHandler;

    public ExtendedEditTextWithAutoComplete(Context context) {
        this(context, (AttributeSet)null);
        this.initDefaultColor();
    }

    public ExtendedEditTextWithAutoComplete(Context context, AttributeSet attrs) {
        this(context, attrs, 16842862);
        this.initDefaultColor();
        this.handleAttributes(context, attrs);
    }

    public ExtendedEditTextWithAutoComplete(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setView();
        this.initDefaultColor();
        this.handleAttributes(context, attrs);
    }

    @SuppressLint("HandlerLeak")
    private void setView() {
        autoCompleteDelay = DEFAULT_AUTO_COMPLETE_DELAY;
        autoCompleteHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                ExtendedEditTextWithAutoComplete.super.performFiltering((CharSequence) msg.obj,
                        msg.arg1);
            }
        };
    }

    protected void initDefaultColor() {
        Resources.Theme theme = this.getContext().getTheme();
        TypedArray themeArray = theme.obtainStyledAttributes(new int[]{16843282});
        this.DEFAULT_TEXT_COLOR = themeArray.getColor(0, 0);
        themeArray.recycle();
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.setPrefix(this.prefix);
        this.setSuffix(this.suffix);
        this.setPrefixTextColor(this.prefixTextColor);
        this.setSuffixTextColor(this.suffixTextColor);
    }

    protected void handleAttributes(Context context, AttributeSet attrs) {
        try {
            TypedArray styledAttrs = context.obtainStyledAttributes(attrs, studio.carbonylgroup.textfieldboxes.R.styleable.ExtendedEditText);
            this.prefix = styledAttrs.getString(studio.carbonylgroup.textfieldboxes.R.styleable.ExtendedEditText_prefix) == null?"":styledAttrs.getString(studio.carbonylgroup.textfieldboxes.R.styleable.ExtendedEditText_prefix);
            this.suffix = styledAttrs.getString(studio.carbonylgroup.textfieldboxes.R.styleable.ExtendedEditText_suffix) == null?"":styledAttrs.getString(studio.carbonylgroup.textfieldboxes.R.styleable.ExtendedEditText_suffix);
            this.prefixTextColor = styledAttrs.getInt(studio.carbonylgroup.textfieldboxes.R.styleable.ExtendedEditText_prefixTextColor, this.DEFAULT_TEXT_COLOR);
            this.suffixTextColor = styledAttrs.getInt(studio.carbonylgroup.textfieldboxes.R.styleable.ExtendedEditText_suffixTextColor, this.DEFAULT_TEXT_COLOR);
            styledAttrs.recycle();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    private void setLoadingIndicator(@NonNull ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    private void setAutoCompleteDelay(int delay) {
        this.autoCompleteDelay = delay;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
        this.setCompoundDrawables(new ExtendedEditTextWithAutoComplete.TextDrawable(), (Drawable)null, (Drawable)null, (Drawable)null);
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
        this.setCompoundDrawables(new ExtendedEditTextWithAutoComplete.TextDrawable(), (Drawable)null, (Drawable)null, (Drawable)null);
    }

    public void setPrefixTextColor(int color) {
        this.prefixTextColor = color;
    }

    public void setSuffixTextColor(int color) {
        this.suffixTextColor = color;
    }

    private class TextDrawable extends Drawable {
        private TextDrawable() {
            this.setBounds(0, 0, (int)ExtendedEditTextWithAutoComplete.this.getPaint().measureText(ExtendedEditTextWithAutoComplete.this.prefix) + 2, (int)ExtendedEditTextWithAutoComplete.this.getTextSize());
            ExtendedEditTextWithAutoComplete.this.setPadding(0, 0, (int)ExtendedEditTextWithAutoComplete.this.getPaint().measureText(ExtendedEditTextWithAutoComplete.this.suffix) - 2, 0);
        }

        public void draw(@NonNull Canvas canvas) {
            int lineBase = ExtendedEditTextWithAutoComplete.this.getLineBounds(0, (Rect)null);
            int lineBottom = ExtendedEditTextWithAutoComplete.this.getLineBounds(ExtendedEditTextWithAutoComplete.this.getLineCount() - 1, (Rect)null);
            float endX = (float)ExtendedEditTextWithAutoComplete.this.getWidth() - ExtendedEditTextWithAutoComplete.this.getPaint().measureText(ExtendedEditTextWithAutoComplete.this.suffix) - 2.0F;
            Paint paint = ExtendedEditTextWithAutoComplete.this.getPaint();
            paint.setColor(ExtendedEditTextWithAutoComplete.this.prefixTextColor);
            canvas.drawText(ExtendedEditTextWithAutoComplete.this.prefix, 0.0F, (float)(canvas.getClipBounds().top + lineBase), paint);
            paint.setColor(ExtendedEditTextWithAutoComplete.this.suffixTextColor);
            canvas.drawText(ExtendedEditTextWithAutoComplete.this.suffix, endX, (float)(canvas.getClipBounds().top + lineBottom), paint);
        }

        public void setAlpha(int alpha) {
        }

        public void setColorFilter(ColorFilter colorFilter) {
        }

        public int getOpacity() {
            return PixelFormat.OPAQUE;
        }
    }

    @Override
    public void performFiltering(CharSequence text, int keyCode) {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }

        autoCompleteHandler.removeMessages(HANDLER_MESSAGE_CHANGED);
        autoCompleteHandler.sendMessageDelayed(getHandler().obtainMessage(HANDLER_MESSAGE_CHANGED,
                text), (long) autoCompleteDelay);
    }

    @Override
    public void onFilterComplete(int count) {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }

        super.onFilterComplete(count);
    }
}
