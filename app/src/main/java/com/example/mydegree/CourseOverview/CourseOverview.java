package com.example.mydegree.CourseOverview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mydegree.R;
import com.example.mydegree.Room.Course;
import com.example.mydegree.Room.CourseDb;
import com.example.mydegree.Room.Prereq;
import com.example.mydegree.Room.Program;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import static com.example.mydegree.Search.SearchAdapter.COURSE_PARCEL;

public class CourseOverview extends AppCompatActivity {

    private TextView code, name, availability, campus, grad, uoc, desc, otherreq;
    private Button courseOut, courseTime;
    private ProgressDialog progDialog;
    private RecyclerView recycler;
    private PrereqAdapter mAdapter;
    private boolean hasreqs = false;

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

        recycler = findViewById(R.id.recyclerPrereq);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        progDialog = new ProgressDialog(CourseOverview.this);
        code = findViewById(R.id.courseCode1);
        name = findViewById(R.id.courseName);
        availability = findViewById(R.id.availability);
        campus = findViewById(R.id.campus);
        grad = findViewById(R.id.type);
        uoc = findViewById(R.id.uoc);
        desc = findViewById(R.id.courseDesc);
        otherreq = findViewById(R.id.otherReqText);
        courseOut = findViewById(R.id.courseOutline);
        courseTime = findViewById(R.id.courseTimetable);

        Intent i = getIntent();
        final Course myCourse =  i.getParcelableExtra(COURSE_PARCEL);
        String fromPrereq = i.getStringExtra(PrereqAdapter.PREREQ_PARCEL);

        if (myCourse!=null){
            fillActivityContent(myCourse);
        }

        if (fromPrereq!=null){
            new GetCourseTask().execute(fromPrereq);
        }

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

    private void fillActivityContent(Course myCourse){
        String courseCode = myCourse.getCourseCode();
        code.setText(courseCode);
        name.setText(myCourse.getCourseName());
        setTitle(courseCode + " - " + myCourse.getCourseName());
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
        uoc.setText(Integer.toString(myCourse.getCourseUoc()) + " UOC");
        desc.setText(myCourse.getCourseDesc());
        if(myCourse.getOtherreq()==null){
            otherreq.setVisibility(View.GONE);
        } else {
            otherreq.setText(myCourse.getOtherreq());
            hasreqs = true;
        }
        new GetPrereqTask().execute(courseCode);

    }

    private class GetPrereqTask extends AsyncTask<String, Void, ArrayList<Prereq>> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progDialog.setMessage("Loading Course...");
            progDialog.setIndeterminate(false);
            progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDialog.setCancelable(true);
            progDialog.show();
        }

        @Override
        protected ArrayList<Prereq> doInBackground(String... query) {
            CourseDb db = Room
                    .databaseBuilder(CourseOverview.this, CourseDb.class, "coursedb")
                    .build();

            ArrayList<Prereq> prereqsList = (ArrayList<Prereq>) db.courseDao().getPrereqs(query[0]);
            return prereqsList;
        }

        @Override
        protected void onPostExecute(ArrayList<Prereq> result){
            if(result.size()>0) {
                mAdapter = new PrereqAdapter(result);
                recycler.setAdapter(mAdapter);
            } else {
                recycler.setVisibility(View.GONE);
                if(!hasreqs){
                    otherreq.setVisibility(View.VISIBLE);
                    otherreq.setText("No pre-requisites");
                }
            }

            progDialog.dismiss();
        }
    }

    private class GetCourseTask extends AsyncTask<String, Void, Course> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progDialog.setMessage("Loading Course...");
            progDialog.setIndeterminate(false);
            progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDialog.setCancelable(true);
            progDialog.show();
        }

        @Override
        protected Course doInBackground(String... query) {
            CourseDb db = Room
                    .databaseBuilder(CourseOverview.this, CourseDb.class, "coursedb")
                    .build();

            ArrayList<Course> courseList = (ArrayList<Course>) db.courseDao().getCourseByCode(query[0]);
            return courseList.get(0);
        }

        @Override
        protected void onPostExecute(Course result){
            fillActivityContent(result);
            progDialog.dismiss();
        }
    }

}