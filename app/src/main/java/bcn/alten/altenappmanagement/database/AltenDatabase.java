package bcn.alten.altenappmanagement.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import bcn.alten.altenappmanagement.mvp.model.FollowUp;

@Database(entities = {FollowUp.class}, version = 1)
public abstract class AltenDatabase extends RoomDatabase {

    private static AltenDatabase INSTANCE;
    private static final Object LOCK = new Object();

    public static AltenDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        AltenDatabase.class, "alten_db").build();
            }
        }

        return INSTANCE;
    }

    public abstract DaoAccess daoAccess();

}
