package bcn.alten.altenappmanagement.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by alten on 8/3/18.
 */

@Entity(tableName = "consultant")
public class Consultant implements Parcelable{

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String name;

    @NonNull
    private String phone;

    public Consultant(@NonNull String name, @NonNull String phone) {
        this.name = name;
        this.phone = phone;
    }

    protected Consultant(Parcel in) {
        id = in.readInt();
        name = in.readString();
        phone = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(phone);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Consultant> CREATOR = new Creator<Consultant>() {
        @Override
        public Consultant createFromParcel(Parcel in) {
            return new Consultant(in);
        }

        @Override
        public Consultant[] newArray(int size) {
            return new Consultant[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getPhone() {
        return phone;
    }

    public void setPhone(@NonNull String phone) {
        this.phone = phone;
    }
}
