package com.example.mydegree.Room;

import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "programstream", primaryKeys = {"programCode", "streamId"})
public class ProgramStream {

    private String programCode;
    private String streamId;

    @Ignore
    public ProgramStream() {};

    public ProgramStream(String programCode, String streamId) {
        this.programCode = programCode;
        this.streamId = streamId;
    }

    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public String getStreamId() {
        return streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }
}

