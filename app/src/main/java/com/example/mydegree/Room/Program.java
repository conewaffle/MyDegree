package com.example.mydegree.Room;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "program")
public class Program implements Parcelable {
    @PrimaryKey
    @NonNull
    private String progCode;
    private String progName;
    private String progDesc;
    private int progUoc;
    private int years;

    @Ignore
    public Program() {}

    public Program(@NonNull String progCode, String progName, String progDesc, int progUoc, int years) {
        this.progCode = progCode;
        this.progName = progName;
        this.progDesc = progDesc;
        this.progUoc = progUoc;
        this.years = years;
    }

    public static final Creator<Program> CREATOR = new Creator<Program>() {
        @Override
        public Program createFromParcel(Parcel in) {
            return new Program(in);
        }

        @Override
        public Program[] newArray(int size) {
            return new Program[size];
        }
    };


    @NonNull
    public String getProgCode() {
        return progCode;
    }

    public void setProgCode(@NonNull String progCode) {
        this.progCode = progCode;
    }

    public String getProgName() {
        return progName;
    }

    public void setProgName(String progName) {
        this.progName = progName;
    }

    public String getProgDesc() {
        return progDesc;
    }

    public void setProgDesc(String progDesc) {
        this.progDesc = progDesc;
    }

    public int getProgUoc() {
        return progUoc;
    }

    public void setProgUoc(int progUoc) {
        this.progUoc = progUoc;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected Program(Parcel in){
        progCode = in.readString();
        progName = in.readString();
        progDesc = in.readString();
        progUoc = in.readInt();
        years = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(progCode);
        dest.writeString(progName);
        dest.writeString(progDesc);
        dest.writeInt(progUoc);
        dest.writeInt(years);
    }
}
