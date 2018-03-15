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
import bcn.alten.altenappmanagement.model.Client;
import bcn.alten.altenappmanagement.model.Consultant;
import bcn.alten.altenappmanagement.model.FollowUp;
import bcn.alten.altenappmanagement.model.QMItem;
import bcn.alten.altenappmanagement.utils.factory.BaseFactory;
import bcn.alten.altenappmanagement.utils.factory.FollowUpFactory;
import bcn.alten.altenappmanagement.utils.factory.QMDataFactory;
import butterknife.BindView;
import butterknife.ButterKnife;

import static bcn.alten.altenappmanagement.data.db.AltenDatabase.getDatabase;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private final String TAG = HomeFragment.class.getSimpleName();

    @BindView(R.id.home_fragment_cc_insert_client_consultant)
    Button btnInsertClientConsultantData;

    @BindView(R.id.home_fragment_cc_fetch_client_consultant)
    Button btnFetchClientConsultantData;

    @BindView(R.id.home_fragment_mock_container)
    RelativeLayout home_mock_container;

    @BindView(R.id.home_tv)
    TextView home_tv;

    @BindView(R.id.fecth_result_tv)
    TextView fetch_result_tv;

    @BindView(R.id.btn_fetch_data)
    Button btnFetchData;

    @BindView(R.id.btn_insert_data)
    Button btnInsertData;

    @BindView(R.id.btn_qm_fetch_data)
    Button btnQmFetchData;

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
        btnFetchData.setOnClickListener(this);
        btnInsertData.setOnClickListener(this);
        btnQmFetchData.setOnClickListener(this);
        btnQmInsertData.setOnClickListener(this);
        btnFetchClientConsultantData.setOnClickListener(this);
        btnInsertClientConsultantData.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_insert_data :
                DatabaseAsync async = new DatabaseAsync();
                async.execute();
                break;

            case R.id.btn_fetch_data :
                LiveData<List<FollowUp>> list = getDatabase(getContext())
                        .daoAccess()
                        .fecthFollowUpData();

                list.observe(this, followUpList -> {

                    fetch_result_tv.setTextSize(10);
                    fetch_result_tv.setText("");
                    if (followUpList != null && followUpList.size() > 0) {
                        for (FollowUp follow : followUpList) {
                            String formattedString = follow.getId() + "." +
                                    follow.getConsultantName() + " " +
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
                LiveData<List<QMItem>> qmList = getDatabase(getContext())
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

            case R.id.home_fragment_cc_insert_client_consultant :
                DatabaseCCAsync ccAsync = new DatabaseCCAsync();
                ccAsync.execute();
                break;

            case R.id.home_fragment_cc_fetch_client_consultant :
                LiveData<List<Client>> clientList = getDatabase(getContext()).daoAccess()
                        .fetchClientData();

                clientList.observe(this, clients -> {
                    fetch_result_tv.setTextSize(10);
                    fetch_result_tv.setText("");
                    if (clients != null && clients.size() > 0) {
                        for (Client client : clients) {
                            String formattedString = client.getId() + "." +
                                    client.getName() + "\n";

                            fetch_result_tv.append(formattedString);
                        }
                    }
                });

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

            List<FollowUp> list = FollowUpFactory.createMockFollowUpList();

            getDatabase(getActivity()).daoAccess().insertFollowUpList(list);

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

            getDatabase(getActivity()).daoAccess().insertQMList(list);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private class DatabaseCCAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            List<Client> clientList = BaseFactory.getInstance().createMockClientList();
            List<Consultant> consultantList = BaseFactory.getInstance().createMockConsultantList();

            getDatabase(getActivity()).daoAccess().insertClientsList(clientList);
            getDatabase(getActivity()).daoAccess().insertConsultantsList(consultantList);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
