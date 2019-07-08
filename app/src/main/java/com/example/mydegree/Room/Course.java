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
    private String code;
    private String name;
    private String description;
    private int level;
    private int uoc;
    private double hrs;
    private boolean t1;
    private boolean t2;
    private boolean t3;
    private String url;
    private String otherreq;
    private String mode;

    @Ignore
    public Course(){    }

    public Course(@NonNull String code, String name, String description, int level, int uoc, double hrs, boolean t1, boolean t2, boolean t3, String url, String otherreq, String mode) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.level = level;
        this.uoc = uoc;
        this.hrs = hrs;
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
        this.url = url;
        this.otherreq = otherreq;
        this.mode = mode;
    }

    protected Course(Parcel in){
        code = in.readString();
        name = in.readString();
        description = in.readString();
        level = in.readInt();
        uoc = in.readInt();
        hrs = in.readDouble();
        t1 = in.readInt()!=0;  //if t1 == true, int ==1
        t2 = in.readInt()!=0;
        t3 = in.readInt()!=0;
        url = in.readString();
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
        dest.writeString(code);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeInt(level);
        dest.writeInt(uoc);
        dest.writeDouble(hrs);
        dest.writeInt(t1?1:0);
        dest.writeInt(t2?1:0);
        dest.writeInt(t3?1:0);
        dest.writeString(url);
        dest.writeString(otherreq);
        dest.writeString(mode);
    }

    @NonNull
    public String getCode() {
        return code;
    }

    public void setCode(@NonNull String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getUoc() {
        return uoc;
    }

    public void setUoc(int uoc) {
        this.uoc = uoc;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
