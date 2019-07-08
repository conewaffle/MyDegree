package com.example.mydegree;

import android.app.ProgressDialog;
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

import java.util.ArrayList;

import static com.example.mydegree.Room.InsertData.getCourses;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences checkDbPrefs = getSharedPreferences(ROOM_INITIALISED, MODE_PRIVATE);
        if (checkDbPrefs.getInt(ROOM_INITIALISED,0)!=1){
            //new insert courses asynctask
        } else {
            // new insert query task
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menusearch) {
            // Handle the search action
        } else if (id == R.id.menuplan) {

        } else if (id == R.id.menuprofile) {

        } else if (id == R.id.menuprogram) {

        } else if (id == R.id.menusaved) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

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

