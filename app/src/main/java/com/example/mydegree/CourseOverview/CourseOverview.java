package com.example.mydegree.CourseOverview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.example.mydegree.Saved.SavedItemAdapter;
import com.example.mydegree.Saved.SavedItems;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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
    private String courseName2;

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
        bookmark.setVisibility(View.INVISIBLE);
        FirebaseApp.initializeApp(this);


        Intent i = getIntent();
        final Course myCourse =  i.getParcelableExtra(COURSE_PARCEL);
        String fromPrereq = i.getStringExtra(PrereqAdapter.PREREQ_PARCEL);
        String fromBookmark = i.getStringExtra(SavedItemAdapter.SAVED_PARCEL);


        //check bookmark task has been moved to end of fillactivitycontent to ensure that all course information is retrieved before creating bookmark object
        if (myCourse!=null) {
            fillActivityContent(myCourse);
        }

        if (fromPrereq!=null) {
            new GetCourseTask().execute(fromPrereq);
        }

        if (fromBookmark!=null) {
            new GetCourseTask().execute(fromBookmark);
        }



        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookmark.setSelected(!bookmark.isSelected());
                if (bookmark.isSelected()) {
                    new AddBkmarkTask().execute(myBookmark);
                }
                else {
                    new RemoveBkmarkTask().execute(myBookmark);
                }
            }
        });

    }

    private class CheckBkMarkTask extends AsyncTask<Bookmark, Void, Void> {

        ProgressDialog progDialog = new ProgressDialog(CourseOverview.this);

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
/*            progDialog.setMessage("Deleting Database...");
            progDialog.setIndeterminate(false);
            progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDialog.show();*/
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
                        bookmark.setSelected(true);
                        bookmark.setVisibility(View.VISIBLE);
                    }
                    else {
                        bookmark.setImageResource(R.drawable.ic_baseline_bookmark_border_24px);
                        bookmark.setSelected(false);
                        bookmark.setVisibility(View.VISIBLE);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(CourseOverview.this, "Connection Error", Toast.LENGTH_SHORT).show();
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            //progDialog.dismiss();
        }
    }

    private class AddBkmarkTask extends AsyncTask<Bookmark, Void, Void> {

        ProgressDialog progDialog = new ProgressDialog(CourseOverview.this);

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
/*            progDialog.setMessage("Loading Bookmarks...");
            progDialog.setIndeterminate(false);
            progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDialog.show();*/
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
                    Toast.makeText(CourseOverview.this, "Connection Error", Toast.LENGTH_SHORT).show();
                }


            });

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            bookmark.setImageResource(R.drawable.ic_baseline_bookmark_24px);
            Snackbar mySnackbar = Snackbar.make(bookmark, "Course bookmarked", Snackbar.LENGTH_SHORT);
            mySnackbar.setAction("Go to bookmarks", new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent viewBkmark = new Intent(CourseOverview.this, SavedItems.class);
                    startActivity(viewBkmark);
                }
            }).show();
            //progDialog.dismiss();

        }
    }

    private class RemoveBkmarkTask extends AsyncTask<Bookmark, Void, Void> {

        ProgressDialog progDialog = new ProgressDialog(CourseOverview.this);

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
/*            progDialog.setMessage("Loading Bookmarks...");
            progDialog.setIndeterminate(false);
            progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDialog.show();*/
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
                    Toast.makeText(CourseOverview.this, "Connection Error", Toast.LENGTH_SHORT).show();
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            bookmark.setImageResource(R.drawable.ic_baseline_bookmark_border_24px);
            Snackbar.make(bookmark, "Course removed from bookmarks", Snackbar.LENGTH_SHORT).show();
            //progDialog.dismiss();

        }
    }

    private void fillActivityContent(Course myCourse){
        final String courseCode = myCourse.getCourseCode();
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
        courseOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.business.unsw.edu.au/degrees-courses/course-outlines/"+courseCode));
                startActivity(webIntent);
            }
        });

        courseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://timetable.unsw.edu.au/2019/"+courseCode+".html"));
                startActivity(webIntent);
            }
        });

        if(myCourse.getOtherreq()==null){
            otherreq.setVisibility(View.GONE);
        } else {
            otherreq.setText(myCourse.getOtherreq());
            hasreqs = true;
        }
        new GetPrereqTask().execute(courseCode);

        myBookmark = new Bookmark(myCourse.getCourseCode(), myCourse.getCourseName());

        if(isNetworkAvailable()) {
            new CheckBkMarkTask().execute(myBookmark);
        }

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
/*            progDialog.setMessage("Loading Course...");
            progDialog.setIndeterminate(false);
            progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDialog.setCancelable(true);
            progDialog.show();*/
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
  /*          progDialog.dismiss();*/
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}