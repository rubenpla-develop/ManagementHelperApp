package bcn.alten.altenappmanagement.mvp.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity
public class FollowUp implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    //@Embedded(prefix = DbKeys.FOLLOWUP_PREFIX)
    private String consultorName;

    //@Embedded(prefix = DbKeys.FOLLOWUP_PREFIX)
    private String currentClient;

    //@Embedded(prefix = DbKeys.FOLLOWUP_PREFIX)
    private String dateLastFollow;

    public FollowUp(@NonNull String consultorName, @NonNull  String currentClient,
                    @NonNull String dateLastFollow) {
        this.consultorName = consultorName;
        this.currentClient = currentClient;
        this.dateLastFollow = dateLastFollow;

    }

    protected FollowUp(Parcel in) {
        consultorName = in.readString();
        currentClient = in.readString();
        dateLastFollow = in.readString();
    }

    public static final Creator<FollowUp> CREATOR = new Creator<FollowUp>() {
        @Override
        public FollowUp createFromParcel(Parcel in) {
            return new FollowUp(in);
        }

        @Override
        public FollowUp[] newArray(int size) {
            return new FollowUp[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(consultorName);
        dest.writeString(currentClient);
        dest.writeString(dateLastFollow);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConsultorName() {
        return consultorName;
    }

    public void setConsultorName(String consultorName) {
        this.consultorName = consultorName;
    }

    public String getCurrentClient() {
        return currentClient;
    }

    public void setCurrentClient(String currentClient) {
        this.currentClient = currentClient;
    }

    public String getDateLastFollow() {
        return dateLastFollow;
    }

    public void setDateLastFollow(String dateLastFollow) {
        this.dateLastFollow = dateLastFollow;
    }
}