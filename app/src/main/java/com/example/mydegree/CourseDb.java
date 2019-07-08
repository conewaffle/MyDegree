package com.example.mydegree;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities={Course.class},version=1)
public abstract class CourseDb extends RoomDatabase {
    public abstract CourseDao courseDao();

}
