package com.example.mydegree;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.mydegree.Room.Course;
import com.example.mydegree.Room.CourseDb;
import com.example.mydegree.Room.InsertData;
import com.example.mydegree.Room.Prereq;
import com.example.mydegree.Room.ProgramStream;
import com.example.mydegree.Room.StreamCourse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class Program extends BaseActivity {

    public static final String ROOM_INITIALISED = "coursesInitialised";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        inflater.inflate(R.layout.activity_program, frameLayout, true);

        //customise this for each nav menu destination
        navigationView.setCheckedItem(R.id.menuprogram);
        setTitle("myProgram");

        //FLOATING ACTION BUTTON STUFFS
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab2);
        fab.show();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "This will eventually lead to add plan?", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });

        //Do the rest as you want for each activity

        //THIS WILL check for the database and execute querying it
        SharedPreferences checkDbPrefs = getSharedPreferences(ROOM_INITIALISED, MODE_PRIVATE);
        if (checkDbPrefs.getInt(ROOM_INITIALISED,0)!=1){
            new InsertRoomTask().execute();
        } else {
            // new insert query task
        }
    }

    //THIS METHOD MUST BE ADDED TO ALL NAV MENU DESTINATIONS
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    //THIS METHOD MUST BE ADDED TO ALL NAV MENU DESTINATIONS, CUSTOMISE FOR IF ID=R.ID.MENU____
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.menuprogram){
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onNavigationItemSelected(item);
        }
        return true;
    }



    //THIS WILL POPULATE THE DATABASE ON INITIALISATION
    private class InsertRoomTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog progDialog = new ProgressDialog(Program.this);

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progDialog.setMessage("Initialising...");
            progDialog.setIndeterminate(false);
            progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids){
            CourseDb db = Room
                    .databaseBuilder(Program.this, CourseDb.class, "coursedb")
                    .build();

            ArrayList<Course> courses = InsertData.getCourses();
            for(int i = 0;i<courses.size();i++){
                db.courseDao().insertCourse(courses.get(i));
            }

            ArrayList<com.example.mydegree.Room.Program> programs = InsertData.getPrograms();
            for(int i = 0; i<programs.size();i++){
                db.courseDao().insertProgram(programs.get(i));
            }

            ArrayList<Prereq> prereqs = InsertData.getPrereqs();
            for(int i = 0; i<prereqs.size();i++){
                db.courseDao().insertPrereq(prereqs.get(i));
            }

            ArrayList<ProgramStream> programStreams = InsertData.getProgramStreams();
            for(int i = 0; i<programStreams.size();i++){
                db.courseDao().insertProgramStreams(programStreams.get(i));
            }

            ArrayList<StreamCourse> streamCourses = InsertData.getStreamCourse();
            for(int i = 0; i<streamCourses.size();i++){
                db.courseDao().insertStreamCourses(streamCourses.get(i));
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            SharedPreferences prefs = getSharedPreferences(ROOM_INITIALISED, MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(ROOM_INITIALISED, 1);
            editor.apply();
            progDialog.dismiss();
            //new Query Courses Task . execute
        }

    }
}
