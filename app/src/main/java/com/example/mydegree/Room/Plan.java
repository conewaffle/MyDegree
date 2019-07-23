package com.example.mydegree.Room;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "plan", primaryKeys = {"planId","courseCode"})
public class Plan {

    @NonNull
    private int planId;
    private String progCode;
    private int year;
    private int term;
    @NonNull
    private String courseCode;

    public Plan(){}

    public Plan(int planId, String progCode, int year, int term, String courseCode) {
        this.planId = planId;
        this.progCode = progCode;
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

    public String getProgCode() {
        return progCode;
    }

    public void setProgCode(String progCode) {
        this.progCode = progCode;
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
