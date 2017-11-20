package bcn.alten.altenappmanagement.mvp.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity(tableName = "qm")
public class QMItem implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private int week;

    @NonNull
    private String clientName;

    @NonNull
    private String clientPhone;

    @NonNull
    private String candidateName;

    @NonNull
    private String candidatePhone;

    @NonNull
    private String status;

    public QMItem(int week, String clientName,String clientPhone, String candidateName, String candidatePhone,
                   String status) {
        this.week = week;
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.candidateName = candidateName;
        this.candidatePhone = candidatePhone;
        this.status = status;
    }

    protected QMItem(Parcel in) {
        week = in.readInt();
        clientName = in.readString();
        clientPhone = in.readString();
        candidateName = in.readString();
        candidatePhone = in.readString();
        status = in.readString();
    }

    public static final Creator<QMItem> CREATOR = new Creator<QMItem>() {
        @Override
        public QMItem createFromParcel(Parcel in) {
            return new QMItem(in);
        }

        @Override
        public QMItem[] newArray(int size) {
            return new QMItem[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getCandidatePhone() {
        return candidatePhone;
    }

    public void setCandidatePhone(String candidatePhone) {
        this.candidatePhone = candidatePhone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(week);
        dest.writeString(clientName);
        dest.writeString(clientPhone);
        dest.writeString(candidateName);
        dest.writeString(candidatePhone);
        dest.writeString(status);
    }
}
