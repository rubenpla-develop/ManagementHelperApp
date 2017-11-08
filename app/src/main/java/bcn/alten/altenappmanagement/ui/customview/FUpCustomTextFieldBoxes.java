package bcn.alten.altenappmanagement.ui.customview;

import android.content.Context;
import android.util.AttributeSet;

import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class FUpCustomTextFieldBoxes extends TextFieldBoxes {

    public FUpCustomTextFieldBoxes(Context context) {
        super(context);
    }

    public FUpCustomTextFieldBoxes(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FUpCustomTextFieldBoxes(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void updateCounterText() {
        if(this.hasClearButton) {
            if(this.editText.getText().toString().length() == 0) {
                this.showClearButton(false);
            } else {
                this.showClearButton(true);
            }
        }

        int length = this.editText.getText().toString().length();
        String lengthStr = Integer.toString(length) + " / ";
        if(this.maxCharacters > 0) {
            if(this.minCharacters > 0) {
                this.counterLabel.setText(lengthStr + Integer.toString(this.minCharacters) + "-"
                        + Integer.toString(this.maxCharacters));
                if(length >= this.minCharacters && length <= this.maxCharacters) {
                    this.removeCounterError();
                } else {
                    this.setCounterError();
                }
            } else {
                this.counterLabel.setText(lengthStr + Integer.toString(this.maxCharacters) + "");
                if(length > this.maxCharacters) {
                    this.setCounterError();
                } else {
                    this.removeCounterError();
                }
            }
        } else if(this.minCharacters > 0) {
            this.counterLabel.setText(lengthStr + Integer.toString(this.minCharacters) + "+");
            if(length < this.minCharacters) {
                this.setCounterError();
            } else {
                this.removeCounterError();
            }
        } else {
            this.counterLabel.setText("");
        }

    }
}
