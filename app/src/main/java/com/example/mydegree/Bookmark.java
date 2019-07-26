package com.example.mydegree;

import androidx.room.Ignore;

public class Bookmark {

    private String courseCode, courseName;

    @Ignore
    public Bookmark() { }

    public Bookmark (String courseCode, String courseName) {
        this.courseCode = courseCode;
        this.courseName = courseName;

    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}