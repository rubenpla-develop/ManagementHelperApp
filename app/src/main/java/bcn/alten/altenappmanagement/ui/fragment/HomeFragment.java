package bcn.alten.altenappmanagement.ui.fragment;


import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import bcn.alten.altenappmanagement.BuildConfig;
import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.database.AltenDatabase;
import bcn.alten.altenappmanagement.mvp.model.FollowUp;
import bcn.alten.altenappmanagement.mvp.model.QMItem;
import bcn.alten.altenappmanagement.utils.CategoryDataFactory;
import bcn.alten.altenappmanagement.utils.QMDataFactory;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private final String TAG = HomeFragment.class.getSimpleName();

    @BindView(R.id.home_fragment_mock_container)
    RelativeLayout home_mock_container;

    @BindView(R.id.home_tv)
    TextView home_tv;

    @BindView(R.id.fecth_result_tv)
    TextView fetch_result_tv;

    @BindView(R.id.btn_fetch_data)
    Button btnFecthData;

    @BindView(R.id.btn_insert_data)
    Button btnInsertData;

    @BindView(R.id.btn_qm_fetch_data)
    Button btnQmFecthData;

    @BindView(R.id.btn_qm_insert_data)
    Button btnQmInsertData;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        if (!BuildConfig.HOME_SCREEN_MOCK_CONTENT_BUTTONS) {
            home_mock_container.setVisibility(View.INVISIBLE);
            home_tv.setVisibility(View.VISIBLE);
        }

        setListeners();
        return view;
    }

    private void setListeners() {
        btnFecthData.setOnClickListener(this);
        btnInsertData.setOnClickListener(this);
        btnQmFecthData.setOnClickListener(this);
        btnQmInsertData.setOnClickListener(this);
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

                    fetch_result_tv.setTextSize(10);
                    fetch_result_tv.setText("");
                    if (followUpList != null && followUpList.size() > 0) {
                        for (FollowUp follow : followUpList) {
                            String formattedString = follow.getId() + "." +
                                    follow.getConsultorName() + " " +
                                    follow.getCurrentClient()  + "\n";

                            fetch_result_tv.append(formattedString);
                        }
                    }
                });
                break;

            case R.id.btn_qm_insert_data :
                DatabaseQMAsync qmAsync = new DatabaseQMAsync();
                qmAsync.execute();
                break;

            case R.id.btn_qm_fetch_data :
                LiveData<List<QMItem>> qmList = AltenDatabase.getDatabase(getContext())
                        .daoAccess()
                        .fecthQMData();

                qmList.observe(this, followUpList -> {

                    fetch_result_tv.setTextSize(10);
                    fetch_result_tv.setText("");
                    if (followUpList != null && followUpList.size() > 0) {
                        for (QMItem qm : followUpList) {
                            String formattedString = qm.getId() + "." +
                                    qm.getClientName() + " " +
                                    qm.getCandidateName()  + "\n";

                            fetch_result_tv.append(formattedString);
                        }
                    }
                });
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

    private class DatabaseQMAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            List<QMItem> list = QMDataFactory.createMockQMItemList();

            AltenDatabase.getDatabase(getActivity()).daoAccess().insertQMList(list);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
