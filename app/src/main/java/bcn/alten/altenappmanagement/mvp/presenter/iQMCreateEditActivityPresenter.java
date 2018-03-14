package bcn.alten.altenappmanagement.mvp.presenter;

import android.arch.lifecycle.LiveData;

/**
 * Created by alten on 14/3/18.
 */

public interface iQMCreateEditActivityPresenter {

    LiveData<String> getConsultantById(int id);

    LiveData<String> getClientById(int id);

    LiveData<String> getConsultantByName(String name);

    LiveData<String> getClientByName(String name);
 }
