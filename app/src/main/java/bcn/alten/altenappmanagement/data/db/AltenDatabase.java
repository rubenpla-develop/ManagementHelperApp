package bcn.alten.altenappmanagement.data.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import bcn.alten.altenappmanagement.model.Client;
import bcn.alten.altenappmanagement.model.Consultant;
import bcn.alten.altenappmanagement.model.FollowUp;
import bcn.alten.altenappmanagement.model.QMItem;

@Database(entities = {FollowUp.class, QMItem.class, Consultant.class, Client.class}, version = 4)
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
     * version 2 - using Room where the {@link FollowUp} has an extra field: status
     * to
     * version 3 - using Room where the {@link AltenDatabase} has an extra table: 'qm'
     * (aka.: QMItem.class)
     */
    @VisibleForTesting
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

    /**
     * Migrate from:
     * version 3 - using Room where the {@link AltenDatabase} has an extra table: 'qm'
     * (aka.: QMItem.class)
     * to
     * version 4 - using Room where the {@link AltenDatabase} has TWO extra tables: 'consultant'
     *  & 'client', both have foreign keys provided to 'qm' & 'FollowUp' tables (clientId &
     *  consultantId respectively)
     */
    @VisibleForTesting
    static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE client(id INTEGER NOT NULL, clientName TEXT NOT NULL, " +
                    "phone TEXT NOT NULL,PRIMARY KEY(id))");

            database.execSQL("CREATE TABLE consultant(id INTEGER NOT NULL, consultantName TEXT NOT NULL, " +
                    "phone TEXT NOT NULL,PRIMARY KEY(id))");

            //TODO Set FOREIGN KEYS for FollowUp
            //todo NOT possible with SQLite to add CONSTRAINTS/FOREIGN keys
            //todo save data in temp table, drop and create new one, then repopulate
            //todo NOT FINISHED

            /**   REFERENCE
             *
             * SQLite supports a limited subset of ALTER TABLE. The ALTER TABLE command in SQLite
             * allows the user to rename a table or add a new column to an existing table. It is not
             * possible to rename a column, remove a column, or add or remove constraints from a table.
             *
             */

            //Copy all content to a temp table, after that we delete current original table
            database.execSQL("CREATE TABLE IF NOT EXISTS tempFollowUp(id INTEGER NOT NULL, consultorName INTEGER NOT NULL, " +
                    "clientName TEXT NOT NULL, " +
                    "dateLastFollow TEXT NOT NULL, " +
                    "dateNextFollow TEXT NOT NULL, " +
                    "status TEXT NOT NULL, " +
                    "PRIMARY KEY(id));");

            database.execSQL("ALTER TABLE tempFollowUp ADD COLUMN clientId TEXT");
            database.execSQL("ALTER TABLE tempFollowUp ADD COLUMN consultantId TEXT");

            //database.execSQL("SELECT * INTO tempFollowUp FROM FollowUp");
            database.execSQL("INSERT INTO tempFollowUp SELECT * FROM FollowUp");
            database.execSQL("DROP TABLE FollowUp");


            //We recreate original table with new fields, etc... And after we Repopulate it
            database.execSQL("CREATE TABLE IF NOT EXISTS FollowUp(id INTEGER NOT NULL, " +
                    "consultantId TEXT, " +
                    "consultorName INTEGER NOT NULL, " +
                    "clientId TEXT, " +
                    "clientName TEXT NOT NULL, " +
                    "dateLastFollow TEXT NOT NULL," +
                    "dateNextFollow TEXT NOT NULL," +
                    "status TEXT NOT NULL," +
                    "FOREIGN KEY (clientId) REFERENCES client(id)," +
                    "FOREIGN KEY (consultantId) REFERENCES consultant(id)," +
                    "PRIMARY KEY(id));");
            database.execSQL("INSERT INTO FollowUp SELECT * FROM tempFollowUp");

            database.execSQL("DROP TABLE tempFollowUp");


        }
    };

    public static AltenDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),

                        AltenDatabase.class, DbKeys.DATABASE_NAME)
                        .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
                        .build();
            }
        }

        return INSTANCE;
    }

    public abstract DaoAccess daoAccess();
}
