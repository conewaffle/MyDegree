package com.example.mydegree.Room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;

@Entity(tableName = "plan", primaryKeys = {"planId","courseCode"},
        foreignKeys = @ForeignKey(
                entity=PlanInfo.class,
                parentColumns = "planId",
                childColumns = "planId",
                onDelete = ForeignKey.CASCADE))

public class Plan {

    @NonNull
    private int planId;
    private int year;
    private int term;
    @NonNull
    private String courseCode;

    @Ignore
    public Plan(){}

    public Plan(int planId, int year, int term, String courseCode) {
        this.planId = planId;
        this.year = year;
        this.term = term;
        this.courseCode = courseCode;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

}
