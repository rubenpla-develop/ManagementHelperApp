package bcn.alten.altenappmanagement.ui.customview.callback;

import android.view.View;
import android.widget.Checkable;

/**
 * Created by alten on 16/1/18.
 */

public interface RadioCheckable extends Checkable {

    void addOnCheckChangeListener(OnCheckedChangeListener onCheckedChangeListener);
    void removeOnCheckChangeListener(OnCheckedChangeListener onCheckedChangeListener);

    public static interface OnCheckedChangeListener {
        void onCheckedChanged(View radioGroup, boolean isChecked);
    }
}
