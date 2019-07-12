package com.example.mydegree.ProgramDetails;

import com.example.mydegree.CourseOverview;
import com.example.mydegree.R;
import com.example.mydegree.Room.CourseDb;
import com.example.mydegree.Room.Prereq;
import com.example.mydegree.Room.Program;
import com.example.mydegree.Search.SearchAdapter;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.room.Room;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ProgramDetail extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    private ProgressDialog progDialog;

    private Program myProgram;

    private Toolbar toolbar;

    private String progString;

    private Bundle bundle;

    private ProgramOverviewFragment programOverviewFragment;

    public static final String PROG_PARCEL = "progParcel";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_detail);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Will return program name

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        progDialog = new ProgressDialog(ProgramDetail.this);

        //activity code begins here
        Intent i = getIntent();
        progString = i.getStringExtra(SearchAdapter.PROG_CODE);

        String title = progString;
        if (progString.equals("3979")){
            title = progString + " - Information Systems";
        } else if (progString.equals("3584")){
            title = progString + " - Commerce / Information Systems";
        } else {
            title = progString + " - Information Systems (Co-op) (Hons)";
        }
        toolbar.setTitle(title);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_program_detail, container, false);
            return rootView;
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
                    fragment = new ProgramOverviewFragment();
                    break;
                case 1:
                    fragment = new ProgramCoursesFragment();
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