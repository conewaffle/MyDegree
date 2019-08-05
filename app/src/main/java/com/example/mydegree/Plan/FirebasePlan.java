package com.example.mydegree.Plan;

public class FirebasePlan {
    private String courseCode;

    public FirebasePlan() {}

    public FirebasePlan(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
}
