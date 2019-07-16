package com.example.mydegree.Saved;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
import android.widget.Toast;

import com.example.mydegree.BaseActivity;
import com.example.mydegree.Bookmark;
import com.example.mydegree.CourseOverview.CourseOverview;
import com.example.mydegree.R;
import com.example.mydegree.Room.Course;
import com.example.mydegree.Room.CourseDb;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SavedItems extends BaseActivity {

    private RecyclerView rv;
    private SavedItemAdapter adapter;
    private ArrayList<Bookmark> bookmarkList;
    private ArrayList<String> myStrings;
    private ProgressDialog progDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        inflater.inflate(R.layout.activity_saved_items, frameLayout, true);

        //customise this for each nav menu destination
        navigationView.setCheckedItem(R.id.menusaved);
        setTitle("Saved Items");

        //Do the rest as you want for each activity

        rv = findViewById(R.id.recyclerSaved);
        progDialog = new ProgressDialog(SavedItems.this);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        bookmarkList = new ArrayList<>();
        myStrings = new ArrayList<>();

        new GetSavedCodesTask().execute();

        //set the adapter to a courseList instead




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
        if (id==R.id.menusaved){
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onNavigationItemSelected(item);
        }
        return true;
    }

    private class GetSavedCodesTask extends AsyncTask<Void, Void, ArrayList<Bookmark>> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progDialog.setMessage("Loading Saved Items...");
            progDialog.setIndeterminate(false);
            progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDialog.setCancelable(true);
            progDialog.show();
        }

        @Override
        protected ArrayList<Bookmark> doInBackground(Void... voids) {
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference yourRef = rootRef.child("User").child("4PUZCL42tVhL6wP90ZO2gZqOyhC3").child("bookmark");
            final ArrayList<Bookmark> myBookmarks = new ArrayList<>();
            yourRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Bookmark bookmark = new Bookmark(ds.getKey());
                        myBookmarks.add(bookmark);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            return myBookmarks;
        }

        @Override
        protected void onPostExecute(ArrayList<Bookmark> result){
            if (result.size()==0){
                Toast.makeText(SavedItems.this, "THERE ARE NO BOOKMARKS", Toast.LENGTH_SHORT).show();
            }
            for(int i = 0; i<result.size();i++){
                myStrings.add(result.get(i).getCourseCode());
            }
            new GetCoursesTask().execute(myStrings);
        }
    }


    private class GetCoursesTask extends AsyncTask<ArrayList<String>, Void, ArrayList<Course>> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Course> doInBackground(ArrayList<String>... query) {
            CourseDb db = Room
                    .databaseBuilder(SavedItems.this, CourseDb.class, "coursedb")
                    .build();

            ArrayList<String> myStrings = query[0];
            ArrayList<Course> courseList = new ArrayList<>();
            for(int i=0; i<myStrings.size();i++){
                courseList.add(db.courseDao().getCourseByCode(myStrings.get(i)).get(0));
            }

            return courseList;
        }

        @Override
        protected void onPostExecute(ArrayList<Course> result){
            adapter = new SavedItemAdapter(result, SavedItems.this);
            rv.setAdapter(adapter);

            progDialog.dismiss();
        }
    }


}
