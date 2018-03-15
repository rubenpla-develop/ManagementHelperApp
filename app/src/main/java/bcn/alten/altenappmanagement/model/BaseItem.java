package bcn.alten.altenappmanagement.model;

import android.arch.persistence.room.PrimaryKey;

/**
 * Created by alten on 15/3/18.
 */

public class BaseItem {

    @PrimaryKey(autoGenerate = true)
    protected int id;

    public BaseItem() {}
}
