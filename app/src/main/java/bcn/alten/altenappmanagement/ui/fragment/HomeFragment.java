package bcn.alten.altenappmanagement.ui.fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.database.AltenDatabase;
import bcn.alten.altenappmanagement.mvp.model.FollowUp;
import bcn.alten.altenappmanagement.mvp.view.IMainActivityView;
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
        setListeners();
        return view;
    }

    private void setListeners() {
        btnFecthData.setOnClickListener(this);
        btnInsertData.setOnClickListener(this);
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

                list.observe(this, new Observer<List<FollowUp>>() {
                    @Override
                    public void onChanged(@Nullable List<FollowUp> followUpList) {
                        home_tv.setTextSize(10);
                        home_tv.setText("");
                        for (FollowUp follow : followUpList) {
                            String formattedString = follow.getId() + "." +
                                    follow.getConsultorName() + " " +
                                    follow.getCurrentClient()  + "\n";

                            home_tv.append(formattedString);
                        }

                        ((IMainActivityView) getActivity())
                                .showMessage("Livedata is Working!!");
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

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            /*Log.i("AltenAPP", "year: " + year + " month : " + month + " day : " + day);
            LocalDate dateTime = new LocalDate(year, month + 1 , day);
            DateTime dt = dateTime.toDateTimeAtCurrentTime();
            String dateInMillies = String.valueOf(dt.getMillis()); //TODO saves this value to DB*/

            String dateInmMillies = JodaTimeConverter.getInstance().parsefromDatePicker(month,day, year);

            //TODO retrieve date value this way
            //DateTime finalDateTime = new DateTime(Long.valueOf(dateInMillies));
            String finalDateTime = JodaTimeConverter.getInstance()
                    .getDateInStringFormat(Long.valueOf(dateInmMillies));
           // dt.toString("dd-MM-yyyy");
            Log.i("Alten", "dateInMillies : " + dateInmMillies + "\nfinalDateTime : "
                    + finalDateTime);
        }
    }
}
