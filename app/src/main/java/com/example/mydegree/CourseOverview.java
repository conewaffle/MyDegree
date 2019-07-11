package com.example.mydegree;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mydegree.Room.Course;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import static com.example.mydegree.Search.SearchAdapter.COURSE_PARCEL;

public class CourseOverview extends AppCompatActivity {

    private TextView code, name, availability, campus, grad, uoc, desc, prereq;
    private Button courseOut, courseTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_overview);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab3);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add block for drag and drop/home page?", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });

       code = findViewById(R.id.courseCode1);
       name = findViewById(R.id.courseName);
       availability = findViewById(R.id.availability);
       campus = findViewById(R.id.campus);
       grad = findViewById(R.id.type);
       uoc = findViewById(R.id.uoc);
       desc = findViewById(R.id.courseDesc);
       prereq = findViewById(R.id.prereqs);
       courseOut = findViewById(R.id.courseOutline);
       courseTime = findViewById(R.id.courseTimetable);

        Intent i = getIntent();
        final Course myCourse =  i.getParcelableExtra(COURSE_PARCEL);
        code.setText(myCourse.getCourseCode());
        name.setText(myCourse.getCourseName());
        String terms = "";
        if(myCourse.isT1()){
            terms = terms + "T1 ";
        }
        if(myCourse.isT2()){
            terms = terms + "T2";
        }
        if(myCourse.isT3()){
            terms = terms + " T3";
        }
        availability.setText(terms);
        String campus1;
        if(myCourse.getMode().equals("Face-to-face")){
            campus1 = "Kensington";
        } else {campus1 = "Other";}
        campus.setText(campus1);
        String level = "Undergraduate";
        if(myCourse.getLevel()>3){level = "Honours";}
        grad.setText(level);
        uoc.setText(Integer.toString(myCourse.getCourseUoc()));
        desc.setText(myCourse.getCourseDesc());
        prereq.setText("Yet to be linked to prereq data");


        courseOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.business.unsw.edu.au/degrees-courses/course-outlines/"+myCourse.getCourseCode()));
                startActivity(webIntent);
            }
        });

        courseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://timetable.unsw.edu.au/2019/"+myCourse.getCourseCode()+".html"));
                startActivity(webIntent);
            }
        });

    }
}
