package com.example.mydegree.Room;

import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "prereq", primaryKeys = {"course","prereq"})
public class Prereq {

    private String course;
    private String prereq;

    @Ignore
    public Prereq() {};

    public Prereq(String course, String prereq) {
        this.course = course;
        this.prereq = prereq;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getPrereq() {
        return prereq;
    }

    public void setPrereq(String prereq) {
        this.prereq = prereq;
    }
}
