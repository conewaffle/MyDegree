package com.example.mydegree.Room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "streamcourse", primaryKeys = {"streamId", "course"})
public class StreamCourse {

    @NonNull
    private String streamId2;
    @NonNull
    private String streamCourse;
    private boolean core;

    @Ignore
    public StreamCourse() {};

    public StreamCourse(String streamId, String course, boolean core) {
        this.streamId2 = streamId;
        this.streamCourse = course;
        this.core = core;
    }

    public String getStreamId() {
        return streamId2;
    }

    public void setStreamId(String streamId) {
        this.streamId2 = streamId;
    }

    public String getCourse() {
        return streamCourse;
    }

    public void setCourse(String course) {
        this.streamCourse = course;
    }

    public boolean isCore() {
        return core;
    }

    public void setCore(boolean core) {
        this.core = core;
    }
}
