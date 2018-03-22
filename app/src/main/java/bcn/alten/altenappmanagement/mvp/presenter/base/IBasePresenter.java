package bcn.alten.altenappmanagement.mvp.presenter.base;

import bcn.alten.altenappmanagement.model.Client;

public interface IBasePresenter {

    /*
     * BY ID
     */
    Client getConsultantById(int idToCheck);

    Client getClientById(int idToCheck);

    String getConsultantNameById(int idToCheck);

    String getClientNameById(int idToCheck);


    /*
     * BY NAME
     */
    Client getConsultantByName(String nameToCheck);

    Client getClientByName(String nameToCheck);

    int getConsultantIdByName(String nameToCheck);

    int getClientIdByName(String nameToCheck);
}
