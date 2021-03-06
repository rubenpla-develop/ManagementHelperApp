package bcn.alten.altenappmanagement.data.db;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import bcn.alten.altenappmanagement.model.FollowUp;
import bcn.alten.altenappmanagement.model.QMItem;

@Dao
public interface DaoAccess {

    /*
     * FOllowUp DAO
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


   /* @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);*/
   /* @Insert
    void insertMultipleRecord(University... universities);

    @Insert
    void insertMultipleListRecord(List<University> universities);

    @Insert
    void insertOnlySingleRecord(University university);

    @Query("SELECT * FROM University")
    List<University> fetchAllData();

    @Query("SELECT * FROM University WHERE clgid =:college_id")
    University getSingleRecord(int college_id);

    @Update
    void updateRecord(University university);

    @Delete
    void deleteRecord(University university);*/
}
