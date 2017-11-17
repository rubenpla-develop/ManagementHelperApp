package bcn.alten.altenappmanagement.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.VisibleForTesting;

import bcn.alten.altenappmanagement.mvp.model.FollowUp;

@Database(entities = {FollowUp.class}, version = 3)
public abstract class AltenDatabase extends RoomDatabase {

    private static AltenDatabase INSTANCE;
    private static final Object LOCK = new Object();

    /**
     * Migrate from:
     * version 1 - using Room
     * to
     * version 2 - using Room where the {@link FollowUp} has an extra field: status
     */
    @VisibleForTesting
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE FollowUp"
                    + " ADD COLUMN status TEXT");
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE FollowUp"
                    + " ADD COLUMN question TEXT");
        }
    };

    public static AltenDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),

                        AltenDatabase.class, DbKeys.DATABASE_NAME)
                        .addMigrations(MIGRATION_1_2,MIGRATION_2_3)
                        .build();
            }
        }

        return INSTANCE;
    }

    public abstract DaoAccess daoAccess();

}
