package com.example.mydegree.CourseOverview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Query;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mydegree.Bookmark;
import com.example.mydegree.R;
import com.example.mydegree.Room.Course;
import com.example.mydegree.Room.CourseDb;
import com.example.mydegree.Room.Prereq;
import com.example.mydegree.Room.Program;
import com.example.mydegree.Saved.SavedItemAdapter;
import com.example.mydegree.Settings;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.mydegree.Program.ROOM_INITIALISED;
import static com.example.mydegree.Search.SearchAdapter.COURSE_PARCEL;

public class CourseOverview extends AppCompatActivity {

    private TextView code, name, availability, campus, grad, uoc, desc, otherreq;
    private Button courseOut, courseTime;
    private ImageButton bookmark;
    private ProgressDialog progDialog;
    private RecyclerView recycler;
    private PrereqAdapter mAdapter;
    private boolean hasreqs = false;
    private Bookmark myBookmark;

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

        bookmark = findViewById(R.id.bookmark);
        FirebaseApp.initializeApp(this);


        Intent i = getIntent();
        final Course myCourse =  i.getParcelableExtra(COURSE_PARCEL);
        String fromPrereq = i.getStringExtra(PrereqAdapter.PREREQ_PARCEL);
        String fromBookmark = i.getStringExtra(SavedItemAdapter.SAVED_PARCEL);


        if (myCourse!=null) {
            String myCode = myCourse.getCourseCode();
            String courseName = myCourse.getCourseName();
            final Bookmark bm = new Bookmark(myCode, courseName);
            myBookmark = bm;
            new CheckBkMarkTask().execute(bm);
            fillActivityContent(myCourse);
        }

        final String courseName = (String) name.getText();

        if (fromPrereq!=null) {
            final Bookmark bm = new Bookmark(fromPrereq, courseName);
            myBookmark = bm;
            new CheckBkMarkTask().execute(bm);
            new GetCourseTask().execute(fromPrereq);
        }

        if (fromBookmark!=null) {
            final Bookmark bm = new Bookmark(fromBookmark, courseName);
            myBookmark = bm;
            new CheckBkMarkTask().execute(bm);
            new GetCourseTask().execute(fromBookmark);
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

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookmark.setSelected(!bookmark.isSelected());
                if (bookmark.isSelected()) {
                    new AddBkmarkTask().execute(myBookmark);
                    bookmark.setImageResource(R.drawable.ic_baseline_bookmark_24px);
                }
                else {
                    new RemoveBkmarkTask().execute(myBookmark);
                    bookmark.setImageResource(R.drawable.ic_baseline_bookmark_border_24px);
                }
            }
        });

    }

    private class CheckBkMarkTask extends AsyncTask<Bookmark, Void, Void> {

        ProgressDialog progDialog = new ProgressDialog(CourseOverview.this);

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progDialog.setMessage("Deleting Database...");
            progDialog.setIndeterminate(false);
            progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDialog.show();
        }

        @Override
        protected Void doInBackground(Bookmark... myBookmarks){
            final Bookmark bm = myBookmarks[0];
            final DatabaseReference bookmarks = FirebaseDatabase.getInstance().getReference();
            // Need to change .child("4PUZCL...") to user ID when login is connected
            DatabaseReference check = bookmarks.child("User").child("4PUZCL42tVhL6wP90ZO2gZqOyhC3").child("bookmark");
            check.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(bm.getCourseCode()).exists()) {
                        bookmark.setImageResource(R.drawable.ic_baseline_bookmark_24px);
                        bookmark.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bookmark.setSelected(!bookmark.isSelected());
                                if (bookmark.isSelected()) {
                                    new RemoveBkmarkTask().execute(bm);
                                    bookmark.setImageResource(R.drawable.ic_baseline_bookmark_border_24px);
                                }
                                else {
                                    new AddBkmarkTask().execute(bm);
                                    bookmark.setImageResource(R.drawable.ic_baseline_bookmark_24px);
                                }
                            }
                        });
                    }
                    else {
                        bookmark.setImageResource(R.drawable.ic_baseline_bookmark_border_24px);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progDialog.dismiss();
        }
    }

    private class AddBkmarkTask extends AsyncTask<Bookmark, Void, Void> {

        ProgressDialog progDialog = new ProgressDialog(CourseOverview.this);

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progDialog.setMessage("Loading Bookmarks...");
            progDialog.setIndeterminate(false);
            progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDialog.show();
        }

        @Override
        protected Void doInBackground(Bookmark... myBookmarks){
            final Bookmark bm = myBookmarks[0];
            DatabaseReference bookmark = FirebaseDatabase.getInstance().getReference();
            // Need to change .child("4PUZCL...") to user ID when login is connected
            bookmark.child("User").child("4PUZCL42tVhL6wP90ZO2gZqOyhC3").child("bookmark").child(bm.getCourseCode()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    dataSnapshot.getRef().setValue(bm.getCourseName());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progDialog.dismiss();

        }
    }

    private class RemoveBkmarkTask extends AsyncTask<Bookmark, Void, Void> {

        ProgressDialog progDialog = new ProgressDialog(CourseOverview.this);

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progDialog.setMessage("Loading Bookmarks...");
            progDialog.setIndeterminate(false);
            progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDialog.show();
        }

        @Override
        protected Void doInBackground(Bookmark... myBookmarks){
            final Bookmark bm = myBookmarks[0];
            DatabaseReference bookmark = FirebaseDatabase.getInstance().getReference();
            // Need to change .child("4PUZCL...") to user ID when login is connected
            bookmark.child("User").child("4PUZCL42tVhL6wP90ZO2gZqOyhC3").child("bookmark").child(bm.getCourseCode()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    dataSnapshot.getRef().removeValue();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progDialog.dismiss();

        }
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
