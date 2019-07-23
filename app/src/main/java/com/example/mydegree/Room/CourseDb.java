package com.example.mydegree.Room;

import com.example.mydegree.Room.Course;
import com.example.mydegree.Room.CourseDao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities={Course.class, Program.class, Stream.class, Prereq.class,
        ProgramStream.class, StreamCourse.class, Plan.class},version=1, exportSchema = false)
public abstract class CourseDb extends RoomDatabase {
    public abstract CourseDao courseDao();

}
