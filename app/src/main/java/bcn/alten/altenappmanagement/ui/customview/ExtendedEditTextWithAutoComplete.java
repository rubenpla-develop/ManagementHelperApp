package bcn.alten.altenappmanagement.ui.customview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;

import bcn.alten.altenappmanagement.ui.customview.base.AutoCompleteTextViewBasic;

/**
 * Created by alten on 5/3/18.
 */

public class ExtendedEditTextWithAutoComplete extends AutoCompleteTextViewBasic {

    public int DEFAULT_TEXT_COLOR;
    protected String prefix;
    protected String suffix;
    protected int prefixTextColor;
    protected int suffixTextColor;

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
        this.initDefaultColor();
        this.handleAttributes(context, attrs);
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
}
