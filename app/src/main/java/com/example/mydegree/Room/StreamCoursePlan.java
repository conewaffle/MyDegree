package com.example.mydegree.Room;

import androidx.room.Ignore;

public class StreamCoursePlan {

    private String streamId;
    private String streamName;
    private String streamCourse;
    private boolean core;

    @Ignore
    public StreamCoursePlan() {}

    public StreamCoursePlan(String streamId, String streamName, String streamCourse, boolean core) {
        this.streamId = streamId;
        this.streamName = streamName;
        this.streamCourse = streamCourse;
        this.core = core;
    }

    public String getStreamId() {
        return streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public String getStreamCourse() {
        return streamCourse;
    }

    public void setStreamCourse(String streamCourse) {
        this.streamCourse = streamCourse;
    }

    public boolean isCore() {
        return core;
    }

    public void setCore(boolean core) {
        this.core = core;
    }
}