package bcn.alten.altenappmanagement.ui.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.ui.customview.FUpCustomTextFieldBoxes;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;

public class QMPhoneInputDialog {

    private final String TAG = QMPhoneInputDialog.class.getSimpleName();

    private View dialogView;
    private Context context;
    private String phoneNumber;
    private String actionMode;
    private OnSetPhoneNumberListener OnSetPhoneNumberListener;

    public QMPhoneInputDialog(Context context, @NonNull String actionMode, @Nullable String phoneNumber,
                              @NonNull OnSetPhoneNumberListener OnSetPhoneNumberListener) {
        this.context = context;
        this.actionMode = actionMode;
        if (phoneNumber != null) {
            this.phoneNumber = phoneNumber;
        }
        this.OnSetPhoneNumberListener = OnSetPhoneNumberListener;
    }

    public AlertDialog getDialog() {
        LayoutInflater inflater = LayoutInflater.from(context);
        dialogView = inflater.inflate(R.layout.dialog_qm_phone_input, null);

        final FUpCustomTextFieldBoxes phoneNumberBox = dialogView.findViewById(R.id.qm_phone_input_dialog_box);
        final ExtendedEditText phoneNumberEditText = dialogView.findViewById(R.id.qm_phone_input_dialog_extendededittext);

        if (QMDialog.EDIT_QM_ACTION.equalsIgnoreCase(actionMode)) {
            phoneNumberEditText.setText(phoneNumber);
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(dialogView);

        alertDialogBuilder.setPositiveButton(R.string.follow_up_dialog_positive_button, null)
                .setNegativeButton(R.string.follow_up_dialog_negative_button, (dialog, which) -> dialog.dismiss());

        AlertDialog qmPhoneInputDialog = alertDialogBuilder.create();

        qmPhoneInputDialog.setOnShowListener(dialog -> {
            Button positiveButton = qmPhoneInputDialog.getButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(v -> {
                OnSetPhoneNumberListener.onSetPhone(phoneNumberEditText.getText().toString());
                qmPhoneInputDialog.dismiss();
            });
        });

        return qmPhoneInputDialog;
    }

    public interface OnSetPhoneNumberListener {
        void onSetPhone(String phoneNumberInput);
    }
}
