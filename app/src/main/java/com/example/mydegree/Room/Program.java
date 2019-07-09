package com.example.mydegree.Room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "program")
public class Program {
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
}
