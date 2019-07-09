package com.example.mydegree.Room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "prereq", primaryKeys = {"course","prereq"})
public class Prereq {

    @NonNull
    private String postreq;
    @NonNull
    private String prereq;

    @Ignore
    public Prereq() {}

    public Prereq(String postreq, String prereq) {
        this.postreq = postreq;
        this.prereq = prereq;
    }

    public String getPostreq() { return postreq;    }

    public void setPostreq(String postreq) {
        this.postreq = postreq;
    }

    public String getPrereq() {
        return prereq;
    }

    public void setPrereq(String prereq) {
        this.prereq = prereq;
    }
}
