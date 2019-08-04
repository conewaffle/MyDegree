package com.example.mydegree.Room;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (tableName = "planinfo")
public class PlanInfo {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int planId;
    private String planName;
    private String progCode;
    private String majorId;

    @Ignore
    public PlanInfo(){}

    public PlanInfo(int planId, String planName, String progCode, String majorId) {
        this.planId = planId;
        this.planName = planName;
        this.progCode = progCode;
        this.majorId = majorId;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getProgCode() {
        return progCode;
    }

    public void setProgCode(String progCode) {
        this.progCode = progCode;
    }

    public String getMajorId() {
        return majorId;
    }

    public void setMajorId(String majorId) {
        this.majorId = majorId;
    }
}
