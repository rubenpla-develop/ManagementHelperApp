package bcn.alten.altenappmanagement.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import bcn.alten.altenappmanagement.mvp.model.FollowUp;
import bcn.alten.altenappmanagement.mvp.model.QMItem;

@Database(entities = {FollowUp.class, QMItem.class}, version = 3)
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
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE FollowUp"
                    + " ADD COLUMN status TEXT");
        }
    };

    /**
     * Migrate from:
     * version 2 - using Room
     * to
     * version 3 - using Room where the {@link AltenDatabase} has an extra table: 'qm'
     * (aka.: QMItem.class)
     */
    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE qm(id INTEGER NOT NULL, week INTEGER NOT NULL, " +
                    "clientName TEXT NOT NULL, " +
                    "clientPhone TEXT NOT NULL, " +
                    "candidateName TEXT NOT NULL, " +
                    "candidatePhone TEXT NOT NULL, " +
                    "status TEXT NOT NULL," +
                    "PRIMARY KEY(id))");
        }
    };

    public static AltenDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),

                        AltenDatabase.class, DbKeys.DATABASE_NAME)
                        .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                        .build();
            }
        }

        return INSTANCE;
    }

    public abstract DaoAccess daoAccess();
}
