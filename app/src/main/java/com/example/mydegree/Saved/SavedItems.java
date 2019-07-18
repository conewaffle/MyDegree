package com.example.mydegree.Saved;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.mydegree.BaseActivity;
import com.example.mydegree.Bookmark;
import com.example.mydegree.CourseOverview.CourseOverview;
import com.example.mydegree.R;
import com.example.mydegree.Room.Course;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SavedItems extends BaseActivity {

    private RecyclerView rv;
    private SavedItemAdapter adapter;
    private ArrayList<Course> bookmarkList;
    private ProgressDialog progDialog;
    private TextView text;

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
        text = findViewById(R.id.text);
        rv = findViewById(R.id.recyclerSaved);
        progDialog = new ProgressDialog(SavedItems.this);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        bookmarkList = new ArrayList<>();

        FirebaseApp.initializeApp(this);
        adapter = new SavedItemAdapter(bookmarkList, this);
        rv.setAdapter(adapter);

        new GetSavedCodesTask().execute();

        //Swipe to remove bookmark - probably need to add an undo option
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                final Course temporaryCourse = bookmarkList.get(position);
                removeBookmark(position);
                bookmarkList.remove(position);
                adapter.notifyDataSetChanged();
                Snackbar mySnackbar = Snackbar.make(viewHolder.itemView, "Item removed from your saved items.", Snackbar.LENGTH_SHORT);
                mySnackbar.setAction("Undo", new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        addBookmark(temporaryCourse, position);
                    }
                }).show();

                if (bookmarkList.size() == 0) {
                    text.setText("You have no courses saved.");
                }
            }

            private void addBookmark(final Course course, final int position){
                DatabaseReference bookmark = FirebaseDatabase.getInstance().getReference();
                bookmark.child("User").child("4PUZCL42tVhL6wP90ZO2gZqOyhC3").child("bookmark").child(course.getCourseCode()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().setValue(course.getCourseName());
                        Toast.makeText(SavedItems.this, "Bookmark Restored",Toast.LENGTH_SHORT).show();
                        bookmarkList.add(position, course);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }

            private void removeBookmark(int position) {
                DatabaseReference bookmark = FirebaseDatabase.getInstance().getReference();
                bookmark.child("User").child("4PUZCL42tVhL6wP90ZO2gZqOyhC3").child("bookmark").child(bookmarkList.get(position).getCourseCode()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().removeValue();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }

      /*      class AddBkmarkTask extends AsyncTask<Bookmark, Void, Void> {

                ProgressDialog progDialog = new ProgressDialog(SavedItems.this);

                @Override
                protected void onPreExecute(){
                    super.onPreExecute();
                    progDialog.setMessage("Loading Bookmarks...");
                    progDialog.setIndeterminate(false);
                    progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progDialog.show();
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
                        }
                    });

                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    progDialog.dismiss();

                }
            }*/

        });

        helper.attachToRecyclerView(rv);

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

    private class GetSavedCodesTask extends AsyncTask<String, Void, ArrayList<Course>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progDialog.setMessage("Loading...");
            progDialog.setIndeterminate(false);
            progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDialog.setCancelable(true);
            progDialog.show();
        }

        @Override
        protected ArrayList<Course> doInBackground(String... strings) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            DatabaseReference load = databaseReference.child("User").child("4PUZCL42tVhL6wP90ZO2gZqOyhC3").child("bookmark");
            load.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Course bookmark = new Course();
                        bookmark.setCourseCode(ds.getKey());
                        bookmark.setCourseName(String.valueOf(ds.getValue()));
                        bookmarkList.add(bookmark);
                    }
                    adapter.notifyDataSetChanged();

                    if (bookmarkList.size() == 0) {
                        text.setText("You have no courses saved.");
                    }
                    progDialog.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            return bookmarkList;
        }
    }

}