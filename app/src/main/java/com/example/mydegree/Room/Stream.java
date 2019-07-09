package com.example.mydegree.Room;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "stream")
public class Stream {

    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private int uoc;
    private String program;
    private boolean ismajor;

    @Ignore
    public Stream() {};

    public Stream(String id, String name, int uoc, String program, boolean ismajor) {
        this.id = id;
        this.name = name;
        this.uoc = uoc;
        this.program = program;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUoc() {
        return uoc;
    }

    public void setUoc(int uoc) {
        this.uoc = uoc;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }


    public boolean isIsmajor() {
        return ismajor;
    }

    public void setIsmajor(boolean ismajor) {
        this.ismajor = ismajor;
    }

}
