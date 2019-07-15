package com.example.mydegree.Room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;

//DECIDED TO NOT USE. JUST ADD PROGRAM CODE TO STREAM INSTEAD.

@Entity(tableName = "programstream", primaryKeys = {"programCode", "streamId"})
public class ProgramStream {

    @NonNull
    private String programCode;
    @NonNull
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

