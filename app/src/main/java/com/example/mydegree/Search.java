package com.example.mydegree;

import androidx.core.view.GravityCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.mydegree.ProgramDetails.ProgramDetail;

public class Search extends BaseActivity {

    private Button prog;
    private Button course;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        inflater.inflate(R.layout.activity_search, frameLayout, true);

        //customise this for each nav menu destination
        navigationView.setCheckedItem(R.id.menusearch);
        setTitle("Search");

        //Do the rest as you want for each activity

        prog = findViewById(R.id.button);
        prog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Search.this, ProgramDetail.class);
                startActivity(intent);
            }
        });

        course = findViewById(R.id.button2);
        course.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Search.this, CourseOverview.class);
                startActivity(intent);
            }
        });
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
        if (id==R.id.menusearch){
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onNavigationItemSelected(item);
        }
        return true;
    }
}
