package com.example.mydegree.Room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "streamcourse", primaryKeys = {"streamId2", "streamCourse"})
public class StreamCourse {

    @NonNull
    private String streamId2;
    @NonNull
    private String streamCourse;
    private boolean core;

    @Ignore
    public StreamCourse() {}

    public StreamCourse(String streamId2, String streamCourse, boolean core) {
        this.streamId2 = streamId2;
        this.streamCourse = streamCourse;
        this.core = core;
    }

    public String getStreamId2() {
        return streamId2;
    }

    public void setStreamId2(String streamId) {
        this.streamId2 = streamId;
    }

    public String getStreamCourse() {
        return streamCourse;
    }

    public void setStreamCourse(String course) {
        this.streamCourse = course;
    }

    public boolean isCore() {
        return core;
    }

    public void setCore(boolean core) {
        this.core = core;
    }
}
