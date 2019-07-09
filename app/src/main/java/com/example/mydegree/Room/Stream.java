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
    private String streamName;
    private int streamUoc;
    private boolean isMajor;

    @Ignore
    public Stream() {}

    public Stream(String id, String streamName, int uoc, boolean isMajor) {
        this.id = id;
        this.streamName = streamName;
        this.streamUoc = uoc;
        this.isMajor = isMajor;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public int getStreamUoc() {
        return streamUoc;
    }

    public void setStreamUoc(int streamUoc) {
        this.streamUoc = streamUoc;
    }

    public boolean isMajor() {
        return isMajor;
    }

    public void setMajor(boolean major) {
        isMajor = major;
    }
}
