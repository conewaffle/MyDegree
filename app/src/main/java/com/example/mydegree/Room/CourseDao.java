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

    @Query("SELECT * FROM prereq WHERE postreq = :postreq")
    List<Prereq> getPrereqs(String postreq);

    @Query("SELECT * FROM program WHERE progCode = :progCode")
    List<Program> getPrograms(String progCode);

    @Query("SELECT * FROM course WHERE courseCode = :query")
    List<Course> getCourseByCode(String query);

    @Query("SELECT * FROM stream WHERE streamProg = :prog")
    List<Stream> getStreams(String prog);

    @Query("SELECT * FROM streamcourse WHERE streamId2 = :stream")
    List<StreamCourse> getStreamCourses(String stream);

    @Update
    void updateCourse(Course course);

}
