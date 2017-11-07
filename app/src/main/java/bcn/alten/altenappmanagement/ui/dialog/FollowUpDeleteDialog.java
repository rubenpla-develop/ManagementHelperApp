package bcn.alten.altenappmanagement.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.mvp.model.FollowUp;
import bcn.alten.altenappmanagement.mvp.presenter.FollowUpFragmentPresenter;
import bcn.alten.altenappmanagement.mvp.view.IMainActivityView;
import bcn.alten.altenappmanagement.utils.JodaTimeConverter;

public class FollowUpDeleteDialog {

    private Context context;
    private View dialogView;
    private FollowUp followUpToDelete;

    private FollowUpFragmentPresenter followUpFragmentPresenter;

    public FollowUpDeleteDialog(Context context, FollowUp followUpToDelete,
                                FollowUpFragmentPresenter presenter) {
        this.context = context;
        this.followUpFragmentPresenter = presenter;
        this.followUpToDelete = followUpToDelete;
    }

    public AlertDialog getDialog() {
        LayoutInflater inflater = LayoutInflater.from(context);

        dialogView = inflater.inflate(R.layout.dialog_followup_delete, null);
        final TextView seeInfoTextview = dialogView.findViewById(R.id.fup_dialog_delete_see_info);
        final LinearLayout followUpInfoContainer = dialogView.findViewById(R.id.fup_dialog_delete_info_container);
        final TextView infoClientTextview = dialogView.findViewById(R.id.fup_dialog_delete_info_client);
        final TextView infoNameTextView = dialogView.findViewById(R.id.fup_dialog_delete_info_name);
        final TextView lastDateTextView = dialogView.findViewById(R.id.fup_dialog_delete_info_last_date);
        final TextView nextDateTextview = dialogView.findViewById(R.id.fup_dialog_delete_info_next_date);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setPositiveButton(R.string.follow_up_dialog_delete_positive_button,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        followUpFragmentPresenter.deleteFollowUp(followUpToDelete);
                        ((IMainActivityView) context).showMessage("FollowUp DELETE OK!");
                        dialog.dismiss();
                    }
                }).setNegativeButton(R.string.follow_up_dialog_delete_negative_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setTitle(context.getResources().getString(R.string.follow_up_dialog_delete_title));

        alertDialogBuilder.setView(dialogView);

        seeInfoTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (followUpInfoContainer.getVisibility()) {
                    case View.GONE:
                        followUpInfoContainer.setVisibility(View.VISIBLE);
                        infoNameTextView.append(followUpToDelete.getConsultorName());
                        infoClientTextview.append(followUpToDelete.getCurrentClient());

                        String formattedLastDate = JodaTimeConverter.getInstance()
                                .getDateInStringFormat(Long.valueOf(followUpToDelete.getDateLastFollow()));

                        String formattedNextDate = JodaTimeConverter.getInstance()
                                .getDateInStringFormat(Long.valueOf(followUpToDelete.getDateNextFollow()));

                        lastDateTextView.append(formattedLastDate);
                        nextDateTextview.append(formattedNextDate);
                        break;
                    case View.VISIBLE:
                        followUpInfoContainer.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }
        });

        AlertDialog dialog = alertDialogBuilder.create();

        return dialog;
    }
}