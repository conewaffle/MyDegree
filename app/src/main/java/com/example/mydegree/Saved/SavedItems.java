package com.example.mydegree.Saved;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mydegree.BaseActivity;
import com.example.mydegree.R;
import com.example.mydegree.Room.Course;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private String uid;
    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

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
        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            uid = user.getUid();
        } else {
            uid = "4PUZCL42tVhL6wP90ZO2gZqOyhC3";
        }


        text = findViewById(R.id.text);
        rv = findViewById(R.id.recyclerSaved);
        progDialog = new ProgressDialog(SavedItems.this);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        bookmarkList = new ArrayList<>();

        FirebaseApp.initializeApp(this);
        adapter = new SavedItemAdapter(bookmarkList, this);
        rv.setAdapter(adapter);
        registerForContextMenu(rv);

        if(isNetworkAvailable()) {
            new GetSavedCodesTask().execute();
        } else {
            text.setText("Unable to access saved items without network connection.");
        }

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

                Snackbar mySnackbar = Snackbar.make(viewHolder.itemView, "Item removed from your saved items.", Snackbar.LENGTH_LONG);
                mySnackbar.setAction("Undo", new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        addBookmark(temporaryCourse, position);
                    }
                }).show();

            }

            //may need to move addBookmark and removeBookmark to Asyntask in case of slow internet connection
            //if no internet, maybe save onto local database ?


        });

        helper.attachToRecyclerView(rv);

    }


    private void addBookmark(final Course course, final int position){
        DatabaseReference bookmark = FirebaseDatabase.getInstance().getReference();
        bookmark.child("User").child(uid).child("bookmark").child(course.getCourseCode()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().setValue(course.getCourseName());
                Toast.makeText(SavedItems.this, "Bookmark Restored",Toast.LENGTH_SHORT).show();
                bookmarkList.add(position, course);
                adapter.notifyDataSetChanged();
                if (bookmarkList.size() == 0) {
                    text.setText("You have no courses saved.");
                } else {
                    text.setText("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SavedItems.this, "Connection Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removeBookmark(final int position) {
        DatabaseReference bookmark = FirebaseDatabase.getInstance().getReference();
        bookmark.child("User").child(uid).child("bookmark").child(bookmarkList.get(position).getCourseCode()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().removeValue();
                bookmarkList.remove(position);
                adapter.notifyDataSetChanged();
                if (bookmarkList.size() == 0) {
                    text.setText("You have no courses saved.");
                } else {
                    text.setText("");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SavedItems.this, "Connection Error", Toast.LENGTH_SHORT).show();
            }
        });



    }


    //FOR LONGPRESS DELETE BOOKMARK
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_remove:
                final int position = ((SavedItemAdapter) rv.getAdapter()).getPosition();

                removeBookmark(position);

                Toast.makeText(SavedItems.this, "Bookmark removed", Toast.LENGTH_SHORT).show();
        }

        return super.onContextItemSelected(item);
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
            DatabaseReference load = databaseReference.child("User").child(uid).child("bookmark");
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

                    if (bookmarkList.size() > 9) {
                        bookshop();
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

    private void bookshop() {
        databaseReference.child("User").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String sync = "Bookshop";
                dataSnapshot.child("achievements").getRef().child(sync).setValue(sync);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}