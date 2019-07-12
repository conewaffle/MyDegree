package com.example.mydegree.Room;

import com.example.mydegree.Room.Course;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface CourseDao {
    @Query("SELECT * FROM course")
    List<Course> getCourses();

    @Insert
    void insertCourse(Course course);

    @Insert
    void insertProgram(Program program);

    @Insert
    void insertPrereq(Prereq prereq);

    @Insert
    void insertProgramStreams(ProgramStream programStream);

    @Insert
    void insertStreamCourses(StreamCourse streamCourse);

    @Query("SELECT * FROM course WHERE courseCode LIKE :query OR courseName LIKE :query")
    List<Course> getSearchCourses(String query);

    @Update
    void updateCourse(Course course);

}
