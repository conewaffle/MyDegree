package com.example.mydegree.Saved;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import com.example.mydegree.BaseActivity;
import com.example.mydegree.Bookmark;
import com.example.mydegree.R;
import com.example.mydegree.Room.Course;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SavedItems extends BaseActivity {

    private RecyclerView rv;
    private SavedItemAdapter adapter;
    private ArrayList<Course> bookmarkList;

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
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        bookmarkList = new ArrayList<>();
        adapter = new SavedItemAdapter(bookmarkList, this);
        rv.setAdapter(adapter);

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference yourRef = rootRef.child("User").child("4PUZCL42tVhL6wP90ZO2gZqOyhC3").child("bookmark");
        yourRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Course bookmark = new Course();
                    bookmark.setCourseCode(ds.getKey());
                    bookmark.setCourseName(String.valueOf(ds.getValue()));
                    bookmarkList.add(bookmark);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
        if (id==R.id.menusaved){
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onNavigationItemSelected(item);
        }
        return true;
    }

}
