package com.example.mydegree.Search;

import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mydegree.BaseActivity;
import com.example.mydegree.R;
import com.example.mydegree.Room.Course;
import com.example.mydegree.Room.CourseDb;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Search extends BaseActivity {

    private Button prog;
    private Button course;
    private FloatingActionButton fabSearch;
    private RecyclerView recycler;
    private ProgressDialog progDialog;
    private EditText searchText;
    private SearchAdapter mAdapter;


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
        progDialog = new ProgressDialog(Search.this);
        recycler = findViewById(R.id.recyclerSearch);
        fabSearch = findViewById(R.id.fabSearch);
        searchText = findViewById(R.id.searchEdit);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        //pressing search button takes EditText text and uses it as a query for database.  It checks to ensure the query exists (otherwise database call fails)
        //consider making it that it searches whenever EditText state change (using listener) so that list updates without having to press search button
        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchText.getText().toString();

                if(query.isEmpty()){
                    Toast.makeText(Search.this, "Error! There is nothing to search for!", Toast.LENGTH_LONG).show();
                } else {
                    //percent are used for the LIKE SQL statement
                    String query2 = "%" + query + "%";
                    new GetSearchTask().execute(query2);
                }
            }
        });


/*        prog = findViewById(R.id.button);
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
        });*/
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

    private class GetSearchTask extends AsyncTask<String, Void, ArrayList<Course>> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progDialog.setMessage("Gathering results...");
            progDialog.setIndeterminate(false);
            progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDialog.setCancelable(true);
            progDialog.show();
        }

        @Override
        protected ArrayList<Course> doInBackground(String... query) {
            CourseDb db = Room
                    .databaseBuilder(Search.this, CourseDb.class, "coursedb")
                    .build();

            ArrayList<Course> courseList = (ArrayList<Course>) db.courseDao().getSearchCourses(query[0]);
            return courseList;
        }

        @Override
        protected void onPostExecute(ArrayList<Course> result){
           mAdapter = new SearchAdapter(result);
           recycler.setAdapter(mAdapter);
           progDialog.dismiss();
        }
    }


}
