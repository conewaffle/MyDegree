package com.example.mydegree.Room;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "course")
public class Course implements Parcelable {

    @PrimaryKey
    @NonNull
    private String courseCode;
    private String courseName;
    private String courseDesc;
    private int level;
    private int courseUoc;
    private double hrs;
    private boolean t1;
    private boolean t2;
    private boolean t3;
    private String otherreq;
    private String mode;

    @Ignore
    public Course(){    }

    public Course(@NonNull String courseCode, String courseName, String courseDesc, int level, int courseUoc, double hrs, boolean t1, boolean t2, boolean t3, String otherreq, String mode) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.courseDesc = courseDesc;
        this.level = level;
        this.courseUoc = courseUoc;
        this.hrs = hrs;
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
        this.otherreq = otherreq;
        this.mode = mode;
    }

    protected Course(Parcel in){
        courseCode = in.readString();
        courseName = in.readString();
        courseDesc = in.readString();
        level = in.readInt();
        courseUoc = in.readInt();
        hrs = in.readDouble();
        t1 = in.readInt()!=0;  //if t1 == true, int ==1
        t2 = in.readInt()!=0;
        t3 = in.readInt()!=0;
        otherreq = in.readString();
        mode = in.readString();
    }


    public static final Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(courseCode);
        dest.writeString(courseName);
        dest.writeString(courseDesc);
        dest.writeInt(level);
        dest.writeInt(courseUoc);
        dest.writeDouble(hrs);
        dest.writeInt(t1?1:0);
        dest.writeInt(t2?1:0);
        dest.writeInt(t3?1:0);
        dest.writeString(otherreq);
        dest.writeString(mode);
    }

    @NonNull
    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(@NonNull String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCourseUoc() {
        return courseUoc;
    }

    public void setCourseUoc(int courseUoc) {
        this.courseUoc = courseUoc;
    }

    public double getHrs() {
        return hrs;
    }

    public void setHrs(double hrs) {
        this.hrs = hrs;
    }

    public boolean isT1() {
        return t1;
    }

    public void setT1(boolean t1) {
        this.t1 = t1;
    }

    public boolean isT2() {
        return t2;
    }

    public void setT2(boolean t2) {
        this.t2 = t2;
    }

    public boolean isT3() {
        return t3;
    }

    public void setT3(boolean t3) {
        this.t3 = t3;
    }

    public String getOtherreq() {
        return otherreq;
    }

    public void setOtherreq(String otherreq) {
        this.otherreq = otherreq;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
