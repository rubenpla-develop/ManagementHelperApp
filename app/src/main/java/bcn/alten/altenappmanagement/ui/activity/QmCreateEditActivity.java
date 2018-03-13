package bcn.alten.altenappmanagement.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.model.QMItem;
import bcn.alten.altenappmanagement.ui.adapter.AutoCompleteViewAdapter;
import bcn.alten.altenappmanagement.ui.customview.ExtendedEditTextWithAutoComplete;
import bcn.alten.altenappmanagement.ui.dialog.AltenDatePickerDialog;
import bcn.alten.altenappmanagement.ui.dialog.AltenTimePickerDialog;
import bcn.alten.altenappmanagement.ui.dialog.QMPhoneInputDialog;
import bcn.alten.altenappmanagement.ui.fragment.QMFragment;
import bcn.alten.altenappmanagement.utils.JodaTimeConverter;
import bcn.alten.altenappmanagement.utils.controller.QmErrorController;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.DatePickerDialog.OnDateSetListener;
import static android.app.TimePickerDialog.OnTimeSetListener;
import static android.view.View.OnClickListener;
import static bcn.alten.altenappmanagement.ui.dialog.QMPhoneInputDialog.OnSetPhoneNumberListener;

public class QmCreateEditActivity extends AppCompatActivity implements OnDateSetListener,
        OnTimeSetListener, OnClickListener, OnSetPhoneNumberListener, RadioGroup.OnCheckedChangeListener {

    public static final int STATUS_SCHEDULED = 0;
    public static final int STATUS_DONE = 1;
    public static final int STATUS_ACCEPTED = 2;
    public static final int STATUS_CANCELLED = 3;

    public static final int QM_ACTIVITY_LAUNCH = 123;

    public static final int RESULT_ADD_OK = 125;
    public static final int RESULT_EDIT_OK = 126;

    private Resources res;

    private QMItem originalQm;
    private String actionMode;
    private int weekOfYear;
    private String finalDateTime;
    private int phoneViewClicked;
    private String chosenStatus;

    private QmErrorController qmErrorController;
    private View activityView;

    @NonNull
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.qm_activity_extended_edittext_client_name)
    ExtendedEditTextWithAutoComplete clientNameExtEditText;

    @BindView(R.id.qm_activity_client_phone_contact)
    ImageView clientPhoneContactList;

    @BindView(R.id.qm_activity_client_phone_edit)
    TextView clientPhoneEditText;

    @BindView(R.id.qm_activity_extended_edittext_candidate_name)
    ExtendedEditTextWithAutoComplete consultantNameExtEditText;

    @BindView(R.id.qm_activity_candidate_phone_contact)
    ImageView consultantPhoneContactList;

    @BindView(R.id.qm_activity_candidate_phone_edit)
    TextView consultantPhoneEditText;

    @BindView(R.id.qm_activity_date_edit)
    TextView dateText;

    @BindView(R.id.qm_activity_time_edit)
    TextView timeText;

    @BindView(R.id.qm_activity_radio_group_status)
    RadioGroup statusGroup;

    @BindView(R.id.qm_activity_status_error_message)
    TextView statusErrorMessage;

    @BindView(R.id.qm_activity_date_error_message)
    TextView dateErrorMessage;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityView = LayoutInflater.from(this).inflate(R.layout.activity_qm_new_edit, null);
        setContentView(activityView);

        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        res = getResources();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            actionMode = bundle.getString(QMFragment.QM_ACTIONMODE_PARAM);
            if (bundle.containsKey(QMFragment.QM_ITEM_PARAM)) {
                originalQm = bundle.getParcelable(QMFragment.QM_ITEM_PARAM);
            }
        }

        qmErrorController = new QmErrorController(this, activityView);

        AutoCompleteViewAdapter autoCompleteAdapter = new AutoCompleteViewAdapter(this);
        consultantNameExtEditText.setAdapter(autoCompleteAdapter);
        clientNameExtEditText.setAdapter(autoCompleteAdapter);

        clientPhoneContactList.setOnClickListener(this);
        consultantPhoneContactList.setOnClickListener(this);

        if ((QMFragment.EDIT_QM_ACTION).equals(actionMode)) {
            //noinspection ConstantConditions
            getSupportActionBar().setTitle("Editar QM");
            clientNameExtEditText.setText(originalQm.getClientName());
            clientPhoneEditText.setText(originalQm.getClientPhone());
            consultantNameExtEditText.setText(originalQm.getCandidateName());
            consultantPhoneEditText.setText(originalQm.getCandidatePhone());
            dateText.setText(JodaTimeConverter.getInstance().getDateInStringFormat(originalQm.getDate()));
            timeText.setText(originalQm.getTime());

            chosenStatus = originalQm.getStatus();

            clientNameExtEditText.requestFocus();
            consultantNameExtEditText.requestFocus();

            if (res.getString(R.string.qm_dialog_radio_group_scheduled_value)
                    .equalsIgnoreCase(originalQm.getStatus())) {
                statusGroup.check(R.id.qm_activity_radio_scheduled);
            } else if (res.getString(R.string.qm_dialog_radio_group_done_value)
                    .equalsIgnoreCase(originalQm.getStatus())) {
                statusGroup.check(R.id.qm_activity_radio_done);
            } else if (res.getString(R.string.qm_dialog_radio_group_accepted_value)
                    .equalsIgnoreCase(originalQm.getStatus())) {
                statusGroup.check(R.id.qm_activity_radio_accepted);
            } else if (res.getString(R.string.qm_dialog_radio_group_cancelled_value)
                    .equalsIgnoreCase(originalQm.getStatus())) {
                statusGroup.check(R.id.qm_activity_radio_cancelled);
            }
        } else {
            getSupportActionBar().setTitle("Crear QM");
        }

        statusGroup.setOnCheckedChangeListener(this);
        clientPhoneEditText.setOnClickListener(this);
        clientPhoneContactList.setOnClickListener(this);
        consultantPhoneEditText.setOnClickListener(this);
        consultantPhoneContactList.setOnClickListener(this);

        dateText.setOnTouchListener((View v, MotionEvent event) -> {
            if (dateErrorMessage.getVisibility() == View.VISIBLE) {
                dateErrorMessage.setVisibility(View.INVISIBLE);
            }

            dateText.performClick();
            return false;
        });
        
        dateText.setOnClickListener(v -> {
            AltenDatePickerDialog datePickerDialog = new AltenDatePickerDialog(this
                    , this);
            datePickerDialog.showDatePicker();
        });

        timeText.setOnClickListener(v -> {
            AltenTimePickerDialog timePickerDialog = new AltenTimePickerDialog(this, this);
            timePickerDialog.showTimePicker();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu_qm_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.menu_toolbar_qm_edit_discard:
                finish();
                break;
            case R.id.menu_toolbar_qm_edit_save:
                if ((!qmErrorController.isAnyFieldEmpty()) &&
                        !qmErrorController.isAnyErrorOnDateWithStates()) {
                    sendActivityResult();
                }
                break;
            default:
                break;
        }

        return true;
    }

    private void sendActivityResult() {
        String formattedDate = JodaTimeConverter.getInstance()
                .parseDateFromStringPatternToMillis(dateText.getText().toString());

        weekOfYear = JodaTimeConverter.getInstance()
                .getWeekOfYearFromDate(dateText.getText().toString());

        Intent data = new Intent();

        //TODO this implementation is WRONGGGGG, consultantId & clientId must be checked
        //todo If has changed/is new and update corresponding values
        QMItem editedQm = new QMItem(weekOfYear, originalQm.getClientId(),
                clientNameExtEditText.getText().toString(),
                clientPhoneEditText.getText().toString(),
                originalQm.getConsultantId(),
                consultantNameExtEditText.getText().toString(),
                consultantPhoneEditText.getText().toString(),
                formattedDate, timeText.getText().toString(),  chosenStatus);

        if (QMFragment.EDIT_QM_ACTION.equals(actionMode)) {
            editedQm.setId(originalQm.getId());
            data.putExtra(QMFragment.QM_ITEM_PARAM, editedQm);
            setResult(RESULT_EDIT_OK, data);
        } else if (QMFragment.ADD_QM_ACTION.equals(actionMode)) {
            data.putExtra(QMFragment.QM_ITEM_PARAM, editedQm);
            setResult(RESULT_ADD_OK, data);
        }

        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == QM_ACTIVITY_LAUNCH && resultCode == RESULT_OK) {
            Uri contactUri = data.getData();
            Cursor cursor = null;
            if (contactUri != null) {
                cursor = getContentResolver().query(contactUri, null, null, null, null);
            }

            if (cursor != null) {
                cursor.moveToFirst();
            }

            int column;
            if (cursor != null) {
                column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String phoneNumber = cursor.getString(column);
                TextView viewClicked = findViewById(phoneViewClicked);
                viewClicked.setText(phoneNumber);

                cursor.close();
            }
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        final String dateInmMillis = JodaTimeConverter.getInstance()
                .parseFromDatePicker(month,dayOfMonth, year);

        finalDateTime = JodaTimeConverter.getInstance()
                .getDateInStringFormat(dateInmMillis);

        weekOfYear = JodaTimeConverter.getInstance().getWeekOfYearFromDate(month, dayOfMonth, year);

        QmErrorController errorController = new QmErrorController(this);

        TextView dateView = findViewById(R.id.qm_activity_date_edit);

        errorController.checkForQmDate(dateView, dateInmMillis, finalDateTime);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String time = JodaTimeConverter.getInstance().parseFromTimePicker(hourOfDay, minute);

        TextView timeView = findViewById(R.id.qm_activity_time_edit);
        timeView.setText(time);
    }

    @Override
    public void onClick(View v) {
        final QMPhoneInputDialog phoneInputDialog;
        final AlertDialog dialog;
        TextView phoneView;

        switch (v.getId()) {
            case R.id.qm_activity_client_phone_edit:
                phoneViewClicked = R.id.qm_activity_client_phone_edit;
                phoneView = findViewById(R.id.qm_activity_client_phone_edit);
                phoneInputDialog = new QMPhoneInputDialog(this, actionMode, phoneView.getText().toString() ,
                        this);
                dialog = phoneInputDialog.getDialog();
                dialog.show();
                break;
            case R.id.qm_activity_candidate_phone_edit:
                phoneViewClicked = R.id.qm_activity_candidate_phone_edit;
                phoneView = findViewById(R.id.qm_activity_candidate_phone_edit);
                phoneInputDialog = new QMPhoneInputDialog(this, actionMode, phoneView.getText().toString(),
                        this);
                dialog = phoneInputDialog.getDialog();
                dialog.show();
                break;
            case R.id.qm_activity_client_phone_contact:
                phoneViewClicked = R.id.qm_activity_client_phone_edit;
                launchContactPicker();
                break;
            case R.id.qm_activity_candidate_phone_contact:
                phoneViewClicked = R.id.qm_activity_candidate_phone_edit;
                launchContactPicker();
                break;
            default :
                break;
        }
    }

    private void launchContactPicker() {
        Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(i, QM_ACTIVITY_LAUNCH);
    }

    @Override
    public void onSetPhone(String phoneNumberInput) {
        View phoneNumberViewClicked = findViewById(phoneViewClicked);
        ((TextView)phoneNumberViewClicked).setText(phoneNumberInput);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        String[] statusList = getResources().getStringArray(R.array.qm_status_values);
        switch (checkedId) {
            case R.id.qm_activity_radio_scheduled:
                chosenStatus = statusList[STATUS_SCHEDULED];
                break;
            case R.id.qm_activity_radio_done:
                chosenStatus = statusList[STATUS_DONE];
                break;
            case R.id.qm_activity_radio_accepted:
                chosenStatus = statusList[STATUS_ACCEPTED];
                break;
            case R.id.qm_activity_radio_cancelled:
                chosenStatus = statusList[STATUS_CANCELLED];
                break;
            default:
                chosenStatus = "";
                break;
        }
    }
}