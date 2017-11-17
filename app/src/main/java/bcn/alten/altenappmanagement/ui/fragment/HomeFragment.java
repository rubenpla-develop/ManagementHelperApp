package bcn.alten.altenappmanagement.ui.fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import bcn.alten.altenappmanagement.BuildConfig;
import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.database.AltenDatabase;
import bcn.alten.altenappmanagement.mvp.model.FollowUp;
import bcn.alten.altenappmanagement.utils.CategoryDataFactory;
import bcn.alten.altenappmanagement.utils.JodaTimeConverter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private final String TAG = HomeFragment.class.getSimpleName();

    @BindView(R.id.hombe_tv)
    TextView home_tv;

    @BindView(R.id.btn_fetch_data)
    Button btnFecthData;

    @BindView(R.id.btn_insert_data)
    Button btnInsertData;

    @BindView(R.id.btn_showPicker)
    Button btnShowDatePicker;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        if (!BuildConfig.HOME_SCREEN_MOCK_CONTENT_BUTTONS) {
            btnShowDatePicker.setVisibility(View.INVISIBLE);
            btnInsertData.setVisibility(View.INVISIBLE);
            btnFecthData.setVisibility(View.INVISIBLE);
        }

        setListeners();
        return view;
    }

    private void setListeners() {
        btnFecthData.setOnClickListener(this);
        btnInsertData.setOnClickListener(this);
        btnShowDatePicker.setVisibility(View.INVISIBLE);
        btnShowDatePicker.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_insert_data :
                DatabaseAsync async = new DatabaseAsync();
                async.execute();
                break;

            case R.id.btn_fetch_data :
                LiveData<List<FollowUp>> list = AltenDatabase.getDatabase(getContext())
                        .daoAccess()
                        .fecthFollowUpData();

                list.observe(this, followUpList -> {
                    home_tv.setTextSize(10);
                    home_tv.setText("");
                    if (followUpList != null && followUpList.size() > 0) {
                        for (FollowUp follow : followUpList) {
                            String formattedString = follow.getId() + "." +
                                    follow.getConsultorName() + " " +
                                    follow.getCurrentClient()  + "\n";

                            home_tv.append(formattedString);
                        }
                    }
                });
                break;

            case R.id.btn_showPicker :
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
                break;

            default :
                break;
        }
    }

    private class DatabaseAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            List<FollowUp> list = CategoryDataFactory.createMockFollowUpList();

            AltenDatabase.getDatabase(getActivity()).daoAccess().insertFollowUpList(list);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            String dateInmMillies = JodaTimeConverter.getInstance().parsefromDatePicker(month,day, year);

            String finalDateTime = JodaTimeConverter.getInstance()
                    .getDateInStringFormat(dateInmMillies);
            Log.i("Alten", "dateInMillies : " + dateInmMillies + "\nfinalDateTime : "
                    + finalDateTime);
        }
    }
}
