package bcn.alten.altenappmanagement.mvp.view;

import android.arch.lifecycle.LiveData;

import java.util.List;

import bcn.alten.altenappmanagement.model.Client;
import bcn.alten.altenappmanagement.mvp.view.base.IBaseView;

/**
 * Created by alten on 14/3/18.
 */

public interface IQMCreateEditActivityView extends IBaseView {

    void setConsultantAutoCompleteAdapter(LiveData<List<Client>> list);
    void setClientAutoCompleteAdapter(LiveData<List<Client>> list);
}
