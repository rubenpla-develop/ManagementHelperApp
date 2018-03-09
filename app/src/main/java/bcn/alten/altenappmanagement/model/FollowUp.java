package bcn.alten.altenappmanagement.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys =
        {@ForeignKey(entity = Client.class, parentColumns = "id",
        childColumns = "clientId", onDelete = CASCADE),

        @ForeignKey(entity = Consultant.class, parentColumns = "id",
        childColumns = "consultantId", onDelete = CASCADE)})
public class FollowUp implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String consultantId;

    private String consultorName;

    private String clientId;

    private String currentClient;

    private String dateLastFollow;

    private String dateNextFollow;

    private String status;

    public FollowUp(@NonNull String consultantId, @NonNull String consultorName,
                    @NonNull  String clientId, @NonNull  String currentClient,
                    @NonNull String dateLastFollow, @NonNull String dateNextFollow,
                    @NonNull String status) {
        this.consultantId = consultantId;
        this.consultorName = consultorName;
        this.clientId = clientId;
        this.currentClient = currentClient;
        this.dateLastFollow = dateLastFollow;
        this.dateNextFollow = dateNextFollow;
        this.status = status;

    }

    protected FollowUp(Parcel in) {
        consultantId = in.readString();
        consultorName = in.readString();
        clientId = in.readString();
        currentClient = in.readString();
        dateLastFollow = in.readString();
        dateNextFollow = in.readString();
        status = in.readString();
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
        dest.writeString(consultantId);
        dest.writeString(consultorName);
        dest.writeString(clientId);
        dest.writeString(currentClient);
        dest.writeString(dateLastFollow);
        dest.writeString(dateNextFollow);
        dest.writeString(status);
    }

    public void setFollowUpStatusToDone(@NonNull String dateLastFollow, @NonNull String status) {
        this.dateLastFollow = dateLastFollow;
        dateNextFollow = "";
        this.status = status;
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

    public String getDateNextFollow() {
        return dateNextFollow;
    }

    public void setDateNextFollow(String dateNextFollow) {
        this.dateNextFollow = dateNextFollow;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConsultantId() {
        return consultantId;
    }

    public String getClientId() {
        return clientId;
    }
}