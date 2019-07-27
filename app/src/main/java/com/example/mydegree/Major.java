package com.example.mydegree;

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
import android.widget.Toast;

import com.example.mydegree.ProgramDetails.CourseAdapter;
import com.example.mydegree.Room.Course;
import com.example.mydegree.Room.CourseDb;
import com.example.mydegree.Room.StreamCourse;

import java.util.ArrayList;

import static com.example.mydegree.Search.SearchAdapter.COURSE_PARCEL;
import static com.example.mydegree.Search.SearchAdapter.MAJOR_CODE;

public class Major extends AppCompatActivity {

    private RecyclerView coreCycler, elecCycler;
    private TextView coreText, elecText, majorName, majorCode, majorDesc,majorReq, majorUoc;
    private CourseAdapter coreAdapter, elecAdapter;
    private Button majorLink;
    private ProgressDialog  progDialog;
    private int uoc;
    public static final String MAJOR_PARCEL = "majorParcel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_major);

        progDialog = new ProgressDialog(Major.this);

        majorName = findViewById(R.id.majorName);
        majorCode = findViewById(R.id.majorCode);
        majorUoc = findViewById(R.id.majorUoc);
        majorDesc = findViewById(R.id.majorDesc);
        majorReq = findViewById(R.id.majorReq);
        majorLink = findViewById(R.id.majorWeb);
        coreText = findViewById(R.id.textCore);
        elecText = findViewById(R.id.textElec);

        coreCycler = findViewById(R.id.recyclerMajCore);
        elecCycler = findViewById(R.id.recyclerMajElec);
        coreCycler.setHasFixedSize(true);
        elecCycler.setHasFixedSize(true);
        coreCycler.setLayoutManager(new LinearLayoutManager(Major.this, LinearLayoutManager.HORIZONTAL, false));
        elecCycler.setLayoutManager(new LinearLayoutManager(Major.this, LinearLayoutManager.HORIZONTAL, false));

        elecAdapter = new CourseAdapter(new ArrayList<StreamCourse>());
        coreAdapter = new CourseAdapter(new ArrayList<StreamCourse>());

        Intent i = getIntent();
        final Course myCourse = i.getParcelableExtra(COURSE_PARCEL);
        final String fromMajorStream = i.getStringExtra(MAJOR_PARCEL);
        if(myCourse!=null){
           fillActivityContent(myCourse);
        }
        if(fromMajorStream!=null){
            new GetMajorTask().execute(fromMajorStream);
        }

    }

    private void fillActivityContent(Course course){
        majorName.setText(course.getCourseName());
        majorCode.setText(course.getCourseCode());
        uoc = course.getCourseUoc();
        majorUoc.setText(course.getCourseUoc() + " UOC");
        majorDesc.setText(course.getCourseDesc());
        if (course.getOtherreq()==null){
            majorReq.setVisibility(View.GONE);
        } else {
            majorReq.setText(course.getOtherreq());
        }
        final String webCode = course.getCourseCode();
        majorLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.handbook.unsw.edu.au/undergraduate/specialisations/2019/"+webCode));
                startActivity(webIntent);
            }
        });
        setTitle(course.getCourseCode() + " - " + course.getCourseName());
        new GetMajorCourses().execute(course.getCourseCode());
    }


    private class GetMajorTask extends AsyncTask<String, Void, Course> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected Course doInBackground(String... query) {
            CourseDb db = Room
                    .databaseBuilder(Major.this, CourseDb.class, "coursedb")
                    .build();

            ArrayList<Course> courseList = (ArrayList<Course>) db.courseDao().getCourseByCode(query[0]);
            return courseList.get(0);
        }

        @Override
        protected void onPostExecute(Course result){
            fillActivityContent(result);
        }
    }


    private class GetMajorCourses extends AsyncTask<String, Void, ArrayList<ArrayList<StreamCourse>>> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progDialog.setMessage("Loading Courses...");
            progDialog.setIndeterminate(false);
            progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDialog.setCancelable(true);
            progDialog.show();
        }

        @Override
        protected ArrayList<ArrayList<StreamCourse>> doInBackground(String... query) {
            CourseDb db = Room
                    .databaseBuilder(Major.this, CourseDb.class, "coursedb")
                    .build();

            ArrayList<ArrayList<StreamCourse>> masterList = new ArrayList<>();
            ArrayList<StreamCourse> coreList = (ArrayList<StreamCourse>) db.courseDao().getMajorCores(query[0]);
            ArrayList<StreamCourse> elecList = (ArrayList<StreamCourse>) db.courseDao().getMajorElecs(query[0]);
            masterList.add(coreList);
            masterList.add(elecList);

            return masterList;
        }

        @Override
        protected void onPostExecute(ArrayList<ArrayList<StreamCourse>> result){

            coreAdapter.setCourses(result.get(0));
            coreText.setText("Core Courses (" + result.get(0).size()*6 + " UOC)");
            elecAdapter.setCourses(result.get(1));
            int elecUoc = uoc - result.get(0).size()*6;
            elecText.setText("Core Courses (" + elecUoc + " UOC)");
            coreCycler.setAdapter(coreAdapter);
            elecCycler.setAdapter(elecAdapter);

            progDialog.dismiss();


        }
    }

}
