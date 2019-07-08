package com.example.mydegree.Room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "program")
public class Program {
    @PrimaryKey
    @NonNull
    private String code;
    private String name;
    private String description;
    private int uoc;
    private int years;
    private String url;

    @Ignore
    public Program() {};

    public Program(@NonNull String code, String name, String description, int uoc, int years, String url) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.uoc = uoc;
        this.years = years;
        this.url = url;
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

    public int getUoc() {
        return uoc;
    }

    public void setUoc(int uoc) {
        this.uoc = uoc;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
