package bcn.alten.altenappmanagement.ui.fragment.dialog;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.mvp.view.IFollowUpFragmentView;
import bcn.alten.altenappmanagement.mvp.view.IMainActivityView;

import static android.app.DatePickerDialog.OnDateSetListener;
import static android.view.View.OnClickListener;

public class FollowUpDialog {

    private Context context;
    private IFollowUpFragmentView view;
    private OnDateSetListener onDateSetListener;
    private OnClickListener onClickListener;

    public FollowUpDialog(Context context, IFollowUpFragmentView view,
                          OnDateSetListener onDateListener, OnClickListener onClickListener) {
        this.context = context;
        this.view = view;
        this.onDateSetListener = onDateListener;
        this.onClickListener = onClickListener;
    }

    public AlertDialog getDialog() {
        LayoutInflater inflater = LayoutInflater.from(context);

        View dialogView = inflater.inflate(R.layout.dialog_followup_new_edit, null);
        final CheckBox addNextFollowCheckbox = dialogView.findViewById(R.id.fup_dialog_checkbox_add_next_follow);
        final LinearLayout addNextFollowContainer = dialogView.findViewById(R.id.fup_dialog_container_next_follow);
        TextView dateText = dialogView.findViewById(R.id.fup_dialog_date_edit);
        TextView addNextFollowTextView = dialogView.findViewById(R.id.fup_dialog_next_date_edit);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ((IMainActivityView) context).showMessage("FollowUp Dialog OK!");
            }
        }).setNegativeButton("Descartar", null);

        alertDialogBuilder.setView(dialogView);




        dateText.setOnClickListener(onClickListener);
        addNextFollowTextView.setOnClickListener(onClickListener);

        addNextFollowCheckbox.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addNextFollowCheckbox.isChecked()) {
                    addNextFollowContainer.setVisibility(View.VISIBLE);
                } else {
                    addNextFollowContainer.setVisibility(View.INVISIBLE);
                }
            }
        });

        AlertDialog dialog = alertDialogBuilder.create();

        return dialog;
    }

    public DatePickerDialog launchDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(context, onDateSetListener, year, month, day);
    }
}



