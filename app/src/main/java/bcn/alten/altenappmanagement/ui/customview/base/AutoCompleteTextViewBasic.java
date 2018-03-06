package bcn.alten.altenappmanagement.ui.customview.base;

import android.content.Context;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.WithHint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

/**
 * Created by alten on 5/3/18.
 */

public class AutoCompleteTextViewBasic extends AppCompatAutoCompleteTextView {
    public AutoCompleteTextViewBasic(Context context) {
        super(context);
    }

    public AutoCompleteTextViewBasic(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoCompleteTextViewBasic(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * TextInputEditText overriden with this method
     */
    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        final InputConnection ic = super.onCreateInputConnection(outAttrs);
        if (ic != null && outAttrs.hintText == null) {
            // If we don't have a hint and our parent implements WithHint, use its hint for the
            // EditorInfo. This allows us to display a hint in 'extract mode'.
            ViewParent parent = getParent();
            while (parent instanceof View) {
                if (parent instanceof WithHint) {
                    outAttrs.hintText = ((WithHint) parent).getHint();
                    break;
                }
                parent = parent.getParent();
            }
        }
        return ic;
    }

    @Override
    public boolean enoughToFilter() {
        return ((getText() != null) && (getText().length() > 0));
    }
}
