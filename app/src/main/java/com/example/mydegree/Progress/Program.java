package com.example.mydegree.Progress;

import com.example.mydegree.BaseActivity;
import com.example.mydegree.R;
import com.example.mydegree.Room.Course;
import com.example.mydegree.Room.CourseDb;
import com.example.mydegree.Room.EnrolmentInfo;
import com.example.mydegree.Room.InsertData;
import com.example.mydegree.Room.Prereq;
import com.example.mydegree.Room.Stream;
import com.example.mydegree.Room.StreamCourse;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.room.Insert;
import androidx.room.Room;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.mydegree.Progress.EnrolProgramFragment.RESULT_MAJOR;
import static com.example.mydegree.Progress.EnrolProgramFragment.RESULT_PROG;
import static com.example.mydegree.Progress.ProgressFragment.ENROL_FRAG_TAG;

public class Program extends BaseActivity implements EnrolProgramFragment.EnrolProgramListener, PickEnrolItemFragment.PickEnrolItemListener {

    public static final String ROOM_INITIALISED = "coursesInitialised";

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private ProgressDialog progDialog;
    private Toolbar toolbar;
    private AppBarLayout appbar;
    private String progCode;
    private String majCode;
    private String pickedCourse;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        inflater.inflate(R.layout.activity_program, frameLayout, true);

        navigationView.setCheckedItem(R.id.menuprogram);
        setTitle("myProgram");

        // Removes shadow from BaseActivity AppBar
        appbar = findViewById(R.id.app_bar);
        appbar.setOutlineProvider(null);

        SharedPreferences checkDbPrefs = getSharedPreferences(ROOM_INITIALISED, MODE_PRIVATE);
        if (checkDbPrefs.getInt(ROOM_INITIALISED,0)!=1){
            new InsertRoomTask().execute();
        } else {
            // new insert query task
        }

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        progDialog = new ProgressDialog(Program.this);

/*        i = getIntent();
        progString = i.getStringExtra(SearchAdapter.PROG_CODE);

        String title = progString;
        if (progString.equals("3979")){
            title = progString + " - Information Systems";
        } else if (progString.equals("3584")){
            title = progString + " - Commerce / Information Systems";
        } else {
            title = progString + " - Information Systems (Co-op) (Hons)";
        }
        toolbar.setTitle(title);*/

    }

    @Override
    public void onFinishEnrol(Bundle bundle){
        progCode = bundle.getString(RESULT_PROG);
        if(bundle.getString(RESULT_MAJOR)!=null){
            majCode = bundle.getString(RESULT_MAJOR);
        } else {
            majCode = null;
        }
        final EnrolmentInfo toInsert = new EnrolmentInfo(progCode, majCode);
        new InsertEnrolInfoTask().execute(toInsert);
    }

    public String getProgCode(){
        return progCode;
    }

    @Override
    public void onFinishPickEnrolItem(String string) {
        pickedCourse = string;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

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

    @Override
    protected void onStart() {
        super.onStart();
        navigationView.setCheckedItem(navigationView.getMenu().getItem(1));
    }

    private class InsertEnrolInfoTask extends AsyncTask<EnrolmentInfo, Void, Void>{

        @Override
        protected Void doInBackground(EnrolmentInfo... enrolmentInfos) {
            CourseDb db = Room
                    .databaseBuilder(Program.this, CourseDb.class, "coursedb")
                    .build();

            db.courseDao().insertEnrolmentInfo(enrolmentInfos[0]);

            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
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

            ArrayList<Stream> streams = InsertData.getStreams();
            for(int i = 0; i<streams.size();i++){
                db.courseDao().insertStream(streams.get(i));
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




    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new ProgressFragment();
                    break;
                case 1:
                    fragment = new RoadmapFragment();
/*                    fragment.setArguments(bundle);*/
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}