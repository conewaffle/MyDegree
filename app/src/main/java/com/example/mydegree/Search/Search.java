package com.example.mydegree.Search;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.mydegree.BaseActivity;
import com.example.mydegree.R;
import com.example.mydegree.Room.Course;
import com.example.mydegree.Room.CourseDb;
import com.example.mydegree.Room.InsertData;
import com.example.mydegree.Saved.SavedItems;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Search extends BaseActivity {

    private Button prog;
    private Button course;
    private FloatingActionButton fabSearch;
    private RecyclerView recycler;
    private ProgressDialog progDialog;
    private EditText searchText;
    private SearchAdapter mAdapter;
    private ArrayList<Course> defaultList;
    private SearchView searchView;
    private SearchView.OnQueryTextListener queryTextListener;


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
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        //adds programs to top of list
        defaultList = InsertData.getCourses();
        mAdapter = new SearchAdapter(defaultList);
        recycler.setAdapter(mAdapter);

        //region OLDSEARCH METHOD WITH BUTTON AND EDITTEXT
/*        fabSearch = findViewById(R.id.fabSearch);
        searchText = findViewById(R.id.searchEdit);
        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSearch(searchText.getText().toString());
            }
        });

        searchText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction()==KeyEvent.ACTION_DOWN)&&(keyCode==KeyEvent.KEYCODE_ENTER)){
                    doSearch(searchText.getText().toString());
                }
                return true;
            }
        });*/
        //endregion

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);
        if(searchItem!=null){
            searchView = (SearchView) searchItem.getActionView();
        }
        if(searchView !=null){
            searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener(){
                @Override
                public boolean onQueryTextChange(String newText){
                    Log.i("onQueryTextChange", newText);
                    doSearch(newText);
                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query){
                    Log.i("onQueryTextSubmit", query);
                    doSearch(query);
                    hideKeyboard(Search.this);
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.app_bar_search) {
            return false;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
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
           if (result.size()==0){
               //Toast.makeText(Search.this,"No results found.",Toast.LENGTH_SHORT).show();
               //searchText.requestFocus();
               mAdapter = new SearchAdapter(result);
               recycler.setAdapter(mAdapter);
               mAdapter.notifyDataSetChanged();
           } else {
               mAdapter = new SearchAdapter(result);
               recycler.setAdapter(mAdapter);
               mAdapter.notifyDataSetChanged();
               //hideKeyboard(Search.this);
           }
        }
    }

    private void doSearch(String query){
        if(query.isEmpty()){
            //Toast.makeText(Search.this, "Error! There is nothing to search for!", Toast.LENGTH_LONG).show();
            mAdapter = new SearchAdapter(defaultList);
            recycler.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            //searchText.requestFocus();
        } else {
            //percent are used for the LIKE SQL statement
            String query2 = "%" + query + "%";
            new GetSearchTask().execute(query2);
        }
    }

    public static void hideKeyboard(Activity activity){
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if(view==null){
            view =  new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

}
