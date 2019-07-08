package com.example.mydegree.Room;

import com.example.mydegree.Room.Course;
import com.example.mydegree.Room.CourseDao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities={Course.class},version=1)
public abstract class CourseDb extends RoomDatabase {
    public abstract CourseDao courseDao();

}
