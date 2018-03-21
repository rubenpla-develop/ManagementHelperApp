package bcn.alten.altenappmanagement.data.db;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import bcn.alten.altenappmanagement.model.Client;
import bcn.alten.altenappmanagement.model.Consultant;
import bcn.alten.altenappmanagement.model.FollowUp;
import bcn.alten.altenappmanagement.model.QMItem;

@Dao
public interface DaoAccess {

    /*
     * FollowUp DAO
     */
    @Query("SELECT * FROM FollowUp")
    LiveData<List<FollowUp>> fecthFollowUpData();

    @Insert
    void insertFollowUpList(List<FollowUp> followUpList);

    @Insert
    void createNewFollowUp(FollowUp followUp);

    @Update
    void updateFollowUp(FollowUp followUp);

    @Delete
    void deleteFollowUp(FollowUp followUp);
    
    /*
     * QM DAO
     */
    @Query("SELECT * FROM qm")
    LiveData<List<QMItem>> fecthQMData();

    @Query("SELECT * FROM QM WHERE week =:weekIndex")
    LiveData<List<QMItem>> fetchQMDataByWeek(int weekIndex);

    @Insert
    void insertQMList(List<QMItem> qmList);

    @Insert
    void createNewQM(QMItem qm);

    @Update
    void updateQM(QMItem qm);

    @Delete
    void deleteQM(QMItem qm);

    /*
     * Consultant & Client Tables
     */

    @Query("SELECT * FROM client")
    LiveData<List<Client>> fetchClientData();

    @Query("SELECT * FROM consultant")
    LiveData<List<Client>> fetchConsultantData();


    /*
     * Queries BY NAME
     */
    @Query("SELECT * FROM client WHERE name LIKE :targetName" ) //TODO OK
    LiveData<Client> getClientByName(String targetName);   // GET ITEM

    @Query("SELECT * FROM consultant WHERE name = :targetName")//TODO OK
    LiveData<Client> getConsultantByName(String targetName); // GET ITEM

    @Query("SELECT id FROM client WHERE name LIKE :targetName" ) //TODO OK
    LiveData<Integer> getClientIdByName(String targetName);  // GET 'id' row

    @Query("SELECT id FROM consultant WHERE name = :targetName")//TODO OK
    LiveData<Integer> getConsultantIdByName(String targetName); //GET 'id' row



    /*
     * Queries BY ID
     */
    @Query("SELECT * FROM client WHERE id =:targetId")//TODO OK
    LiveData<Client> getClientById(int targetId); // GET ITEM

    @Query("SELECT * FROM consultant WHERE id =:targetId") //TODO OK
    LiveData<Client> getConsultantById(int targetId);  //GET ITEM

    @Query("SELECT IFNULL(name, 'NO_NAME_FOUND')name FROM client WHERE id =:targetId") //TODO OK
    LiveData<String> getClientNameById(int targetId);//GET 'name' row

    @Query("SELECT IFNULL(name, 'NO_NAME_FOUND')name FROM consultant WHERE id =:targetId")//TODO OK
    LiveData<String> getConsultantNameById(int targetId); //GET 'name' row


    /*
     *  INSERT Queries  for Client & Consultant Pojos
     */
    @Insert
    void insertClientsList(List<Client> clientList);

    @Insert
    void insertConsultantsList(List<Consultant> consultantList);

    @Insert
    void insertClient(Client client);

    @Insert
    void insertConsultant(Client client);

   /* @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);*/
   /* @Insert
    void insertMultipleRecord(University... universities);*/
}
