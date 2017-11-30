package bcn.alten.altenappmanagement.ui.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.mvp.model.QMItem;
import bcn.alten.altenappmanagement.mvp.presenter.QmFragmentPresenter;
import bcn.alten.altenappmanagement.utils.JodaTimeConverter;

public class QMDeleteDialog {

    private Context context;
    private View dialogView;
    private QMItem qmToDelete;

    private QmFragmentPresenter qmFragmentPresenter;

    public QMDeleteDialog(Context context, QMItem qmToDelete,
                                QmFragmentPresenter presenter) {
        this.context = context;
        this.qmFragmentPresenter = presenter;
        this.qmToDelete = qmToDelete;
    }

    public AlertDialog getDialog() {
        LayoutInflater inflater = LayoutInflater.from(context);

        dialogView = inflater.inflate(R.layout.dialog_qm_delete, null);
        final TextView seeInfoTextview = dialogView.findViewById(R.id.qm_dialog_delete_see_info);
        final LinearLayout qmInfoContainer = dialogView.findViewById(R.id.qm_dialog_delete_info_container);
        final TextView infoClientTextview = dialogView.findViewById(R.id.qm_dialog_delete_info_client_name);
        final TextView infoNameTextView = dialogView.findViewById(R.id.qm_dialog_delete_info_candidate_name);
        final TextView dateTextView = dialogView.findViewById(R.id.qm_dialog_delete_info_date);
        final TextView timeTextview = dialogView.findViewById(R.id.qm_dialog_delete_info_time);
        final TextView statusTextView = dialogView.findViewById(R.id.qm_dialog_delete_info_status);

        infoNameTextView.append(qmToDelete.getCandidateName());
        infoClientTextview.append(qmToDelete.getClientName());

        String formattedDate = JodaTimeConverter.getInstance()
                .getDateInStringFormat(qmToDelete.getDate());

        dateTextView.append(formattedDate);
        timeTextview.append(qmToDelete.getTime());

        if (qmToDelete.getStatus().equals("")) {
            statusTextView.append(context.getString(R.string.qm_dialog_no_status));
        } else {
            statusTextView.append(qmToDelete.getStatus());
        }


        qmInfoContainer.setVisibility(View.GONE);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setPositiveButton(R.string.qm_dialog_delete_positive_button,
                (dialog, which) -> {
                    qmFragmentPresenter.deleteQm(qmToDelete);
                    dialog.dismiss();
                }).setNegativeButton(R.string.qm_dialog_delete_negative_button, (dialog, which) -> dialog.dismiss()).setTitle(context.getResources().getString(R.string.qm_dialog_delete_title));

        alertDialogBuilder.setView(dialogView);

        seeInfoTextview.setOnClickListener(v -> {
            switch (qmInfoContainer.getVisibility()) {
                case View.GONE:
                    qmInfoContainer.setVisibility(View.VISIBLE);
                    break;
                case View.VISIBLE:
                    qmInfoContainer.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        });

        AlertDialog dialog = alertDialogBuilder.create();

        return dialog;
    }
}
