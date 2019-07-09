package com.example.mydegree;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.mydegree.Room.Course;
import com.example.mydegree.Room.CourseDb;
import com.example.mydegree.Room.InsertData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.mydegree.Room.InsertData.getCourses;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected DrawerLayout drawer;
    public static final String ROOM_INITIALISED = "coursesInitialised";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "This will eventually lead to add plan?", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.menuprogram);

        //THIS WILL check for the database and execute querying it
        SharedPreferences checkDbPrefs = getSharedPreferences(ROOM_INITIALISED, MODE_PRIVATE);
        if (checkDbPrefs.getInt(ROOM_INITIALISED,0)!=1){
            //new insert courses asynctask
        } else {
            // new insert query task
        }

    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
            Intent intent = null;
        if (id == R.id.menusearch) {
            intent = new Intent(this, Search.class);
        } else if (id == R.id.menuplan) {
            Toast.makeText(this, "This has yet to be implemented", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menuprofile) {
            Toast.makeText(this, "This has yet to be implemented", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menuprogram) {

        } else if (id == R.id.menusaved) {
            Toast.makeText(this, "This has yet to be implemented", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menusettings) {
            Toast.makeText(this, "This has yet to be implemented", Toast.LENGTH_SHORT).show();
        }

        if(intent!=null){
            startActivity(intent);
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //THIS WILL POPULATE THE DATABASE ON INITIALISATION
    private class InsertRoomTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog progDialog = new ProgressDialog(MainActivity.this);

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
                    .databaseBuilder(MainActivity.this, CourseDb.class, "coursedb")
                    .build();
            ArrayList<Course> courses = InsertData.getCourses();
            for(int i = 0;i<courses.size();i++){
                db.courseDao().insert(courses.get(i));
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

