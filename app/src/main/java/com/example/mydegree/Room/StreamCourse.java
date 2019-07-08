package com.example.mydegree.Room;

import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "streamcourse", primaryKeys = {"streamId", "course"})
public class StreamCourse {

    private String streamId;
    private String course;
    private boolean core;

    @Ignore
    public StreamCourse() {};

    public StreamCourse(String streamId, String course, boolean core) {
        this.streamId = streamId;
        this.course = course;
        this.core = core;
    }

    public String getStreamId() {
        return streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public boolean isCore() {
        return core;
    }

    public void setCore(boolean core) {
        this.core = core;
    }
}
