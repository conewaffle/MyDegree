package com.example.mydegree;

import com.example.mydegree.Room.Course;

public class Bookmark {

    public String courseCode;

    public Bookmark() { }

    public Bookmark(String courseCode, String courseName) {
        this.courseCode = courseCode;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

}
