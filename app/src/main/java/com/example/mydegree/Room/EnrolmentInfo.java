package com.example.mydegree.Room;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (tableName = "enrolmentinfo")
public class EnrolmentInfo {

    @PrimaryKey
    @NonNull
    private String progCode;
    private String majorId;

    @Ignore
    public EnrolmentInfo(){}

    public EnrolmentInfo(String progCode, String majorId) {
        this.progCode = progCode;
        this.majorId = majorId;
    }

    @NonNull
    public String getProgCode() {
        return progCode;
    }

    public void setProgCode(@NonNull String progCode) {
        this.progCode = progCode;
    }

    public String getMajorId() {
        return majorId;
    }

    public void setMajorId(String majorId) {
        this.majorId = majorId;
    }
}
