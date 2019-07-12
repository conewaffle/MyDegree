package com.example.mydegree.Room;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "stream", primaryKeys = {"id","streamProg"})
public class Stream {

    @NonNull
    private String id;
    private String streamName;
    private int streamUoc;
    private boolean isMajor;
    @NonNull
    private String streamProg;

    @Ignore
    public Stream() {}

    public Stream(@NonNull String id, String streamName, int streamUoc, boolean isMajor, String streamProg) {
        this.id = id;
        this.streamName = streamName;
        this.streamUoc = streamUoc;
        this.isMajor = isMajor;
        this.streamProg = streamProg;
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

    public String getStreamProg() { return streamProg; }

    public void setStreamProg(String streamProg) { this.streamProg = streamProg; }
}
