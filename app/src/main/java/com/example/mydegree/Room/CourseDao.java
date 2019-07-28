package com.example.mydegree.Room;

import com.example.mydegree.Bookmark;
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
    void insertStream(Stream stream);

    @Insert
    void insertStreamCourses(StreamCourse streamCourse);

    @Insert
    long insertPlanInfo(PlanInfo planInfo);

    @Insert
    long insertPlan(Plan plan);

    @Query("SELECT * FROM course WHERE courseCode LIKE :query OR courseName LIKE :query")
    List<Course> getSearchCourses(String query);

    @Query("SELECT * FROM prereq WHERE postreq = :postreq")
    List<Prereq> getPrereqs(String postreq);

    @Query("SELECT * FROM program WHERE progCode = :progCode")
    List<Program> getPrograms(String progCode);

    @Query("SELECT * FROM course WHERE courseCode = :query")
    List<Course> getCourseByCode(String query);

    @Query("SELECT * FROM stream WHERE streamProg = :prog AND isMajor = '0'")
    List<Stream> getStreams(String prog);

    @Query("SELECT * FROM streamcourse WHERE streamId2 = :stream")
    List<StreamCourse> getStreamCourses(String stream);

    @Query("SELECT * FROM streamcourse WHERE streamId2 = :stream AND core = '1'")
    List<StreamCourse> getMajorCores(String stream);

    @Query("SELECT * FROM streamcourse WHERE streamId2 = :stream AND core = '0'")
    List<StreamCourse> getMajorElecs(String stream);

    @Query("SELECT progCode AS courseCode, progName AS courseName FROM program")
    List<Bookmark> getProgramList();

    @Query("SELECT id AS courseCode, streamName AS courseName FROM stream WHERE isMajor = '1'")
    List<Bookmark> getMajors();

    @Query("SELECT streamId2, streamCourse, core FROM streamcourse a JOIN stream b ON a.streamId2=b.id JOIN course c ON a.streamCourse = c.courseCode WHERE b.streamProg = :program AND c.t1 = '1' ")
    List<StreamCourse> getTermOne(String program);

    @Query("SELECT streamId2, streamCourse, core FROM streamcourse a JOIN stream b ON a.streamId2=b.id JOIN course c ON a.streamCourse = c.courseCode WHERE b.streamProg = :program AND c.t2 = '1' ")
    List<StreamCourse> getTermTwo(String program);

    @Query("SELECT streamId2, streamCourse, core FROM streamcourse a JOIN stream b ON a.streamId2=b.id JOIN course c ON a.streamCourse = c.courseCode WHERE b.streamProg = :program AND c.t3 = '1' ")
    List<StreamCourse> getTermThree(String program);

    @Query("SELECT streamId2 AS streamId, streamName,  streamCourse, core FROM streamcourse a JOIN stream b ON a.streamId2=b.id JOIN course c ON a.streamCourse = c.courseCode WHERE b.streamProg = :program AND c.t1 = '1' ")
    List<StreamCoursePlan> getTermOneX(String program);

    @Query("SELECT streamId2 AS streamId, streamName,  streamCourse, core FROM streamcourse a JOIN stream b ON a.streamId2=b.id JOIN course c ON a.streamCourse = c.courseCode WHERE b.streamProg = :program AND c.t2 = '1' ")
    List<StreamCoursePlan> getTermTwoX(String program);

    @Query("SELECT streamId2 AS streamId, streamName,  streamCourse, core FROM streamcourse a JOIN stream b ON a.streamId2=b.id JOIN course c ON a.streamCourse = c.courseCode WHERE b.streamProg = :program AND c.t3 = '1' ")
    List<StreamCoursePlan> getTermThreeX(String program);

    @Query("SELECT * FROM planinfo")
    List<PlanInfo> getAllPlanInfos();

    @Query("SELECT * FROM 'plan' WHERE planId = :planId")
    List<Plan> getPlanItems(int planId);


    @Update
    void updateCourse(Course course);

}
