package com.example.mydegree.Room;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (tableName = "enrolmentitem", primaryKeys = {"progCode","courseCode"},
        foreignKeys = @ForeignKey(
                entity=EnrolmentInfo.class,
                parentColumns = "progCode",
                childColumns = "progCode",
                onDelete = ForeignKey.CASCADE))
public class EnrolmentItem {

    @NonNull
    private String progCode;
    @NonNull
    private String courseCode;

    @Ignore
    public EnrolmentItem(){}

    public EnrolmentItem(String progCode, String courseCode) {
        this.progCode = progCode;
        this.courseCode = courseCode;
    }

    @NonNull
    public String getProgCode() {
        return progCode;
    }

    public void setProgCode(@NonNull String progCode) {
        this.progCode = progCode;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
}
