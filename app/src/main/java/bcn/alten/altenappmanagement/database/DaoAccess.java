package bcn.alten.altenappmanagement.database;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import bcn.alten.altenappmanagement.mvp.model.FollowUp;

@Dao
public interface DaoAccess {

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
