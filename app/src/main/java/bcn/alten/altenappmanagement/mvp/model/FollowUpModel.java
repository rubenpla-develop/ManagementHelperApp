package bcn.alten.altenappmanagement.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FollowUpModel implements Parcelable {

    private String consultorName;
    private String currentClient;
    private long dateLastFollow;
    private String status;
    private String comments;

    protected FollowUpModel(Parcel in) {
        consultorName = in.readString();
        currentClient = in.readString();
        dateLastFollow = in.readLong();
        status = in.readString();
        comments = in.readString();
    }

    public static final Creator<FollowUpModel> CREATOR = new Creator<FollowUpModel>() {
        @Override
        public FollowUpModel createFromParcel(Parcel in) {
            return new FollowUpModel(in);
        }

        @Override
        public FollowUpModel[] newArray(int size) {
            return new FollowUpModel[size];
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
        dest.writeLong(dateLastFollow);
        dest.writeString(status);
        dest.writeString(comments);
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

    public long getDateLastFollow() {
        return dateLastFollow;
    }

    public void setDateLastFollow(long dateLastFollow) {
        this.dateLastFollow = dateLastFollow;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}