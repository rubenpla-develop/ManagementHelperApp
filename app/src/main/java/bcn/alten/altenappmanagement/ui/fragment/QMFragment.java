package bcn.alten.altenappmanagement.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bcn.alten.altenappmanagement.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class QMFragment extends Fragment {


    public QMFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_qm, container, false);
    }

}
