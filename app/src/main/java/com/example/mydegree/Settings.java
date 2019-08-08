package com.example.mydegree;

import androidx.core.view.GravityCompat;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mydegree.Room.CourseDb;

import static com.example.mydegree.Progress.Program.ROOM_INITIALISED;

public class Settings extends BaseActivity {

    private Button btnClear;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        inflater.inflate(R.layout.activity_settings, frameLayout, true);

        btnClear = findViewById(R.id.btnSettingClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeleteRoomTask().execute();
            }
        });

        //customise this for each nav menu destination
        navigationView.setCheckedItem(R.id.menusettings);
        setTitle("Settings");

        //Do the rest as you want for each activity
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
        if (id==R.id.menusettings){
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onNavigationItemSelected(item);
        }
        return true;
    }
    @Override
    protected void onStart() {
        super.onStart();
        navigationView.setCheckedItem(navigationView.getMenu().getItem(5));
    }

    //THIS WILL DELETE ALL ROWS FROM THE DB (useful for updating database
    private class DeleteRoomTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog progDialog = new ProgressDialog(Settings.this);

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progDialog.setMessage("Deleting Database...");
            progDialog.setIndeterminate(false);
            progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids){
            CourseDb db = Room
                    .databaseBuilder(Settings.this, CourseDb.class, "coursedb")
                    .build();

            db.clearAllTables();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            SharedPreferences prefs = getSharedPreferences(ROOM_INITIALISED, MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(ROOM_INITIALISED, 0);
            editor.apply();
            progDialog.dismiss();
            Toast.makeText(Settings.this,"Database Cleared", Toast.LENGTH_SHORT).show();
        }

    }
}
